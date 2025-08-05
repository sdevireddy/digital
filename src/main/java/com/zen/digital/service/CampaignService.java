package com.zen.digital.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zen.digital.dto.CampaignRequest;
import com.zen.digital.entity.Campaign;
import com.zen.digital.entity.Campaign.CampaignStatus;
import com.zen.digital.entity.Campaign.CampaignType;
import com.zen.digital.entity.CampaignUser;
import com.zen.digital.service.CampaignUserRequest;

public interface CampaignService {

    // Campaign CRUD operations
    Campaign createCampaign(Campaign campaign);
    Campaign getCampaignById(Long id);
    Campaign updateCampaign(Long id, Campaign campaign);
    void deleteCampaign(Long id);
    
    // Campaign listing and filtering
    Page<Campaign> getAllCampaigns(Pageable pageable);
    List<Campaign> getCampaignsByStatus(CampaignStatus status);
    List<Campaign> getCampaignsByType(CampaignType type);
    List<Campaign> getActiveCampaigns();
    List<Campaign> getCampaignsByTenant(String tenantId);
    
    // Campaign scheduling and execution
    Campaign scheduleCampaign(Long campaignId, LocalDateTime scheduledAt);
    Campaign startCampaign(Long campaignId);
    Campaign pauseCampaign(Long campaignId);
    Campaign cancelCampaign(Long campaignId);
    Campaign completeCampaign(Long campaignId);
    Campaign failCampaign(Long campaignId, String errorMessage);
    
    // Campaign status management
    List<Campaign> getReadyToExecuteCampaigns();
    void executeScheduledCampaigns();
    
    // Campaign search and filtering
    List<Campaign> searchCampaignsByName(String name);
    List<Campaign> searchCampaignsByDescription(String description);
    Page<Campaign> getCampaignsWithFilters(CampaignStatus status, CampaignType type, Boolean isActive, String tenantId, Pageable pageable);
    
    // Campaign statistics
    long getCampaignCountByStatus(CampaignStatus status);
    long getCampaignCountByTenant(String tenantId);
    List<Campaign> getCampaignsCreatedAfter(LocalDateTime startDate);
    
    // Campaign validation
    boolean isCampaignNameExists(String name, String tenantId);
    boolean isCampaignReadyToExecute(Long campaignId);
    void validateCampaign(Campaign campaign);
    
    // Campaign user management
//    void addUsersToCampaign(Long campaignId, List<Long> userIds);
    List<CampaignUser> addUsersToCampaign(Long campaignId, List<CampaignUserRequest> userRequests);
    List<CampaignUser> getCampaignUsers(Long campaignId);
    void removeUsersFromCampaign(Long campaignId, List<Long> userIds);
    long getCampaignUserCount(Long campaignId);
    
    // Batch processing
    void processCampaignBatch(Long campaignId, int batchSize);
    void retryFailedEmails(Long campaignId, int maxRetries);
} 