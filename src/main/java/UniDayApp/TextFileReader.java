package UniDayApp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class TextFileReader implements FileReader {
	@Override
	public String readTextFromFile(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
	}
}
