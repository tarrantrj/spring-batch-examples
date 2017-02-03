package com.captechconsulting.springbatchexamples.multithreadedstep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
	
	private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	
	@Override
	public void beforeJob(JobExecution jobExecution){
		super.beforeJob(jobExecution);
		
		logger.info("Job Started");
	}
	
	@Override
	public void afterJob(JobExecution jobExecution){
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.info("Job Completed");
		}
	}

}
