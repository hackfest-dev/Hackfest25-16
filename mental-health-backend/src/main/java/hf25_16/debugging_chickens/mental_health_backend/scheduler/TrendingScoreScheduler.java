package hf25_16.debugging_chickens.mental_health_backend.scheduler;

import hf25_16.debugging_chickens.mental_health_backend.config.TrendingScoreConfig;
import hf25_16.debugging_chickens.mental_health_backend.model.Blog;
import hf25_16.debugging_chickens.mental_health_backend.model.BlogTrendingScore;
import hf25_16.debugging_chickens.mental_health_backend.repository.BlogRepository;
import hf25_16.debugging_chickens.mental_health_backend.repository.BlogTrendingScoreRepository;
import hf25_16.debugging_chickens.mental_health_backend.util.TrendingScoreCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrendingScoreScheduler {
    private final BlogRepository blogRepository;
    private final BlogTrendingScoreRepository trendingScoreRepository;
    private final TrendingScoreConfig properties;
    private final TrendingScoreCalculator calculator;

    @Scheduled(fixedRate = 3600000) // 1 hour
    @Transactional
    public void updateTrendingScores() {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(properties.getDecayHours());
        log.info("Starting trending score update for blogs modified after {}", cutoff);

        try {
            processAllBlogsBatch(cutoff);
            log.info("Successfully completed trending score updates");
        } catch (Exception e) {
            log.error("Error during trending score update", e);
        }
    }

    private void processAllBlogsBatch(LocalDateTime cutoff) {
        Page<Blog> blogs = blogRepository.findRecentlyActiveBlogs(
                cutoff,
                PageRequest.of(0, properties.getBatchSize())
        );

        while (blogs.hasContent()) {
            processBlogBatch(blogs.getContent());

            if (blogs.hasNext()) {
                blogs = blogRepository.findRecentlyActiveBlogs(
                        cutoff,
                        blogs.nextPageable()
                );
            } else {
                break;
            }
        }
    }

    private void processBlogBatch(List<Blog> blogs) {
        List<BlogTrendingScore> scores = blogs.stream()
                .map(calculator::calculateScore)
                .toList();

        for (BlogTrendingScore score : scores) {
            updateOrCreateScore(score);
        }
    }

    private void updateOrCreateScore(BlogTrendingScore score) {
        trendingScoreRepository.findById(score.getBlogId())
                .ifPresentOrElse(
                        existingScore -> {
                            existingScore.setTrendingScore(score.getTrendingScore());
                            existingScore.setViewCount(score.getViewCount());
                            existingScore.setLikeCount(score.getLikeCount());
                            existingScore.setLastCalculated(score.getLastCalculated());
                            trendingScoreRepository.save(existingScore);
                        },
                        () -> trendingScoreRepository.save(score)
                );
    }
}