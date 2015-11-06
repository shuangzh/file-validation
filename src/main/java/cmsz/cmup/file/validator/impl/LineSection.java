package cmsz.cmup.file.validator.impl;

import cmsz.cmup.file.validator.Section;

public class LineSection implements Section {

	private int rownum;
	private String line;

	public LineSection(int rownum, String line) {
		this.rownum = rownum;
		this.line = line;
	}

	public int getPosition() {
		return this.rownum;
	}

	public String getContent() {
		return this.line;
	}

}
