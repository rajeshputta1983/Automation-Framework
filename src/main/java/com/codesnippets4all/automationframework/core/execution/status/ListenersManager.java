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

import java.util.List;

import com.codesnippets4all.automationframework.core.config.handlers.AutomationConfig;
import com.codesnippets4all.automationframework.core.config.handlers.ListenerConfig;
import com.codesnippets4all.automationframework.core.config.handlers.ListenersConfig;
import com.codesnippets4all.automationframework.core.config.types.ListenerType;
import com.codesnippets4all.automationframework.core.exceptions.AutomationException;
import com.codesnippets4all.automationframework.core.listeners.EventType;
import com.codesnippets4all.automationframework.core.listeners.EventTypeCode;
import com.codesnippets4all.automationframework.core.listeners.IListener;
import com.codesnippets4all.automationframework.core.state.IContext;

/*
 * @author Rajesh Putta
 */
public class ListenersManager {
	
	private AutomationConfig automationConfig=null;
	private ListenersConfig listenersConfig=null;
	
	public ListenersManager(AutomationConfig automationConfig) {
		this.automationConfig=automationConfig;
		this.listenersConfig=this.automationConfig.getListenersConfig();
	}
	
	public void invokePreLifecycleListeners(String lifecycle, List<String> listeners, IContext context)
	{
		for(String listener:listeners)
		{
			ListenerConfig listenerConfig=this.listenersConfig.getListener(listener);
			
			if(listenerConfig.getListenerType()!=ListenerType.LIFECYCLE)
			{
				throw new AutomationException("Only Lifecycle type listeners should be registered as Triggers at Lifecycle level...");
			}
			
			IListener listenerObj=listenerConfig.getListener();
			
			EventType event=new EventType();
			
			event.setEventSource(lifecycle);
			event.setEventType(EventTypeCode.PRE_LIFECYCLE);
			
			listenerObj.beforeLifeCycleExecution(event, context);
		}
	}

	public void invokePostLifecycleListeners(String lifecycle, List<String> listeners, IContext context) {
		for(String listener:listeners)
		{
			ListenerConfig listenerConfig=this.listenersConfig.getListener(listener);
			
			if(listenerConfig.getListenerType()!=ListenerType.LIFECYCLE)
			{
				throw new AutomationException("Only Lifecycle type listeners should be registered as Triggers at Lifecycle level...");
			}
			
			IListener listenerObj=listenerConfig.getListener();
			
			EventType event=new EventType();
			
			event.setEventSource(lifecycle);
			event.setEventType(EventTypeCode.POST_LIFECYCLE);
			
			listenerObj.afterLifeCycleExecution(event, context);
		}
	}

	public void invokePreTaskListeners(String task, List<String> listeners, IContext context)
	{
		for(String listener:listeners)
		{
			ListenerConfig listenerConfig=this.listenersConfig.getListener(listener);
			
			if(listenerConfig.getListenerType()!=ListenerType.TASK)
			{
				throw new AutomationException("Only Task type listeners should be registered as Triggers at Task level...");
			}
			
			IListener listenerObj=listenerConfig.getListener();
			
			EventType event=new EventType();
			
			event.setEventSource(task);
			event.setEventType(EventTypeCode.PRE_TASK);
			
			listenerObj.beforeTaskExecution(event, context);
		}
	}

	public void invokePostTaskListeners(String task, List<String> listeners, IContext context) {
		for(String listener:listeners)
		{
			ListenerConfig listenerConfig=this.listenersConfig.getListener(listener);
			
			if(listenerConfig.getListenerType()!=ListenerType.TASK)
			{
				throw new AutomationException("Only Task type listeners should be registered as Triggers at Task level...");
			}
			
			IListener listenerObj=listenerConfig.getListener();
			
			EventType event=new EventType();
			
			event.setEventSource(task);
			event.setEventType(EventTypeCode.POST_TASK);
			
			listenerObj.afterTaskExecution(event, context);
		}
	}
	
}
