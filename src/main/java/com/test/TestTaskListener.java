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

import com.codesnippets4all.automationframework.core.listeners.EventType;
import com.codesnippets4all.automationframework.core.listeners.TaskListener;
import com.codesnippets4all.automationframework.core.state.IContext;

/*
 * @author Rajesh Putta
 */
public class TestTaskListener extends TaskListener{
	public void beforeTaskExecution(EventType eType, IContext context) {
		System.out.println("Before Test Task Listener...event type ::"+eType.getEventSource()+"\t"+eType.getEventType());
	}
	
	public void afterTaskExecution(EventType eType, IContext context) {
		System.out.println("After Test Task Listener...event type ::"+eType.getEventSource()+"\t"+eType.getEventType());
	}
}
