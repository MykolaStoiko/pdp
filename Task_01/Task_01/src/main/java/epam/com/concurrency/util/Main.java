package epam.com.concurrency.util;

import java.io.File;
import org.apache.log4j.Logger;

import epam.com.concurrency.service.MultiThreadSearchService;
import epam.com.concurrency.service.SingleThreadSearchService;

public class Main {

	public static final Logger LOG = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		MultiThreadSearchService searchInThreadManager = new MultiThreadSearchService();
		long startThreadSearch = System.nanoTime();
		searchInThreadManager.searchInFolder(new File(
				Constants.FOLDER_FOR_SEARCH_IN), Constants.SEARCHED_STRING);
		long endThreadSearch = System.nanoTime();

		SingleThreadSearchService searchManager = new SingleThreadSearchService();
		long startSearch = System.nanoTime();
		searchManager.searchInFolder(new File(Constants.FOLDER_FOR_SEARCH_IN),
				Constants.SEARCHED_STRING);
		searchManager.showResults();
		long endSearch = System.nanoTime();

		LOG.info("===============================================================");
		LOG.info("Multithread Search Execution Time: "
				+ (endThreadSearch - startThreadSearch));
		LOG.info("Singlethread Search Execution Time: "
				+ (endSearch - startSearch));
	}
}
