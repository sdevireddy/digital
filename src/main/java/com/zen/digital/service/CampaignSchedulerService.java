package com.zen.digital.service;

import java.time.LocalDateTime;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.zen.digital.entity.Campaign;
import com.zen.digital.entity.Campaign.CampaignStatus;

@Service
public class CampaignSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(CampaignSchedulerService.class);

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job emailCampaignJob;

    @Autowired
    private JavaMailSender mailSender;

    @PostConstruct
    public void init() {
        logger.info("CampaignSchedulerService initialized successfully!");
        logger.info("Scheduled tasks will run every 5 minutes for campaigns, 10 minutes for email batches");
    }

    // Run every 5 minutes to check for scheduled campaigns
    @Scheduled(fixedRate = 300000)
    public void executeScheduledCampaigns() {
        logger.info("=== EXECUTING SCHEDULED CAMPAIGNS ===");
        try {
            // Get campaigns ready to execute
            java.util.List<Campaign> readyCampaigns = campaignService.getReadyToExecuteCampaigns();
            logger.info("Found {} campaigns ready to execute", readyCampaigns.size());
            
            for (Campaign campaign : readyCampaigns) {
                try {
                    logger.info("Starting campaign: {} (ID: {})", campaign.getName(), campaign.getId());
                    // Start the campaign
                    Campaign startedCampaign = campaignService.startCampaign(campaign.getId());
                    
                    // Trigger batch job for email processing
                    triggerEmailCampaignJob(startedCampaign.getId());
                    logger.info("Successfully started campaign: {} (ID: {})", campaign.getName(), campaign.getId());
                    
                } catch (Exception e) {
                    logger.error("Failed to start campaign: {} (ID: {})", campaign.getName(), campaign.getId(), e);
                    campaignService.failCampaign(campaign.getId(), e.getMessage());
                }
            }
            
        } catch (Exception e) {
            logger.error("Error in scheduled campaign execution", e);
        }
        logger.info("=== SCHEDULED CAMPAIGNS EXECUTION COMPLETED ===");
    }

    // Run every 10 minutes to process email batches
    @Scheduled(fixedRate = 600000)
    public void processEmailBatches() {
        logger.info("=== PROCESSING EMAIL BATCHES ===");
        try {
            // Get running campaigns
            java.util.List<Campaign> runningCampaigns = campaignService.getCampaignsByStatus(CampaignStatus.RUNNING);
            logger.info("Found {} running campaigns", runningCampaigns.size());
            
            for (Campaign campaign : runningCampaigns) {
                try {
                    logger.info("Processing email batch for campaign: {} (ID: {})", campaign.getName(), campaign.getId());
                    // Trigger batch job for this campaign
                    triggerEmailCampaignJob(campaign.getId());
                    logger.info("Successfully triggered email batch for campaign: {} (ID: {})", campaign.getName(), campaign.getId());
                    
                } catch (Exception e) {
                    logger.error("Failed to process email batch for campaign: {} (ID: {})", campaign.getName(), campaign.getId(), e);
                }
            }
            
        } catch (Exception e) {
            logger.error("Error in email batch processing", e);
        }
        logger.info("=== EMAIL BATCH PROCESSING COMPLETED ===");
    }

    // Run every hour to retry failed emails
    @Scheduled(fixedRate = 3600000)
    public void retryFailedEmails() {
        logger.info("=== RETRYING FAILED EMAILS ===");
        try {
            // Get campaigns with failed emails
            java.util.List<Campaign> campaigns = campaignService.getCampaignsByStatus(CampaignStatus.RUNNING);
            logger.info("Found {} running campaigns for retry", campaigns.size());
            
            for (Campaign campaign : campaigns) {
                try {
                    logger.info("Retrying failed emails for campaign: {} (ID: {})", campaign.getName(), campaign.getId());
                    // Retry failed emails for this campaign
                    campaignService.retryFailedEmails(campaign.getId(), campaign.getMaxRetries());
                    logger.info("Successfully retried failed emails for campaign: {} (ID: {})", campaign.getName(), campaign.getId());
                    
                } catch (Exception e) {
                    logger.error("Failed to retry emails for campaign: {} (ID: {})", campaign.getName(), campaign.getId(), e);
                }
            }
            
        } catch (Exception e) {
            logger.error("Error in failed email retry", e);
        }
        logger.info("=== FAILED EMAIL RETRY COMPLETED ===");
    }

    // Run daily at 2 AM to clean up old campaigns
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupOldCampaigns() {
        logger.info("=== CLEANING UP OLD CAMPAIGNS ===");
        try {
            // Get campaigns older than 30 days that are completed or failed
            LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
            java.util.List<Campaign> oldCampaigns = campaignService.getCampaignsCreatedAfter(thirtyDaysAgo);
            logger.info("Found {} old campaigns", oldCampaigns.size());
            
            int cleanedCount = 0;
            for (Campaign campaign : oldCampaigns) {
                if (campaign.getStatus() == CampaignStatus.COMPLETED || 
                    campaign.getStatus() == CampaignStatus.FAILED ||
                    campaign.getStatus() == CampaignStatus.CANCELLED) {
                    
                    try {
                        campaignService.deleteCampaign(campaign.getId());
                        cleanedCount++;
                        logger.info("Cleaned up campaign: {} (ID: {})", campaign.getName(), campaign.getId());
                    } catch (Exception e) {
                        logger.error("Failed to clean up campaign: {} (ID: {})", campaign.getName(), campaign.getId(), e);
                    }
                }
            }
            logger.info("Cleaned up {} old campaigns", cleanedCount);
            
        } catch (Exception e) {
            logger.error("Error in campaign cleanup", e);
        }
        logger.info("=== OLD CAMPAIGN CLEANUP COMPLETED ===");
    }

    private void triggerEmailCampaignJob(Long campaignId) {
        try {
            logger.info("Triggering email campaign job for campaign ID: {}", campaignId);
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("campaignId", campaignId)
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();
            
            jobLauncher.run(emailCampaignJob, jobParameters);
            logger.info("Successfully triggered email campaign job for campaign ID: {}", campaignId);
            
        } catch (JobExecutionAlreadyRunningException e) {
            logger.warn("Email campaign job already running for campaign ID: {}", campaignId);
        } catch (JobRestartException e) {
            logger.error("Failed to restart email campaign job for campaign ID: {}", campaignId, e);
        } catch (JobInstanceAlreadyCompleteException e) {
            logger.warn("Email campaign job already completed for campaign ID: {}", campaignId);
        } catch (JobParametersInvalidException e) {
            logger.error("Invalid job parameters for email campaign job, campaign ID: {}", campaignId, e);
        } catch (Exception e) {
            logger.error("Failed to trigger email campaign job for campaign ID: {}", campaignId, e);
        }
    }

    // Manual trigger for immediate execution
    public void triggerCampaignExecution(Long campaignId) {
        try {
            logger.info("Manually triggering campaign execution for campaign ID: {}", campaignId);
            Campaign campaign = campaignService.getCampaignById(campaignId);
            
            if (campaign.getStatus() == CampaignStatus.SCHEDULED) {
                campaignService.startCampaign(campaignId);
                logger.info("Started scheduled campaign: {} (ID: {})", campaign.getName(), campaignId);
            }
            
            triggerEmailCampaignJob(campaignId);
            logger.info("Successfully triggered campaign execution for campaign ID: {}", campaignId);
            
        } catch (Exception e) {
            logger.error("Failed to trigger campaign execution for campaign ID: {}", campaignId, e);
            throw new RuntimeException("Failed to trigger campaign execution", e);
        }
    }

    // Test email configuration
    public void testEmailConfiguration() {
        try {
            logger.info("Testing email configuration...");
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("sendtosankar@gmail.com");
            message.setTo("jp7903424@gmail.com");
            message.setSubject("Test Email");
            message.setText("This is a test email to verify SMTP configuration.");
            
            mailSender.send(message);
            logger.info("Email configuration test successful");
            
        } catch (Exception e) {
            logger.error("Email configuration test failed", e);
            throw new RuntimeException("Email configuration test failed", e);
        }
    }
} 