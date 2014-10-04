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

/*
 * @author Rajesh Putta
 */
public class TaskStatusManager {

	private TaskStatusContext inputTasksContext = null;
	private TaskStatusContext processorTasksContext = null;
	private TaskStatusContext outputTasksContext = null;

	public TaskStatusContext getInputTasksContext() {
		return inputTasksContext;
	}

	public void setInputTasksContext(TaskStatusContext inputTasksContext) {
		this.inputTasksContext = inputTasksContext;
	}

	public TaskStatusContext getProcessorTasksContext() {
		return processorTasksContext;
	}

	public void setProcessorTasksContext(TaskStatusContext processorTasksContext) {
		this.processorTasksContext = processorTasksContext;
	}

	public TaskStatusContext getOutputTasksContext() {
		return outputTasksContext;
	}

	public void setOutputTasksContext(TaskStatusContext outputTasksContext) {
		this.outputTasksContext = outputTasksContext;
	}
}
