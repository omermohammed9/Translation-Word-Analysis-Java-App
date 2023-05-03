package UniDayApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class WordAnalyzer {
	private List<String> words;
	private double averageWordLength;
	private String longestWord;
	private String shortestWord;
	private List<Map.Entry<String, Integer>> mostCommonWords;
	private int vowelCount;
	private int consonantCount;
	private ConcurrentHashMap<String, Integer> wordFrequency;
	private double timeTaken;

	public WordAnalyzer(List<String> words) {
		this.words = new ArrayList<>(words); // Make a defensive copy of the input list
	}

	public void analyze() {
		ExecutorService executor = Executors.newWorkStealingPool();
		List<Task> tasks = new ArrayList<>();
		tasks.add(new AverageWordLengthTask(words));
		tasks.add(new LongestWordTask(words));
		tasks.add(new ShortestWordTask(words));
		tasks.add(new MostCommonWordsTask(words));
		tasks.add(new VowelConsonantCountTask(words));
		// tasks.add(new WordFrequencyTask(words));

		long startTime = System.nanoTime();
		for (Task task : tasks) {
			executor.submit(task);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		long endTime = System.nanoTime();

		averageWordLength = (double) ((AverageWordLengthTask) tasks.get(0)).getResult();
		longestWord = (String) ((LongestWordTask) tasks.get(1)).getResult();
		shortestWord = (String) ((ShortestWordTask) tasks.get(2)).getResult();
		mostCommonWords = (List<Entry<String, Integer>>) ((MostCommonWordsTask) tasks.get(3)).getResult();
		vowelCount = ((VowelConsonantCountTask) tasks.get(4)).getVowelCount();
		consonantCount = ((VowelConsonantCountTask) tasks.get(4)).getConsonantCount();
		// wordFrequency = ((WordFrequencyTask) tasks.get(5)).getWordFrequency();

		timeTaken = (endTime - startTime) / 1e9;
	}

	public double getAverageWordLength() {
		return averageWordLength;
	}

	public String getLongestWord() {
		return longestWord;
	}

	public String getShortestWord() {
		return shortestWord;
	}

	public List<Map.Entry<String, Integer>> getMostCommonWords() {
		return mostCommonWords;
	}

	public int getVowelCount() {
		return vowelCount;
	}

	public int getConsonantCount() {
		return consonantCount;
	}

	public ConcurrentHashMap<String, Integer> getWordFrequency() {
		return wordFrequency;
	}

	public double getTimeTaken() {
		return timeTaken;
	}
}

/*
 * class WordFrequencyTask extends Task { private ConcurrentHashMap<String,
 * Integer> wordFrequency;
 * 
 * public WordFrequencyTask(List<String> words) { super(words); }
 * 
 * @Override public void run() { wordFrequency = new ConcurrentHashMap<>(); for
 * (String word : words) { wordFrequency.compute(word, (key, value) -> (value ==
 * null) ? 1 : value + 1); } }
 * 
 * public ConcurrentHashMap<String, Integer> getWordFrequency() { return
 * wordFrequency; } }
 */