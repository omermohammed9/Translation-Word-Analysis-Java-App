package UniDayApp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

class MostCommonWordsTask extends Task {
	public MostCommonWordsTask(List<String> words) {
		super(words);
	}

	@Override
	public void run() {
		// Iterate over the words and update the frequency in the ConcurrentHashMap

		ConcurrentHashMap<String, Integer> wordCounts = new ConcurrentHashMap<>();

		for (String word : words) {
			wordCounts.compute(word, (key, value) -> value == null ? 1 : value + 1);
		}

		// Define a comparator for Map.Entry objects based on the value (frequency)
		Comparator<Map.Entry<String, Integer>> entryComparator = Map.Entry.comparingByValue();
		// Create a PriorityQueue to store the top 3 most common words

		PriorityQueue<Map.Entry<String, Integer>> topWordsQueue = new PriorityQueue<>(3, entryComparator);

		// Iterate over the wordCounts entries
		for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
			// If the PriorityQueue has less than 3 elements, add the entry

			if (topWordsQueue.size() < 3) {
				topWordsQueue.offer(entry);
				// If the entry has a higher frequency than the lowest frequency in the
				// PriorityQueue,
				// remove the lowest frequency and add the current entry
			} else if (entryComparator.compare(entry, topWordsQueue.peek()) > 0) {
				topWordsQueue.poll();
				topWordsQueue.offer(entry);
			}
		}
		// Create a list to store the most common words in the correct order

		List<Map.Entry<String, Integer>> mostCommonWords = new ArrayList<>();
		// Add the elements from the PriorityQueue to the list in reverse order

		while (!topWordsQueue.isEmpty()) {
			mostCommonWords.add(0, topWordsQueue.poll());
		}

		result = mostCommonWords;
	}
}