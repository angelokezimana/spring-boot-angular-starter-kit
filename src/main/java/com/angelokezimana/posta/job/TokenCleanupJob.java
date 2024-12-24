package com.angelokezimana.posta.job;

import com.angelokezimana.posta.repository.security.TokenRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TokenCleanupJob {
    private final TokenRepository tokenRepository;
    private static final Logger log = LoggerFactory.getLogger(TokenCleanupJob.class);

    public TokenCleanupJob(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    @Transactional
    public void ScheduleDeleteExpiredTokens() {
        deleteExpiredTokens();
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void executeStartupTask() {
        deleteExpiredTokens();
    }

    private void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        log.info("==============The tokens that have expired and are scheduled for deletion are: {}==============",
                tokenRepository.countByExpiresAtBefore(now));
        tokenRepository.deleteByExpiresAtBefore(now);
        log.info("==============Expired tokens deleted at: {}==============", now);
    }
}
