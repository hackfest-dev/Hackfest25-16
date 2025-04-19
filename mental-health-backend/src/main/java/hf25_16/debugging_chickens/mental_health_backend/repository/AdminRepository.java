package hf25_16.debugging_chickens.mental_health_backend.repository;


import hf25_16.debugging_chickens.mental_health_backend.model.Admin;
import hf25_16.debugging_chickens.mental_health_backend.model.User;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Optional<Admin> findByAdminId(Integer adminId);
    Optional<Admin> findByUser(User user);
    Optional<Admin> findByUser_UserId(Integer userId);
    Optional<Admin> findAdminByUser(User user);
    Optional<Admin> findAdminByUser_UserId(Integer userId);
}
