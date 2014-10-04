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
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import com.codesnippets4all.automationframework.core.exceptions.AutomationConfigException;
import com.codesnippets4all.automationframework.core.utils.Utility;

/*
 * @author Rajesh Putta
 */
public class ErrorHandlers implements Serializable{

	private static final long serialVersionUID = 3255667740915416485L;

	private List<String> config=new ArrayList<String>();
	
	public void addErrorHandler(Attributes attr)
	{
		String name=attr.getValue("name");
		
		if(Utility.isNullOrEmpty(name))
		{
			throw new AutomationConfigException("error-handlers -> error-handler config element should have name attribute configured...");
		}
		
		this.config.add(name.trim());
	}
	
	public List<String> getConfig()
	{
		return this.config;
	}
}
