package cmsz.cmup.file.validator.template;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("field")
public class FieldCfg {
	public String name;
	public String regx;
	public int begin;
	public int end;
	
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
}
