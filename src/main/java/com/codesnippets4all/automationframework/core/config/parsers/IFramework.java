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
import com.codesnippets4all.automationframework.core.execution.status.TaskStatusManager;

/*
 * @author Rajesh Putta
 */
public interface IFramework {
	
	public void initialize(String configFilePath);

	public AutomationConfig getAutomationConfig();
	public LifeCycleConfig getLifeCycleConfig(String lifeCycleName);

	public InputTasksConfig getInputTasksConfig(String lifeCycleName);
	public OutputTasksConfig getOutputTasksConfig(String lifeCycleName);
	public ProcessorTasksConfig getProcessorTasksConfig(String lifeCycleName);

	public TaskHierarchyManager getTaskHierarchyManager(String lifeCycleName);

	public TaskHierarchy getInputTaskHierarchy(String lifeCycleName);
	public TaskHierarchy getOutputTaskHierarchy(String lifeCycleName);
	public TaskHierarchy getProcessorTaskHierarchy(String lifeCycleName);

	public void execute();
	public void executeLifeCycle(String lifeCycleName);
	
	public TaskStatusManager getTaskStatusManager(String lifeCycleName);
	public Map<String, TaskStatusManager> getTaskStatusManagerMap();
	
	public void destroy();

}
