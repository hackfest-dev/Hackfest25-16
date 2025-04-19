package hf25_16.debugging_chickens.mental_health_backend.service;


import hf25_16.debugging_chickens.mental_health_backend.dto.user.request.UserLoginRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.user.request.UserRegistrationRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.user.request.UserUpdateRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.user.response.FullUserDetailsDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.user.response.UserDataResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.user.response.UserInfoResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.user.response.UserRegistrationResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.model.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;

public interface UserService {
    void clearCookies(HttpServletResponse response, String baseUrl);
    Map<String, Object> loginUser(UserLoginRequestDTO userLoginDTO);
    UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO userRegistrationDTO);
    void setUserActiveStatus(String email, boolean isActive);
    void deleteUserById(Integer userId);
    FullUserDetailsDTO getFullUserDetailsById(Integer userId);
    UserInfoResponseDTO getUserInfoById(Integer userId);
    void updateUserBasedOnRole(Integer userId, UserUpdateRequestDTO userUpdateDTO, Authentication authentication);
    void changePasswordById(Integer userId, String oldPassword, String newPassword);
    void sendVerificationEmail(String email);
    void verifyUser(String verificationCode);
    void resendVerificationEmail(String email);
    void forgotPassword(String email);
    void resetPassword(String token, String newPassword);
    boolean isAdmin(Integer userId);
    UserDetails loadUserByUsername(String email);
    void updateUserActivity(String email);
    void suspendOrUnSuspendUser(Integer userId, String action);
    UserDataResponseDTO getUserData(Integer userId);
    void sendDataRequestVerificationEmail(String email);
    void verifyDataRequestCode(String verificationCode);
    Page<User> getUsersByFilters(String status, String searchTerm, Pageable pageable);
}