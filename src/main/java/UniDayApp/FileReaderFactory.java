package UniDayApp;

import java.io.File;

class FileReaderFactory {
	public static FileReader getReader(File file) {
		String fileName = file.getName().toLowerCase();
		if (fileName.endsWith(".pdf")) {
			return new PDFFileReader();
		} else if (fileName.endsWith(".txt")) {
			return new TextFileReader();
		} else {
			throw new IllegalArgumentException("Unsupported file format.");
		}
	}
}