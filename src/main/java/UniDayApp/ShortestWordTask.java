package UniDayApp;

import java.util.List;

class ShortestWordTask extends Task {
	public ShortestWordTask(List<String> words) {
		super(words);
	}

	@Override
	public void run() {
		String shortestWord = null;
		int shortestLength = Integer.MAX_VALUE;

		for (String word : words) {
			int length = word.length();
			if (length < shortestLength) {
				shortestWord = word;
				shortestLength = length;
			}
		}

		result = shortestWord;

	}
}