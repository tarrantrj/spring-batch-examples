package com.captechconsulting.springbatchexamples.multithreadedstep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class AttemptProcessor implements ItemProcessor<Attempt,Attempt> {
	
	private static final Logger logger = LoggerFactory.getLogger(AttemptProcessor.class);

	@Override
	public Attempt process(Attempt attempt) throws Exception {
		processAttempt(attempt);
		
		return attempt;
	}

	private void processAttempt(Attempt attempt) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			logger.error("Error during sleep", e);
		}
	}

}
