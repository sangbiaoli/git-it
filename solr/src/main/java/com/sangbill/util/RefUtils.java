package com.sangbill.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang.StringUtils;


public class RefUtils {
	/**
	 * 调用Getter方法.
	 * @throws Exception 
	 */
	public static Object invokeGetterMethod(Object obj, String propertyName) throws Exception {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(obj, getterMethodName, new Class[] {}, new Object[] {});
	}

	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) throws Exception {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}

	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {
		//Assert.notNull(obj, "object不能为空");

		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

				method.setAccessible(true);

				return method;

			} catch (NoSuchMethodException e) {//NOSONAR
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	public static void invokeSetterMethod(Object object, Field field,
			Object content) throws Exception {
		String setterMethodName = "set" + StringUtils.capitalize(field.getName());
		Object value = null;
		Class<?> clazz = field.getType();
		if(content instanceof String){			
			if(clazz == String.class){
				value = content;
			}else if(clazz == Integer.class){
				value = Integer.parseInt(content.toString());
			}else if(clazz == Date.class){
				value = DateUtil.convertToDateTime(content.toString());
			}
		}else{		//暂定content类型和field类型一样
			value = content;
		}
		invokeMethod(object, setterMethodName, new Class[] {clazz}, new Object[] {value});
	}
}
