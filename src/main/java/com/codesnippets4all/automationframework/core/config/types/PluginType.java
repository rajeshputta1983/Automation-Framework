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

package com.codesnippets4all.automationframework.core.config.types;

import java.util.HashMap;
import java.util.Map;

/*
 * @author Rajesh Putta
 */
public enum PluginType {

	INPUT("input"), PROCESSOR("processor"), OUTPUT("output");

	private static Map<String, PluginType> pluginTypes=new HashMap<String, PluginType>();
	
	private String type=null;
	
	PluginType(String type){
		this.type=type;
	}
	
	static{
		PluginType[] typeArray=PluginType.values();
		
		for(PluginType lType:typeArray)
		{
			pluginTypes.put(lType.type, lType);
		}
	}
	
	public static PluginType getPluginType(String type)
	{
		return pluginTypes.get(type);
	}
}
