package com.zen.digital.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zen.digital.dto.CampaignRequest;
import com.zen.digital.entity.Campaign;
import com.zen.digital.entity.Campaign.CampaignStatus;
import com.zen.digital.entity.Campaign.CampaignType;
import com.zen.digital.entity.CampaignUser;
import com.zen.digital.entity.CampaignUser.EmailStatus;
import com.zen.digital.entity.CampaignUser.SubscriptionStatus;
import com.zen.digital.repository.CampaignRepository;
import com.zen.digital.repository.CampaignUserRepository;

@Service
@Transactional
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CampaignUserRepository campaignUserRepository;

    @Override
    public Campaign createCampaign(Campaign campaign) {
        // Validate campaign
        validateCampaign(campaign);
        
        // Check if campaign name already exists for the tenant
        if (isCampaignNameExists(campaign.getName(), campaign.getTenantId())) {
            throw new RuntimeException("Campaign with name '" + campaign.getName() + "' already exists for this tenant");
        }
        
        // Set default values
        if (campaign.getStatus() == null) {
            campaign.setStatus(CampaignStatus.DRAFT);
        }
        if (campaign.getIsActive() == null) {
            campaign.setIsActive(true);
        }
        
        Campaign savedCampaign = campaignRepository.save(campaign);
        return savedCampaign;
    }

    @Override
    public Campaign getCampaignById(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Campaign not found with id: " + id));
    }

    @Override
    public Campaign updateCampaign(Long id, Campaign campaign) {
        Campaign existingCampaign = getCampaignById(id);
        
        // Validate campaign
        validateCampaign(campaign);
        
        // Check if name is being changed and if it conflicts
        if (!existingCampaign.getName().equals(campaign.getName()) && 
            isCampaignNameExists(campaign.getName(), campaign.getTenantId())) {
            throw new RuntimeException("Campaign with name '" + campaign.getName() + "' already exists for this tenant");
        }
        
        // Update fields
        existingCampaign.setName(campaign.getName());
        existingCampaign.setDescription(campaign.getDescription());
        existingCampaign.setType(campaign.getType());
        existingCampaign.setEmailSubject(campaign.getEmailSubject());
        existingCampaign.setEmailTemplate(campaign.getEmailTemplate());
        existingCampaign.setHtmlContent(campaign.getHtmlContent());
        existingCampaign.setTextContent(campaign.getTextContent());
        existingCampaign.setSenderEmail(campaign.getSenderEmail());
        existingCampaign.setSenderName(campaign.getSenderName());
        existingCampaign.setReplyToEmail(campaign.getReplyToEmail());
        existingCampaign.setScheduledAt(campaign.getScheduledAt());
        existingCampaign.setBatchSize(campaign.getBatchSize());
        existingCampaign.setBatchIntervalMinutes(campaign.getBatchIntervalMinutes());
        existingCampaign.setMaxRetries(campaign.getMaxRetries());
        existingCampaign.setIsActive(campaign.getIsActive());
        existingCampaign.setUpdatedBy(campaign.getUpdatedBy());
        
        Campaign updatedCampaign = campaignRepository.save(existingCampaign);
        return updatedCampaign;
    }

    @Override
    public void deleteCampaign(Long id) {
        Campaign campaign = getCampaignById(id);
        
        // Check if campaign is running
        if (campaign.getStatus() == CampaignStatus.RUNNING) {
            throw new RuntimeException("Cannot delete a running campaign. Please stop it first.");
        }
        
        // Delete associated campaign users first
        List<CampaignUser> campaignUsers = campaignUserRepository.findByCampaignId(id);
        if (!campaignUsers.isEmpty()) {
            campaignUserRepository.deleteAll(campaignUsers);
        }
        
        campaignRepository.deleteById(id);
    }

    @Override
    public Page<Campaign> getAllCampaigns(Pageable pageable) {
        return campaignRepository.findAll(pageable);
    }

    @Override
    public List<Campaign> getCampaignsByStatus(CampaignStatus status) {
        return campaignRepository.findByStatus(status);
    }

    @Override
    public List<Campaign> getCampaignsByType(CampaignType type) {
        return campaignRepository.findByType(type);
    }

    @Override
    public List<Campaign> getActiveCampaigns() {
        return campaignRepository.findByIsActiveTrue();
    }

    @Override
    public List<Campaign> getCampaignsByTenant(String tenantId) {
        return campaignRepository.findByTenantId(tenantId);
    }

    @Override
    public Campaign scheduleCampaign(Long campaignId, LocalDateTime scheduledAt) {
        Campaign campaign = getCampaignById(campaignId);
        
        if (campaign.getStatus() != CampaignStatus.DRAFT) {
            throw new RuntimeException("Only draft campaigns can be scheduled");
        }
        
        if (scheduledAt.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Scheduled time cannot be in the past");
        }
        
        campaign.setScheduledAt(scheduledAt);
        campaign.setStatus(CampaignStatus.SCHEDULED);
        
        Campaign scheduledCampaign = campaignRepository.save(campaign);
        return scheduledCampaign;
    }

    @Override
    public Campaign startCampaign(Long campaignId) {
        Campaign campaign = getCampaignById(campaignId);
        
        if (campaign.getStatus() != CampaignStatus.SCHEDULED && campaign.getStatus() != CampaignStatus.PAUSED) {
            throw new RuntimeException("Campaign must be scheduled or paused to start");
        }
        
        campaign.setStatus(CampaignStatus.RUNNING);
        
        Campaign startedCampaign = campaignRepository.save(campaign);
        return startedCampaign;
    }

    @Override
    public Campaign pauseCampaign(Long campaignId) {
        Campaign campaign = getCampaignById(campaignId);
        
        if (campaign.getStatus() != CampaignStatus.RUNNING) {
            throw new RuntimeException("Only running campaigns can be paused");
        }
        
        campaign.setStatus(CampaignStatus.PAUSED);
        
        Campaign pausedCampaign = campaignRepository.save(campaign);
        return pausedCampaign;
    }

    @Override
    public Campaign cancelCampaign(Long campaignId) {
        Campaign campaign = getCampaignById(campaignId);
        
        if (campaign.getStatus() == CampaignStatus.COMPLETED || campaign.getStatus() == CampaignStatus.FAILED) {
            throw new RuntimeException("Cannot cancel completed or failed campaigns");
        }
        
        campaign.setStatus(CampaignStatus.CANCELLED);
        
        Campaign cancelledCampaign = campaignRepository.save(campaign);
        return cancelledCampaign;
    }

    @Override
    public Campaign completeCampaign(Long campaignId) {
        Campaign campaign = getCampaignById(campaignId);
        campaign.setStatus(CampaignStatus.COMPLETED);
        
        Campaign completedCampaign = campaignRepository.save(campaign);
        return completedCampaign;
    }

    @Override
    public Campaign failCampaign(Long campaignId, String errorMessage) {
        Campaign campaign = getCampaignById(campaignId);
        campaign.setStatus(CampaignStatus.FAILED);
        
        Campaign failedCampaign = campaignRepository.save(campaign);
        return failedCampaign;
    }

    @Override
    public List<Campaign> getReadyToExecuteCampaigns() {
        return campaignRepository.findReadyToExecuteCampaigns(LocalDateTime.now());
    }

    @Override
    public void executeScheduledCampaigns() {
        List<Campaign> readyCampaigns = getReadyToExecuteCampaigns();
        
        for (Campaign campaign : readyCampaigns) {
            try {
                startCampaign(campaign.getId());
            } catch (Exception e) {
                failCampaign(campaign.getId(), e.getMessage());
            }
        }
    }

    @Override
    public List<Campaign> searchCampaignsByName(String name) {
        return campaignRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Campaign> searchCampaignsByDescription(String description) {
        return campaignRepository.findByDescriptionContainingIgnoreCase(description);
    }

    @Override
    public Page<Campaign> getCampaignsWithFilters(CampaignStatus status, CampaignType type, Boolean isActive, String tenantId, Pageable pageable) {
        return campaignRepository.findCampaignsWithFilters(status, type, isActive, tenantId, pageable);
    }

    @Override
    public long getCampaignCountByStatus(CampaignStatus status) {
        return campaignRepository.countByStatus(status);
    }

    @Override
    public long getCampaignCountByTenant(String tenantId) {
        return campaignRepository.countByTenantId(tenantId);
    }

    @Override
    public List<Campaign> getCampaignsCreatedAfter(LocalDateTime startDate) {
        return campaignRepository.findCampaignsCreatedAfter(startDate);
    }

    @Override
    public boolean isCampaignNameExists(String name, String tenantId) {
        return campaignRepository.existsByNameAndTenantId(name, tenantId);
    }

    @Override
    public boolean isCampaignReadyToExecute(Long campaignId) {
        Campaign campaign = getCampaignById(campaignId);
        return campaign.getStatus() == CampaignStatus.SCHEDULED && 
               campaign.getScheduledAt() != null && 
               campaign.getScheduledAt().isBefore(LocalDateTime.now()) && 
               campaign.getIsActive();
    }

    @Override
    public void validateCampaign(Campaign campaign) {
        if (campaign.getName() == null || campaign.getName().trim().isEmpty()) {
            throw new RuntimeException("Campaign name is required");
        }
        
        if (campaign.getType() == null) {
            throw new RuntimeException("Campaign type is required");
        }
        
        if (campaign.getType() == CampaignType.EMAIL) {
            if (campaign.getEmailSubject() == null || campaign.getEmailSubject().trim().isEmpty()) {
                throw new RuntimeException("Email subject is required for email campaigns");
            }
            
            if (campaign.getHtmlContent() == null || campaign.getHtmlContent().trim().isEmpty()) {
                throw new RuntimeException("Email content is required for email campaigns");
            }
        }
        
        if (campaign.getScheduledAt() != null && campaign.getScheduledAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Scheduled time cannot be in the past");
        }
    }



    @Override
    public List<CampaignUser> addUsersToCampaign(Long campaignId, List<CampaignUserRequest> userRequests) {
        Campaign campaign = getCampaignById(campaignId);
        
        if (userRequests == null || userRequests.isEmpty()) {
            throw new RuntimeException("User requests cannot be null or empty");
        }
        
        List<CampaignUser> campaignUsers = new java.util.ArrayList<>();
        
        for (CampaignUserRequest userRequest : userRequests) {
            // Validate required fields
            if (userRequest.getEmailId() == null || userRequest.getEmailId().trim().isEmpty()) {
                throw new RuntimeException("Email ID is required for all users");
            }
            
            // Check if user already exists for this campaign
            if (campaignUserRepository.existsByCampaignIdAndEmailId(campaignId, userRequest.getEmailId())) {
                continue; // Skip if user already exists
            }
            
            // Create CampaignUser entity
            CampaignUser campaignUser = CampaignUser.builder()
                    .campaign(campaign)
                    .userName(userRequest.getUserName())
                    .userGroup(userRequest.getUserGroup())
                    .emailId(userRequest.getEmailId())
                    .facebookId(userRequest.getFacebookId())
                    .instagramId(userRequest.getInstagramId())
                    .whatsappNumber(userRequest.getWhatsappNumber())
                    .phoneNumber(userRequest.getPhoneNumber())
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .companyName(userRequest.getCompanyName())
                    .jobTitle(userRequest.getJobTitle())
                    .location(userRequest.getLocation())
                    .timezone(userRequest.getTimezone())
                    .languagePreference(userRequest.getLanguagePreference())
                    .subscriptionStatus(SubscriptionStatus.SUBSCRIBED)
                    .emailStatus(EmailStatus.PENDING)
                    .isActive(true)
                    .tenantId(campaign.getTenantId())
                    .build();
            
            campaignUsers.add(campaignUser);
        }
        
        // Save all campaign users
        List<CampaignUser> savedUsers = campaignUserRepository.saveAll(campaignUsers);
        return savedUsers;
    }

    @Override
    public List<CampaignUser> getCampaignUsers(Long campaignId) {
        Campaign campaign = getCampaignById(campaignId);
        return campaignUserRepository.findByCampaignId(campaignId);
    }

    @Override
    public void removeUsersFromCampaign(Long campaignId, List<Long> userIds) {
        Campaign campaign = getCampaignById(campaignId);
        
        // This would typically involve removing CampaignUser entities
    }

    @Override
    public long getCampaignUserCount(Long campaignId) {
        return campaignUserRepository.countByCampaignId(campaignId);
    }

    @Override
    public void processCampaignBatch(Long campaignId, int batchSize) {
        Campaign campaign = getCampaignById(campaignId);
        
        if (campaign.getStatus() != CampaignStatus.RUNNING) {
            throw new RuntimeException("Campaign must be running to process batches");
        }
        
        // This would typically involve Spring Batch processing
    }

    @Override
    public void retryFailedEmails(Long campaignId, int maxRetries) {
        Campaign campaign = getCampaignById(campaignId);
        
        // This would typically involve retrying failed email sends
    }
} 