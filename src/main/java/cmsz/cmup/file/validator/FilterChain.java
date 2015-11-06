package cmsz.cmup.file.validator;

public interface FilterChain {
	
	void doFilter(Section section) throws ValidateException;

}
