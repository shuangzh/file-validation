package cmsz.cmup.file.validator;

import java.util.LinkedList;
import java.util.List;

public class ValidateException extends Exception {

	private Section section;
	private String code;
	private String errmsg;
	private List<ValidateException> list = null;

	public ValidateException(Section section, String code, String errmsg) {
		this.section = section;
		this.code = code;
		this.errmsg = errmsg;
	}

	public void append(ValidateException ve) {
		if (list == null) {
			list = new LinkedList<ValidateException>();
		}
		list.add(ve);
	}

	public List<ValidateException> trace() {
		return this.list;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
