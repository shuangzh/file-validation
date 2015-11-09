package cmsz.cmup.file.validator.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cmsz.cmup.file.validator.FieldMatcher;

public class DefaultFieldMatcherImpl implements FieldMatcher {
	
	Map<String, Pattern> patternMap=new HashMap<String, Pattern>();

	public boolean match(String fieldName, String fieldValue, String fieldRegx) {
		Pattern pattern=patternMap.get(fieldRegx);
		if(pattern == null){
			pattern=Pattern.compile(fieldRegx);
			patternMap.put(fieldRegx, pattern);
		}
		Matcher matcher = pattern.matcher(fieldValue);
		return matcher.matches();
	}

}
