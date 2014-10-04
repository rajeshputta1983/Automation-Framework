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
import com.codesnippets4all.automationframework.core.execution.status.TaskStatusManager;

/*
 * @author Rajesh Putta
 */
public interface ILifecycleManager {
	
	public void init(AutomationConfig automationConfig);
	
	public Map<String,TaskHierarchyManager> getTaskHierarchyManagerMap();
	
	public void createHierarchy();
	
	public void execute();
	
	public void executeLifeCycle(String lifeCycleName);
	
	public Map<String, TaskStatusManager> getTaskStatusManagerMap();
	
	public void destroy();
}
