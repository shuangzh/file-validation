package cmsz.cmup.file.validator.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cmsz.cmup.file.validator.FilterChain;
import cmsz.cmup.file.validator.ValidateException;
import cmsz.cmup.file.validator.Validator;

public class FileValidator implements Validator {
	
	private FilterChain chain;
	
	public void validate(InputStream inputStrem) throws ValidateException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStrem));
		ValidateException ve = null;
		String line = null;
		int n = 0;
		while ((line = reader.readLine()) != null) {
			try {
				n++;
				chain.doFilter(new LineSection(n, line));
			} catch (ValidateException e) {
				if (ve == null) {
					ve = new ValidateException(null, null, null);
				}
				ve.append(e);
			}
		}
		
		if( ve != null){
			throw ve;
		}
	}

}
