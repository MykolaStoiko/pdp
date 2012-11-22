package epam.com.concurrency.service;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

public class Main {
	
	public static final Logger LOG = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		SearchInThreadManager searchInThreadManager = new SearchInThreadManager();
		long startThreadSearch = System.nanoTime();
		searchInThreadManager.searchInFolder(new File("D:/test"), "my");
		long endThreadSearch = System.nanoTime();
		
		SearchManager searchManager = new SearchManager();
		long startSearch = System.nanoTime();
		searchManager.searchInFolder(new File("D:/test"), "my");
		Map<String, Integer> results = searchManager.getResults();
		LOG.info("Single thread search results:");
		for(String fileName: results.keySet()){
			LOG.info("File Name: " + fileName + "; Count: "
					+ results.get(fileName));
		}
		long endSearch = System.nanoTime();
		
		LOG.info("===============================================================");
		LOG.info("Multithread Search Execution Time: " + (endThreadSearch - startThreadSearch));
		LOG.info("Singlethread Search Execution Time: " + (endSearch - startSearch));
	}

}
