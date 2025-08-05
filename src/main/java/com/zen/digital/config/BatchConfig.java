package com.zen.digital.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.zen.digital.batch.EmailCampaignProcessor;
import com.zen.digital.batch.EmailCampaignReader;
import com.zen.digital.batch.EmailCampaignWriter;
import com.zen.digital.entity.CampaignUser;

@Configuration
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private EmailCampaignProcessor emailCampaignProcessor;

    @Autowired
    private EmailCampaignReader emailCampaignReader;

    @Autowired
    private EmailCampaignWriter emailCampaignWriter;

    // Job for processing email campaigns
    @Bean
    public Job emailCampaignJob() {
        return new JobBuilder("emailCampaignJob", jobRepository)
                .start(emailCampaignStep())
                .build();
    }

    // Step for processing email campaigns
    @Bean
    public Step emailCampaignStep() {
        return new StepBuilder("emailCampaignStep", jobRepository)
                .<CampaignUser, CampaignUser>chunk(100, transactionManager)
                .reader(emailCampaignReader)
                .processor(emailCampaignProcessor)
                .writer(emailCampaignWriter)
                .build();
    }
} 