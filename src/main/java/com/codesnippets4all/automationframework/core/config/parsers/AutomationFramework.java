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

import java.util.Map;

import com.codesnippets4all.automationframework.core.config.handlers.AutomationConfig;
import com.codesnippets4all.automationframework.core.config.handlers.InputTasksConfig;
import com.codesnippets4all.automationframework.core.config.handlers.LifeCycleConfig;
import com.codesnippets4all.automationframework.core.config.handlers.OutputTasksConfig;
import com.codesnippets4all.automationframework.core.config.handlers.ProcessorTasksConfig;
import com.codesnippets4all.automationframework.core.exceptions.AutomationException;
import com.codesnippets4all.automationframework.core.execution.status.TaskStatusManager;

/*
 * @author Rajesh Putta
 */
public class AutomationFramework implements IFramework {
	
	private AutomationConfig automationConfig=null;
	private ILifecycleManager lifeCycleManager=null;
	
	public void initialize(String configFilePath)
	{
		AutomationConfigParser parser=new AutomationConfigParser();
		
		parser.setConfigFilePath(configFilePath);
		
		parser.parse();
		
		this.automationConfig = parser.getAutomationConfiguration();
		

		this.lifeCycleManager=new AutomationLifecycleManager();
		
		this.lifeCycleManager.init(this.automationConfig);
		
		this.lifeCycleManager.createHierarchy();
	}
	
	public AutomationConfig getAutomationConfig()
	{
		return this.automationConfig;
	}
	
	public LifeCycleConfig getLifeCycleConfig(String lifeCycleName)
	{
		return this.automationConfig.getLifeCycleConfig().get(lifeCycleName);
	}
	
	public InputTasksConfig getInputTasksConfig(String lifeCycleName)
	{
		LifeCycleConfig lifeCycleConfig= this.getLifeCycleConfig(lifeCycleName);
		
		return lifeCycleConfig.getInputTasksConfig();
	}

	public OutputTasksConfig getOutputTasksConfig(String lifeCycleName)
	{
		LifeCycleConfig lifeCycleConfig= this.getLifeCycleConfig(lifeCycleName);
		
		return lifeCycleConfig.getOutputTasksConfig();
	}

	public ProcessorTasksConfig getProcessorTasksConfig(String lifeCycleName)
	{
		LifeCycleConfig lifeCycleConfig= this.getLifeCycleConfig(lifeCycleName);
		
		return lifeCycleConfig.getProcessorTasksConfig();
	}
	
	
	public TaskHierarchyManager getTaskHierarchyManager(String lifeCycleName)
	{
		return this.lifeCycleManager.getTaskHierarchyManagerMap().get(lifeCycleName);
	}
	
	public TaskHierarchy getInputTaskHierarchy(String lifeCycleName)
	{
		return this.getTaskHierarchyManager(lifeCycleName).getInputTaskHierarchy();
	}
	
	public TaskHierarchy getOutputTaskHierarchy(String lifeCycleName)
	{
		return this.getTaskHierarchyManager(lifeCycleName).getOutputTaskHierarchy();
	}

	public TaskHierarchy getProcessorTaskHierarchy(String lifeCycleName)
	{
		return this.getTaskHierarchyManager(lifeCycleName).getProcessorTaskHierarchy();
	}
	
	
	public void execute()
	{
		if(this.automationConfig==null)
		{
			throw new AutomationException("Automation Framework is not properly initialized with configuration...");
		}
		
		this.lifeCycleManager.execute();
	}
	
	public void executeLifeCycle(String lifeCycleName)
	{
		if(this.automationConfig==null)
		{
			throw new AutomationException("Automation Framework is not properly initialized with configuration...");
		}
		
		this.lifeCycleManager.executeLifeCycle(lifeCycleName);
	}
	
	public TaskStatusManager getTaskStatusManager(String lifeCycleName)
	{
		return this.lifeCycleManager.getTaskStatusManagerMap().get(lifeCycleName);
	}
	
	public Map<String, TaskStatusManager> getTaskStatusManagerMap() {
		return this.lifeCycleManager.getTaskStatusManagerMap();
	}
	
	public void destroy()
	{
		this.automationConfig=null;
		this.lifeCycleManager.destroy();
	}
}
