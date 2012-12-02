package epam.com.concurrency.service;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class InFileSearchService implements Runnable {

	private final static Logger LOG = Logger.getLogger(InFileSearchService.class);

	private final int id;
	private File file;
	private String searchedString;
	private ThreadManager threadManager;
	private boolean threadTask = false;

	public InFileSearchService(File file, String searchedString,
			ThreadManager threadManager, int id) {
		this.id = id;
		this.file = file;
		this.searchedString = searchedString;
		this.threadManager = threadManager;
		this.threadTask = true;
	}
	
	public InFileSearchService() {
		this.id = 0;
	}

	@Override
	public void run() {
		if (threadTask) {
			int result = searchInFile(this.file, this.searchedString);
			this.threadManager.addSuccessResult(this.file.getName(), result);
		}else{
			LOG.warn("Object should be initialize like a thread task or run 'search' method.");
		}
	}
	
	public int searchInFile(File file, String searchedString) {
		Scanner in = null;
		int foundedCount = 0;
		try {
			in = new Scanner(new FileReader(file));
			while (in.hasNextLine()) {
				foundedCount += searchInLine(in.nextLine(), searchedString);
				LOG.info("#" + id + ": Founded " + foundedCount
						+ " entire(s) in file: " + file.getAbsolutePath());
			}
		} catch (Exception e) {
			LOG.warn("File problems.", e);
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				LOG.warn("Can't close file.", e);
			}
		}
		return foundedCount;
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
