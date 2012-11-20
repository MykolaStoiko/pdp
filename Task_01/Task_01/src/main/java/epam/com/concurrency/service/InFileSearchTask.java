package epam.com.concurrency.service;

import java.io.File;

public class InFileSearchTask implements Runnable {

	private File file;
	private String searchedString;
	private ThreadManager threadManager;
	
	public InFileSearchTask(File file, String searchedString, ThreadManager threadManager) {
		this.file = file;
		this.searchedString = searchedString;
		this.threadManager = threadManager;
	}
	
	@Override
	public void run() {
		
	}
	
	private void searchInFile(File file, String searchedString){
		
	}
}
