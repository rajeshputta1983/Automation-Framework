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
import java.util.Stack;

import org.xml.sax.Attributes;

import com.codesnippets4all.automationframework.core.config.handlers.AutomationConfig;
import com.codesnippets4all.automationframework.core.config.handlers.Configuration;
import com.codesnippets4all.automationframework.core.config.handlers.DependsConfig;
import com.codesnippets4all.automationframework.core.config.handlers.ErrorHandlers;
import com.codesnippets4all.automationframework.core.config.handlers.ExceptionMapper;
import com.codesnippets4all.automationframework.core.config.handlers.ExceptionMapperConfig;
import com.codesnippets4all.automationframework.core.config.handlers.InputTasksConfig;
import com.codesnippets4all.automationframework.core.config.handlers.LifeCycleConfig;
import com.codesnippets4all.automationframework.core.config.handlers.ListenerConfig;
import com.codesnippets4all.automationframework.core.config.handlers.ListenersConfig;
import com.codesnippets4all.automationframework.core.config.handlers.OutputTasksConfig;
import com.codesnippets4all.automationframework.core.config.handlers.PluginsConfig;
import com.codesnippets4all.automationframework.core.config.handlers.ProcessorTasksConfig;
import com.codesnippets4all.automationframework.core.config.handlers.TaskConfig;
import com.codesnippets4all.automationframework.core.config.handlers.TriggersConfig;
import com.codesnippets4all.automationframework.core.exceptions.AutomationConfigException;
import com.codesnippets4all.automationframework.core.utils.Utility;

/*
 * @author Rajesh Putta
 */
public class AutomationConfigParserHelper {
	
	private AutomationConfig automationConfig=null;
	private PluginsConfig pluginsConfig=null;
	private ListenersConfig listenersConfig=null;
	private ExceptionMapperConfig exceptionConfig=null;
	private LifeCycleConfig lifeCycleConfig=null;
	private Configuration configuration=null;
	private InputTasksConfig inputTasksConfig=null;
	private ProcessorTasksConfig processorTasksConfig=null;
	private OutputTasksConfig outputTasksConfig=null;
	private TaskConfig taskConfig=null;
	private DependsConfig depends=null;
	private TriggersConfig triggers=null;
	private ErrorHandlers errorHandlers=null;
	
	private Map<String, String> globalConfiguration=new HashMap<String, String>();
	
	private Map<String, String> localConfiguration=new HashMap<String, String>();
	
	
	public AutomationConfigParserHelper() {
	}
	
	public void processStartElement(String qName, Attributes attributes, Stack<String> tagStack)
	{
		if(qName.equalsIgnoreCase("automation-config"))
		{
			automationConfig = new AutomationConfig();
		}
		else if(qName.equalsIgnoreCase("plug-ins-config"))
		{
			pluginsConfig = new PluginsConfig();
		}
		else if(qName.equalsIgnoreCase("plug-in"))
		{
			pluginsConfig.addPlugin(attributes);
		}
		else if(qName.equalsIgnoreCase("listeners-config"))
		{
			listenersConfig = new ListenersConfig();
		}
		else if(qName.equalsIgnoreCase("listener"))
		{
			listenersConfig.addListener(attributes);
		}
		else if(qName.equalsIgnoreCase("exception-mappers-config"))
		{
			exceptionConfig = new ExceptionMapperConfig();
		}
		else if(qName.equalsIgnoreCase("exception-mapper"))
		{
			exceptionConfig.addExceptionMapper(attributes);
		}
		else if(qName.equalsIgnoreCase("life-cycle-config"))
		{
			
			String lifecycle=attributes.getValue("name");
			
			if(automationConfig.getLifeCycleConfig().containsKey(lifecycle))
			{
				throw new AutomationConfigException("Duplicate Lifecycle configuration identified..."+lifecycle);
			}
			
			lifeCycleConfig = new LifeCycleConfig();
			lifeCycleConfig.setName(attributes.getValue("name"));
		}
		else if(qName.equalsIgnoreCase("configuration"))
		{
			configuration =  new Configuration();
		}
		else if(qName.equalsIgnoreCase("key"))
		{
			configuration.addConfig(attributes);
		}
		else if(qName.equalsIgnoreCase("input-tasks-config"))
		{
			inputTasksConfig =  new InputTasksConfig();
		}
		else if(qName.equalsIgnoreCase("processor-tasks-config"))
		{
			processorTasksConfig =  new ProcessorTasksConfig();
		}
		else if(qName.equalsIgnoreCase("output-tasks-config"))
		{
			outputTasksConfig =  new OutputTasksConfig();
		}
		else if(qName.equalsIgnoreCase("task"))
		{
			String parent=tagStack.elementAt(tagStack.size()-2);
			
			if(parent.equalsIgnoreCase("depends"))
			{
				depends.addDependTaskConfig(attributes);
				return;
			}
			
			String taskName=attributes.getValue("name");
			
			Map<String, TaskConfig> taskConfigMap=null;
			
			if(parent.equalsIgnoreCase("input-tasks-config"))
				taskConfigMap=inputTasksConfig.getConfig();
			else if(parent.equalsIgnoreCase("output-tasks-config"))
				taskConfigMap=outputTasksConfig.getConfig();
			else if(parent.equalsIgnoreCase("processor-tasks-config"))
				taskConfigMap=processorTasksConfig.getConfig();
			
			if(taskConfigMap.containsKey(taskName))
			{
				String message=Utility.concatenate("Lifecycle Name:",lifeCycleConfig.getName(),"\tUnder config element:",parent,"\tTask Name:",taskName);
				throw new AutomationConfigException("Duplicate Task identified in the configuration..."+message);
			}
			
			taskConfig = new TaskConfig();
			taskConfig.setName(attributes.getValue("name"));
			taskConfig.setPluginName(attributes.getValue("plug-in"));
		}
		else if(qName.equalsIgnoreCase("depends"))
		{
			depends=new DependsConfig();
		}
		else if(qName.equalsIgnoreCase("triggers"))
		{
			triggers=new TriggersConfig();
		}
		else if(qName.equalsIgnoreCase("trigger"))
		{
			String triggerName=attributes.getValue("name");
			
			Map<String, ListenerConfig> listenerConfigMap=automationConfig.getListenersConfig().getConfig();
			
			if(!listenerConfigMap.containsKey(triggerName))
			{
				String message=Utility.concatenate("Lifecycle Name:",lifeCycleConfig.getName(),"\tTrigger Name:",triggerName);
				throw new AutomationConfigException("Invalid Trigger is configured...."+message);
			}
			
			triggers.addTriggerConfig(attributes);
		}
		else if(qName.equalsIgnoreCase("exception-mappers-config"))
		{
			exceptionConfig=new ExceptionMapperConfig();
		}
		else if(qName.equalsIgnoreCase("exception-mapper"))
		{
			exceptionConfig.addExceptionMapper(attributes);
		}
		else if(qName.equalsIgnoreCase("error-handlers"))
		{
			errorHandlers=new ErrorHandlers();
		}
		else if(qName.equalsIgnoreCase("error-handler"))
		{
			String errorHandlerName=attributes.getValue("name");
			
			Map<String, ExceptionMapper> exceptions=automationConfig.getExceptionMappersConfig().getConfig();
			
			if(!exceptions.containsKey(errorHandlerName))
			{
				String message=Utility.concatenate("Lifecycle Name: ",lifeCycleConfig.getName(),"\tTask Name:",taskConfig.getName(),"\tError Handler Name:",errorHandlerName);
				throw new AutomationConfigException("Error Handler Not Registered :: "+message);
			}
		
			errorHandlers.addErrorHandler(attributes);
		}
	}
	
	public void processEndElement(String qName, String tagContent, Stack<String> tagStack)
	{
		if(qName.equalsIgnoreCase("exception-mappers-config"))
		{
			automationConfig.setExceptionMappersConfig(exceptionConfig);
			
			exceptionConfig=null;
		}
		else if(qName.equalsIgnoreCase("plug-ins-config"))
		{
			automationConfig.setPluginsConfig(pluginsConfig);
			
			pluginsConfig=null;
		}
		else if(qName.equalsIgnoreCase("listeners-config"))
		{
			automationConfig.setListenersConfig(listenersConfig);
			
			listenersConfig=null;
		}
		else if(qName.equalsIgnoreCase("life-cycle-config"))
		{
			lifeCycleConfig.setInputTasksConfig(inputTasksConfig);
			lifeCycleConfig.setProcessorTasksConfig(processorTasksConfig);
			lifeCycleConfig.setOutputTasksConfig(outputTasksConfig);
			
			automationConfig.addLifeCycleConfig(lifeCycleConfig);
			
			inputTasksConfig=null;
			processorTasksConfig=null;
			outputTasksConfig=null;
			lifeCycleConfig=null;
			
		}
		else if(qName.equalsIgnoreCase("task"))
		{
			String parent=tagStack.elementAt(tagStack.size()-2);
			
			if(parent.equalsIgnoreCase("depends"))
			{
				return;
			}
			
			taskConfig.setDependsConfig(depends);
			
			if(parent.equalsIgnoreCase("input-tasks-config"))
				inputTasksConfig.addTaskConfig(taskConfig);
			else if(parent.equalsIgnoreCase("output-tasks-config"))
				outputTasksConfig.addTaskConfig(taskConfig);
			else if(parent.equalsIgnoreCase("processor-tasks-config"))
				processorTasksConfig.addTaskConfig(taskConfig);
			
			depends=null;
			taskConfig=null;
		}
		else if(qName.equalsIgnoreCase("configuration"))
		{
			String parent=tagStack.elementAt(tagStack.size()-2);
			
			if(parent.equalsIgnoreCase("task"))
			{
				Map<String, String> config=new HashMap<String, String>();
				
				config.putAll(globalConfiguration);
				config.putAll(localConfiguration);
				config.putAll(configuration.getConfig());
				
				configuration.setConfig(config);
				
				taskConfig.setConfiguration(configuration);
			}
			else if(parent.equalsIgnoreCase("input-tasks-config"))
			{
				inputTasksConfig.setConfiguration(configuration);

				localConfiguration.clear();
				localConfiguration.putAll(configuration.getConfig());
			}
			else if(parent.equalsIgnoreCase("output-tasks-config"))
			{
				outputTasksConfig.setConfiguration(configuration);
				
				localConfiguration.clear();
				localConfiguration.putAll(configuration.getConfig());
			}
			else if(parent.equalsIgnoreCase("processor-tasks-config"))
			{
				processorTasksConfig.setConfiguration(configuration);
				
				localConfiguration.clear();
				localConfiguration.putAll(configuration.getConfig());
			}
			else if(parent.equalsIgnoreCase("life-cycle-config"))
			{
				lifeCycleConfig.setConfiguration(configuration);
				
				globalConfiguration.clear();
				globalConfiguration.putAll(configuration.getConfig());
			}
			
			configuration=null;
		}
		else if(qName.equalsIgnoreCase("triggers"))
		{
			String parent=tagStack.elementAt(tagStack.size()-2);
			
			if(parent.equalsIgnoreCase("task"))
				taskConfig.setTriggersConfig(triggers);
			else if(parent.equalsIgnoreCase("lifecycle"))
				lifeCycleConfig.setTriggersConfig(triggers);
			
			triggers=null;
		}
		else if(qName.equalsIgnoreCase("error-handlers"))
		{
			taskConfig.setErrorHandlers(errorHandlers);
			
			errorHandlers=null;
		}
	}

	
	public AutomationConfig getAutomationConfiguration()
	{
		return automationConfig;
	}
	
	
}
