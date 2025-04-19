package hf25_16.debugging_chickens.mental_health_backend.repository;


import hf25_16.debugging_chickens.mental_health_backend.enums.ProfileStatus;
import hf25_16.debugging_chickens.mental_health_backend.enums.Role;
import hf25_16.debugging_chickens.mental_health_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByAnonymousName(String anonymousName);
    User findByEmail(String email);
    List<User> findByIsActive(Boolean isActive);
    boolean existsByEmail(String email);
    boolean existsByAnonymousName(String anonymousName);
    List<User> findByRole(Role role);
    @Query("SELECT u FROM User u WHERE " +
            "u.role = 'USER' AND " +
            "(:status IS NULL OR u.profileStatus = :status) AND " +
            "(:search IS NULL OR " +
            "LOWER(CAST(u.anonymousName AS string)) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<User> findUsersWithFilters(
            @Param("status") ProfileStatus status,
            @Param("search") String search,
            Pageable pageable);
    @Query("SELECT u FROM User u WHERE " +
            "u.role = 'USER' AND " +
            "(:status IS NULL OR u.profileStatus = :status)")
    Page<User> findUsersWithFilters(
            @Param("status") ProfileStatus status,
            Pageable pageable);
}
