package com.zen.digital.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.zen.digital.entity.Campaign.CampaignType;

import com.zen.digital.service.CampaignUserRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public class CampaignRequest {

    @NotBlank(message = "Campaign name is required")
    private String name;

    private String description;

    @NotNull(message = "Campaign type is required")
    private CampaignType type;

    private String emailSubject;
    private String emailTemplate;
    private String htmlContent;
    private String textContent;
    private String senderEmail;
    private String senderName;
    private String replyToEmail;
    private LocalDateTime scheduledAt;
    private Integer batchSize;
    private Integer batchIntervalMinutes;
    private Integer maxRetries;
    private Boolean isActive;
    private String createdBy;
    private String tenantId;
    private List<CampaignUserRequest> users;

    // Default constructor
    public CampaignRequest() {
    }

    // All-args constructor
    public CampaignRequest(String name, String description, CampaignType type, String emailSubject,
                          String emailTemplate, String htmlContent, String textContent, String senderEmail,
                          String senderName, String replyToEmail, LocalDateTime scheduledAt, Integer batchSize,
                          Integer batchIntervalMinutes, Integer maxRetries, Boolean isActive, String createdBy,
                          String tenantId, List<CampaignUserRequest> users) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.emailSubject = emailSubject;
        this.emailTemplate = emailTemplate;
        this.htmlContent = htmlContent;
        this.textContent = textContent;
        this.senderEmail = senderEmail;
        this.senderName = senderName;
        this.replyToEmail = replyToEmail;
        this.scheduledAt = scheduledAt;
        this.batchSize = batchSize;
        this.batchIntervalMinutes = batchIntervalMinutes;
        this.maxRetries = maxRetries;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.tenantId = tenantId;
        this.users = users;
    }

    // Builder pattern
    public static CampaignRequestBuilder builder() {
        return new CampaignRequestBuilder();
    }

    public static class CampaignRequestBuilder {
        private String name;
        private String description;
        private CampaignType type;
        private String emailSubject;
        private String emailTemplate;
        private String htmlContent;
        private String textContent;
        private String senderEmail;
        private String senderName;
        private String replyToEmail;
        private LocalDateTime scheduledAt;
        private Integer batchSize;
        private Integer batchIntervalMinutes;
        private Integer maxRetries;
        private Boolean isActive;
        private String createdBy;
        private String tenantId;
        private List<CampaignUserRequest> users;

        public CampaignRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CampaignRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CampaignRequestBuilder type(CampaignType type) {
            this.type = type;
            return this;
        }

        public CampaignRequestBuilder emailSubject(String emailSubject) {
            this.emailSubject = emailSubject;
            return this;
        }

        public CampaignRequestBuilder emailTemplate(String emailTemplate) {
            this.emailTemplate = emailTemplate;
            return this;
        }

        public CampaignRequestBuilder htmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
            return this;
        }

        public CampaignRequestBuilder textContent(String textContent) {
            this.textContent = textContent;
            return this;
        }

        public CampaignRequestBuilder senderEmail(String senderEmail) {
            this.senderEmail = senderEmail;
            return this;
        }

        public CampaignRequestBuilder senderName(String senderName) {
            this.senderName = senderName;
            return this;
        }

        public CampaignRequestBuilder replyToEmail(String replyToEmail) {
            this.replyToEmail = replyToEmail;
            return this;
        }

        public CampaignRequestBuilder scheduledAt(LocalDateTime scheduledAt) {
            this.scheduledAt = scheduledAt;
            return this;
        }

        public CampaignRequestBuilder batchSize(Integer batchSize) {
            this.batchSize = batchSize;
            return this;
        }

        public CampaignRequestBuilder batchIntervalMinutes(Integer batchIntervalMinutes) {
            this.batchIntervalMinutes = batchIntervalMinutes;
            return this;
        }

        public CampaignRequestBuilder maxRetries(Integer maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        public CampaignRequestBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public CampaignRequestBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public CampaignRequestBuilder tenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public CampaignRequestBuilder users(List<CampaignUserRequest> users) {
            this.users = users;
            return this;
        }

        public CampaignRequest build() {
            return new CampaignRequest(name, description, type, emailSubject, emailTemplate, htmlContent,
                                     textContent, senderEmail, senderName, replyToEmail, scheduledAt, batchSize,
                                     batchIntervalMinutes, maxRetries, isActive, createdBy, tenantId, users);
        }
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CampaignType getType() {
        return type;
    }

    public void setType(CampaignType type) {
        this.type = type;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReplyToEmail() {
        return replyToEmail;
    }

    public void setReplyToEmail(String replyToEmail) {
        this.replyToEmail = replyToEmail;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public Integer getBatchIntervalMinutes() {
        return batchIntervalMinutes;
    }

    public void setBatchIntervalMinutes(Integer batchIntervalMinutes) {
        this.batchIntervalMinutes = batchIntervalMinutes;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public List<CampaignUserRequest> getUsers() {
        return users;
    }

    public void setUsers(List<CampaignUserRequest> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampaignRequest that = (CampaignRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return "CampaignRequest{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", scheduledAt=" + scheduledAt +
                '}';
    }
}

