package UniDayApp;

import java.util.List;

abstract class Task implements Runnable {
	protected List<String> words;
	protected Object result;

	public Task(List<String> words) {
		this.words = words;
	}

	public Object getResult() {
		return result;
	}
}
