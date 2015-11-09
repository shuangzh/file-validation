package cmsz.cmup.file.validator.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cmsz.cmup.file.validator.Field;
import cmsz.cmup.file.validator.Filter;
import cmsz.cmup.file.validator.FilterChain;
import cmsz.cmup.file.validator.Section;
import cmsz.cmup.file.validator.ValidateException;
import cmsz.cmup.file.validator.Validator;

public class ValidatorImpl implements Validator {
	
	private String regxFile;
	
	private FilterChainImpl chain;
	private List<Filter> filterList;
	private MinusRowNumChecker minusRowNumChecker;

	public void validate(InputStream inputStrem) throws ValidateException, IOException {
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
					ve = new ValidateException(null, ValidateException.CODE_FAILED,  ValidateException.EMSG_FAILED);
				}
				ve.append(e);
			}
		}

		if (ve != null) {
			throw ve;
		}
	}

	public void prepare() throws IOException {
		InputStream in =new FileInputStream(regxFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		
		this.filterList = new ArrayList<Filter>();
		while ((line = reader.readLine()) != null) {
			if( StringUtils.isNotEmpty(line) && StringUtils.isNotBlank(line))
			{
				Filter f = this.createFilter(line);
				this.filterList.add(f);
			}
		}
		this.chain = new FilterChainImpl();
		chain.setFilterlist(filterList);
		
		minusRowNumChecker= new MinusRowNumChecker();
		
	}

	protected class MinusRowNumChecker {
		Map<Filter, Integer> filterRowMap = new HashMap<Filter, Integer>();
		Map<Filter, Section> filterSectionMap = new HashMap<Filter, Section>();

		public void check(Filter filter, Section section) throws ValidateException {
			if (filter.getFixRowNum() < 0) {
				if (filterRowMap.containsKey(filter)) {
					throw new ValidateException(section, ValidateException.CODE_REPEATED, ValidateException.EMSG_REPEATED);
				} else {
					filterRowMap.put(filter, filter.getFixRowNum());
					filterSectionMap.put(filter, section);
				}
			}

			for (Filter f : filterRowMap.keySet()) {
				Integer r = filterRowMap.get(f);
				filterRowMap.put(f, ++r);
				if (r == 1) {
					throw new ValidateException(filterSectionMap.get(f), ValidateException.CODE_POSITION, ValidateException.EMSG_POSITION);
				}
			}
		}
	}
	
	
	private Filter createFilter(String jsonDefine)
	{
		// {"type":"sep","startWidth":"10","fixRowNum":2,"seperator":"\\|","fixLength":50,
		//		"fieldMatcherClass":"default",fields:[{"name":"f1","regx":"xxddff","begin":0,"end":10}, {}]}
		
		JSONObject json=JSONObject.fromObject(jsonDefine);
		FilterImpl filter=new FilterImpl();
		
		filter.setType(json.getString("type"));
		filter.setStartWidth(json.getString("startWidth"));
		filter.setSeperator(json.getString("seperator"));
		filter.setFixRowNum(json.getInt("fixRowNum"));
		filter.setFixLength(json.getInt("fixLength"));
		
		JSONArray jarray=JSONArray.fromObject(json.get("fields"));
		List<Field> list=new ArrayList<Field>();
		
		for(int i=0; i< jarray.size(); i++){
			Field f = new Field();
			JSONObject j = jarray.getJSONObject(i);
			f.setName(j.getString("name"));
			f.setRegx(j.getString("regx"));
			f.setBegin(j.getInt("begin"));
			f.setEnd(j.getInt("end"));
			list.add(f);
		}
		
		filter.setFieldList(list);
		
		filter.setFieldMatcher(new DefaultFieldMatcherImpl());
		return filter;
	}

	public String getRegxFile() {
		return regxFile;
	}

	public void setRegxFile(String regxFile) {
		this.regxFile = regxFile;
	}
	
}
