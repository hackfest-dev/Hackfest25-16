package hf25_16.debugging_chickens.mental_health_backend.service.impl;

import hf25_16.debugging_chickens.mental_health_backend.dto.EmergencyHelpline.EmergencyHelplineDTO;
import hf25_16.debugging_chickens.mental_health_backend.exception.admin.AdminNotFoundException;
import hf25_16.debugging_chickens.mental_health_backend.exception.emergency.EmergencyHelplineNotFoundException;
import hf25_16.debugging_chickens.mental_health_backend.mapper.EmergencyHelplineMapper;
import hf25_16.debugging_chickens.mental_health_backend.model.Admin;
import hf25_16.debugging_chickens.mental_health_backend.model.EmergencyHelpline;
import hf25_16.debugging_chickens.mental_health_backend.repository.AdminRepository;
import hf25_16.debugging_chickens.mental_health_backend.repository.EmergencyHelplineRepository;
import hf25_16.debugging_chickens.mental_health_backend.security.jwt.JwtUtils;
import hf25_16.debugging_chickens.mental_health_backend.service.EmergencyHelplineService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergencyHelplineServiceImpl implements EmergencyHelplineService {

    private final EmergencyHelplineRepository emergencyHelplineRepository;
    private final AdminRepository adminRepository;
    private final EmergencyHelplineMapper emergencyHelplineMapper;
    private final JwtUtils jwtUtils;

    @Autowired
    public EmergencyHelplineServiceImpl(EmergencyHelplineRepository emergencyHelplineRepository, AdminRepository adminRepository, EmergencyHelplineMapper emergencyHelplineMapper, @Lazy JwtUtils jwtUtils) {
        this.emergencyHelplineRepository = emergencyHelplineRepository;
        this.adminRepository = adminRepository;
        this.emergencyHelplineMapper = emergencyHelplineMapper;
        this.jwtUtils = jwtUtils;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmergencyHelplineDTO> getAllEmergencyHelplines() {
        return emergencyHelplineRepository.findAll().stream()
                .map(emergencyHelplineMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public EmergencyHelplineDTO addEmergencyHelpline(EmergencyHelplineDTO emergencyHelplineDTO) {
        Integer userId = jwtUtils.getUserIdFromContext();
        Admin admin = adminRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with ID: " + userId));
        EmergencyHelpline emergencyHelpline = emergencyHelplineMapper.toEntity(emergencyHelplineDTO,admin);
        emergencyHelpline.setAdmin(admin);
        return emergencyHelplineMapper.toDTO(emergencyHelplineRepository.save(emergencyHelpline));
    }

    @Override
    @Transactional
    public EmergencyHelplineDTO updateEmergencyHelpline(Integer helplineId, EmergencyHelplineDTO emergencyHelplineDTO) {
        Integer userId = jwtUtils.getUserIdFromContext();
        Admin admin = adminRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with ID: " + userId));
        EmergencyHelpline existingHelpline = emergencyHelplineRepository.findById(helplineId)
                .orElseThrow(() -> new EmergencyHelplineNotFoundException("Emergency Helpline not found with ID: " + helplineId));
        existingHelpline.setName(emergencyHelplineDTO.getName());
        existingHelpline.setPhoneNumber(emergencyHelplineDTO.getPhoneNumber());
        existingHelpline.setCountryCode(emergencyHelplineDTO.getCountryCode());
        existingHelpline.setEmergencyType(emergencyHelplineDTO.getEmergencyType());
        existingHelpline.setPriority(emergencyHelplineDTO.getPriority());
        existingHelpline.setAdmin(admin);
        return emergencyHelplineMapper.toDTO(emergencyHelplineRepository.save(existingHelpline));
    }

    @Override
    @Transactional
    public void deleteEmergencyHelpline(Integer helplineId) {
        emergencyHelplineRepository.deleteById(helplineId);
    }
}