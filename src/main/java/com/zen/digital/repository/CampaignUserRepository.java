package com.zen.digital.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zen.digital.entity.CampaignUser;
import com.zen.digital.entity.CampaignUser.EmailStatus;
import com.zen.digital.entity.CampaignUser.SubscriptionStatus;

@Repository
public interface CampaignUserRepository extends JpaRepository<CampaignUser, Long> {

    // Find users by campaign
    List<CampaignUser> findByCampaignId(Long campaignId);
    
    // Find users by campaign with pagination
    Page<CampaignUser> findByCampaignId(Long campaignId, Pageable pageable);
    
    // Find users by email status
    List<CampaignUser> findByEmailStatus(EmailStatus emailStatus);
    
    // Find users by subscription status
    List<CampaignUser> findBySubscriptionStatus(SubscriptionStatus subscriptionStatus);
    
    // Find users by campaign and email status
    List<CampaignUser> findByCampaignIdAndEmailStatus(Long campaignId, EmailStatus emailStatus);
    
    // Find users by campaign and subscription status
    List<CampaignUser> findByCampaignIdAndSubscriptionStatus(Long campaignId, SubscriptionStatus subscriptionStatus);
    
    // Find users by email
    List<CampaignUser> findByEmailId(String emailId);
    
    // Find user by campaign and email
    Optional<CampaignUser> findByCampaignIdAndEmailId(Long campaignId, String emailId);
    
    // Find users by user group
    List<CampaignUser> findByUserGroup(String userGroup);
    
    // Find users by campaign and user group
    List<CampaignUser> findByCampaignIdAndUserGroup(Long campaignId, String userGroup);
    
    // Find active users
    List<CampaignUser> findByIsActiveTrue();
    
    // Find active users by campaign
    List<CampaignUser> findByCampaignIdAndIsActiveTrue(Long campaignId);
    
    // Find users by tenant
    List<CampaignUser> findByTenantId(String tenantId);
    
    // Find users by campaign and tenant
    List<CampaignUser> findByCampaignIdAndTenantId(Long campaignId, String tenantId);
    
    // Find users with pending emails
    @Query("SELECT cu FROM CampaignUser cu WHERE cu.emailStatus = 'PENDING' AND cu.isActive = true")
    List<CampaignUser> findUsersWithPendingEmails();
    
    // Find users with pending emails for a specific campaign
    @Query("SELECT cu FROM CampaignUser cu WHERE cu.campaign.id = :campaignId AND cu.emailStatus = 'PENDING' AND cu.isActive = true")
    List<CampaignUser> findUsersWithPendingEmailsForCampaign(@Param("campaignId") Long campaignId);
    
    // Find users that need retry (failed emails with retry count less than max)
    @Query("SELECT cu FROM CampaignUser cu WHERE cu.emailStatus = 'FAILED' AND cu.retryCount < :maxRetries AND cu.isActive = true")
    List<CampaignUser> findUsersNeedingRetry(@Param("maxRetries") Integer maxRetries);
    
    // Find users by name (case insensitive)
    @Query("SELECT cu FROM CampaignUser cu WHERE LOWER(cu.userName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<CampaignUser> findByUserNameContainingIgnoreCase(@Param("name") String name);
    
    // Find users by first name or last name (case insensitive)
    @Query("SELECT cu FROM CampaignUser cu WHERE LOWER(cu.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(cu.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<CampaignUser> findByFirstNameOrLastNameContainingIgnoreCase(@Param("name") String name);
    
    // Find users by company name (case insensitive)
    @Query("SELECT cu FROM CampaignUser cu WHERE LOWER(cu.companyName) LIKE LOWER(CONCAT('%', :companyName, '%'))")
    List<CampaignUser> findByCompanyNameContainingIgnoreCase(@Param("companyName") String companyName);
    
    // Count users by campaign
    long countByCampaignId(Long campaignId);
    
    // Count users by campaign and email status
    long countByCampaignIdAndEmailStatus(Long campaignId, EmailStatus emailStatus);
    
    // Count users by campaign and subscription status
    long countByCampaignIdAndSubscriptionStatus(Long campaignId, SubscriptionStatus subscriptionStatus);
    
    // Count users by email status
    long countByEmailStatus(EmailStatus emailStatus);
    
    // Count users by subscription status
    long countBySubscriptionStatus(SubscriptionStatus subscriptionStatus);
    
    // Count users by tenant
    long countByTenantId(String tenantId);
    
    // Find users created in the last N days
    @Query("SELECT cu FROM CampaignUser cu WHERE cu.createdAt >= :startDate")
    List<CampaignUser> findUsersCreatedAfter(@Param("startDate") LocalDateTime startDate);
    
    // Find users by campaign with filters
    @Query("SELECT cu FROM CampaignUser cu WHERE cu.campaign.id = :campaignId AND " +
           "(:emailStatus IS NULL OR cu.emailStatus = :emailStatus) AND " +
           "(:subscriptionStatus IS NULL OR cu.subscriptionStatus = :subscriptionStatus) AND " +
           "(:userGroup IS NULL OR cu.userGroup = :userGroup) AND " +
           "(:isActive IS NULL OR cu.isActive = :isActive)")
    Page<CampaignUser> findUsersByCampaignWithFilters(@Param("campaignId") Long campaignId,
                                                     @Param("emailStatus") EmailStatus emailStatus,
                                                     @Param("subscriptionStatus") SubscriptionStatus subscriptionStatus,
                                                     @Param("userGroup") String userGroup,
                                                     @Param("isActive") Boolean isActive,
                                                     Pageable pageable);
    
    // Update email status for a user
    @Modifying
    @Query("UPDATE CampaignUser cu SET cu.emailStatus = :emailStatus, cu.emailSentAt = :sentAt, cu.updatedAt = :updatedAt WHERE cu.id = :userId")
    int updateEmailStatus(@Param("userId") Long userId, 
                         @Param("emailStatus") EmailStatus emailStatus, 
                         @Param("sentAt") LocalDateTime sentAt,
                         @Param("updatedAt") LocalDateTime updatedAt);
    
    // Update email opened status
    @Modifying
    @Query("UPDATE CampaignUser cu SET cu.emailStatus = 'OPENED', cu.emailOpenedAt = :openedAt, cu.updatedAt = :updatedAt WHERE cu.id = :userId")
    int updateEmailOpened(@Param("userId") Long userId, 
                         @Param("openedAt") LocalDateTime openedAt,
                         @Param("updatedAt") LocalDateTime updatedAt);
    
    // Update email clicked status
    @Modifying
    @Query("UPDATE CampaignUser cu SET cu.emailStatus = 'CLICKED', cu.emailClickedAt = :clickedAt, cu.updatedAt = :updatedAt WHERE cu.id = :userId")
    int updateEmailClicked(@Param("userId") Long userId, 
                          @Param("clickedAt") LocalDateTime clickedAt,
                          @Param("updatedAt") LocalDateTime updatedAt);
    
    // Update bounce status
    @Modifying
    @Query("UPDATE CampaignUser cu SET cu.emailStatus = 'BOUNCED', cu.subscriptionStatus = 'BOUNCED', cu.bounceReason = :bounceReason, cu.updatedAt = :updatedAt WHERE cu.id = :userId")
    int updateBounceStatus(@Param("userId") Long userId, 
                          @Param("bounceReason") String bounceReason,
                          @Param("updatedAt") LocalDateTime updatedAt);
    
    // Update retry count
    @Modifying
    @Query("UPDATE CampaignUser cu SET cu.retryCount = cu.retryCount + 1, cu.lastErrorMessage = :errorMessage, cu.updatedAt = :updatedAt WHERE cu.id = :userId")
    int incrementRetryCount(@Param("userId") Long userId, 
                           @Param("errorMessage") String errorMessage,
                           @Param("updatedAt") LocalDateTime updatedAt);
    
    // Unsubscribe user
    @Modifying
    @Query("UPDATE CampaignUser cu SET cu.subscriptionStatus = 'UNSUBSCRIBED', cu.unsubscribedAt = :unsubscribedAt, cu.updatedAt = :updatedAt WHERE cu.id = :userId")
    int unsubscribeUser(@Param("userId") Long userId, 
                       @Param("unsubscribedAt") LocalDateTime unsubscribedAt,
                       @Param("updatedAt") LocalDateTime updatedAt);
    
    // Check if email exists in campaign
    boolean existsByCampaignIdAndEmailId(Long campaignId, String emailId);
    
    // Check if email exists
    boolean existsByEmailId(String emailId);
} 