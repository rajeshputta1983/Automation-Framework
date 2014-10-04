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

import com.codesnippets4all.automationframework.core.config.handlers.InputTasksConfig;
import com.codesnippets4all.automationframework.core.config.parsers.AutomationFramework;
import com.codesnippets4all.automationframework.core.config.parsers.IFramework;
import com.codesnippets4all.automationframework.core.config.parsers.TaskHierarchy;

/*
 * @author Rajesh Putta
 */
public class Tester {

	public static void main(String[] args) {

		IFramework framework=new AutomationFramework();
		
		// initializes framework
		framework.initialize("com/codesnippets4all/automationframework/core/config/automation-config.xml");

		
		TaskHierarchy hierarchy=framework.getOutputTaskHierarchy("second");
		
		String result=hierarchy.display();
		
		System.out.println(result);
		
		InputTasksConfig taskConfig=framework.getInputTasksConfig("first");
		
		System.out.println(taskConfig.getConfig());
		
		
		// executes all lifecycles sequentially
		framework.execute();
		
		
		// destroys framework
		framework.destroy();
		
	}

}
