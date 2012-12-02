package epam.com.concurrency.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import epam.com.concurrency.util.Constants;

public class ThreadManager {

	private final static Logger LOG = Logger.getLogger(ThreadManager.class);
	private Map<String, Integer> searchSuccessResults = new HashMap<String, Integer>();

	private ExecutorService executorService = Executors
			.newFixedThreadPool(Constants.THREADS_COUNT);

	synchronized public void addSuccessResult(String fileName, int foundedCount) {
		if (foundedCount != 0) {
			if (searchSuccessResults.get(fileName) != null) {
				searchSuccessResults.put(fileName,
						searchSuccessResults.get(fileName) + foundedCount);
			} else {
				searchSuccessResults.put(fileName, foundedCount);
			}
		}
	}

	public void addTask(Runnable task) {
		executorService.execute(task);
	}

	public void finishWork() {
		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE,
					TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			LOG.warn("", e);
		}
		showResults();
	}

	private void showResults() {
		LOG.info("Results:");
		for (String fileName : this.searchSuccessResults.keySet()) {
			LOG.info("File Name: " + fileName + "; Count: "
					+ this.searchSuccessResults.get(fileName));
		}
	}

}
