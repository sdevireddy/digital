package com.zen.digital.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
@Entity
@Table(name = "campaigns")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campaign name is required")
    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignType type;

    @Column(name = "email_subject")
    private String emailSubject;

    @Column(name = "email_template", columnDefinition = "TEXT")
    private String emailTemplate;

    @Column(name = "html_content", columnDefinition = "LONGTEXT")
    private String htmlContent;

    @Column(name = "text_content", columnDefinition = "TEXT")
    private String textContent;

    @Column(name = "sender_email")
    private String senderEmail;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "reply_to_email")
    private String replyToEmail;

    @Column(name = "scheduled_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime scheduledAt;

    @Column(name = "batch_size")
    private Integer batchSize;

    @Column(name = "batch_interval_minutes")
    private Integer batchIntervalMinutes;

    @Column(name = "max_retries")
    private Integer maxRetries;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "tenant_id")
    private String tenantId;

    // Relationships
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<CampaignUser> campaignUsers;

    // Campaign status enum
    public enum CampaignStatus {
        DRAFT, SCHEDULED, RUNNING, COMPLETED, FAILED, CANCELLED, PAUSED
    }

    // Campaign type enum
    public enum CampaignType {
        EMAIL, SMS, PUSH_NOTIFICATION, SOCIAL_MEDIA, MULTI_CHANNEL
    }

    // Pre-persist method to set creation timestamp
    @jakarta.persistence.PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = CampaignStatus.DRAFT;
        }
        if (isActive == null) {
            isActive = true;
        }
        if (batchSize == null) {
            batchSize = 100;
        }
        if (batchIntervalMinutes == null) {
            batchIntervalMinutes = 5;
        }
        if (maxRetries == null) {
            maxRetries = 3;
        }
    }

    // Pre-update method to set update timestamp
    @jakarta.persistence.PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Default constructor
    public Campaign() {
    }

    // All-args constructor
    public Campaign(Long id, String name, String description, CampaignStatus status, CampaignType type,
                   String emailSubject, String emailTemplate, String htmlContent, String textContent,
                   String senderEmail, String senderName, String replyToEmail, LocalDateTime scheduledAt,
                   Integer batchSize, Integer batchIntervalMinutes, Integer maxRetries, Boolean isActive,
                   LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy,
                   String tenantId, List<CampaignUser> campaignUsers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
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
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.tenantId = tenantId;
        this.campaignUsers = campaignUsers;
    }

    // Builder pattern
    public static CampaignBuilder builder() {
        return new CampaignBuilder();
    }

    public static class CampaignBuilder {
        private Long id;
        private String name;
        private String description;
        private CampaignStatus status;
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
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String createdBy;
        private String updatedBy;
        private String tenantId;
        private List<CampaignUser> campaignUsers;

        public CampaignBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CampaignBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CampaignBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CampaignBuilder status(CampaignStatus status) {
            this.status = status;
            return this;
        }

        public CampaignBuilder type(CampaignType type) {
            this.type = type;
            return this;
        }

        public CampaignBuilder emailSubject(String emailSubject) {
            this.emailSubject = emailSubject;
            return this;
        }

        public CampaignBuilder emailTemplate(String emailTemplate) {
            this.emailTemplate = emailTemplate;
            return this;
        }

        public CampaignBuilder htmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
            return this;
        }

        public CampaignBuilder textContent(String textContent) {
            this.textContent = textContent;
            return this;
        }

        public CampaignBuilder senderEmail(String senderEmail) {
            this.senderEmail = senderEmail;
            return this;
        }

        public CampaignBuilder senderName(String senderName) {
            this.senderName = senderName;
            return this;
        }

        public CampaignBuilder replyToEmail(String replyToEmail) {
            this.replyToEmail = replyToEmail;
            return this;
        }

        public CampaignBuilder scheduledAt(LocalDateTime scheduledAt) {
            this.scheduledAt = scheduledAt;
            return this;
        }

        public CampaignBuilder batchSize(Integer batchSize) {
            this.batchSize = batchSize;
            return this;
        }

        public CampaignBuilder batchIntervalMinutes(Integer batchIntervalMinutes) {
            this.batchIntervalMinutes = batchIntervalMinutes;
            return this;
        }

        public CampaignBuilder maxRetries(Integer maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        public CampaignBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public CampaignBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CampaignBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public CampaignBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public CampaignBuilder updatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        public CampaignBuilder tenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public CampaignBuilder campaignUsers(List<CampaignUser> campaignUsers) {
            this.campaignUsers = campaignUsers;
            return this;
        }

        public Campaign build() {
            return new Campaign(id, name, description, status, type, emailSubject, emailTemplate,
                              htmlContent, textContent, senderEmail, senderName, replyToEmail, scheduledAt,
                              batchSize, batchIntervalMinutes, maxRetries, isActive, createdAt, updatedAt,
                              createdBy, updatedBy, tenantId, campaignUsers);
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public CampaignStatus getStatus() {
        return status;
    }

    public void setStatus(CampaignStatus status) {
        this.status = status;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public List<CampaignUser> getCampaignUsers() {
        return campaignUsers;
    }

    public void setCampaignUsers(List<CampaignUser> campaignUsers) {
        this.campaignUsers = campaignUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campaign campaign = (Campaign) o;
        return Objects.equals(id, campaign.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", isActive=" + isActive +
                '}';
    }
} 