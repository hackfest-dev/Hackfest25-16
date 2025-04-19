package hf25_16.debugging_chickens.mental_health_backend.controller;

import hf25_16.debugging_chickens.mental_health_backend.dto.session.response.SimpleSessionDTO;
import hf25_16.debugging_chickens.mental_health_backend.enums.SessionCategory;
import hf25_16.debugging_chickens.mental_health_backend.model.SessionStatus;
import hf25_16.debugging_chickens.mental_health_backend.repository.SessionStatusRepository;
import hf25_16.debugging_chickens.mental_health_backend.urlMapper.SessionStatusUrlMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SessionCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(SessionCategoryController.class);

    @Autowired
    private SessionStatusRepository sessionStatusRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(SessionStatusUrlMapping.FILTER_SESSIONS)
    public ResponseEntity<List<SimpleSessionDTO>> getSessions(
            @RequestParam(required = false) SessionCategory sessionCategory) {

        logger.info("Entered getSessions endpoint");
        logger.debug("Received sessionCategory: {}", sessionCategory);

        List<SessionStatus> sessionStatuses;

        try {
            if (sessionCategory != null) {
                logger.info("Filtering sessions by category: {}", sessionCategory);
                sessionStatuses = sessionStatusRepository.findByCategory(SessionCategory.valueOf(sessionCategory.name()));
            } else {
                logger.info("No category provided, retrieving all sessions");
                sessionStatuses = sessionStatusRepository.findAll();
            }

            logger.debug("Number of session statuses retrieved: {}", sessionStatuses.size());

            List<SimpleSessionDTO> sessionDTOs = sessionStatuses.stream()
                    .map(sessionStatus -> {
                        logger.trace("Mapping SessionStatus to DTO: sessionId={}, category={}, summary={}",
                                sessionStatus.getSession().getSessionId(),
                                sessionStatus.getCategory(),
                                sessionStatus.getSummary());
                        return new SimpleSessionDTO(
                                sessionStatus.getSession().getSessionId(),
                                sessionStatus.getCategory(),
                                sessionStatus.getSummary()
                        );
                    })
                    .collect(Collectors.toList());

            logger.info("Returning {} session DTOs", sessionDTOs.size());
            return ResponseEntity.ok(sessionDTOs);

        } catch (Exception e) {
            logger.error("Error occurred while fetching sessions", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
