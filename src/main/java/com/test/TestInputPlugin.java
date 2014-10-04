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

package com.test;

import java.util.Map;

import com.codesnippets4all.automationframework.core.execution.TaskStatus;
import com.codesnippets4all.automationframework.core.execution.TaskStatusType;
import com.codesnippets4all.automationframework.core.execution.status.TaskStatusManager;
import com.codesnippets4all.automationframework.core.input.InputPlugin;
import com.codesnippets4all.automationframework.core.state.IContext;

/*
 * @author Rajesh Putta
 */
public class TestInputPlugin extends InputPlugin{
	public TaskStatus process(Map<String, String> configuration, IContext context, TaskStatusManager statusManager) {
		System.out.println("read plugin ...");
		
		for(int i=0;i<10;i++)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		TaskStatus taskStatus=new TaskStatus(TaskStatusType.SUCCESS, null);
		return taskStatus;
	}
}
