package hf25_16.debugging_chickens.mental_health_backend.repository;


import hf25_16.debugging_chickens.mental_health_backend.enums.ProfileStatus;
import hf25_16.debugging_chickens.mental_health_backend.model.Listener;
import hf25_16.debugging_chickens.mental_health_backend.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ListenerRepository extends JpaRepository<Listener, Integer> {
    @Query("""
            SELECT l FROM Listener l JOIN l.user u WHERE
            (:status IS NULL OR u.profileStatus = :status)
            """)
    Page<Listener> findListenersWithStatus(
            @Param("status") ProfileStatus status,
            Pageable pageable);

    @Query("""
            SELECT l FROM Listener l JOIN l.user u WHERE
            (:status IS NULL OR u.profileStatus = :status) AND
            (LOWER(u.anonymousName) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<Listener> findListenersWithFilters(
            @Param("status") ProfileStatus status,
            @Param("search") String search,
            Pageable pageable);

    Optional<Listener> findByUser_UserId(Integer userId);

    Optional<Listener> findByUser(User user);

    boolean existsByUser(User user);

    @Modifying
    @Transactional
    @Query("UPDATE Listener l SET l.totalMessagesSent = l.totalMessagesSent + :count WHERE l.user.anonymousName = :anonymousName")
    void incrementMessageCount(@Param("anonymousName") String anonymousName, @Param("count") Integer count);

}
