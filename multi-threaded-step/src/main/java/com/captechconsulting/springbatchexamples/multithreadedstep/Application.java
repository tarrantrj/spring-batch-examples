package com.captechconsulting.springbatchexamples.multithreadedstep;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@SpringBootApplication
@EnableBatchProcessing
public class Application extends DefaultBatchConfigurer {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Value("${chunk-size}")
	private int chunkSize;

	@Value("${max-attempts}")
	private int maxAttempts;

	@Value("${backoff-period}")
	private int backoffPeriod;

	@Value("${max-threads}")
	private int maxThreads;

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource batchDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public AttemptReader processAttemptReader() {
		return new AttemptReader();
	}

	@Bean
	public AttemptProcessor processAttemptProcessor() {
		return new AttemptProcessor();
	}

	@Bean
	public AttemptWriter processAttemptWriter() {
		return new AttemptWriter();
	}

	@Bean
	public JobCompletionNotificationListener listener() {
		return new JobCompletionNotificationListener();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		taskExecutor.setConcurrencyLimit(maxThreads);
		return taskExecutor;
	}

	@Bean
	public Job processAttemptJob() {
		return jobBuilderFactory.get("process-attempt-job").incrementer(new RunIdIncrementer()).listener(listener())
				.flow(step()).end().build();
	}

	@Bean
	public Step step() {
		return stepBuilderFactory.get("step").<Attempt, Attempt>chunk(chunkSize).reader(processAttemptReader())
				.processor(processAttemptProcessor()).writer(processAttemptWriter()).taskExecutor(taskExecutor())
				.throttleLimit(maxThreads).build();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
