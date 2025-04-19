package hf25_16.debugging_chickens.mental_health_backend.mapper;

import hf25_16.debugging_chickens.mental_health_backend.dto.EmergencyHelpline.EmergencyHelplineDTO;
import hf25_16.debugging_chickens.mental_health_backend.model.Admin;
import hf25_16.debugging_chickens.mental_health_backend.model.EmergencyHelpline;
import hf25_16.debugging_chickens.mental_health_backend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmergencyHelplineMapper {

    @Autowired
    private AdminRepository adminRepository;

    public EmergencyHelplineDTO toDTO(EmergencyHelpline emergencyHelpline) {
        EmergencyHelplineDTO dto = new EmergencyHelplineDTO();
        dto.setHelplineId(emergencyHelpline.getHelplineId());
        dto.setName(emergencyHelpline.getName());
        dto.setPhoneNumber(emergencyHelpline.getPhoneNumber());
        dto.setCountryCode(emergencyHelpline.getCountryCode());
        dto.setEmergencyType(emergencyHelpline.getEmergencyType());
        dto.setPriority(emergencyHelpline.getPriority());
        dto.setAdminId(emergencyHelpline.getAdmin().getAdminId());
        return dto;
    }

    public EmergencyHelpline toEntity(EmergencyHelplineDTO dto, Admin admin) {
        EmergencyHelpline emergencyHelpline = new EmergencyHelpline();
        emergencyHelpline.setHelplineId(dto.getHelplineId());
        emergencyHelpline.setName(dto.getName());
        emergencyHelpline.setPhoneNumber(dto.getPhoneNumber());
        emergencyHelpline.setCountryCode(dto.getCountryCode());
        emergencyHelpline.setEmergencyType(dto.getEmergencyType());
        emergencyHelpline.setPriority(dto.getPriority());
        emergencyHelpline.setAdmin(admin);
        return emergencyHelpline;
    }
}