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

/*
 * @author Rajesh Putta
 */
public class Configuration implements Serializable{

	private static final long serialVersionUID = 7338176822814067900L;

	private Map<String, String> config=new HashMap<String, String>();
	
	public void addConfig(Attributes attr)
	{
		String name=attr.getValue("name");
		String value=attr.getValue("value");
		
		if(name==null || name.trim().equals("") || value==null)
			throw new AutomationConfigException("configuration -> key name/value attributes pair is mandatory...");
		
		this.config.put(name.trim(), value.trim());
	}
	
	public void setConfig(Map<String, String> config)
	{
		this.config=config;
	}
	
	public Map<String, String> getConfig()
	{
		return this.config;
	}
}
