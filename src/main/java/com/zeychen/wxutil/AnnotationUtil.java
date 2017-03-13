package com.zeychen.wxutil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zeychen.wxentity.TextMessage;

public class AnnotationUtil {
	private static Logger log = LoggerFactory.getLogger(AnnotationUtil.class);
	/**
	 * 获取实体类对应的数据库名
	 * @param clazz
	 * @return
	 */
	public static String getDbNameFromEntity(Class<?> clazz){
		Annotation[] ans =clazz.getAnnotations();
		String dbName = null;
		for(Annotation an : ans){
			if(an.annotationType().equals(ClassMapping.class)){
				dbName =  ((ClassMapping)an).dbName();
			}
		}
		return dbName;
	}
	
	/**
	 * 获取实体类对应的表名
	 * @param clazz
	 * @return
	 */
	public static String getTbNameFromEntity(Class<?> clazz){
		Annotation[] ans =clazz.getAnnotations();
		String tbName = null;
		for(Annotation an : ans){
			if(an.annotationType().equals(ClassMapping.class)){
				tbName =  ((ClassMapping)an).tableName();
			}
		}
		return tbName;
	}
	
	
	public static <T> T getUserInfoByKey(Map<String,String> entityValues , Class<T> targetClazz, T sourceObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Class<?> sourceClazz = sourceObject.getClass();
		Field[] fields = targetClazz.getDeclaredFields();//
		for(Field field:fields){//遍历属性
			Annotation[] fas = field.getAnnotations();
			String methodName = "";
			String fieldName = "";
			String fieldValue = "";
			for(Annotation fa:fas){//遍历属性的注解
				if(fa.annotationType().equals(FieldMapping.class)){
					fieldName =  ((FieldMapping)fa).columnName();
					log.debug(fieldName);
					fieldValue =  entityValues.get(fieldName);
					log.debug(fieldValue);
					methodName = convertSetterMethod(field.getName());
				}else{
					log.error("FieldMapping not setted");
				}
			}
			log.info(field.getType().toString());
			Method m = sourceClazz.getDeclaredMethod(methodName, field.getType());
			m.invoke(sourceObject, typeConvert(entityValues.get(fieldName),field.getType()));
		}
		return sourceObject;
	}
	
	/**
	 * 
	 * @param originValue
	 * @param fieldClazz
	 * @return
	 */
	public static Object typeConvert(String originValue , Class<?> fieldClazz){
		Object convertValue = null;
		if (fieldClazz == Integer.class) {
			convertValue = Integer.parseInt(originValue);
		} else if (fieldClazz == String.class) {
			convertValue = originValue;
		} else if (fieldClazz == Double.class) {
			convertValue = Double.parseDouble(originValue);
		} else if (fieldClazz == Float.class) {
			convertValue = Float.parseFloat(originValue);
		} else if (fieldClazz == Long.class) {
			convertValue = Long.parseLong(originValue);
		} else if (fieldClazz ==  Boolean.class) {
			convertValue = Boolean.parseBoolean(originValue);
		}
			
		return convertValue;
	}
	
	/**
	 * 
	 * @param fieldName
	 * @return
	 */
	private static String convertSetterMethod(String fieldName){
		StringBuilder setterName = new StringBuilder("set");
		setterName.append(String.valueOf(fieldName.charAt(0)).toUpperCase());
		setterName.append(fieldName.substring(1));
		return setterName.toString();
	}
	
	/**
	 * 
	 * @param fieldName
	 * @param className
	 * @return
	 */
	private String convertGetterMethod(String fieldName,String className){
		StringBuilder getterName = new StringBuilder();
		if("java.lang.Boolean".equalsIgnoreCase(className)){
			getterName.append("is");
		}else{
			getterName.append("get");
		}
		getterName.append(String.valueOf(fieldName.charAt(0)).toUpperCase());
		getterName.append(fieldName.substring(1));
		return getterName.toString();
		
	}
	public static void main(String[] dd){
		TextMessage tm = new TextMessage();
		try {
			Map ff = new HashMap();
			ff.put("toUserName", "ddddd");
			ff.put("fromUserName", "frfrfrf");
			ff.put("createTime", "fgfgfgf");
			ff.put("msgType", "ggtgt");
			ff.put("content", "hghghg");
			ff.put("msgId", "dfdsfdsfs");
			TextMessage tx = getUserInfoByKey(ff,TextMessage.class,tm);
			log.debug(tx.toString());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
}
