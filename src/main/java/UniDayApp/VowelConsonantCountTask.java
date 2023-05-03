package UniDayApp;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class VowelConsonantCountTask extends Task {
    private AtomicInteger vowelCount;
    private AtomicInteger consonantCount;

    public VowelConsonantCountTask(List<String> words) {
        super(words);
        vowelCount = new AtomicInteger();
        consonantCount = new AtomicInteger();
    }

    @Override
    public void run() {
        words.forEach(word -> {
            for (char ch : word.toLowerCase().toCharArray()) {
                if (ch >= 'a' && ch <= 'z') {
                    if ("aeiou".contains(Character.toString(ch))) {
                        vowelCount.incrementAndGet();
                    } else {
                        consonantCount.incrementAndGet();
                    }
                }
            }
        });
    }

    public int getVowelCount() {
        return vowelCount.get();
    }

    public int getConsonantCount() {
        return consonantCount.get();
    }
}