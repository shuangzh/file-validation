package cmsz.cmup.file.validator.template;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("template")
public class Template {
	
	private List<FilterCfg>	filters;

	public List<FilterCfg> getFilters() {
		return filters;
	}

	public void setFilters(List<FilterCfg> filters) {
		this.filters = filters;
	}
	
}
