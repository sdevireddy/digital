package com.zen.digital.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name = "campaign_users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CampaignUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    @JsonBackReference
    private Campaign campaign;

    @NotBlank(message = "User name is required")
    @Column(name = "user_name", nullable = false, length = 255)
    private String userName;

    @Column(name = "user_group", length = 100)
    private String userGroup;

    @Email(message = "Email should be valid")
    @Column(name = "email_id", nullable = false, length = 255)
    private String emailId;

    @Column(name = "facebook_id", length = 100)
    private String facebookId;

    @Column(name = "instagram_id", length = 100)
    private String instagramId;

    @Column(name = "whatsapp_number", length = 20)
    private String whatsappNumber;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "company_name", length = 255)
    private String companyName;

    @Column(name = "job_title", length = 100)
    private String jobTitle;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "timezone", length = 50)
    private String timezone;

    @Column(name = "language_preference", length = 10)
    private String languagePreference;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_status", nullable = false)
    private SubscriptionStatus subscriptionStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "email_status")
    private EmailStatus emailStatus;

    @Column(name = "email_sent_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime emailSentAt;

    @Column(name = "email_opened_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime emailOpenedAt;

    @Column(name = "email_clicked_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime emailClickedAt;

    @Column(name = "unsubscribed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime unsubscribedAt;

    @Column(name = "bounce_reason", columnDefinition = "TEXT")
    private String bounceReason;

    @Column(name = "retry_count")
    private Integer retryCount;

    @Column(name = "last_error_message", columnDefinition = "TEXT")
    private String lastErrorMessage;

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

    // Custom fields for additional data
    @Column(name = "custom_field_1", length = 255)
    private String customField1;

    @Column(name = "custom_field_2", length = 255)
    private String customField2;

    @Column(name = "custom_field_3", length = 255)
    private String customField3;

    @Column(name = "custom_field_4", length = 255)
    private String customField4;

    @Column(name = "custom_field_5", length = 255)
    private String customField5;

    // Subscription status enum
    public enum SubscriptionStatus {
        SUBSCRIBED, UNSUBSCRIBED, PENDING, BOUNCED, SPAM
    }

    // Email status enum
    public enum EmailStatus {
        PENDING, SENT, DELIVERED, OPENED, CLICKED, BOUNCED, FAILED, UNSUBSCRIBED
    }

    // Pre-persist method to set creation timestamp
    @jakarta.persistence.PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (subscriptionStatus == null) {
            subscriptionStatus = SubscriptionStatus.SUBSCRIBED;
        }
        if (emailStatus == null) {
            emailStatus = EmailStatus.PENDING;
        }
        if (isActive == null) {
            isActive = true;
        }
        if (retryCount == null) {
            retryCount = 0;
        }
    }

    // Pre-update method to set update timestamp
    @jakarta.persistence.PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Helper method to get full name
    public String getFullName() {
        if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        } else if (firstName != null) {
            return firstName;
        } else if (lastName != null) {
            return lastName;
        } else {
            return userName;
        }
    }

    // Helper method to check if email is valid
    public boolean isEmailValid() {
        return emailId != null && !emailId.trim().isEmpty() && 
               subscriptionStatus != SubscriptionStatus.BOUNCED && 
               subscriptionStatus != SubscriptionStatus.UNSUBSCRIBED;
    }

    // Default constructor
    public CampaignUser() {
    }

    // All-args constructor
    public CampaignUser(Long id, Campaign campaign, String userName, String userGroup, String emailId,
                       String facebookId, String instagramId, String whatsappNumber, String phoneNumber,
                       String firstName, String lastName, String companyName, String jobTitle,
                       String location, String timezone, String languagePreference,
                       SubscriptionStatus subscriptionStatus, EmailStatus emailStatus,
                       LocalDateTime emailSentAt, LocalDateTime emailOpenedAt, LocalDateTime emailClickedAt,
                       LocalDateTime unsubscribedAt, String bounceReason, Integer retryCount,
                       String lastErrorMessage, Boolean isActive, LocalDateTime createdAt,
                       LocalDateTime updatedAt, String createdBy, String updatedBy, String tenantId,
                       String customField1, String customField2, String customField3, String customField4,
                       String customField5) {
        this.id = id;
        this.campaign = campaign;
        this.userName = userName;
        this.userGroup = userGroup;
        this.emailId = emailId;
        this.facebookId = facebookId;
        this.instagramId = instagramId;
        this.whatsappNumber = whatsappNumber;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.location = location;
        this.timezone = timezone;
        this.languagePreference = languagePreference;
        this.subscriptionStatus = subscriptionStatus;
        this.emailStatus = emailStatus;
        this.emailSentAt = emailSentAt;
        this.emailOpenedAt = emailOpenedAt;
        this.emailClickedAt = emailClickedAt;
        this.unsubscribedAt = unsubscribedAt;
        this.bounceReason = bounceReason;
        this.retryCount = retryCount;
        this.lastErrorMessage = lastErrorMessage;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.tenantId = tenantId;
        this.customField1 = customField1;
        this.customField2 = customField2;
        this.customField3 = customField3;
        this.customField4 = customField4;
        this.customField5 = customField5;
    }

    // Builder pattern
    public static CampaignUserBuilder builder() {
        return new CampaignUserBuilder();
    }

    public static class CampaignUserBuilder {
        private Long id;
        private Campaign campaign;
        private String userName;
        private String userGroup;
        private String emailId;
        private String facebookId;
        private String instagramId;
        private String whatsappNumber;
        private String phoneNumber;
        private String firstName;
        private String lastName;
        private String companyName;
        private String jobTitle;
        private String location;
        private String timezone;
        private String languagePreference;
        private SubscriptionStatus subscriptionStatus;
        private EmailStatus emailStatus;
        private LocalDateTime emailSentAt;
        private LocalDateTime emailOpenedAt;
        private LocalDateTime emailClickedAt;
        private LocalDateTime unsubscribedAt;
        private String bounceReason;
        private Integer retryCount;
        private String lastErrorMessage;
        private Boolean isActive;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String createdBy;
        private String updatedBy;
        private String tenantId;
        private String customField1;
        private String customField2;
        private String customField3;
        private String customField4;
        private String customField5;

        public CampaignUserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CampaignUserBuilder campaign(Campaign campaign) {
            this.campaign = campaign;
            return this;
        }

        public CampaignUserBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public CampaignUserBuilder userGroup(String userGroup) {
            this.userGroup = userGroup;
            return this;
        }

        public CampaignUserBuilder emailId(String emailId) {
            this.emailId = emailId;
            return this;
        }

        public CampaignUserBuilder facebookId(String facebookId) {
            this.facebookId = facebookId;
            return this;
        }

        public CampaignUserBuilder instagramId(String instagramId) {
            this.instagramId = instagramId;
            return this;
        }

        public CampaignUserBuilder whatsappNumber(String whatsappNumber) {
            this.whatsappNumber = whatsappNumber;
            return this;
        }

        public CampaignUserBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public CampaignUserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public CampaignUserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CampaignUserBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public CampaignUserBuilder jobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
            return this;
        }

        public CampaignUserBuilder location(String location) {
            this.location = location;
            return this;
        }

        public CampaignUserBuilder timezone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        public CampaignUserBuilder languagePreference(String languagePreference) {
            this.languagePreference = languagePreference;
            return this;
        }

        public CampaignUserBuilder subscriptionStatus(SubscriptionStatus subscriptionStatus) {
            this.subscriptionStatus = subscriptionStatus;
            return this;
        }

        public CampaignUserBuilder emailStatus(EmailStatus emailStatus) {
            this.emailStatus = emailStatus;
            return this;
        }

        public CampaignUserBuilder emailSentAt(LocalDateTime emailSentAt) {
            this.emailSentAt = emailSentAt;
            return this;
        }

        public CampaignUserBuilder emailOpenedAt(LocalDateTime emailOpenedAt) {
            this.emailOpenedAt = emailOpenedAt;
            return this;
        }

        public CampaignUserBuilder emailClickedAt(LocalDateTime emailClickedAt) {
            this.emailClickedAt = emailClickedAt;
            return this;
        }

        public CampaignUserBuilder unsubscribedAt(LocalDateTime unsubscribedAt) {
            this.unsubscribedAt = unsubscribedAt;
            return this;
        }

        public CampaignUserBuilder bounceReason(String bounceReason) {
            this.bounceReason = bounceReason;
            return this;
        }

        public CampaignUserBuilder retryCount(Integer retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public CampaignUserBuilder lastErrorMessage(String lastErrorMessage) {
            this.lastErrorMessage = lastErrorMessage;
            return this;
        }

        public CampaignUserBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public CampaignUserBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CampaignUserBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public CampaignUserBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public CampaignUserBuilder updatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        public CampaignUserBuilder tenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public CampaignUserBuilder customField1(String customField1) {
            this.customField1 = customField1;
            return this;
        }

        public CampaignUserBuilder customField2(String customField2) {
            this.customField2 = customField2;
            return this;
        }

        public CampaignUserBuilder customField3(String customField3) {
            this.customField3 = customField3;
            return this;
        }

        public CampaignUserBuilder customField4(String customField4) {
            this.customField4 = customField4;
            return this;
        }

        public CampaignUserBuilder customField5(String customField5) {
            this.customField5 = customField5;
            return this;
        }

        public CampaignUser build() {
            return new CampaignUser(id, campaign, userName, userGroup, emailId, facebookId, instagramId,
                                  whatsappNumber, phoneNumber, firstName, lastName, companyName, jobTitle,
                                  location, timezone, languagePreference, subscriptionStatus, emailStatus,
                                  emailSentAt, emailOpenedAt, emailClickedAt, unsubscribedAt, bounceReason,
                                  retryCount, lastErrorMessage, isActive, createdAt, updatedAt, createdBy,
                                  updatedBy, tenantId, customField1, customField2, customField3, customField4,
                                  customField5);
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getInstagramId() {
        return instagramId;
    }

    public void setInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference(String languagePreference) {
        this.languagePreference = languagePreference;
    }

    public SubscriptionStatus getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public EmailStatus getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(EmailStatus emailStatus) {
        this.emailStatus = emailStatus;
    }

    public LocalDateTime getEmailSentAt() {
        return emailSentAt;
    }

    public void setEmailSentAt(LocalDateTime emailSentAt) {
        this.emailSentAt = emailSentAt;
    }

    public LocalDateTime getEmailOpenedAt() {
        return emailOpenedAt;
    }

    public void setEmailOpenedAt(LocalDateTime emailOpenedAt) {
        this.emailOpenedAt = emailOpenedAt;
    }

    public LocalDateTime getEmailClickedAt() {
        return emailClickedAt;
    }

    public void setEmailClickedAt(LocalDateTime emailClickedAt) {
        this.emailClickedAt = emailClickedAt;
    }

    public LocalDateTime getUnsubscribedAt() {
        return unsubscribedAt;
    }

    public void setUnsubscribedAt(LocalDateTime unsubscribedAt) {
        this.unsubscribedAt = unsubscribedAt;
    }

    public String getBounceReason() {
        return bounceReason;
    }

    public void setBounceReason(String bounceReason) {
        this.bounceReason = bounceReason;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    public void setLastErrorMessage(String lastErrorMessage) {
        this.lastErrorMessage = lastErrorMessage;
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

    public String getCustomField1() {
        return customField1;
    }

    public void setCustomField1(String customField1) {
        this.customField1 = customField1;
    }

    public String getCustomField2() {
        return customField2;
    }

    public void setCustomField2(String customField2) {
        this.customField2 = customField2;
    }

    public String getCustomField3() {
        return customField3;
    }

    public void setCustomField3(String customField3) {
        this.customField3 = customField3;
    }

    public String getCustomField4() {
        return customField4;
    }

    public void setCustomField4(String customField4) {
        this.customField4 = customField4;
    }

    public String getCustomField5() {
        return customField5;
    }

    public void setCustomField5(String customField5) {
        this.customField5 = customField5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampaignUser that = (CampaignUser) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CampaignUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", subscriptionStatus=" + subscriptionStatus +
                ", emailStatus=" + emailStatus +
                '}';
    }
} 