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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.codesnippets4all.automationframework.core.IPlugin;
import com.codesnippets4all.automationframework.core.config.handlers.AutomationConfig;
import com.codesnippets4all.automationframework.core.config.handlers.Configuration;
import com.codesnippets4all.automationframework.core.config.handlers.ErrorHandlers;
import com.codesnippets4all.automationframework.core.config.handlers.ExceptionMapper;
import com.codesnippets4all.automationframework.core.config.handlers.ExceptionMapperConfig;
import com.codesnippets4all.automationframework.core.config.handlers.LifeCycleConfig;
import com.codesnippets4all.automationframework.core.config.handlers.PluginConfig;
import com.codesnippets4all.automationframework.core.config.handlers.TaskConfig;
import com.codesnippets4all.automationframework.core.config.handlers.TriggersConfig;
import com.codesnippets4all.automationframework.core.config.parsers.TaskHierarchy;
import com.codesnippets4all.automationframework.core.config.parsers.TaskHierarchyManager;
import com.codesnippets4all.automationframework.core.config.parsers.TaskNode;
import com.codesnippets4all.automationframework.core.config.types.TriggerType;
import com.codesnippets4all.automationframework.core.execution.status.ListenersManager;
import com.codesnippets4all.automationframework.core.execution.status.TaskStatusContext;
import com.codesnippets4all.automationframework.core.execution.status.TaskStatusManager;
import com.codesnippets4all.automationframework.core.state.GlobalContext;
import com.codesnippets4all.automationframework.core.state.IContext;

/*
 * @author Rajesh Putta
 */
public class HierarchicalTaskExecutionManager implements ITaskExecutionManager {
	private static final Logger LOGGER=Logger.getLogger(HierarchicalTaskExecutionManager.class.getName());

	private AutomationConfig automationConfig=null;
	private ListenersManager listenersManager=null;
	private Map<String, PluginConfig> pluginConfigMap=null;
	private ThreadPoolLifecycle executorService=null;
	private TaskStatusManager taskStatusManager=null;
	private Map<String, ExceptionMapper> exceptionMapperMap=null; 
	
	public HierarchicalTaskExecutionManager(AutomationConfig automationConfig) {
		this.automationConfig=automationConfig;
		this.pluginConfigMap=this.automationConfig.getPluginsConfig().getConfig();
		this.listenersManager=new ListenersManager(this.automationConfig);
		
		ExceptionMapperConfig exceptionMappers=this.automationConfig.getExceptionMappersConfig();
		
		if(exceptionMappers!=null)
		{
			this.exceptionMapperMap=exceptionMappers.getConfig();
		}
		
		this.executorService=new ExecutorServiceLifecycle();
		this.executorService.init();
	}
	
	public void setTaskStatusManager(TaskStatusManager taskStatusManager) {
		this.taskStatusManager = taskStatusManager;
	}
	
	public void destroy() {
		this.executorService.destory();
		
		this.pluginConfigMap=null;
		this.listenersManager=null;
		this.automationConfig=null;
	}
	
	public void execute(String lifecycle, TaskHierarchyManager hierarchyManager)
	{
		LifeCycleConfig lifeCycleConfig=this.automationConfig.getLifeCycleConfig().get(lifecycle);
		
		TriggersConfig triggersConfig=lifeCycleConfig.getTriggersConfig();
		IContext context=new GlobalContext();
		
		Map<TriggerType, List<String>>  triggers=null;
		if(triggersConfig!=null)
		{
			triggers= triggersConfig.getConfig();
			
			List<String> preLifecycleListeners = triggers.get(TriggerType.PRE);

			LOGGER.log(Level.INFO, "Before executing pre-lifecycle listeners ..."+lifecycle);		
			
			this.listenersManager.invokePreLifecycleListeners(lifecycle, preLifecycleListeners, context);
			
			LOGGER.log(Level.INFO, "After executing pre-lifecycle listeners ..."+lifecycle);
		}
		
		
		TaskStatusContext taskStatusContext=new TaskStatusContext();
		taskStatusManager.setInputTasksContext(taskStatusContext);
		
		LOGGER.log(Level.INFO, "Before executing input task hierarchy of Lifecycle ..."+lifecycle);
		
		
		this.executeTaskHierarchy( hierarchyManager.getInputTaskHierarchy(), lifeCycleConfig.getInputTasksConfig().getConfig(), context, taskStatusContext);
		
		LOGGER.log(Level.INFO, "After executing input task hierarchy of Lifecycle ..."+lifecycle);
		
		taskStatusContext=new TaskStatusContext();
		taskStatusManager.setProcessorTasksContext(taskStatusContext);

		
		LOGGER.log(Level.INFO, "Before executing processor task hierarchy of Lifecycle ..."+lifecycle);
		
		this.executeTaskHierarchy( hierarchyManager.getProcessorTaskHierarchy(), lifeCycleConfig.getProcessorTasksConfig().getConfig(), context, taskStatusContext);
		
		LOGGER.log(Level.INFO, "After executing processor task hierarchy of Lifecycle ..."+lifecycle);
		
		taskStatusContext=new TaskStatusContext();
		taskStatusManager.setOutputTasksContext(taskStatusContext);
		
		
		LOGGER.log(Level.INFO, "Before executing output task hierarchy of Lifecycle ..."+lifecycle);
		
		this.executeTaskHierarchy(hierarchyManager.getOutputTaskHierarchy(), lifeCycleConfig.getOutputTasksConfig().getConfig() ,context, taskStatusContext);
		
		LOGGER.log(Level.INFO, "After executing output task hierarchy of Lifecycle ..."+lifecycle);
		
		
		if(triggersConfig!=null)
		{
			List<String> postLifecycleListeners = triggers.get(TriggerType.POST);
			
			LOGGER.log(Level.INFO, "Before executing post-lifecycle listeners ..."+lifecycle);		
			
			this.listenersManager.invokePostLifecycleListeners(lifecycle, postLifecycleListeners, context);
			
			LOGGER.log(Level.INFO, "After executing post-lifecycle listeners ..."+lifecycle);
		}
	}
	
	private void executeTaskHierarchy(TaskHierarchy taskHierarchy, Map<String, TaskConfig> tasksConfig, IContext context, TaskStatusContext taskStatusContext)
	{
		TaskNode node=taskHierarchy.getHierarchy();
		
		if(node==null)
			return;
		
		executeTasks(node.getChildren(), tasksConfig, context, taskStatusContext);
	}
	
	private void executeTasks(List<TaskNode> nodeList, Map<String, TaskConfig> tasksConfig, final IContext context, TaskStatusContext taskStatusContext)
	{
		if(nodeList==null || nodeList.isEmpty())
			return;
		
		Map<String, Future<TaskStatus>> futureTaskMap=new HashMap<String, Future<TaskStatus>>();
		
		for(final TaskNode taskNode:nodeList)
		{
			final String taskName=taskNode.getName();
			
			TaskConfig taskConfig=tasksConfig.get(taskName);
			
			String plugin=taskConfig.getPluginName();
			
			PluginConfig pluginConfig=this.pluginConfigMap.get(plugin);
			
			final IPlugin pluginObj=pluginConfig.getPlugin();
			
			Configuration config=taskConfig.getConfiguration();
			
			final Map<String, String> configuration = (config!=null)?config.getConfig():new HashMap<String, String>();
			
			TriggersConfig triggersConfig=taskConfig.getTriggersConfig();
			
			final Map<TriggerType, List<String>> triggers=(triggersConfig!=null)?triggersConfig.getConfig():null;
			
			final ListenersManager listenerManager=this.listenersManager;
			
			Callable<TaskStatus> callable=new Callable<TaskStatus>(){

                public TaskStatus call() throws Exception {
                	List<String> preTaskTriggers=null;
                	List<String> postTaskTriggers=null;
                	
                	if(triggers!=null)
                	{
                		preTaskTriggers=triggers.get(TriggerType.PRE);
                		listenerManager.invokePreTaskListeners(taskName, preTaskTriggers, context);
                	}
                	
                	TaskStatus taskStatus=pluginObj.process(configuration, context, taskStatusManager);
                	taskStatus.setNode(taskNode);

                	if(triggers!=null)
                	{
            			postTaskTriggers=triggers.get(TriggerType.POST);
                    	listenerManager.invokePostTaskListeners(taskName, postTaskTriggers, context);
                	}
                	
                	return taskStatus;
                }
			};
			
			Future<TaskStatus> taskStatus=this.executorService.submitTask(callable);
			
			futureTaskMap.put(taskName, taskStatus);
		}

		Set<String> tasks=futureTaskMap.keySet();
		
		while(true)
		{
			boolean executionStatus=true;
			
			for(String task: tasks)
			{
				Future<TaskStatus> futureTask=futureTaskMap.get(task);
				
				// ensures same task doesn't get triggered multiple times
				if(taskStatusContext.get(task)!=null)
					continue;
				
				executionStatus=executionStatus && futureTask.isDone();
				
				TaskStatus taskStatus=null;
				if(futureTask.isDone())
				{
					try {
						taskStatus=futureTask.get();
					} catch (Exception e) {
						if(taskStatus==null)
							taskStatus=new TaskStatus(task, TaskStatusType.EXCEPTION, null);
						
						// execute error handlers
						TaskConfig taskConfig=tasksConfig.get(task);
						
						ErrorHandlers errorHandlers=taskConfig.getErrorHandlers();
						
						if(errorHandlers!=null)
						{
							List<String> handlers=errorHandlers.getConfig();
	
							for(String handler: handlers)
							{
								ExceptionMapper mapper=this.exceptionMapperMap.get(handler);
								
								mapper.getExceptionMapper().handleException(e, context);
							}
						}
						
					}
					
					if(taskStatus.getType()==null)
						taskStatus.setType(TaskStatusType.NOT_SET);
					
					taskStatus.setTaskName(task);
					taskStatusContext.addTaskStatus(task, taskStatus);
					
					
					if(taskStatus.getType()==TaskStatusType.SUCCESS)
					{
						executeTasks(taskStatus.getNode().getChildren(), tasksConfig, context, taskStatusContext);
					}
				}
			}
			
			if(executionStatus)
				break;
		}
		
	}
}
