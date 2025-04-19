package hf25_16.debugging_chickens.mental_health_backend.scheduler;

import hf25_16.debugging_chickens.mental_health_backend.service.SessionAnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SessionAnalysisScheduler {

    private final SessionAnalysisService sessionAnalysisService;

    @Scheduled(fixedRate = 60000)
    public void scheduleUnanalyzedSessions() {
        log.info("Scheduled task: processing unanalyzed sessions");
        sessionAnalysisService.processUnanalyzedSessions();
    }
}