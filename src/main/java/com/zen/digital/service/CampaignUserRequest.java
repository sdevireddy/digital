package com.zen.digital.service;

import java.util.Objects;

public class CampaignUserRequest {
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

    // Default constructor
    public CampaignUserRequest() {
    }

    // All-args constructor
    public CampaignUserRequest(String userName, String userGroup, String emailId, String facebookId,
                               String instagramId, String whatsappNumber, String phoneNumber, String firstName,
                               String lastName, String companyName, String jobTitle, String location,
                               String timezone, String languagePreference) {
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
    }

    // Builder pattern
    public static CampaignUserRequestBuilder builder() {
        return new CampaignUserRequestBuilder();
    }

    public static class CampaignUserRequestBuilder {
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

        public CampaignUserRequestBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public CampaignUserRequestBuilder userGroup(String userGroup) {
            this.userGroup = userGroup;
            return this;
        }

        public CampaignUserRequestBuilder emailId(String emailId) {
            this.emailId = emailId;
            return this;
        }

        public CampaignUserRequestBuilder facebookId(String facebookId) {
            this.facebookId = facebookId;
            return this;
        }

        public CampaignUserRequestBuilder instagramId(String instagramId) {
            this.instagramId = instagramId;
            return this;
        }

        public CampaignUserRequestBuilder whatsappNumber(String whatsappNumber) {
            this.whatsappNumber = whatsappNumber;
            return this;
        }

        public CampaignUserRequestBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public CampaignUserRequestBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public CampaignUserRequestBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CampaignUserRequestBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public CampaignUserRequestBuilder jobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
            return this;
        }

        public CampaignUserRequestBuilder location(String location) {
            this.location = location;
            return this;
        }

        public CampaignUserRequestBuilder timezone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        public CampaignUserRequestBuilder languagePreference(String languagePreference) {
            this.languagePreference = languagePreference;
            return this;
        }

        public CampaignUserRequest build() {
            return new CampaignUserRequest(userName, userGroup, emailId, facebookId, instagramId,
                    whatsappNumber, phoneNumber, firstName, lastName, companyName,
                    jobTitle, location, timezone, languagePreference);
        }
    }

    // Getters and Setters
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampaignUserRequest that = (CampaignUserRequest) o;
        return Objects.equals(emailId, that.emailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailId);
    }

    @Override
    public String toString() {
        return "CampaignUserRequest{" +
                "userName='" + userName + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
