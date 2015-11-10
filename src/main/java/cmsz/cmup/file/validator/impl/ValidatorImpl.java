package cmsz.cmup.file.validator.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.xstream.XStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cmsz.cmup.file.validator.Field;
import cmsz.cmup.file.validator.Filter;
import cmsz.cmup.file.validator.FilterChain;
import cmsz.cmup.file.validator.Section;
import cmsz.cmup.file.validator.ValidateException;
import cmsz.cmup.file.validator.Validator;
import cmsz.cmup.file.validator.template.FieldCfg;
import cmsz.cmup.file.validator.template.FilterCfg;
import cmsz.cmup.file.validator.template.Template;

public class ValidatorImpl implements Validator {

	private String regxFile;
	private FilterChainImpl chain;
	private List<Filter> filterList;
	private MinusRowNumChecker minusRowNumChecker;
	
	
	public String getTemplateFile() {
		return regxFile;
	}

	public void setTemplateFile(String regxFile) {
		this.regxFile = regxFile;
	}
	

	public void validate(InputStream inputStrem) throws ValidateException, IOException {
		initFilters();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStrem));
		ValidateException ve = null;
		String line = null;
		int n = 0;
		while ((line = reader.readLine()) != null) {
			try {
				n++;
				Section section = new LineSection(n, line);
				chain.doFilter(section);
				minusRowNumChecker.check(chain.getMatchedFilter(), section);
			} catch (ValidateException e) {
				if (ve == null) {
					ve = new ValidateException(null, ValidateException.CODE_FAILED, ValidateException.EMSG_FAILED);
				}
				ve.append(e);
			}
		}
		if (ve != null) {
			throw ve;
		}
	}


	protected class MinusRowNumChecker {
		Map<Filter, Integer> filterRowMap = new HashMap<Filter, Integer>();
		Map<Filter, Section> filterSectionMap = new HashMap<Filter, Section>();

		public void check(Filter filter, Section section) throws ValidateException {
			if (filter.getFixRowNum() < 0) {
				if (filterRowMap.containsKey(filter)) {
					throw new ValidateException(section, ValidateException.CODE_REPEATED,
							ValidateException.EMSG_REPEATED);
				} else {
					filterRowMap.put(filter, filter.getFixRowNum());
					filterSectionMap.put(filter, section);
				}
			}

			for (Filter f : filterRowMap.keySet()) {
				Integer r = filterRowMap.get(f);
				filterRowMap.put(f, ++r);
				if (r == 1) {
					throw new ValidateException(filterSectionMap.get(f), ValidateException.CODE_POSITION,
							ValidateException.EMSG_POSITION);
				}
			}
		}
	}

	private void initFilters() throws IOException {
		XStream xs = new XStream();
		xs.setMode(XStream.NO_REFERENCES);
		xs.processAnnotations(new Class[] { Template.class, FilterCfg.class, FieldCfg.class });
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(regxFile);
		Template template = (Template) xs.fromXML(in);
		filterList = new ArrayList<Filter>();
		for (FilterCfg fcfg : template.getFilters()) {
			FilterImpl filter = new FilterImpl();
			filter.setType(fcfg.getType());
			filter.setSeperator(fcfg.getSeperator());
			filter.setStartWidth(fcfg.getStartWidth());
			filter.setFixLength(fcfg.getFixLength());
			filter.setFixRowNum(fcfg.getFixRowNum());
			List<Field> list = new ArrayList<Field>();
			for (FieldCfg cf : fcfg.getFields()) {
				Field field = new Field();
				field.setName(cf.getName());
				field.setRegx(cf.getRegx());
				field.setBegin(cf.getBegin());
				field.setEnd(cf.getEnd());
				list.add(field);
			}
			filter.setFieldList(list);
			filter.setFieldMatcher(new DefaultFieldMatcherImpl());
			filterList.add(filter);
		}
		this.chain = new FilterChainImpl();
		chain.setFilterlist(filterList);
		minusRowNumChecker = new MinusRowNumChecker();
	}


	

//	private Filter createFilter(String jsonDefine) {
//		// {"type":"sep","startWidth":"10","fixRowNum":2,"seperator":"\\|","fixLength":50,
//		// "fieldMatcherClass":"default",fields:[{"name":"f1","regx":"xxddff","begin":0,"end":10},
//		// {}]}
//		JSONObject json = JSONObject.fromObject(jsonDefine);
//		FilterImpl filter = new FilterImpl();
//
//		filter.setType(json.getString("type"));
//		filter.setStartWidth(json.getString("startWidth"));
//		filter.setSeperator(json.getString("seperator"));
//		filter.setFixRowNum(json.getInt("fixRowNum"));
//		filter.setFixLength(json.getInt("fixLength"));
//
//		JSONArray jarray = JSONArray.fromObject(json.get("fields"));
//		List<Field> list = new ArrayList<Field>();
//
//		for (int i = 0; i < jarray.size(); i++) {
//			Field f = new Field();
//			JSONObject j = jarray.getJSONObject(i);
//			f.setName(j.getString("name"));
//			f.setRegx(j.getString("regx"));
//			f.setBegin(j.getInt("begin"));
//			f.setEnd(j.getInt("end"));
//			list.add(f);
//		}
//		filter.setFieldList(list);
//		filter.setFieldMatcher(new DefaultFieldMatcherImpl());
//		return filter;
//	}


}
