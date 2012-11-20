package epam.com.concurrency.service;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		SearchManager searchManager = new SearchManager();
		searchManager.searchInFolder(new File("C:/WINDOWS/system32"), "windows");

	}

}
