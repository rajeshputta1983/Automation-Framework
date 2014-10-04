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

/*
 * @author Rajesh Putta
 */
public class OutputTasksConfig implements Serializable{

	private static final long serialVersionUID = -5459772686238070756L;

	private Map<String, TaskConfig> config=new LinkedHashMap<String, TaskConfig>();
	
	private Configuration configuration;	
	
	public void addTaskConfig(TaskConfig taskConfig)
	{
		this.config.put(taskConfig.getName(), taskConfig);
	}
	
	public Map<String, TaskConfig> getConfig()
	{
		return this.config;
	}
	
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}
}
