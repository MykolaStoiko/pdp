package epam.com.concurrency.service;

import java.io.File;

import org.apache.log4j.Logger;

public class SearchManager {

	private final static Logger LOG = Logger.getLogger(SearchManager.class);

	private ThreadManager threadManager = new ThreadManager();
	private int threadsCreated = 0;

	public void searchInFolder(File folder, String searchString) {
		if (folder.isDirectory()) {
			for (String fileName : folder.list()) {
				File file = new File(folder.getAbsolutePath() + "/" + fileName);
				if (file.isFile()) {
					// find(file, searchString);
					LOG.info("Starting thread.");
					this.threadsCreated++;
					threadManager.createNewThread(new InFileSearchTask(file,
							searchString, this.threadManager,
							this.threadsCreated));
				} else {
					searchInFolder(file, searchString);
				}
			}
		}
		threadManager.setAllFilesAdded(true);
	}
}
