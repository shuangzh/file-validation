package cmsz.cmup.file.validator;

import java.util.LinkedList;
import java.util.List;

public class ValidateException extends Exception {
	
	
	public static String CODE_FAILED = "0001";					// 通用错误， 验证失败
	public static String EMSG_FAILED = "validate failed";
	
	public static String CODE_REPEATED = "0002";							// 固定行，出现一次以上
	public static String EMSG_REPEATED = "fix row repeated";
	
	public static String CODE_POSITION = "0003";							// 固定行，出现一次以上
	public static String EMSG_POSITION = "fix row position is not right";
	
	public static String CODE_NOFILTER = "0004";							// 固定行，出现一次以上
	public static String EMSG_NOFILTER = "there is not filter to handle";
	
	public static String CODE_NOTYPE = "0005";							// 
	public static String EMSG_NOTYPE = "the filter type is wrong";
	
	public static String CODE_NOMATCH = "0006";							// 
	public static String EMSG_NOMATCH = "field not match regx ";
	
	public static String CODE_LENGTH = "0007";							// 
	public static String EMSG_LENGTH = "the length is not right";
	

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
	
	public String toString()
	{
		StringBuilder sb=new StringBuilder();
		sb.append("ValidateException:{").append("section:position=").append(this.section.getPosition());
		sb.append(",content=").append(this.section.getContent());
		sb.append(",code:").append(this.code).append(",msg:").append(this.errmsg);
		sb.append("}");
		return sb.toString();
	}

}
