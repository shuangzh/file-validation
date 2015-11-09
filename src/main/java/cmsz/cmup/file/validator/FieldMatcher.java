package cmsz.cmup.file.validator;

public interface FieldMatcher {
	
	boolean match(String fieldName, String fieldValue, String fieldRegx);

}
