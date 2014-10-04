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

package com.codesnippets4all.automationframework.core.config.parsers;

import com.codesnippets4all.automationframework.core.config.handlers.LifeCycleConfig;
import com.codesnippets4all.automationframework.core.exceptions.AutomationException;

/*
 * @author Rajesh Putta
 */
public class TaskHierarchyManager {
	
	private LifeCycleConfig lifecycleConfig=null;
	
	private TaskHierarchy inputTaskHierarchy=null;
	private TaskHierarchy processorTaskHierarchy=null;
	private TaskHierarchy outputTaskHierarchy=null;
	
	public void setLifecycleConfig(LifeCycleConfig lifecycleConfig) {
		this.lifecycleConfig = lifecycleConfig;
	}
	
	public TaskHierarchy getInputTaskHierarchy() {
		return inputTaskHierarchy;
	}
	
	public TaskHierarchy getProcessorTaskHierarchy() {
		return processorTaskHierarchy;
	}
	
	public TaskHierarchy getOutputTaskHierarchy() {
		return outputTaskHierarchy;
	}
	
	public void initialize()
	{
		if(this.lifecycleConfig==null)
			throw new AutomationException("Task Hierarchy cannot be created without Lifecycle Config is set ...");
		
		this.createInputTaskHierarchy();
		this.createProcessorTaskHierarchy();
		this.createOutputTaskHierarchy();
	}
	
	public void createInputTaskHierarchy()
	{
		this.inputTaskHierarchy=new TaskHierarchy();
		
		this.inputTaskHierarchy.transform(this.lifecycleConfig.getInputTasksConfig().getConfig());
	}
	
	public void createProcessorTaskHierarchy()
	{
		this.processorTaskHierarchy=new TaskHierarchy();
		
		this.processorTaskHierarchy.transform(this.lifecycleConfig.getProcessorTasksConfig().getConfig());
	}
	
	public void createOutputTaskHierarchy()
	{
		this.outputTaskHierarchy=new TaskHierarchy();
		
		this.outputTaskHierarchy.transform(this.lifecycleConfig.getOutputTasksConfig().getConfig());
	}
	
}
