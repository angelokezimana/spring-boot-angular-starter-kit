package com.angelokezimana.starter.common.job;

import com.angelokezimana.starter.auth.repository.ActivationTokenRepository;
import com.angelokezimana.starter.auth.repository.PasswordResetTokenRepository;
import com.angelokezimana.starter.auth.repository.BlacklistedTokenRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class TokenCleanupJob {
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final ActivationTokenRepository activationTokenRepository;

    private static final Logger log = LoggerFactory.getLogger(TokenCleanupJob.class);

    public TokenCleanupJob(BlacklistedTokenRepository blacklistedTokenRepository,
                           PasswordResetTokenRepository passwordResetTokenRepository,
                           ActivationTokenRepository activationTokenRepository) {
        this.blacklistedTokenRepository = blacklistedTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.activationTokenRepository = activationTokenRepository;
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
        Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        log.info("==============The blacklisted tokens that have expired and are scheduled for deletion are: {}==============",
                blacklistedTokenRepository.countByExpiresAtBefore(now));
        log.info("==============The password reset tokens that have expired and are scheduled for deletion are: {}==============",
                passwordResetTokenRepository.countByExpiryDateBefore(nowDate));
        log.info("==============The activation tokens that have expired and are scheduled for deletion are: {}==============",
                activationTokenRepository.countByExpiresAtBefore(now));

        blacklistedTokenRepository.deleteByExpiresAtBefore(now);
        passwordResetTokenRepository.deleteByExpiryDateBefore(nowDate);
        activationTokenRepository.deleteByExpiresAtBefore(now);

        log.info("==============Expired blacklisted tokens deleted at: {}==============", now);
        log.info("==============Expired Password reset tokens deleted at: {}==============", nowDate);
        log.info("==============Expired activation tokens deleted at: {}==============", now);
    }
}
