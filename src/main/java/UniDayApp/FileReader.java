package UniDayApp;

import java.io.File;
import java.io.IOException;

interface FileReader {
	String readTextFromFile(File file) throws IOException;
}
