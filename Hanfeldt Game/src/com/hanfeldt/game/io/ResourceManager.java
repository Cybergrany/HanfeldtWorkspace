package com.hanfeldt.game.io;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hanfeldt.game.Main;

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
	
	public static void appendToFile(String string,  String path, String fileName){
		//Declare writer in try block to auto-close it
		try(FileWriter fw = new FileWriter(Main.class.getResourceAsStream(path+fileName).toString(), true)){
				Debug.printDebug("Modifying file:  " + path+fileName +  ", adding attribute " + string);
				fw.append(string);
		} catch (FileNotFoundException e) {
			Debug.printErrorDebug("File " + fileName + " not found at " + path);
		} catch (IOException e1) {
			Debug.printStackTraceDebug(e1);
		}
	}
	
	public static void clearFile(String path, String fileName){
		try {
			PrintWriter pw = new PrintWriter(Main.class.getResourceAsStream(path+fileName).toString());
			Debug.printDebug("Wiping file: "+ path+fileName);
			pw.close();
		} catch (IOException e) {
			Debug.printStackTraceDebug(e);
		}
	}
}
