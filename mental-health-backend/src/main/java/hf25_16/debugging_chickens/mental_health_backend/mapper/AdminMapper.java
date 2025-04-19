package hf25_16.debugging_chickens.mental_health_backend.mapper;

import hf25_16.debugging_chickens.mental_health_backend.dto.Admin.request.AdminProfileRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Admin.response.AdminProfileResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Admin.response.AdminProfileSummaryResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Admin.response.FullAdminProfileResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.model.Admin;
import hf25_16.debugging_chickens.mental_health_backend.model.User;
import hf25_16.debugging_chickens.mental_health_backend.model.UserMetrics;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public Admin toEntity(AdminProfileRequestDTO adminProfileRequestDTO, User user, String profilePictureUrl) {
        Admin admin = new Admin();
        admin.setUser(user);
        admin.setAdminNotes(adminProfileRequestDTO.getAdminNotes());
        admin.setQualifications(adminProfileRequestDTO.getQualifications());
        admin.setContactNumber(adminProfileRequestDTO.getContactNumber());
        admin.setProfilePictureUrl(profilePictureUrl); // Set profile picture URL
        admin.setEmail(adminProfileRequestDTO.getEmail());
        admin.setFullName(adminProfileRequestDTO.getFullName());
        return admin;
    }

    public AdminProfileResponseDTO toResponseDTO(Admin admin) {
        AdminProfileResponseDTO responseDTO = new AdminProfileResponseDTO();
        responseDTO.setAdminId(admin.getAdminId());
        responseDTO.setUserId(admin.getUser().getUserId());
        responseDTO.setAdminNotes(admin.getAdminNotes());
        responseDTO.setQualifications(admin.getQualifications());
        responseDTO.setContactNumber(admin.getContactNumber());
        responseDTO.setEmail(admin.getEmail());
        responseDTO.setProfilePictureUrl(admin.getProfilePictureUrl()); // Set profile picture URL
        responseDTO.setCreatedAt(admin.getCreatedAt());
        responseDTO.setFullName(admin.getFullName());
        responseDTO.setUpdatedAt(admin.getUpdatedAt());
        return responseDTO;
    }

    public FullAdminProfileResponseDTO toFullResponseDTO(Admin admin, UserMetrics userMetrics) {
        FullAdminProfileResponseDTO responseDTO = new FullAdminProfileResponseDTO();
        responseDTO.setAdminId(admin.getAdminId());
        responseDTO.setUserId(admin.getUser().getUserId());
        responseDTO.setAdminNotes(admin.getAdminNotes());
        responseDTO.setQualifications(admin.getQualifications());
        responseDTO.setContactNumber(admin.getContactNumber());
        responseDTO.setEmail(admin.getEmail());
        responseDTO.setProfilePictureUrl(admin.getProfilePictureUrl()); // Set profile picture URL
        responseDTO.setCreatedAt(admin.getCreatedAt());
        responseDTO.setFullName(admin.getFullName());
        responseDTO.setUpdatedAt(admin.getUpdatedAt());
        responseDTO.setTotalAppointments(userMetrics.getTotalAppointments());
        responseDTO.setLastAppointmentDate(userMetrics.getLastAppointmentDate());
        responseDTO.setTotalBlogsPublished(userMetrics.getTotalBlogsPublished());
        responseDTO.setTotalLikesReceived(userMetrics.getTotalLikesReceived());
        responseDTO.setTotalViewsReceived(userMetrics.getTotalViewsReceived());
        return responseDTO;
    }

    public AdminProfileSummaryResponseDTO toSummaryResponseDTO(Admin admin) {
        AdminProfileSummaryResponseDTO summaryDTO = new AdminProfileSummaryResponseDTO();
        summaryDTO.setAdminId(admin.getAdminId());
        summaryDTO.setFullName(admin.getFullName());
        summaryDTO.setAdminNotes(admin.getAdminNotes());
        summaryDTO.setContactNumber(admin.getContactNumber());
        return summaryDTO;
    }
}