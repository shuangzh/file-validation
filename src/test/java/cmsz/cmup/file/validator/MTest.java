package cmsz.cmup.file.validator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import cmsz.cmup.file.validator.impl.ValidatorImpl;

public class MTest {

	public static void main(String[] args) {
		
		ValidatorImpl v = new ValidatorImpl();
		
		v.setTemplateFile("ValidCfg.xml");
		
		try {
			v.prepare();
			v.validate(new FileInputStream("sample_1.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidateException e) {
			List<ValidateException> l=e.trace();
			for(ValidateException v2: l){
				System.out.println(v2.toString());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
