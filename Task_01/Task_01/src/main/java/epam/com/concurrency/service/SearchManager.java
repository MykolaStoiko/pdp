package epam.com.concurrency.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class SearchManager {

	private final static Logger LOG = Logger.getLogger(SearchManager.class);
	
	private Map<String, Integer> results = new HashMap<String, Integer>();
	
	public void searchInFolder(File folder, String searchString) {
		if (folder.isDirectory()) {
			for (String fileName : folder.list()) {
				File file = new File(folder.getAbsolutePath() + "/" + fileName);
				if (file.isFile()) {
					find(file, searchString);
				} else {
					searchInFolder(file, searchString);
				}
			}
		}
	}
	
	private void find(File file, String searchedString) {
		Scanner in = null;
		int foundedCount = 0;
		try {
			in = new Scanner(new FileReader(file));
			while (in.hasNextLine()) {
				foundedCount += searchInLine(in.nextLine(), searchedString);
			}
		} catch (IOException e) {
			LOG.warn("File problems.", e);
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				LOG.warn("Can't close file.", e);
			}
		}
		saveFileSearchResult(file, foundedCount);
		
		LOG.info("Founded " + foundedCount + " entire(s) in file: " + file.getAbsolutePath());
	}
	
	private Integer searchInLine(String line, String searchedString){
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

	private void saveFileSearchResult(File file, int foundedCount) {
		Integer count = this.results.get(file.getName());
		if(count != null){
			results.put(file.getName(), count + foundedCount);
		} else{
			results.put(file.getName(), foundedCount);
		}
	}
	
	public Map<String, Integer> getResults(){
		return this.results;
	}
}
