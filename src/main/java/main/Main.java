package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class Main {

	public static void main(String[] args) {
		try {
			merge(getFileList());
		} catch (Exception e) {
			System.err.println("fail to merge files");
			e.printStackTrace();
		}
	}

	static List<String> getFileList() throws IOException {

		List<String> listOfFiles = Files.list(Paths.get(".")).filter(f -> f.toString().toLowerCase().endsWith(".pdf"))
				.map(f -> f.toString()).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
		return listOfFiles;
	}

	static void merge(List<String> list) throws Exception {

		final String mergedFileName = "Merged.pdf";

		if (list == null || list.size() == 0) {
			System.err.println("no pdf file found to merge");
			return;
		}

		PDFMergerUtility ut = new PDFMergerUtility();

		System.out.println("Merging below pdf files");
		for (String name : list) {
			System.out.println(name);
			ut.addSource(name);
		}
		ut.setDestinationFileName(mergedFileName);
		ut.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
		System.out.println("===merged final file===->" + mergedFileName);
	}

}
