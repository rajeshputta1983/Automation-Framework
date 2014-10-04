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

package com.codesnippets4all.automationframework.core.execution.status;

import java.util.HashMap;

import com.codesnippets4all.automationframework.core.exceptions.AutomationException;
import com.codesnippets4all.automationframework.core.execution.TaskStatus;
import com.codesnippets4all.automationframework.core.utils.Constants;

/*
 * @author Rajesh Putta
 */
public class TaskStatusContext extends HashMap<String, TaskStatus> {
	
	private static final long serialVersionUID = -40589902827398388L;

	public void addTaskStatus(String taskName, TaskStatus taskStatus)
	{
		if(taskName==null || taskName.trim().equals(Constants.EMPTY_STRING) || taskStatus==null)
		{
			throw new AutomationException("Invalid Task Name or Task Status for task ..."+taskName);
		}
		
		super.put(taskName, taskStatus);
	}
	
	public TaskStatus getTaskStatus(String taskName)
	{
		return super.get(taskName);
	}
	
	public TaskStatus remove(String taskName) {
		throw new AutomationException("Illegal Operation...");
	}
}
