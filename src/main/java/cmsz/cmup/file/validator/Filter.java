package cmsz.cmup.file.validator;


public interface Filter {
	
	
	void doFilter(Section section, FilterChain filterChain) throws ValidateException;
	
	FieldMatcher getFieldMatcher();
	
	int getFixRowNum();
	int getFixLength();
	String getStartWidth();
	
	
	
}
