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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;

import com.codesnippets4all.automationframework.core.config.types.TriggerType;
import com.codesnippets4all.automationframework.core.exceptions.AutomationConfigException;
import com.codesnippets4all.automationframework.core.utils.Utility;

/*
 * @author Rajesh Putta
 */
public class TriggersConfig implements Serializable {

	private static final long serialVersionUID = -452666387244730560L;

	private Map<TriggerType, List<String>> config=new HashMap<TriggerType, List<String>>();
	
	public void addTriggerConfig(Attributes attr)
	{
		String name=attr.getValue("name");
		String type=attr.getValue("type");
		
		if(Utility.isNullOrEmpty(name, type))
		{
			throw new AutomationConfigException("Trigger -> Trigger element name, type attributes are mandatory...");
		}
		
		TriggerType triggerType = TriggerType.getTriggerType(type);
		
		List<String> listeners=this.config.get(triggerType);
		
		if(listeners==null)
		{
			listeners = new ArrayList<String>();
		}
		
		listeners.add(name.trim());
		
		this.config.put(triggerType, listeners);
				
	}
	
	public Map<TriggerType, List<String>> getConfig()
	{
		return this.config;
	}
	
}
