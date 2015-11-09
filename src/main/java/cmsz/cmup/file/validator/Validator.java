package cmsz.cmup.file.validator;

import java.io.IOException;
import java.io.InputStream;

public interface Validator {
	void validate(InputStream inputStrem) throws ValidateException, IOException ;
	void prepare() throws IOException;
	
}
