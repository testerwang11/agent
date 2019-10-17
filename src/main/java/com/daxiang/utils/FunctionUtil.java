package com.daxiang.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionUtil {

	private static final Map<String, Class<? extends Function>> functionsMap = new HashMap<String, Class<? extends Function>>();

	static {
		//bodyfile 特殊处理
		functionsMap.put("bodyfile", null);
		List<Class<?>> clazzes = ClassFinder.getAllAssignedClass(Function.class);
		clazzes.forEach((clazz) -> {
			try {
				Function tempFunc = (Function) clazz.newInstance();
				String referenceKey = tempFunc.getReferenceKey();
				if (referenceKey.length() > 0) { // ignore self
					functionsMap.put(referenceKey, tempFunc.getClass());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}
	
	public static boolean isFunction(String functionName){
		return functionsMap.containsKey(functionName);
	}
	
	public static String getValue(String functionName,String[] args){
		try {
			return functionsMap.get(functionName).newInstance().execute(args);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

    public static void main(String[] args) {
        System.out.println(getValue("randomPhone", null));

    }
}

