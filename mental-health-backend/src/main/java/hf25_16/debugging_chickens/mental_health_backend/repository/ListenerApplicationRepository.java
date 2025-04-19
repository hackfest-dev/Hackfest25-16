package hf25_16.debugging_chickens.mental_health_backend.repository;


import hf25_16.debugging_chickens.mental_health_backend.enums.ListenerApplicationStatus;
import hf25_16.debugging_chickens.mental_health_backend.model.ListenerApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListenerApplicationRepository extends JpaRepository<ListenerApplication, Integer> {
    boolean existsByUserEmail(String userEmail);
    Page<ListenerApplication> findByApplicationStatus(ListenerApplicationStatus status, Pageable pageable);
    ListenerApplication findByUser_UserId(Integer listenerId);
}