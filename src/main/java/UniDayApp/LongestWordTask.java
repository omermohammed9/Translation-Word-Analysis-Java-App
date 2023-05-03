package UniDayApp;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

class LongestWordTask extends Task {
	private final AtomicReference<String> longestWord = new AtomicReference<>("");

	public LongestWordTask(List<String> words) {
		super(words);
	}

	@Override
	public void run() {
		for (String word : words) {
			String currentLongestWord = longestWord.get();
			if (word.length() > currentLongestWord.length()) {
				longestWord.compareAndSet(currentLongestWord, word);
			}
		}
		result = longestWord.get();
	}
}
