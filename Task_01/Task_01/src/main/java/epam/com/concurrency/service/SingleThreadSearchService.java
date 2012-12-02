package epam.com.concurrency.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class SingleThreadSearchService {

	private final static Logger LOG = Logger.getLogger(SingleThreadSearchService.class);

	private Map<String, Integer> results = new HashMap<String, Integer>();
	private InFileSearchService inFileSearchService = new InFileSearchService();

	public void searchInFolder(File folder, String searchedString) {
		if (folder.isDirectory()) {
			for (String fileName : folder.list()) {
				File file = new File(folder.getAbsolutePath() + "/" + fileName);
				if (file.isFile()) {
					int foundedCount = this.inFileSearchService.searchInFile(
							file, searchedString);
					saveFileSearchResult(file, foundedCount);
				} else {
					searchInFolder(file, searchedString);
				}
			}
		}
	}

	private void saveFileSearchResult(File file, int foundedCount) {
		Integer count = this.results.get(file.getName());
		if (count != null) {
			results.put(file.getName(), count + foundedCount);
		} else {
			results.put(file.getName(), foundedCount);
		}
	}

	public Map<String, Integer> getResults() {
		return this.results;
	}

	public void showResults() {
		LOG.info("Single thread search results:");
		for (String fileName : results.keySet()) {
			LOG.info("File Name: " + fileName + "; Count: "
					+ results.get(fileName));
		}
	}
}
