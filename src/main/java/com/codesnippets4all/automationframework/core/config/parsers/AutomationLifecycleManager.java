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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.codesnippets4all.automationframework.core.config.handlers.AutomationConfig;
import com.codesnippets4all.automationframework.core.config.handlers.LifeCycleConfig;
import com.codesnippets4all.automationframework.core.exceptions.AutomationException;
import com.codesnippets4all.automationframework.core.execution.HierarchicalTaskExecutionManager;
import com.codesnippets4all.automationframework.core.execution.ITaskExecutionManager;
import com.codesnippets4all.automationframework.core.execution.status.TaskStatusManager;

/*
 * @author Rajesh Putta
 */
public class AutomationLifecycleManager implements ILifecycleManager {
	
	private AutomationConfig automationConfig=null;
	private Map<String, TaskHierarchyManager> taskHierarchyManagerMap=new HashMap<String, TaskHierarchyManager>();
	private ITaskExecutionManager taskExecutionManager=null;
	private Map<String, TaskStatusManager> taskStatusManagerMap=new HashMap<String, TaskStatusManager>();

	
	public void init(AutomationConfig automationConfig)
	{
		// initialization related activities
		this.automationConfig=automationConfig;
		this.taskExecutionManager=new HierarchicalTaskExecutionManager(this.automationConfig);
	}
	
	public Map<String,TaskHierarchyManager> getTaskHierarchyManagerMap()
	{
		return this.taskHierarchyManagerMap;
	}
	
	public void createHierarchy()
	{
		Map<String, LifeCycleConfig> lifeCycleConfig=this.automationConfig.getLifeCycleConfig();
		
		Set<Map.Entry<String, LifeCycleConfig>> entrySet=lifeCycleConfig.entrySet();
		
		for(Map.Entry<String, LifeCycleConfig> entry: entrySet)
		{
			String lifeCycleName = entry.getKey();
			LifeCycleConfig lifeCycle=entry.getValue();
			
			TaskHierarchyManager taskHierarchyManager=new TaskHierarchyManager();
			
			taskHierarchyManager.setLifecycleConfig(lifeCycle);
			
			taskHierarchyManager.initialize();
			
			taskHierarchyManagerMap.put(lifeCycleName, taskHierarchyManager);
		}
	}
	
	public void execute()
	{
		// executes all life cycles
		Map<String, LifeCycleConfig> lifecycleConfig=this.automationConfig.getLifeCycleConfig();
		
		Set<String> lifecycles=lifecycleConfig.keySet();
		
		for(String lifecycle: lifecycles)
		{
			this.executeLifeCycle(lifecycle);
		}
	}
	
	public void executeLifeCycle(String lifeCycleName)
	{
		// execute single life cycle
		if(this.taskExecutionManager==null)
		{
			throw new AutomationException("Automation Framework is not properly initialized...Task Execution Manager is not available...");
		}
		
		TaskHierarchyManager hierarchyManager=this.taskHierarchyManagerMap.get(lifeCycleName);
		
		if(hierarchyManager==null)
		{
			throw new AutomationException("Lifecycle requested for execution is not registered in the automation configuration file..."+lifeCycleName);
		}
		
		TaskStatusManager taskStatusManager=new TaskStatusManager();
		
		taskStatusManagerMap.put(lifeCycleName, taskStatusManager);
		
		this.taskExecutionManager.setTaskStatusManager(taskStatusManager);
		
		this.taskExecutionManager.execute(lifeCycleName, hierarchyManager);
	}
	
	public Map<String, TaskStatusManager> getTaskStatusManagerMap() {
		return taskStatusManagerMap;
	}
	
	public void destroy()
	{
		this.automationConfig=null;
		this.taskHierarchyManagerMap.clear();
		this.taskHierarchyManagerMap=null;
		
		this.taskExecutionManager.destroy();
	}
}
