package cmsz.cmup.file.validator.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cmsz.cmup.file.validator.Filter;
import cmsz.cmup.file.validator.FilterChain;
import cmsz.cmup.file.validator.Regular;
import cmsz.cmup.file.validator.RowNumRegular;
import cmsz.cmup.file.validator.Section;
import cmsz.cmup.file.validator.ValidateException;

public class LineFilterImpl implements Filter, RowNumRegular {

	private String startStr;
	private String seperator;

	private List<String> fieldNames;
	private List<String> fieldRegx;
	private int fixRowNum;
	
	
	private List<Regular> regList=new ArrayList<Regular>();
	
	public LineFilterImpl()
	{
		regList.add(this);
	}
	
	
	public int getRowNum() {
		return fixRowNum;
	}

	public void doFilter(Section section, FilterChain filterChain) throws ValidateException {
		int row = section.getPosition();
		String line = section.getContent();
		if (line.startsWith(startStr)) {
			String[] fields = line.split(seperator);
			if (fields.length != fieldRegx.size()) {
				throw new ValidateException(section, "ERR001", "字段数目不匹配");
			}

			String errmsg = null;
			for (int i = 0; i < fieldRegx.size(); i++) {
				String fieldValue = fields[i];
				String regx = fieldRegx.get(i);
				String name = fieldNames.get(i);
				Pattern pattern = Pattern.compile(regx);
				Matcher matcher = pattern.matcher(fieldValue);
				if (!matcher.matches()) {
					errmsg +=" field :  "+ name + " "+ regx + " not matchs " + fieldValue+ " - ";
				}
			}
			if(errmsg!=null){
				throw new ValidateException(section, "ERR000", errmsg);
			}
		} else {
			filterChain.doFilter(section);
		}
	}

	public List<Regular> getRegulars() {
		return regList;
		
	}


	public String getStartStr() {
		return startStr;
	}


	public void setStartStr(String startStr) {
		this.startStr = startStr;
	}


	public String getSeperator() {
		return seperator;
	}


	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}


	public List<String> getFieldNames() {
		return fieldNames;
	}


	public void setFieldNames(List<String> fieldNames) {
		this.fieldNames = fieldNames;
	}


	public List<String> getFieldRegx() {
		return fieldRegx;
	}


	public void setFieldRegx(List<String> fieldRegx) {
		this.fieldRegx = fieldRegx;
	}


	public int getFixRowNum() {
		return fixRowNum;
	}


	public void setFixRowNum(int fixRowNum) {
		this.fixRowNum = fixRowNum;
	}

}
