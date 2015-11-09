package cmsz.cmup.file.validator;

public class Field {
	
	private String name;
	private String regx;
	private String value;
	private int begin = -1;
	private int end = -1;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegx() {
		return regx;
	}
	public void setRegx(String regx) {
		this.regx = regx;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
	public String toString()
	{
		StringBuilder sb=new StringBuilder();
		sb.append("{\"fieldName\":").append(this.name);
		sb.append(",\"regx\":").append(this.regx);
		sb.append(",\"value\":").append(this.value);
		sb.append(",begin\":").append(this.begin);
		sb.append(",\"end\":").append(this.end);
		sb.append("}");
		return sb.toString();
	}
	
}
