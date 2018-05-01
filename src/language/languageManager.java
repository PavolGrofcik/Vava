package language;

import java.util.Locale;
import java.util.ResourceBundle;

public class languageManager {

	private  String slovak = "Slovenčina";
	private  String slovakShort = "sk";
	
	private String english = "English";
	private String englishShort ="en";
	
	private  Locale localeSk = new Locale(slovak,slovakShort);
	private Locale localeEn = new Locale(english,englishShort);
	
	@SuppressWarnings("unused")
	private ResourceBundle r = null;
	
	public ResourceBundle slovakLanguage() {
	    return ResourceBundle.getBundle("language/languageSk",localeSk);
	}
	
	public ResourceBundle englishLanguage() {
		return ResourceBundle.getBundle("language/languageEn",localeEn);
	}
}
