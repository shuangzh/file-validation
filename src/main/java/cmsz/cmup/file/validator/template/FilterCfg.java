package cmsz.cmup.file.validator.template;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("filter")
public class FilterCfg {
	
	public String type;
	public String startWidth;
	public int fixRowNum;
	public String seperator;
	public int fixLength;
	
	public List<FieldCfg>	fields;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStartWidth() {
		return startWidth;
	}
	public void setStartWidth(String startWidth) {
		this.startWidth = startWidth;
	}
	public int getFixRowNum() {
		return fixRowNum;
	}
	public void setFixRowNum(int fixRowNum) {
		this.fixRowNum = fixRowNum;
	}
	public String getSeperator() {
		return seperator;
	}
	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}
	public int getFixLength() {
		return fixLength;
	}
	
	public void setFixLength(int fixLength) {
		this.fixLength = fixLength;
	}
	public List<FieldCfg> getFields() {
		return fields;
	}
	public void setFields(List<FieldCfg> fields) {
		this.fields = fields;
	}
	
	
	
	
}
