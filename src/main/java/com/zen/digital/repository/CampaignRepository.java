package com.zen.digital.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zen.digital.entity.Campaign;
import com.zen.digital.entity.Campaign.CampaignStatus;
import com.zen.digital.entity.Campaign.CampaignType;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    // Find campaigns by status
    List<Campaign> findByStatus(CampaignStatus status);
    
    // Find campaigns by type
    List<Campaign> findByType(CampaignType type);
    
    // Find campaigns by status and type
    List<Campaign> findByStatusAndType(CampaignStatus status, CampaignType type);
    
    // Find active campaigns
    List<Campaign> findByIsActiveTrue();
    
    // Find campaigns by tenant
    List<Campaign> findByTenantId(String tenantId);
    
    // Find campaigns by creator
    List<Campaign> findByCreatedBy(String createdBy);
    
    // Find campaigns scheduled between dates
    @Query("SELECT c FROM Campaign c WHERE c.scheduledAt BETWEEN :startDate AND :endDate")
    List<Campaign> findCampaignsScheduledBetween(@Param("startDate") LocalDateTime startDate, 
                                                @Param("endDate") LocalDateTime endDate);
    
    // Find campaigns ready to be executed (scheduled and not yet started)
    @Query("SELECT c FROM Campaign c WHERE c.status = 'SCHEDULED' AND c.scheduledAt <= :currentTime AND c.isActive = true")
    List<Campaign> findReadyToExecuteCampaigns(@Param("currentTime") LocalDateTime currentTime);
    
    // Find campaigns by name (case insensitive)
    @Query("SELECT c FROM Campaign c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Campaign> findByNameContainingIgnoreCase(@Param("name") String name);
    
    // Find campaigns by description (case insensitive)
    @Query("SELECT c FROM Campaign c WHERE LOWER(c.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    List<Campaign> findByDescriptionContainingIgnoreCase(@Param("description") String description);
    
    // Find campaigns with pagination and filters
    @Query("SELECT c FROM Campaign c WHERE " +
           "(:status IS NULL OR c.status = :status) AND " +
           "(:type IS NULL OR c.type = :type) AND " +
           "(:isActive IS NULL OR c.isActive = :isActive) AND " +
           "(:tenantId IS NULL OR c.tenantId = :tenantId)")
    Page<Campaign> findCampaignsWithFilters(@Param("status") CampaignStatus status,
                                           @Param("type") CampaignType type,
                                           @Param("isActive") Boolean isActive,
                                           @Param("tenantId") String tenantId,
                                           Pageable pageable);
    
    // Count campaigns by status
    @Query("SELECT COUNT(c) FROM Campaign c WHERE c.status = :status")
    long countByStatus(@Param("status") CampaignStatus status);
    
    // Count campaigns by tenant
    @Query("SELECT COUNT(c) FROM Campaign c WHERE c.tenantId = :tenantId")
    long countByTenantId(@Param("tenantId") String tenantId);
    
    // Find campaigns created in the last N days
    @Query("SELECT c FROM Campaign c WHERE c.createdAt >= :startDate")
    List<Campaign> findCampaignsCreatedAfter(@Param("startDate") LocalDateTime startDate);
    
    // Find campaigns with user count
    @Query("SELECT c, COUNT(cu) as userCount FROM Campaign c LEFT JOIN c.campaignUsers cu GROUP BY c")
    List<Object[]> findCampaignsWithUserCount();
    
    // Check if campaign name exists
    boolean existsByName(String name);
    
    // Check if campaign name exists for a specific tenant
    boolean existsByNameAndTenantId(String name, String tenantId);
    
    // Find campaign by name and tenant
    Optional<Campaign> findByNameAndTenantId(String name, String tenantId);
} 