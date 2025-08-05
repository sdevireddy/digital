package com.zen.digital.batch;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.zen.digital.entity.Campaign;
import com.zen.digital.entity.CampaignUser;
import com.zen.digital.entity.CampaignUser.EmailStatus;
import com.zen.digital.repository.CampaignRepository;

@Component
public class EmailCampaignProcessor implements ItemProcessor<CampaignUser, CampaignUser> {

    private static final Logger logger = LoggerFactory.getLogger(EmailCampaignProcessor.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CampaignRepository campaignRepository;

    @Override
    public CampaignUser process(CampaignUser campaignUser) throws Exception {
        logger.info("Processing email for user: {} (Email: {})", campaignUser.getFullName(), campaignUser.getEmailId());
        
        try {
            // Get campaign details
            Campaign campaign = campaignUser.getCampaign();
            if (campaign == null) {
                logger.error("Campaign not found for user: {}", campaignUser.getEmailId());
                campaignUser.setEmailStatus(EmailStatus.FAILED);
                campaignUser.setLastErrorMessage("Campaign not found");
                return campaignUser;
            }

            logger.info("Processing email for campaign: {} (ID: {})", campaign.getName(), campaign.getId());

            // Check if user is still valid for email
            if (!campaignUser.isEmailValid()) {
                logger.warn("User not valid for email sending: {}", campaignUser.getEmailId());
                campaignUser.setEmailStatus(EmailStatus.FAILED);
                campaignUser.setLastErrorMessage("User not valid for email sending");
                return campaignUser;
            }

            // Send email
            boolean emailSent = sendEmail(campaignUser, campaign);
            
            if (emailSent) {
                logger.info("Successfully sent email to: {}", campaignUser.getEmailId());
                campaignUser.setEmailStatus(EmailStatus.SENT);
                campaignUser.setEmailSentAt(LocalDateTime.now());
            } else {
                logger.error("Failed to send email to: {}", campaignUser.getEmailId());
                campaignUser.setEmailStatus(EmailStatus.FAILED);
                campaignUser.setLastErrorMessage("Failed to send email");
            }

        } catch (Exception e) {
            logger.error("Error processing email for user: {}", campaignUser.getEmailId(), e);
            campaignUser.setEmailStatus(EmailStatus.FAILED);
            campaignUser.setLastErrorMessage(e.getMessage());
        }

        return campaignUser;
    }

    private boolean sendEmail(CampaignUser campaignUser, Campaign campaign) {
        try {
            logger.info("Sending email to: {} from: {}", campaignUser.getEmailId(), campaign.getSenderEmail());
            
            SimpleMailMessage message = new SimpleMailMessage();
            
            // Set sender
            message.setFrom(campaign.getSenderEmail());
            message.setReplyTo(campaign.getReplyToEmail());
            
            // Set recipient
            message.setTo(campaignUser.getEmailId());
            
            // Set subject
            message.setSubject(campaign.getEmailSubject());
            
            // Set content (use text content if available, otherwise use HTML content)
            String content = campaign.getTextContent();
            if (content == null || content.trim().isEmpty()) {
                content = stripHtmlTags(campaign.getHtmlContent());
            }
            message.setText(content);
            
            logger.debug("Email content: {}", content);
            
            // Send email
            mailSender.send(message);
            logger.info("Email sent successfully to: {}", campaignUser.getEmailId());
            return true;
            
        } catch (Exception e) {
            logger.error("Failed to send email to: {}", campaignUser.getEmailId(), e);
            return false;
        }
    }

    private String stripHtmlTags(String htmlContent) {
        if (htmlContent == null) {
            return "";
        }
        // Simple HTML tag removal - in production, use a proper HTML parser
        return htmlContent.replaceAll("<[^>]*>", "").replaceAll("&nbsp;", " ").trim();
    }
} 