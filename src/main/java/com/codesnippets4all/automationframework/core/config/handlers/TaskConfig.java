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
public class TaskConfig implements Serializable {
	
	private static final long serialVersionUID = -26139541092370973L;

	private String name;
	
	private String pluginName;
	
	private String type;
	
	private DependsConfig dependsConfig;
	
	private Configuration configuration;
	
	private TriggersConfig triggersConfig;
	
	private ErrorHandlers errorHandlers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setDependsConfig(DependsConfig dependsConfig) {
		this.dependsConfig = dependsConfig;
	}
	
	public DependsConfig getDependsConfig() {
		return dependsConfig;
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
	
	public void setErrorHandlers(ErrorHandlers errorHandlers) {
		this.errorHandlers = errorHandlers;
	}
	
	public ErrorHandlers getErrorHandlers() {
		return errorHandlers;
	}
}
