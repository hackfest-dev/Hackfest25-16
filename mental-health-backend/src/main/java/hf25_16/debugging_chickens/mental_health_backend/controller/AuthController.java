package hf25_16.debugging_chickens.mental_health_backend.controller;

import hf25_16.debugging_chickens.mental_health_backend.dto.user.request.ResetPasswordRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.user.request.UserLoginRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.user.request.UserRegistrationRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.user.request.VerifyEmailRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.user.response.ResetPasswordResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.user.response.UserLoginResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.user.response.UserRegistrationResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.user.response.VerifyEmailResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.exception.token.MissingRequestCookieException;
import hf25_16.debugging_chickens.mental_health_backend.service.UserService;
import hf25_16.debugging_chickens.mental_health_backend.service.impl.RefreshTokenServiceImpl;
import hf25_16.debugging_chickens.mental_health_backend.urlMapper.UserUrlMapping;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
public class AuthController {
    private final String baseUrl;
    private final UserService userService;
    private final RefreshTokenServiceImpl refreshTokenService;

    public AuthController(UserService userService,
                          RefreshTokenServiceImpl refreshTokenService,
                          @Value("${spring.app.base-url}") String baseUrl) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.baseUrl = baseUrl;
    }

    @PostMapping(UserUrlMapping.USER_REGISTER)
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserRegistrationResponseDTO> registerUser(@RequestBody UserRegistrationRequestDTO userRegistrationDTO) {
        UserRegistrationResponseDTO userDTO = userService.registerUser(userRegistrationDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping(UserUrlMapping.USER_LOGIN)
    public ResponseEntity<?> authenticateUser(
            @RequestBody UserLoginRequestDTO loginRequest,
            HttpServletResponse response) {
        log.debug("Processing login request for email: {}", loginRequest.getEmail());
        Map<String, Object> loginResponse = userService.loginUser(loginRequest);
        String accessToken = (String) loginResponse.get("accessToken");
        String refreshToken = (String) loginResponse.get("refreshToken");
        UserLoginResponseDTO userDTO = (UserLoginResponseDTO) loginResponse.get("user");

        if (accessToken == null || refreshToken == null || userDTO == null) {
            log.error("Login response missing required fields");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Authentication failed due to internal error");
        }
        refreshTokenService.setSecureRefreshTokenCookie(response, refreshToken);
        String bearerToken = "Bearer " + accessToken;

        log.info("User successfully authenticated: {}", userDTO.getEmail());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .body(userDTO);
    }

    @PostMapping(UserUrlMapping.USER_LOGOUT)
    public ResponseEntity<String> logoutUser(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            HttpServletResponse response) {

        log.info("Logout request received");

        if (refreshToken != null && !refreshToken.isEmpty()) {
            String email = refreshTokenService.getEmailFromRefreshToken(refreshToken);
            userService.setUserActiveStatus(email, false);
            refreshTokenService.deleteRefreshToken(refreshToken);
            log.info("Refresh token deleted for user: {}", email);
        }

        SecurityContextHolder.clearContext();
        userService.clearCookies(response, baseUrl);
        return ResponseEntity.ok("User logged out successfully.");
    }

    @PostMapping(UserUrlMapping.VERIFY_EMAIL)
    @PreAuthorize("permitAll()")
    public ResponseEntity<VerifyEmailResponseDTO> sendVerificationEmail(@RequestBody VerifyEmailRequestDTO verifyEmailRequestDTO) {
        userService.sendVerificationEmail(verifyEmailRequestDTO.getEmail());
        return ResponseEntity.ok(new VerifyEmailResponseDTO("Verification email sent successfully."));
    }

    @PostMapping(UserUrlMapping.RESEND_VERIFICATION_EMAIL)
    @PreAuthorize("permitAll()")
    public ResponseEntity<VerifyEmailResponseDTO> resendVerificationEmail(@RequestBody VerifyEmailRequestDTO verifyEmailRequestDTO) {
        userService.resendVerificationEmail(verifyEmailRequestDTO.getEmail());
        return ResponseEntity.ok(new VerifyEmailResponseDTO("Verification email sent successfully."));
    }

    @PostMapping(UserUrlMapping.FORGOT_PASSWORD)
    @PreAuthorize("permitAll()")
    public ResponseEntity<VerifyEmailResponseDTO> forgotPassword(@RequestBody VerifyEmailRequestDTO verifyEmailRequestDTO) {
        userService.forgotPassword(verifyEmailRequestDTO.getEmail());
        return ResponseEntity.ok(new VerifyEmailResponseDTO("Password reset email sent successfully."));
    }

    @PostMapping(UserUrlMapping.RESET_PASSWORD)
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResetPasswordResponseDTO> resetPassword(@RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {
        userService.resetPassword(resetPasswordRequestDTO.getToken(), resetPasswordRequestDTO.getNewPassword());
        return ResponseEntity.ok(new ResetPasswordResponseDTO("Password has been reset successfully."));
    }

    @PostMapping(UserUrlMapping.RENEW_TOKEN)
    public ResponseEntity<?> renewToken(@CookieValue("refreshToken") String refreshToken,
                                        HttpServletResponse response) {
        if (refreshToken == null) {
            throw new MissingRequestCookieException("Required cookie 'refreshToken' is not present");
        }

        Map<String, Object> renewResponse = refreshTokenService.renewToken(refreshToken);

        String newAccessToken = (String) renewResponse.get("accessToken");
        String newRefreshToken = (String) renewResponse.get("refreshToken");
        UserLoginResponseDTO responseDTO = (UserLoginResponseDTO) renewResponse.get("user");

        // Set new refresh token as HttpOnly cookie
        refreshTokenService.setSecureRefreshTokenCookie(response, newRefreshToken);
        String bearerToken = "Bearer " + newAccessToken;

        // Return new access token in Authorization header and user data in body
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .body(responseDTO);
    }
}