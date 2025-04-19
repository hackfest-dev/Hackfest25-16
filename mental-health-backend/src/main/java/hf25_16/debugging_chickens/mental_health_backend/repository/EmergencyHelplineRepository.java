package hf25_16.debugging_chickens.mental_health_backend.repository;

import hf25_16.debugging_chickens.mental_health_backend.model.EmergencyHelpline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyHelplineRepository extends JpaRepository<EmergencyHelpline, Integer> {
}