package com.captechconsulting.springbatchexamples.multithreadedstep;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;

public class AttemptWriter implements ItemWriter<Attempt> {

	private final static Logger logger = LoggerFactory.getLogger(AttemptWriter.class);

	@Value("${processed-directory}")
	private String processedDirectory;

	@Value("${failed-directory}")
	private String failedDirectory;

	@Override
	public void write(List<? extends Attempt> attempts) throws Exception {

		logger.info("Write attempt list: {}",attempts);

		for (Attempt attempt : attempts) {
			if (attempt.isSuccess()) {
				//logger.info("Attempt was successful");
			}
			moveFile(attempt);
		}		
	}

	private void moveFile(Attempt attempt) {
		if (!attempt.hasSystemError()) {
			File processedDateDirectory = new File(processedDirectory + System.getProperty("file.separator")
					+ new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()));
			File failedDateDirectory = new File(failedDirectory + System.getProperty("file.separator")
					+ new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()));

			if (attempt.isSuccess() && !processedDateDirectory.exists()) {
				processedDateDirectory.mkdirs();
			}

			if (!attempt.isSuccess() && !failedDateDirectory.exists()) {
				failedDateDirectory.mkdirs();
			}

			logger.info("Moving {} document: {}.", attempt.isSuccess() ? "processed" : "failed",
					attempt.getFile().getName());

			String directory = attempt.isSuccess() ? processedDateDirectory.getAbsolutePath()
					: failedDateDirectory.getAbsolutePath();

			attempt.getFile()
					.renameTo(new File(directory + System.getProperty("file.separator") + attempt.getFile().getName()));
		}
	}

}
