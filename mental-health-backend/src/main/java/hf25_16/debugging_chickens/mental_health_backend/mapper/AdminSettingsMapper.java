package hf25_16.debugging_chickens.mental_health_backend.mapper;

import hf25_16.debugging_chickens.mental_health_backend.dto.adminSettings.request.AdminSettingsRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.adminSettings.response.AdminSettingsResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.model.AdminSettings;
import org.springframework.stereotype.Component;

@Component
public class AdminSettingsMapper {

    public AdminSettingsResponseDTO toResponseDTO(AdminSettings adminSettings) {
        if (adminSettings == null) {
            return null;
        }

        AdminSettingsResponseDTO responseDTO = new AdminSettingsResponseDTO();
        responseDTO.setSettingId(adminSettings.getSettingId());
        responseDTO.setAdminId(adminSettings.getAdmin().getAdminId());
        responseDTO.setIsCounsellor(adminSettings.getIsCounsellor());
        responseDTO.setMaxAppointmentsPerDay(adminSettings.getMaxAppointmentsPerDay());
        responseDTO.setDefaultTimeSlotDuration(adminSettings.getDefaultTimeSlotDuration());
        responseDTO.setCreatedAt(adminSettings.getCreatedAt());
        responseDTO.setUpdatedAt(adminSettings.getUpdatedAt());

        return responseDTO;
    }

    public AdminSettings toEntity(AdminSettingsRequestDTO adminSettingsRequestDTO) {
        if (adminSettingsRequestDTO == null) {
            return null;
        }

        AdminSettings adminSettings = new AdminSettings();
        adminSettings.setIsCounsellor(adminSettingsRequestDTO.getIsCounsellor());
        adminSettings.setMaxAppointmentsPerDay(adminSettingsRequestDTO.getMaxAppointmentsPerDay());
        adminSettings.setDefaultTimeSlotDuration(adminSettingsRequestDTO.getDefaultTimeSlotDuration());

        return adminSettings;
    }
}