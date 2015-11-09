package cmsz.cmup.file.validator.impl;

import java.util.List;

import cmsz.cmup.file.validator.Filter;
import cmsz.cmup.file.validator.FilterChain;
import cmsz.cmup.file.validator.Section;
import cmsz.cmup.file.validator.ValidateException;

public class FilterChainImpl implements FilterChain {

	private List<Filter> filterlist;
	private int index = 0;
	private InnerChain innerChain = new InnerChain();

	public void doFilter(Section section) throws ValidateException {
		index = 0;
		innerChain.doFilter(section);
	}
	
	public Filter getMatchedFilter() {
		return innerChain.getMatchedFilter();
	}

	protected class InnerChain implements FilterChain {
		private Filter currFilter = null;

		public void doFilter(Section section) throws ValidateException {
			int size = filterlist.size();
			if (index < size) {
				currFilter = filterlist.get(index++);
				currFilter.doFilter(section, innerChain);
			} else {
				currFilter = null;
				throw new ValidateException(section, ValidateException.CODE_NOFILTER, ValidateException.EMSG_NOFILTER);
			}
		}

		public Filter getMatchedFilter() {
			return currFilter;
		}

	}

	public List<Filter> getFilterlist() {
		return filterlist;
	}

	public void setFilterlist(List<Filter> filterlist) {
		this.filterlist = filterlist;
	}

}
