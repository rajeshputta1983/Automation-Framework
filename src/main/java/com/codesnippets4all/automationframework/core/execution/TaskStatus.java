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

package com.codesnippets4all.automationframework.core.execution;

import com.codesnippets4all.automationframework.core.config.parsers.TaskNode;

/*
 * @author Rajesh Putta
 */
public class TaskStatus {

	private String taskName;
	
	private TaskStatusType type = null;

	private Object result = null;

	private TaskNode node=null;
	
	public void setNode(TaskNode node) {
		this.node = node;
	}
	
	public TaskNode getNode() {
		return node;
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public TaskStatus(TaskStatusType type, Object result) {
		this.type = type;
		this.result = result;
	}
	
	public TaskStatus(String taskName, TaskStatusType type, Object result) {
		this.taskName=taskName;
		this.type = type;
		this.result = result;
	}

	public TaskStatusType getType() {
		return type;
	}

	public void setType(TaskStatusType type) {
		this.type = type;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
