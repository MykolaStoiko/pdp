package epam.com.concurrency.service;

import java.io.File;

public class MultiThreadSearchService {

	private ThreadManager threadManager = new ThreadManager();
	private int threadsCreated = 0;

	public void searchInFolder(File folder, String searchString) {
		search(folder, searchString);
		threadManager.finishWork();
	}
	
	public void search(File folder, String searchString){
		if (folder.isDirectory()) {
			for (String fileName : folder.list()) {
				File file = new File(folder.getAbsolutePath() + "/" + fileName);
				if (file.isFile()) {
					this.threadsCreated++;
					threadManager.addTask(new InFileSearchService(file,
							searchString, this.threadManager,
							this.threadsCreated));
				} else {
					search(file, searchString);
				}
			}
		}
	}
}
