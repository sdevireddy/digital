package com.zen.digital.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zen.digital.entity.CampaignUser;
import com.zen.digital.repository.CampaignUserRepository;

@Component
public class EmailCampaignReader implements ItemReader<CampaignUser> {

    private static final Logger logger = LoggerFactory.getLogger(EmailCampaignReader.class);

    @Autowired
    private CampaignUserRepository campaignUserRepository;

    private java.util.Iterator<CampaignUser> userIterator;
    private Long currentCampaignId;

    @Override
    public CampaignUser read() throws Exception {
        if (userIterator == null) {
            // Load all pending users for email processing
            java.util.List<CampaignUser> pendingUsers = campaignUserRepository.findUsersWithPendingEmails();
            logger.info("Found {} users with pending emails", pendingUsers.size());
            
            if (pendingUsers.isEmpty()) {
                logger.warn("No users with pending emails found");
                return null;
            }
            
            userIterator = pendingUsers.iterator();
            logger.info("Initialized user iterator with {} users", pendingUsers.size());
        }

        if (userIterator.hasNext()) {
            CampaignUser user = userIterator.next();
            logger.debug("Reading user: {} (Email: {})", user.getFullName(), user.getEmailId());
            return user;
        }

        logger.info("No more users to process");
        return null; // End of data
    }
} 