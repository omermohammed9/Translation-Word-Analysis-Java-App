package UniDayApp;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

class PDFFileReader implements FileReader {
	@Override
	public String readTextFromFile(File file) throws IOException {
		String fileText;

		// This method loads the content of a PDF file and extracts its text.
		try (PDDocument document = PDDocument.load(file)) {
			PDFTextStripper textStripper = new PDFTextStripper();
			fileText = textStripper.getText(document);
		}
		return fileText;
	}
}

