package implementations;

import java.util.regex.Pattern;

import interfaces.Regex.IRegexChecker;

public class PasswordCheckerImplementation implements IRegexChecker {

	private final Pattern pattern;
	private final static String patternString = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*(_|[^\\w])).{6,10}+$";
	
	public PasswordCheckerImplementation(){
		this.pattern = Pattern.compile(patternString);
	}
	
	@Override
	public boolean hasMatch(String string) {
		return pattern.matcher(string).matches();
	}

}
