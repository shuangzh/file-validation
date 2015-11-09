package cmsz.cmup.file.validator.impl;

import java.util.List;

import cmsz.cmup.file.validator.Field;
import cmsz.cmup.file.validator.FieldMatcher;
import cmsz.cmup.file.validator.Filter;
import cmsz.cmup.file.validator.FilterChain;
import cmsz.cmup.file.validator.Section;
import cmsz.cmup.file.validator.ValidateException;

public class FilterImpl implements Filter {
	
	final public static String C_TYPE_SEP = "sep";
	final public static String C_TYPE_FIX = "fix";

	private String seperator;
	private String type;
	private FieldMatcher fieldMatcher = null;

	private String startWidth;
	private int fixRowNum;

	private int fixLength;

	private List<Field> fieldList;

	
	private Section section;
	private String line;

	public void doFilter(Section section, FilterChain filterChain) throws ValidateException {
		this.section = section;
		line = section.getContent();
		if (startWidth != null && startWidth.trim().length() > 0) {
			if (line.startsWith(startWidth)) {
				if (fixRowNum != 0) {
					if ( fixRowNum >0 && fixRowNum != section.getPosition()) {
						throw new ValidateException(section, ValidateException.CODE_POSITION, ValidateException.EMSG_POSITION);
					}
				}

				if (this.type.compareTo(C_TYPE_SEP) == 0) {
					this.doSep();
				} else if (this.type.compareTo(C_TYPE_FIX) == 0) {
					this.doFix();
				} else {
					throw new ValidateException(section, ValidateException.CODE_NOTYPE, ValidateException.EMSG_NOTYPE);
				}
			} else {
				filterChain.doFilter(section);
				return;
			}
		} else {

			if (fixRowNum != 0) {
				if (fixRowNum >0 && fixRowNum != section.getPosition()) {
					filterChain.doFilter(section);
					return;
				}
			}

			if (this.type.compareTo(C_TYPE_SEP) == 0) {
				this.doSep();
			} else if (this.type.compareTo(C_TYPE_FIX) == 0) {
				this.doFix();
			} else {
				throw new ValidateException(section, ValidateException.CODE_NOTYPE, ValidateException.EMSG_NOTYPE);
			}
		}

	}

	public FieldMatcher getFieldMatcher() {
		return this.fieldMatcher;
	}

	public int getFixRowNum() {
		return this.fixRowNum;
	}

	public String getStartWidth() {
		return this.startWidth;
	}

	private void doSep() throws ValidateException {
		String[] fields = section.getContent().split(seperator);
//		if (fields.length != fieldList.size()) {
//			throw new ValidateException(this.section, ValidateException.CODE_LENGTH, ValidateException.EMSG_LENGTH);
//		}
		for (int i = 0; i < fieldList.size(); i++) {
			fieldList.get(i).setValue(fields[i]);
		}

		String errmsg = null;
		for (Field f : fieldList) {
			boolean b = this.fieldMatcher.match(f.getName(), f.getValue(), f.getRegx());
			if (b == false) {
				errmsg += " "+f.toString(); 
			}
		}

		if (errmsg != null) {
			throw new ValidateException(this.section, ValidateException.CODE_NOMATCH, ValidateException.EMSG_NOMATCH + errmsg);
		}
	}

	private void doFix() throws ValidateException {
		if (this.fixLength != line.length()) {
			throw new ValidateException(this.section, "", " length");
		}
		for (Field f : fieldList) {
			f.setValue(line.substring(f.getBegin(), f.getEnd()));
		}

		String errmsg = null;
		for (Field f : fieldList) {
			boolean b = this.fieldMatcher.match(f.getName(), f.getValue(), f.getRegx());
			if (b == false) {
				errmsg += f.toString() + " ";
			}
		}

		if (errmsg != null) {
			throw new ValidateException(this.section,ValidateException.CODE_NOMATCH, ValidateException.EMSG_NOMATCH + errmsg);
		}

	}

	public int getFixLength() {
		return this.fixLength;
	}

	public String getSeperator() {
		return seperator;
	}

	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Field> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}

	public void setFieldMatcher(FieldMatcher fieldMatcher) {
		this.fieldMatcher = fieldMatcher;
	}

	public void setStartWidth(String startWidth) {
		this.startWidth = startWidth;
	}

	public void setFixRowNum(int fixRowNum) {
		this.fixRowNum = fixRowNum;
	}

	public void setFixLength(int fixLength) {
		this.fixLength = fixLength;
	}
}
