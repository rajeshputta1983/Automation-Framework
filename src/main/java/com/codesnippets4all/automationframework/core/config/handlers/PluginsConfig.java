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

import com.codesnippets4all.automationframework.core.config.types.PluginType;
import com.codesnippets4all.automationframework.core.input.InputPlugin;
import com.codesnippets4all.automationframework.core.output.OutputPlugin;
import com.codesnippets4all.automationframework.core.processor.ProcessorPlugin;
import com.codesnippets4all.automationframework.core.utils.Utility;

/*
 * @author Rajesh Putta
 */
public class PluginsConfig implements Serializable{
	
	private static final long serialVersionUID = 6553133742435778886L;

	private Map<String, PluginConfig> config=new HashMap<String, PluginConfig>();
	
	public void addPlugin(Attributes attr)
	{
		String name=attr.getValue("name");
		
		String className=attr.getValue("class");
		
		String type=attr.getValue("type");
		
		if(Utility.isNullOrEmpty(name, className, type))
		{
			throw new IllegalArgumentException("Plug-in element should consists of name, class and type attributes configured...");
		}
		
		PluginConfig pluginConfig=new PluginConfig();
		pluginConfig.setName(name);
		
		if(type.equals("input"))
			pluginConfig.setPlugin(Utility.getClass(className, InputPlugin.class));
		else if(type.equals("processor"))
			pluginConfig.setPlugin(Utility.getClass(className, ProcessorPlugin.class));
		else if(type.equals("output"))
			pluginConfig.setPlugin(Utility.getClass(className, OutputPlugin.class));
		
		pluginConfig.setPluginType(PluginType.getPluginType(type));
		
		this.config.put(name, pluginConfig);
	}
	
	public void removePlugin(String name)
	{
		this.config.remove(name);
	}
	
	public Map<String, PluginConfig> getConfig()
	{
		return this.config;
	}
	
}
