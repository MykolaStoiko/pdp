package epam.com.concurrency.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class InFileSearchTask implements Runnable {

	private final static Logger LOG = Logger.getLogger(InFileSearchTask.class);

	private final int id;
	private File file;
	private String searchedString;
	private ThreadManager threadManager;

	public InFileSearchTask(File file, String searchedString,
			ThreadManager threadManager, int id) {
		this.id = id;
		this.file = file;
		this.searchedString = searchedString;
		this.threadManager = threadManager;
	}

	@Override
	public void run() {
		searchInFile();
	}

	private void searchInFile() {
		Scanner in = null;
		int foundedCount = 0;
		try {
			in = new Scanner(new FileReader(file));
			while (in.hasNextLine()) {
				foundedCount += searchInLine(in.nextLine(), searchedString);
			}
		} catch (IOException e) {
			LOG.warn("File problems.", e);
			this.threadManager.fileSearchFailedResult(file.getName());
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				LOG.warn("Can't close file.", e);
			}
		}
		// saveFileSearchResult(file, foundedCount);

		LOG.info("#" + id + ": Founded " + foundedCount
				+ " entire(s) in file: " + file.getAbsolutePath());

		threadManager.addResult(file.getName(), foundedCount);
	}

	private Integer searchInLine(String line, String searchedString) {
		Integer entiresCount = 0;
		int index = 0;
		int searchStringLength = searchedString.length();
		while (index > -1) {
			index = line.indexOf(searchedString, index);
			if (index >= 0) {
				entiresCount++;
				index += searchStringLength;
			}
		}
		return entiresCount;
	}
}
