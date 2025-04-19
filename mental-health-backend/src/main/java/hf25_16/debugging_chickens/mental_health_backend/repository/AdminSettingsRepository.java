package hf25_16.debugging_chickens.mental_health_backend.repository;

import hf25_16.debugging_chickens.mental_health_backend.model.AdminSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminSettingsRepository extends JpaRepository<AdminSettings, Integer> {
    boolean existsByAdmin_AdminId(Integer adminId);
    AdminSettings findByAdmin_AdminId(Integer adminId);
}
