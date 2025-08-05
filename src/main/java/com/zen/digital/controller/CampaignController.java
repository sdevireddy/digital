package com.zen.digital.controller;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zen.digital.dto.ApiResponse;
import com.zen.digital.dto.CampaignRequest;
import com.zen.digital.entity.Campaign;
import com.zen.digital.entity.Campaign.CampaignStatus;
import com.zen.digital.entity.Campaign.CampaignType;
import com.zen.digital.entity.CampaignUser;
import com.zen.digital.service.CampaignService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private com.zen.digital.service.CampaignSchedulerService campaignSchedulerService;

    // ✅ Get all campaigns with pagination
    @GetMapping
    public ResponseEntity<ApiResponse<List<Campaign>>> getAllCampaigns(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Campaign> campaignPage = campaignService.getAllCampaigns(pageable);

            ApiResponse<List<Campaign>> response = ApiResponse.<List<Campaign>>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(campaignPage.getContent())
                    .totalRecords(campaignPage.getTotalElements())
                    .pageSize(campaignPage.getSize())
                    .currentPage(campaignPage.getNumber())
                    .totalPages(campaignPage.getTotalPages())
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Failed to fetch campaigns: " + e.getMessage());
        }
    }

    // ✅ Get campaign by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Campaign>> getCampaignById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Campaign campaign = campaignService.getCampaignById(id);
            ApiResponse<Campaign> response = ApiResponse.<Campaign>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(campaign)
                    .build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return buildErrorResponse(request, 404, "Campaign not found with id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error fetching campaign: " + e.getMessage());
        }
    }

    // ✅ Create Campaign
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Campaign>> createCampaign(@Valid @RequestBody Campaign campaign, HttpServletRequest request) {
        try {
            Campaign savedCampaign = campaignService.createCampaign(campaign);
            ApiResponse<Campaign> response = ApiResponse.<Campaign>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(201)
                    .path(request.getRequestURI())
                    .data(savedCampaign)
                    .build();
            return ResponseEntity.status(201).body(response);
        } catch (RuntimeException e) {
            return buildErrorResponse(request, 400, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error creating campaign: " + e.getMessage());
        }
    }

    // ✅ Update Campaign
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Campaign>> updateCampaign(
            @PathVariable Long id,
            @Valid @RequestBody Campaign campaign,
            HttpServletRequest request) {
        try {
            Campaign updatedCampaign = campaignService.updateCampaign(id, campaign);
            ApiResponse<Campaign> response = ApiResponse.<Campaign>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(updatedCampaign)
                    .build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return buildErrorResponse(request, 404, "Campaign not found with id: " + id);
        } catch (RuntimeException e) {
            return buildErrorResponse(request, 400, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error updating campaign: " + e.getMessage());
        }
    }

    // ✅ Delete Campaign
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCampaign(@PathVariable Long id, HttpServletRequest request) {
        try {
            campaignService.deleteCampaign(id);
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(204)
                    .path(request.getRequestURI())
                    .data(null)
                    .build();
            return ResponseEntity.status(204).body(response);
        } catch (NoSuchElementException e) {
            return buildErrorResponse(request, 404, "Campaign not found with id: " + id);
        } catch (RuntimeException e) {
            return buildErrorResponse(request, 400, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error deleting campaign: " + e.getMessage());
        }
    }

    // ✅ Get campaigns by status
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Campaign>>> getCampaignsByStatus(
            @PathVariable CampaignStatus status,
            HttpServletRequest request) {
        try {
            List<Campaign> campaigns = campaignService.getCampaignsByStatus(status);
            ApiResponse<List<Campaign>> response = ApiResponse.<List<Campaign>>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(campaigns)
                    .totalRecords((long) campaigns.size())
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error fetching campaigns by status: " + e.getMessage());
        }
    }

    // ✅ Get campaigns by type
    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<Campaign>>> getCampaignsByType(
            @PathVariable CampaignType type,
            HttpServletRequest request) {
        try {
            List<Campaign> campaigns = campaignService.getCampaignsByType(type);
            ApiResponse<List<Campaign>> response = ApiResponse.<List<Campaign>>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(campaigns)
                    .totalRecords((long) campaigns.size())
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error fetching campaigns by type: " + e.getMessage());
        }
    }

    // ✅ Get active campaigns
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<Campaign>>> getActiveCampaigns(HttpServletRequest request) {
        try {
            List<Campaign> campaigns = campaignService.getActiveCampaigns();
            ApiResponse<List<Campaign>> response = ApiResponse.<List<Campaign>>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(campaigns)
                    .totalRecords((long) campaigns.size())
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error fetching active campaigns: " + e.getMessage());
        }
    }

    // ✅ Schedule Campaign
    @PostMapping("/{id}/schedule")
    public ResponseEntity<ApiResponse<Campaign>> scheduleCampaign(
            @PathVariable Long id,
            @RequestParam String scheduledAt,
            HttpServletRequest request) {
        try {
            LocalDateTime scheduledDateTime = LocalDateTime.parse(scheduledAt);
            Campaign scheduledCampaign = campaignService.scheduleCampaign(id, scheduledDateTime);
            ApiResponse<Campaign> response = ApiResponse.<Campaign>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(scheduledCampaign)
                    .build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return buildErrorResponse(request, 404, "Campaign not found with id: " + id);
        } catch (RuntimeException e) {
            return buildErrorResponse(request, 400, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error scheduling campaign: " + e.getMessage());
        }
    }

    // ✅ Start Campaign
    @PostMapping("/{id}/start")
    public ResponseEntity<ApiResponse<Campaign>> startCampaign(@PathVariable Long id, HttpServletRequest request) {
        try {
            Campaign startedCampaign = campaignService.startCampaign(id);
            ApiResponse<Campaign> response = ApiResponse.<Campaign>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(startedCampaign)
                    .build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return buildErrorResponse(request, 404, "Campaign not found with id: " + id);
        } catch (RuntimeException e) {
            return buildErrorResponse(request, 400, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error starting campaign: " + e.getMessage());
        }
    }

    // ✅ Pause Campaign
    @PostMapping("/{id}/pause")
    public ResponseEntity<ApiResponse<Campaign>> pauseCampaign(@PathVariable Long id, HttpServletRequest request) {
        try {
            Campaign pausedCampaign = campaignService.pauseCampaign(id);
            ApiResponse<Campaign> response = ApiResponse.<Campaign>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(pausedCampaign)
                    .build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return buildErrorResponse(request, 404, "Campaign not found with id: " + id);
        } catch (RuntimeException e) {
            return buildErrorResponse(request, 400, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error pausing campaign: " + e.getMessage());
        }
    }

    // ✅ Cancel Campaign
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Campaign>> cancelCampaign(@PathVariable Long id, HttpServletRequest request) {
        try {
            Campaign cancelledCampaign = campaignService.cancelCampaign(id);
            ApiResponse<Campaign> response = ApiResponse.<Campaign>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(cancelledCampaign)
                    .build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return buildErrorResponse(request, 404, "Campaign not found with id: " + id);
        } catch (RuntimeException e) {
            return buildErrorResponse(request, 400, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error cancelling campaign: " + e.getMessage());
        }
    }

    // ✅ Search campaigns by name
    @GetMapping("/search/name")
    public ResponseEntity<ApiResponse<List<Campaign>>> searchCampaignsByName(
            @RequestParam String name,
            HttpServletRequest request) {
        try {
            List<Campaign> campaigns = campaignService.searchCampaignsByName(name);
            ApiResponse<List<Campaign>> response = ApiResponse.<List<Campaign>>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(campaigns)
                    .totalRecords((long) campaigns.size())
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error searching campaigns: " + e.getMessage());
        }
    }

    // ✅ Get campaigns with filters
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<Campaign>>> getCampaignsWithFilters(
            @RequestParam(required = false) CampaignStatus status,
            @RequestParam(required = false) CampaignType type,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) String tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Campaign> campaignPage = campaignService.getCampaignsWithFilters(status, type, isActive, tenantId, pageable);

            ApiResponse<List<Campaign>> response = ApiResponse.<List<Campaign>>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(campaignPage.getContent())
                    .totalRecords(campaignPage.getTotalElements())
                    .pageSize(campaignPage.getSize())
                    .currentPage(campaignPage.getNumber())
                    .totalPages(campaignPage.getTotalPages())
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error filtering campaigns: " + e.getMessage());
        }
    }

    // ✅ Get campaign statistics
    @GetMapping("/{id}/stats")
    public ResponseEntity<ApiResponse<Object>> getCampaignStats(@PathVariable Long id, HttpServletRequest request) {
        try {
            Campaign campaign = campaignService.getCampaignById(id);
            long userCount = campaignService.getCampaignUserCount(id);
            
            // Create stats object
            var stats = new Object() {
                public final Long campaignId = id;
                public final String campaignName = campaign.getName();
                public final CampaignStatus status = campaign.getStatus();
                public final long totalUsers = userCount;
                public final LocalDateTime createdAt = campaign.getCreatedAt();
                public final LocalDateTime scheduledAt = campaign.getScheduledAt();
            };
            
            ApiResponse<Object> response = ApiResponse.<Object>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(stats)
                    .build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return buildErrorResponse(request, 404, "Campaign not found with id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error fetching campaign stats: " + e.getMessage());
        }
    }

    // ✅ Add users to campaign
    @PostMapping("/{id}/users")
    public ResponseEntity<ApiResponse<List<CampaignUser>>> addUsersToCampaign(
            @PathVariable Long id,
            @Valid @RequestBody CampaignRequest request,
            HttpServletRequest httpRequest) {
        try {
            List<CampaignUser> addedUsers = campaignService.addUsersToCampaign(id, request.getUsers());
            ApiResponse<List<CampaignUser>> response = ApiResponse.<List<CampaignUser>>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(201)
                    .path(httpRequest.getRequestURI())
                    .data(addedUsers)
                    .totalRecords((long) addedUsers.size())
                    .build();
            return ResponseEntity.status(201).body(response);
        } catch (NoSuchElementException e) {
            return buildErrorResponse(httpRequest, 404, "Campaign not found with id: " + id);
        } catch (RuntimeException e) {
            return buildErrorResponse(httpRequest, 400, e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse(httpRequest, 500, "Error adding users to campaign: " + e.getMessage());
        }
    }

    // ✅ Get users for a campaign
    @GetMapping("/{id}/users")
    public ResponseEntity<ApiResponse<List<CampaignUser>>> getCampaignUsers(
            @PathVariable Long id,
            HttpServletRequest request) {
        try {
            List<CampaignUser> users = campaignService.getCampaignUsers(id);
            ApiResponse<List<CampaignUser>> response = ApiResponse.<List<CampaignUser>>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data(users)
                    .totalRecords((long) users.size())
                    .build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return buildErrorResponse(request, 404, "Campaign not found with id: " + id);
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error fetching campaign users: " + e.getMessage());
        }
    }

    // ✅ Execute scheduled campaigns (admin endpoint)
    @PostMapping("/execute-scheduled")
    public ResponseEntity<ApiResponse<String>> executeScheduledCampaigns(HttpServletRequest request) {
        try {
            campaignService.executeScheduledCampaigns();
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data("Scheduled campaigns execution completed")
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Error executing scheduled campaigns: " + e.getMessage());
        }
    }

    // ✅ Test email configuration
    @PostMapping("/test-email-config")
    public ResponseEntity<ApiResponse<String>> testEmailConfiguration(HttpServletRequest request) {
        try {
            // Get the scheduler service and test email config
            campaignSchedulerService.testEmailConfiguration();
            
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data("Email configuration test completed successfully")
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Email configuration test failed: " + e.getMessage());
        }
    }

    // ✅ Force email processing for a campaign
    @PostMapping("/{id}/force-email-processing")
    public ResponseEntity<ApiResponse<String>> forceEmailProcessing(@PathVariable Long id, HttpServletRequest request) {
        try {
            // Get the scheduler service and trigger campaign execution
            campaignSchedulerService.triggerCampaignExecution(id);
            
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(200)
                    .path(request.getRequestURI())
                    .data("Email processing triggered for campaign ID: " + id)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(request, 500, "Failed to trigger email processing: " + e.getMessage());
        }
    }

    // 🔧 Common method for error responses
    private <T> ResponseEntity<ApiResponse<T>> buildErrorResponse(HttpServletRequest request, int status, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .timestamp(ZonedDateTime.now())
                .status(status)
                .path(request.getRequestURI())
                .error(message)
                .build();
        return ResponseEntity.status(status).body(response);
    }
} 