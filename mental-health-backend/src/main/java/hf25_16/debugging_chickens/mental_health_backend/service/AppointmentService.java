package hf25_16.debugging_chickens.mental_health_backend.service;

import hf25_16.debugging_chickens.mental_health_backend.dto.Appointment.request.AppointmentRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Appointment.response.AppointmentResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Appointment.response.AppointmentSummaryResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.enums.AppointmentStatus;
import hf25_16.debugging_chickens.mental_health_backend.enums.AppointmentTimeFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    AppointmentResponseDTO createAppointment(AppointmentRequestDTO appointmentRequestDTO);
    List<AppointmentSummaryResponseDTO> getAppointmentsByUser(Integer userId);
    AppointmentResponseDTO getAppointmentById(Integer appointmentId);
    Page<AppointmentSummaryResponseDTO> getAppointmentsByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);
    void updateAppointmentStatus(Integer appointmentId, String status, String cancellationReason);
    Page<AppointmentSummaryResponseDTO> getAppointmentsForAdmin(AppointmentTimeFilter timeFilter, AppointmentStatus status, Pageable pageable, Integer userId, Integer adminId);
}