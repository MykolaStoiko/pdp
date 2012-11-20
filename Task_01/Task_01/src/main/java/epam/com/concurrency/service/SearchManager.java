package epam.com.concurrency.service;

public class SearchManager {
	
	private ThreadManager threadManager = new ThreadManager();
	
	public void searchInFolder(){
		/* 
		 * 
		 * Logic that getting all files from folder an starting threads for searching string in files.
		 *  
		 *  */
		
		threadManager.setAllFilesAdded(true);
	}
}
