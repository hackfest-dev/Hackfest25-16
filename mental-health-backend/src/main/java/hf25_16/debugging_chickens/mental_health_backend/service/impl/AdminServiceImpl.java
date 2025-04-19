package hf25_16.debugging_chickens.mental_health_backend.service.impl;

import hf25_16.debugging_chickens.mental_health_backend.dto.Admin.request.AdminProfileRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Admin.response.AdminProfileSummaryResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Admin.response.FullAdminProfileResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.enums.Role;
import hf25_16.debugging_chickens.mental_health_backend.exception.admin.AdminNotFoundException;
import hf25_16.debugging_chickens.mental_health_backend.mapper.AdminMapper;
import hf25_16.debugging_chickens.mental_health_backend.model.Admin;
import hf25_16.debugging_chickens.mental_health_backend.model.User;
import hf25_16.debugging_chickens.mental_health_backend.repository.AdminRepository;
import hf25_16.debugging_chickens.mental_health_backend.repository.UserMetricsRepository;
import hf25_16.debugging_chickens.mental_health_backend.repository.UserRepository;
import hf25_16.debugging_chickens.mental_health_backend.security.jwt.JwtUtils;
import hf25_16.debugging_chickens.mental_health_backend.service.AdminService;
import hf25_16.debugging_chickens.mental_health_backend.service.ImageStorageService;
import hf25_16.debugging_chickens.mental_health_backend.service.UserMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final AdminMapper adminMapper;
    private final ImageStorageService imageStorageService;
    private final JwtUtils jwtUtils;
    private final UserMetricService userMetricService;
    private final UserMetricsRepository userMetricsRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, UserRepository userRepository, AdminMapper adminMapper, ImageStorageService imageStorageService, JwtUtils jwtUtils, UserMetricService userMetricService, UserMetricsRepository userMetricsRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.adminMapper = adminMapper;
        this.imageStorageService = imageStorageService;
        this.jwtUtils = jwtUtils;
        this.userMetricService = userMetricService;
        this.userMetricsRepository = userMetricsRepository;
    }

    @Override
    @Transactional
    public FullAdminProfileResponseDTO createAdminProfile(AdminProfileRequestDTO adminProfileRequestDTO, MultipartFile profilePicture) throws Exception {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new AdminNotFoundException("User is not authorized to create an admin profile");
        }

        Optional<Admin> existingAdmin = adminRepository.findByUser(user);
        if (existingAdmin.isPresent()) {
            throw new AdminNotFoundException("Admin profile already exists");
        }

        // Upload image and get URL asynchronously
        CompletableFuture<String> profilePictureUrlFuture = imageStorageService.uploadImage(profilePicture);
        String profilePictureUrl = profilePictureUrlFuture.get();
        Admin admin = adminMapper.toEntity(adminProfileRequestDTO, user, profilePictureUrl);

        Admin savedAdmin = adminRepository.save(admin);

        return adminMapper.toFullResponseDTO(savedAdmin, userMetricsRepository.findByUser(user).orElseThrow(() -> new AdminNotFoundException("User metrics not found for the given user")));
    }

    @Override
    @Transactional(readOnly = true)
    public FullAdminProfileResponseDTO getAdminProfile(Integer userId, Integer adminId) {
        User user = null;
        Admin admin = null;

        if (userId == null && adminId == null) {
            userId = jwtUtils.getUserIdFromContext();
        }

        if (userId != null) {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            admin = adminRepository.findByUser(user)
                    .orElseThrow(() -> new AdminNotFoundException("Admin profile not found"));
        } else {
            admin = adminRepository.findById(adminId)
                    .orElseThrow(() -> new AdminNotFoundException("Admin profile not found"));
            user = admin.getUser();
        }

        return adminMapper.toFullResponseDTO(admin, userMetricsRepository.findByUser(user).orElseThrow(() -> new AdminNotFoundException("User metrics not found for the given user")));
    }

    @Override
    @Transactional
    public FullAdminProfileResponseDTO updateAdminProfile(AdminProfileRequestDTO adminProfileRequestDTO, MultipartFile profilePicture) throws Exception {
        Integer userId = jwtUtils.getUserIdFromContext();
        Admin admin = adminRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new AdminNotFoundException("Admin profile not found"));

        String profilePictureUrl = admin.getProfilePictureUrl();
        if (profilePicture != null) {
            // Delete current image if exists
            if (profilePictureUrl != null) {
                imageStorageService.deleteImage(profilePictureUrl);
            }
            // Upload new image asynchronously
            CompletableFuture<String> profilePictureUrlFuture = imageStorageService.uploadImage(profilePicture);
            profilePictureUrl = profilePictureUrlFuture.get();
        }

        admin.setAdminNotes(adminProfileRequestDTO.getAdminNotes());
        admin.setQualifications(adminProfileRequestDTO.getQualifications());
        admin.setFullName(adminProfileRequestDTO.getFullName());
        admin.setContactNumber(adminProfileRequestDTO.getContactNumber());
        admin.setEmail(adminProfileRequestDTO.getEmail());
        admin.setProfilePictureUrl(profilePictureUrl);
        Admin savedAdmin = adminRepository.save(admin);

        return adminMapper.toFullResponseDTO(savedAdmin, userMetricsRepository.findByUser(admin.getUser()).orElseThrow(() -> new AdminNotFoundException("User metrics not found for the given user")));
    }

    @Override
    @Transactional
    public String deleteAdminProfile(Integer adminId) {
        Admin admin = adminRepository.findByAdminId(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin profile not found"));

        adminRepository.delete(admin);
        return "Admin profile deleted successfully";
    }

    @Override
    public List<AdminProfileSummaryResponseDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(adminMapper::toSummaryResponseDTO).toList();
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : principal.toString();
    }
}