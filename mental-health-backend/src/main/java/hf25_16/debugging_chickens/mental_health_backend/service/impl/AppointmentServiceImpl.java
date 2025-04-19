package hf25_16.debugging_chickens.mental_health_backend.service.impl;

import hf25_16.debugging_chickens.mental_health_backend.dto.Appointment.request.AppointmentRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Appointment.response.AppointmentResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Appointment.response.AppointmentSummaryResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.enums.AppointmentStatus;
import hf25_16.debugging_chickens.mental_health_backend.enums.AppointmentTimeFilter;
import hf25_16.debugging_chickens.mental_health_backend.exception.admin.AdminNotFoundException;
import hf25_16.debugging_chickens.mental_health_backend.exception.appointment.AppointmentNotFoundException;
import hf25_16.debugging_chickens.mental_health_backend.exception.appointment.InvalidAppointmentStatusException;
import hf25_16.debugging_chickens.mental_health_backend.exception.appointment.PendingAppointmentException;
import hf25_16.debugging_chickens.mental_health_backend.exception.timeslot.TimeSlotNotFoundException;
import hf25_16.debugging_chickens.mental_health_backend.exception.token.UnauthorizedException;
import hf25_16.debugging_chickens.mental_health_backend.exception.user.UserNotFoundException;
import hf25_16.debugging_chickens.mental_health_backend.mapper.AppointmentMapper;
import hf25_16.debugging_chickens.mental_health_backend.model.Admin;
import hf25_16.debugging_chickens.mental_health_backend.model.Appointment;
import hf25_16.debugging_chickens.mental_health_backend.model.TimeSlot;
import hf25_16.debugging_chickens.mental_health_backend.model.User;
import hf25_16.debugging_chickens.mental_health_backend.repository.AdminRepository;
import hf25_16.debugging_chickens.mental_health_backend.repository.AppointmentRepository;
import hf25_16.debugging_chickens.mental_health_backend.repository.TimeSlotRepository;
import hf25_16.debugging_chickens.mental_health_backend.repository.UserRepository;
import hf25_16.debugging_chickens.mental_health_backend.security.jwt.JwtUtils;
import hf25_16.debugging_chickens.mental_health_backend.service.AppointmentService;
import hf25_16.debugging_chickens.mental_health_backend.service.EmailService;
import hf25_16.debugging_chickens.mental_health_backend.service.UserMetricService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    private final AppointmentRepository appointmentRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final JwtUtils jwtUtils;
    private final UserMetricService userMetricsService;
    private final EmailService emailService;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  JwtUtils jwtUtils,
                                  TimeSlotRepository timeSlotRepository,
                                  UserRepository userRepository,
                                  AdminRepository adminRepository,
                                  UserMetricService userMetricsService,
                                  EmailService emailService) {
        this.appointmentRepository = appointmentRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.jwtUtils = jwtUtils;
        this.userMetricsService = userMetricsService;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        logger.info("Creating appointment for user with ID: {}", jwtUtils.getUserIdFromContext());

        Integer userId = jwtUtils.getUserIdFromContext();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Admin admin = adminRepository.findById(appointmentRequestDTO.getAdminId())
                .orElseThrow(() -> new AdminNotFoundException("Admin not found"));

        if (appointmentRepository.existsByUserAndAdminAndStatus(user, admin, AppointmentStatus.REQUESTED)) {
            logger.warn("User with ID: {} has a pending appointment", userId);
            throw new PendingAppointmentException("You have a pending appointment. Please wait for it to be processed.");
        }

        TimeSlot timeSlot = timeSlotRepository.findById(appointmentRequestDTO.getTimeSlotId())
                .orElseThrow(() -> new TimeSlotNotFoundException("Time slot not found"));

        Appointment appointment = AppointmentMapper.toEntity(appointmentRequestDTO);
        appointment.setUser(user);
        appointment.setAdmin(admin);
        timeSlot.setIsAvailable(false);
        timeSlotRepository.save(timeSlot);
        appointment.setTimeSlot(timeSlot);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        LocalDateTime appointmentTime = timeSlot.getDate().atTime(timeSlot.getStartTime());
        emailService.sendAppointmentRequestedEmail(
                user.getEmail(),
                admin.getEmail(),
                appointmentTime
        );

        logger.info("Appointment created with ID: {}", savedAppointment.getAppointmentId());
        return AppointmentMapper.toDTO(savedAppointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSummaryResponseDTO> getAppointmentsByUser(Integer userId) {
        Integer currentUserId = jwtUtils.getUserIdFromContext();
        boolean isAdmin = jwtUtils.isAdminFromContext();
        logger.info("Fetching appointments for user ID: {}", userId != null ? userId : currentUserId);

        if (userId != null && !isAdmin && !currentUserId.equals(userId)) {
            throw new UnauthorizedException("You can only view your own appointments.");
        }

        List<Appointment> appointments = appointmentRepository.findByUser_UserId(isAdmin ? userId : currentUserId);
        return appointments.stream()
                .map(AppointmentMapper::toSummaryDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponseDTO getAppointmentById(Integer appointmentId) {
        Integer currentUserId = jwtUtils.getUserIdFromContext();
        boolean isAdmin = jwtUtils.isAdminFromContext();
        logger.info("Fetching appointment with ID: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

        if (!isAdmin && !currentUserId.equals(appointment.getUser().getUserId())) {
            throw new UnauthorizedException("You can only view your own appointments.");
        }

        return AppointmentMapper.toDTO(appointment);
    }

    @Override
    @Transactional
    public void updateAppointmentStatus(Integer appointmentId, String status, String cancellationReason) {
        logger.info("Updating status of appointment ID: {} to {}", appointmentId, status);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

        LocalDateTime appointmentTime = appointment.getTimeSlot().getDate()
                .atTime(appointment.getTimeSlot().getStartTime());

        AppointmentStatus newStatus = parseAndValidateStatus(status);
        TimeSlot timeSlot = appointment.getTimeSlot();

        switch (newStatus) {
            case CONFIRMED -> handleConfirmedStatus(appointment, timeSlot, appointmentTime);
            case CANCELLED -> handleCancelledStatus(appointment, timeSlot, appointmentTime, cancellationReason);
            default -> throw new InvalidAppointmentStatusException("Invalid appointment status: " + status);
        }

        appointmentRepository.save(appointment);
        logger.info("Updated status of appointment ID: {} to {}", appointmentId, status);
    }

    private AppointmentStatus parseAndValidateStatus(String status) {
        try {
            return AppointmentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidAppointmentStatusException("Invalid appointment status: " + status);
        }
    }

    private void handleConfirmedStatus(Appointment appointment, TimeSlot timeSlot, LocalDateTime appointmentTime) {
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        timeSlot.setIsAvailable(false);
        timeSlotRepository.save(timeSlot);

        User userAdmin = appointment.getAdmin().getUser();
        updateUserMetrics(userAdmin, appointment.getUser(), appointmentTime, 1);

        emailService.sendAppointmentConfirmedEmail(
                appointment.getUser().getEmail(),
                appointmentTime
        );
    }

    private void handleCancelledStatus(Appointment appointment, TimeSlot timeSlot,
                                       LocalDateTime appointmentTime, String cancellationReason) {
        timeSlot.setIsAvailable(true);
        timeSlotRepository.save(timeSlot);

        if (appointment.getStatus() == AppointmentStatus.CONFIRMED) {
            User userAdmin = appointment.getAdmin().getUser();
            updateUserMetrics(userAdmin, appointment.getUser(), null, -1);
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setCancellationReason(cancellationReason);

        emailService.sendAppointmentCancelledEmail(
                appointment.getUser().getEmail(),
                appointmentTime,
                cancellationReason
        );
    }

    private void updateUserMetrics(User admin, User user, LocalDateTime appointmentTime, int delta) {
        if (appointmentTime != null) {
            userMetricsService.setLastAppointmentDate(admin, appointmentTime);
            userMetricsService.setLastAppointmentDate(user, appointmentTime);
        }
        userMetricsService.updateAppointmentCount(admin, delta);
        userMetricsService.updateAppointmentCount(user, delta);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentSummaryResponseDTO> getAppointmentsByDateRange(
            LocalDate startDate, LocalDate endDate, Pageable pageable) {
        logger.info("Fetching appointments between dates: {} and {}", startDate, endDate);

        Integer userId = jwtUtils.getUserIdFromContext();
        Admin admin = adminRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found"));

        Page<Appointment> appointments = appointmentRepository.findByTimeSlot_DateBetween(
                admin, startDate, endDate, pageable);
        return appointments.map(AppointmentMapper::toSummaryDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentSummaryResponseDTO> getAppointmentsForAdmin(
            AppointmentTimeFilter timeFilter,
            AppointmentStatus status,
            Pageable pageable,
            Integer userId,
            Integer adminId) {

        Admin admin = resolveAdmin(userId, adminId);
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        Page<Appointment> appointments = (timeFilter == AppointmentTimeFilter.UPCOMING) ?
                appointmentRepository.findUpcomingAppointments(admin, today, now, status, pageable) :
                appointmentRepository.findPastAppointments(admin, today, now, status, pageable);

        logger.info("Fetching {} appointments for admin ID: {} with status: {}",
                timeFilter, admin.getAdminId(), status);

        return appointments.map(AppointmentMapper::toSummaryDTO);
    }

    private Admin resolveAdmin(Integer userId, Integer adminId) {
        if (userId == null && adminId == null) {
            userId = jwtUtils.getUserIdFromContext();
        }

        return (adminId == null) ?
                adminRepository.findByUser_UserId(userId)
                        .orElseThrow(() -> new AdminNotFoundException("Admin not found")) :
                adminRepository.findById(adminId)
                        .orElseThrow(() -> new AdminNotFoundException("Admin not found"));
    }
}