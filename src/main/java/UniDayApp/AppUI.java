package UniDayApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;

public class AppUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private Translator translator;

	public AppUI() {
		super("Translation & Word Analysiz App");
		initializeTranslator();
		initComponents();
	}

	private void initializeTranslator() {
		try {
			GoogleCredentials credentials = GoogleCredentials
					.fromStream(new FileInputStream("src/main/java/resources/translationapp-384915-47acc39541a3.json"))
					.createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));
			TranslateOptions options = TranslateOptions.newBuilder().setCredentials(credentials).build();
			Translate translate = options.getService();
			translator = new Translator(translate);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	private void initComponents() {
	    textArea = new JTextArea(10, 30);
	    textArea.setLineWrap(true); // Add this line to enable line wrapping in the JTextArea
	    textArea.setWrapStyleWord(true); // Add this line to wrap lines at word boundaries

	    JScrollPane scrollPane = new JScrollPane(textArea);
	    scrollPane.setPreferredSize(new Dimension(800, 400)); // Set the preferred size for the JScrollPane

	    JPanel buttonPanel = new JPanel();
	    buttonPanel.add(createOpenFileButton());
	    buttonPanel.add(createTranslateButton());
	    buttonPanel.add(createAnalyzeButton());

	    getContentPane().add(scrollPane, BorderLayout.CENTER);
	    getContentPane().add(buttonPanel, BorderLayout.SOUTH);

	   
	    
	    setPreferredSize(new Dimension(800, 500));
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    pack();
	    setLocationRelativeTo(null);
	    setVisible(true);
	}


	private JButton createTranslateButton() {
		JButton button = new JButton("Translate");
		button.addActionListener(e -> performTranslation());
		return button;
	}

	private JButton createAnalyzeButton() {
		JButton button = new JButton("Analyze");
		button.addActionListener(e -> performAnalysis());
		return button;
	}

	private JButton createOpenFileButton() {
		JButton button = new JButton("Open File");
		button.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				try {
					String fileText = FileReaderFactory.getReader(selectedFile).readTextFromFile(selectedFile);
					textArea.setText(fileText);
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(AppUI.this, "Error reading file.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return button;
	}

	private void performTranslation() {
		String text = textArea.getText();
		List<String> words = Arrays.asList(text.split("\\s+"));
		StringBuilder translatedTextBuilder = new StringBuilder();
		int numberOfCores = Runtime.getRuntime().availableProcessors();
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfCores);
		for (String word : words) {
			Callable<String> arabicTranslationTask = new Callable<String>() {
				public String call() throws Exception {
					return translator.translateToArabic(word);
				}
			};
			Callable<String> kurdishTranslationTask = () -> translator.translateToKurdish(word);
			List<Callable<String>> tasks = Arrays.asList(arabicTranslationTask, kurdishTranslationTask);
			List<Future<String>> futures;
			try {
				futures = executorService.invokeAll(tasks);
				for (Future<String> future : futures) {
					translatedTextBuilder.append(future.get()).append(" ");
				}
				translatedTextBuilder.append("\n");
			} catch (InterruptedException | ExecutionException ex) {
				ex.printStackTrace();
			}
		}

		executorService.shutdown();

		String translatedText = translatedTextBuilder.toString();
		JOptionPane.showMessageDialog(this, translatedText, "Translated Text", JOptionPane.INFORMATION_MESSAGE);
	}

	private void performAnalysis() {
		String text = textArea.getText();
		List<String> words = Arrays.asList(text.split("\\s+"));

		WordAnalyzer analyzer = new WordAnalyzer(words);
		analyzer.analyze();

		double avgWordLength = analyzer.getAverageWordLength();
		String longestWord = analyzer.getLongestWord();
		String shortestWord = analyzer.getShortestWord();
		List<Map.Entry<String, Integer>> mostCommonWords = analyzer.getMostCommonWords();
		int vowelCount = analyzer.getVowelCount();
		int consontantCount = analyzer.getConsonantCount();

		double timeTaken = analyzer.getTimeTaken();
		String results = String.format(
				"Average word length: %.2f\nLongest word: %s\nShortest word: %s\nMost common words: %s\nvowelCount: %d\nconsontantCount: %d\nTime taken: %.3f seconds",
				avgWordLength, longestWord, shortestWord, mostCommonWords, vowelCount, consontantCount, timeTaken);
		JOptionPane.showMessageDialog(this, results, "Word Analysis Results", JOptionPane.INFORMATION_MESSAGE);
	}

	public void run() {
		initializeTranslator();
		initComponents();
	}

}
