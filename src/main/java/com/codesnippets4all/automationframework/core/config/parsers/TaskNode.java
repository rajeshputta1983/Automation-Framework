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

import java.util.ArrayList;
import java.util.List;

/*
 * @author Rajesh Putta
 */
public class TaskNode {

	private String name;
    private List<TaskNode> children=new ArrayList<TaskNode>();
    private TaskNode parent=null;
    private String depends=null;
   
    public void setName(String name) {
           this.name = name;
    }
   
    public void addTaskNode(TaskNode node)
    {
           this.children.add(node);
    }
   
    public String getName() {
           return name;
    }
   
    public List<TaskNode> getChildren() {
           return children;
    }
    
    public void setParent(TaskNode parent) {
		this.parent = parent;
	}
    
    public TaskNode getParent() {
		return parent;
	}
    
    public void setDepends(String depends) {
		this.depends = depends;
	}
    
    public String getDepends() {
		return depends;
	}
}
