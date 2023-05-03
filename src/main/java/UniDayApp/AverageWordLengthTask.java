package UniDayApp;

import java.util.List;

class AverageWordLengthTask extends Task {
    public AverageWordLengthTask(List<String> words) {
        super(words);
    }

    @Override
    public void run() {
        int wordCount = words.size();
        int totalLength = 0;

        for (String word : words) {
            totalLength += word.length();
        }

        result = wordCount == 0 ? 0.0 : (double) totalLength / wordCount;
    }
}
