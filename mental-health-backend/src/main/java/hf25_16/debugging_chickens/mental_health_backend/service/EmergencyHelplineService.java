package hf25_16.debugging_chickens.mental_health_backend.service;


import hf25_16.debugging_chickens.mental_health_backend.dto.EmergencyHelpline.EmergencyHelplineDTO;

import java.util.List;

public interface EmergencyHelplineService {
    List<EmergencyHelplineDTO> getAllEmergencyHelplines();
    EmergencyHelplineDTO addEmergencyHelpline(EmergencyHelplineDTO emergencyHelplineDTO);
    EmergencyHelplineDTO updateEmergencyHelpline(Integer helplineId, EmergencyHelplineDTO emergencyHelplineDTO);
    void deleteEmergencyHelpline(Integer helplineId);
}