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
import java.util.LinkedHashMap;
import java.util.Map;

import org.xml.sax.Attributes;

import com.codesnippets4all.automationframework.core.config.types.ListenerType;
import com.codesnippets4all.automationframework.core.exceptions.AutomationConfigException;
import com.codesnippets4all.automationframework.core.exceptions.AutomationException;
import com.codesnippets4all.automationframework.core.listeners.LifeCycleListener;
import com.codesnippets4all.automationframework.core.listeners.TaskListener;
import com.codesnippets4all.automationframework.core.utils.Utility;

/*
 * @author Rajesh Putta
 */
public class ListenersConfig implements Serializable{

	private static final long serialVersionUID = -7491764836046244229L;

	private Map<String, ListenerConfig> config=new LinkedHashMap<String, ListenerConfig>();
	
	public void addListener(Attributes attr)
	{
		String name=attr.getValue("name");
		String className=attr.getValue("class");
		String type=attr.getValue("type");
		
		if(Utility.isNullOrEmpty(name, className, type))
		{
			throw new AutomationConfigException("Listener element's attributes are missing !! name, className and type are expected...");
		}
		
		ListenerConfig listener=new ListenerConfig();
		
		listener.setName(name.trim());
		listener.setListenerType(ListenerType.getListenerType(type.trim()));
		
		if(type.equals("task"))
			listener.setListener(Utility.getClass(className, TaskListener.class));
		else if(type.equals("lifecycle"))
			listener.setListener(Utility.getClass(className, LifeCycleListener.class));
	
		this.config.put(name, listener);
	}
	
	public void removeListener(String name)
	{
		this.config.remove(name);
	}
	
	public Map<String, ListenerConfig> getConfig()
	{
		return this.config;
	}
	
	public ListenerConfig getListener(String name)
	{
		ListenerConfig listener=this.config.get(name);
		
		if(listener==null)
		{
			throw new AutomationException("Listener being invoked is not registered in Automation-Config file..."+name);
		}
		
		return listener;
	}
}
