package com.sangbill.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class SolrConfig {
	private static Properties config = new Properties();
	static {
		try {
			InputStream in = SolrConfig.class.getClassLoader().getResourceAsStream("solr.properties");
			config.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getStrParam(String key) {
		return config.getProperty(key);
	}

	public static List<String> getListParam(String key) {
		List<String> list = new ArrayList<String>();
		String value = config.getProperty(key);
		if(StringUtils.isBlank(value))
			return list;
		String[] s = value.split(",");
		for (int i = 0; i < s.length; i++) {
			list.add(s[i].trim());
		}
		return list;
	}
	
}
