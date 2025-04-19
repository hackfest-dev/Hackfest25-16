package hf25_16.debugging_chickens.mental_health_backend.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import hf25_16.debugging_chickens.mental_health_backend.dto.Admin.response.AdminProfileResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Admin.response.AdminProfileSummaryResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Appointment.response.AppointmentResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Appointment.response.AppointmentSummaryResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.EmergencyHelpline.EmergencyHelplineDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Listener.response.ListenerDetailsResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.SessionReport.response.SessionReportResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.SessionReport.response.SessionReportSummaryResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.UserActivity.UserActivityDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.adminSettings.response.AdminSettingsResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.chatMessage.ChatMessageDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.listenerApplication.response.ListenerApplicationResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.listenerApplication.response.ListenerApplicationSummaryResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.session.response.SessionResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.sessionFeedback.response.SessionFeedbackResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.sessionFeedback.response.SessionFeedbackSummaryResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.model.Session;
import hf25_16.debugging_chickens.mental_health_backend.service.UserActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
@EnableCaching(proxyTargetClass = true)
public class CacheConfig {
    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);
    private static final int LARGE_CACHE_SIZE = 1000;
    private static final int MEDIUM_CACHE_SIZE = 500;
    private static final int STANDARD_CACHE_SIZE = 100;
    private static final int SMALL_CACHE_SIZE = 50;
    private static final int TINY_CACHE_SIZE = 10;
    private static final int USER_INACTIVITY_MINUTES = 30;

    @Value("${cache.duration.minutes}")
    private Long cacheExpiry;

    private final UserActivityService userActivityService;

    @Autowired
    public CacheConfig(@Lazy UserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }

    // Create base Caffeine builder with common configurations
    private Caffeine<Object, Object> createBaseBuilder() {
        return Caffeine.newBuilder()
                .recordStats()
                .removalListener((key, value, cause) ->
                        logger.info("Key {} was removed ({})", key, cause));
    }

    // Create standard cache builder
    private Caffeine<Object, Object> createStandardBuilder() {
        return createBaseBuilder()
                .expireAfterAccess(cacheExpiry, TimeUnit.MINUTES);
    }

    // Create list cache builder
    private Caffeine<Object, Object> createListBuilder() {
        return createBaseBuilder()
                .expireAfterWrite(cacheExpiry, TimeUnit.MINUTES);
    }

    private Caffeine<Object, Object> createUserDetailsCacheBuilder() {
        return Caffeine.newBuilder()
                .recordStats()
                .expireAfterWrite(USER_INACTIVITY_MINUTES, TimeUnit.MINUTES)
                .removalListener((key, value, cause) -> {
                    if (cause.wasEvicted() && key instanceof String email) {
                        logger.info("User {} marked inactive due to cache eviction", email);
                        userActivityService.markUserInactive(email);
                        userActivityService.broadcastUpdates();
                    }
                });
    }

    // Create cache builder for last seen with custom removal listener
    private Caffeine<Object, Object> createLastSeenCacheBuilder() {
        return Caffeine.newBuilder()
                .recordStats()
                .expireAfterWrite(USER_INACTIVITY_MINUTES, TimeUnit.MINUTES)
                .removalListener((key, value, cause) -> {
                    if (cause.wasEvicted() && key instanceof String email) {
                        logger.info("User {} marked inactive due to cache eviction", email);
                        userActivityService.markUserInactive(email);
                        userActivityService.broadcastUpdates();
                    }
                });
    }


    @Bean
    public Cache<Integer, AdminSettingsResponseDTO> adminSettingsCache() {
        return createStandardBuilder()
                .maximumSize(STANDARD_CACHE_SIZE)
                .build();
    }



    @Bean
    public Cache<Integer, Session> ongoingSessionsCache() {
        return createBaseBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .maximumSize(STANDARD_CACHE_SIZE)
                .build();
    }


    // User related caches
    @Bean
    public Cache<String, UserActivityDTO> userDetailsCache() {
        return createUserDetailsCacheBuilder()
                .maximumSize(MEDIUM_CACHE_SIZE)
                .build();
    }

    @Bean
    public Cache<String, LocalDateTime> lastSeenCache() {
        return createLastSeenCacheBuilder()
                .maximumSize(LARGE_CACHE_SIZE)
                .build();
    }

    @Bean
    public Cache<String, List<UserActivityDTO>> roleBasedDetailsCache() {
        return createBaseBuilder()
                .expireAfterWrite(USER_INACTIVITY_MINUTES, TimeUnit.MINUTES)
                .maximumSize(MEDIUM_CACHE_SIZE)
                .build();
    }

    @Bean
    public Cache<Integer, Integer> currentlyInSessionCache() {
        return createBaseBuilder()
                .expireAfterWrite(USER_INACTIVITY_MINUTES, TimeUnit.MINUTES)
                .maximumSize(LARGE_CACHE_SIZE)
                .build();
    }
    @Bean
    public Cache<String, LocalDateTime> blogViewCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)  // 10 minute cooldown
                .maximumSize(10000)  // Adjust based on your needs
                .recordStats()
                .build();
    }


}