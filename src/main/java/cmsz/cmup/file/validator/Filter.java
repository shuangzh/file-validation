package cmsz.cmup.file.validator;

import java.util.List;

public interface Filter {
	
	void doFilter(Section section, FilterChain filterChain) throws ValidateException;
	
	List<Regular> getRegulars();
	
	
}
