package epam.com.concurrency.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class ThreadManager {

	private final static Logger LOG = Logger.getLogger(ThreadManager.class);
	private boolean allFilesAdded = false;
	private int threadsFinishedCount = 0;
	private List<Thread> threads = new ArrayList<Thread>();
	private Map<String, Integer> threadsResults = new HashMap<String, Integer>();
	private List<String> failedFiles = new ArrayList<String>();

	public void check() {
		if (allFilesAdded) {
			if (threads.size() == threadsFinishedCount) {
				LOG.info("All Treads Finished!");
				showResults();
			}
		}
	}

	public boolean isAllFilesAdded() {
		return allFilesAdded;
	}

	public void setAllFilesAdded(boolean allFilesAdded) {
		this.allFilesAdded = allFilesAdded;
	}

	synchronized public void addResult(String fileName, int foundedCount) {
		if (foundedCount != 0) {
			if (threadsResults.get(fileName) != null) {
				threadsResults.put(fileName, threadsResults.get(fileName)
						+ foundedCount);
			} else {
				threadsResults.put(fileName, foundedCount);
			}
		}
		threadsFinishedCount++;
		check();
	}

	public void createNewThread(Runnable task) {
		Thread thread = new Thread(task);
		this.threads.add(thread);
		thread.start();
	}

	private void showResults() {
		LOG.info("Results:");
		for (String fileName : this.threadsResults.keySet()) {
			LOG.info("File Name: " + fileName + "; Count: "
					+ this.threadsResults.get(fileName));
		}
		if (!this.failedFiles.isEmpty()) {
			LOG.info("Search failed in files:");
			for (String fileName : this.failedFiles) {
				LOG.info("File Name: " + fileName);
			}
		}
	}

	public void fileSearchFailedResult(String filename) {
		this.failedFiles.add(filename);
		threadsFinishedCount++;
	}

}
