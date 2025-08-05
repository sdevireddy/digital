package com.zen.digital.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zen.digital.entity.CampaignUser;
import com.zen.digital.repository.CampaignUserRepository;

@Component
public class EmailCampaignWriter implements ItemWriter<CampaignUser> {

    private static final Logger logger = LoggerFactory.getLogger(EmailCampaignWriter.class);

    @Autowired
    private CampaignUserRepository campaignUserRepository;

    @Override
    public void write(Chunk<? extends CampaignUser> items) throws Exception {
        logger.info("Writing {} users to database", items.size());
        
        for (CampaignUser user : items) {
            try {
                // Update the user's email status and timestamps
                campaignUserRepository.save(user);
                logger.debug("Updated user: {} (Email: {}, Status: {})", 
                    user.getFullName(), user.getEmailId(), user.getEmailStatus());
            } catch (Exception e) {
                logger.error("Failed to update user: {} (Email: {})", user.getFullName(), user.getEmailId(), e);
                // Continue processing other users even if one fails
            }
        }
        
        logger.info("Successfully wrote {} users to database", items.size());
    }
} 