package UniDayApp;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;

class Translator {
	private static final String ARABIC_LANGUAGE_CODE = "ar";
	private static final String KURDISH_LANGUAGE_CODE = "ckb";
	private final Translate translate;

	public Translator(Translate translate) {
		this.translate = translate;
	}

	public String translateToArabic(String text) {
		return translate(text, ARABIC_LANGUAGE_CODE);
	}

	public String translateToKurdish(String text) {
		return translate(text, KURDISH_LANGUAGE_CODE);
	}

	private String translate(String text, String targetLanguage) {
		Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage(targetLanguage));
		return translation.getTranslatedText();
	}
}