package com.hanfeldt.game.io;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ResourceManager {
	
	public ResourceManager(){
		
	}
	
	public static void callMethodFromString(Object classObject, String methodName, Class<?> params, String... args){
		Method method = null;
		
		try{
			method = classObject.getClass().getMethod(methodName, params);
		}catch(SecurityException e){
			System.err.println("No permission to access method: " + methodName);
			Debug.printStackTraceDebug(e);
		}catch(NoSuchMethodException e){
			System.err.println("There is no such error as: " + methodName);
			Debug.printStackTraceDebug(e);
		}
		
		if(method != null){
			try{
				method.invoke(classObject, args[0]);
			} catch (IllegalArgumentException e) {
				System.err.println("IllegalArgumentException when calling method " + methodName);
				Debug.printStackTraceDebug(e);
			} catch (IllegalAccessException e) {
				System.err.println("IllegalAccessException when calling method " + methodName);
				Debug.printStackTraceDebug(e);
			} catch (InvocationTargetException e) {
				System.err.println("InvocationTargetException when calling method " + methodName);
				Debug.printStackTraceDebug(e);
			}
		}else{
			System.err.println("Critical error while calling method " + methodName + "\nMethod not found");
		}
	}
	
//	public static void invokeClassFromString(String className, String... args){
//		getClassFromString(className).;
//	}
	
	@SuppressWarnings("rawtypes")
	public static Class getClassFromString(String className){
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.err.println("Class " + className + " not found!");
			Debug.printStackTraceDebug(e);
		}
		return null;
	}
}
