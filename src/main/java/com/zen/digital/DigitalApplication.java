package com.zen.digital;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class DigitalApplication implements SchedulingConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(DigitalApplication.class, args);
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
	}

}
