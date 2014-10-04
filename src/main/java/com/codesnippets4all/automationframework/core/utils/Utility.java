/*Copyright 2014 Rajesh Putta http://www.codesnippets4all.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
* 
*/

package com.codesnippets4all.automationframework.core.utils;

import java.io.IOException;
import java.io.InputStream;

import com.codesnippets4all.automationframework.core.exceptions.AutomationConfigException;
import com.codesnippets4all.automationframework.core.exceptions.AutomationException;

/*
 * @author Rajesh Putta
 */
public class Utility {
	public static boolean isNullOrEmpty(String... arguments)
	{
		for(String argument: arguments)
		{
			if(argument==null || argument.trim().equals(""))
				return true;
		}
		
		return false;
	}
	
	public static String concatenate(String... arguments){
		StringBuilder builder=new StringBuilder();
		
		for(String argument:arguments)
		{
			builder.append(argument);
		}
		
		return builder.toString();
	}
	
	public static Class<?> getClass(String className)
	{
		if(className==null || className.trim().equals(""))
			throw new IllegalArgumentException("Class Name is invalid...");
		
		try {
			return Utility.class.getClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new AutomationConfigException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getClass(String className, Class<T> clazz)
	{
		if(className==null || className.trim().equals(""))
			throw new IllegalArgumentException("Class Name is invalid...");
		
		try {
			return (T)Utility.class.getClassLoader().loadClass(className).newInstance();
		} catch (ClassNotFoundException e) {
			throw new AutomationConfigException(e);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static InputStream loadStream(String classpathResource)
	{
		if(classpathResource==null || classpathResource.trim().equals(Constants.EMPTY_STRING))
			throw new IllegalArgumentException("the classpath resource to be loaded cannot be null...");
		
		InputStream iStream = Utility.class.getResourceAsStream(classpathResource);
		
		if(iStream==null)
		{
			iStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(classpathResource);
		}
		
		if(iStream==null)
			throw new AutomationException("stream cannot be loaded from classpath..."+classpathResource);
		
		return iStream;
	}
	
	public static void closeStream(InputStream iStream)
	{
		if(iStream!=null)
		{
			try {
				iStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
