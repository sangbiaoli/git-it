package com.sangbill.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
	private static  ObjectMapper jsonMapper = new ObjectMapper();
	/**
	 * 装换为json格式字符串
	 * @param obj
	 * @return	
	 */
	public static String toJson(Object obj) {
		try {
			return jsonMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @param json:json数据格式的数组
	 * @param clazz：实体类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List jsonToList(String json, Class<?> clazz){
		try {
			List list = new ArrayList();
			List mapList = jsonMapper.readValue(json, List.class);
			for (int i = 0; i < mapList.size(); i++) {
				LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) mapList.get(i);
				Object obj = clazz.newInstance();
				Field[] fileds = obj.getClass().getDeclaredFields();
				for (Field field : fileds) {
					String name = field.getName();
					if(map.containsKey(name) && map.get(name)!= null){
						//json会把原来的Date类型转换成时间戳，Float装换成Double
						if(field.getType().equals(Date.class)){
							Long l = (Long) map.get(name);
							Date d = DateUtil.convertTimestampToDate(l);
							RefUtils.invokeSetterMethod(obj,field,d);
						}else if(field.getType().equals(Float.class)){
							Double f = (Double)(Object)map.get(name);		
							RefUtils.invokeSetterMethod(obj, field, f);
						}else{
							RefUtils.invokeSetterMethod(obj, field, map.get(name));
						}
					}
				}
				list.add(obj);
			}
			return list;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
