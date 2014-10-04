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


package com.codesnippets4all.automationframework.core.config.handlers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

import com.codesnippets4all.automationframework.core.exceptions.AutomationConfigException;
import com.codesnippets4all.automationframework.core.mappers.IExceptionMapper;
import com.codesnippets4all.automationframework.core.utils.Utility;

/*
 * @author Rajesh Putta
 */
public class ExceptionMapperConfig implements Serializable{

	private static final long serialVersionUID = 3959033508132261422L;

	private Map<String, ExceptionMapper> config=new HashMap<String, ExceptionMapper>();
	
	public void addExceptionMapper(Attributes attr)
	{
		String name = attr.getValue("name");
		
		String className = attr.getValue("class");
		
		if(Utility.isNullOrEmpty(name, className))
		{
			throw new AutomationConfigException("exception-mapper-config element requires name, class as mandatory attributes...");
		}
		
		ExceptionMapper mapper=new ExceptionMapper();
		mapper.setName(name);
		mapper.setExceptionMapper(Utility.getClass(className, IExceptionMapper.class));
		
		this.config.put(name, mapper);
	}
	
	public void removeExceptionMapper(String name)
	{
		this.config.remove(name);
	}
	
	public Map<String, ExceptionMapper> getConfig()
	{
		return this.config;
	}
}
