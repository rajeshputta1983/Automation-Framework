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

package com.codesnippets4all.automationframework.core.config.handlers;

import java.io.Serializable;

/*
 * @author Rajesh Putta
 */
public class LifeCycleConfig implements Serializable{
	
	private static final long serialVersionUID = -8247443206087684442L;

	private String name;
	
	private Configuration configuration; 
	
	private InputTasksConfig inputTasksConfig;
	
	private ProcessorTasksConfig processorTasksConfig;
	
	private OutputTasksConfig outputTasksConfig;
	
	private TriggersConfig triggersConfig;
	
	private ListenersConfig listenersConfig;
	
	private ExceptionMapperConfig exceptionMapperConfig;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InputTasksConfig getInputTasksConfig() {
		return inputTasksConfig;
	}

	public void setInputTasksConfig(InputTasksConfig inputTasksConfig) {
		this.inputTasksConfig = inputTasksConfig;
	}

	public ProcessorTasksConfig getProcessorTasksConfig() {
		return processorTasksConfig;
	}

	public void setProcessorTasksConfig(ProcessorTasksConfig processorTasksConfig) {
		this.processorTasksConfig = processorTasksConfig;
	}

	public OutputTasksConfig getOutputTasksConfig() {
		return outputTasksConfig;
	}

	public void setOutputTasksConfig(OutputTasksConfig outputTasksConfig) {
		this.outputTasksConfig = outputTasksConfig;
	}
	
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}
	
	public void setTriggersConfig(TriggersConfig triggersConfig) {
		this.triggersConfig = triggersConfig;
	}
	
	public TriggersConfig getTriggersConfig() {
		return triggersConfig;
	}
	
	public void setListenersConfig(ListenersConfig listenersConfig) {
		this.listenersConfig = listenersConfig;
	}
	
	public ListenersConfig getListenersConfig() {
		return listenersConfig;
	}
	
	public void setExceptionMapperConfig(
			ExceptionMapperConfig exceptionMapperConfig) {
		this.exceptionMapperConfig = exceptionMapperConfig;
	}
	
	public ExceptionMapperConfig getExceptionMapperConfig() {
		return exceptionMapperConfig;
	}
	
}
