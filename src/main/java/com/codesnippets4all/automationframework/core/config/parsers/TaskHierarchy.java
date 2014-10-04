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
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.codesnippets4all.automationframework.core.config.handlers.DependTaskConfig;
import com.codesnippets4all.automationframework.core.config.handlers.DependsConfig;
import com.codesnippets4all.automationframework.core.config.handlers.TaskConfig;
import com.codesnippets4all.automationframework.core.exceptions.AutomationConfigException;

/*
 * @author Rajesh Putta
 */
public class TaskHierarchy {
	
	private TaskNode root=null;
	
	private Map<String, TaskNode> taskNodeMap=new HashMap<String, TaskNode>();
	
	public void transform(Map<String, TaskConfig> taskConfigMap)
	{
        Set<Map.Entry<String, TaskConfig>> entrySet = taskConfigMap.entrySet();
        
        for(Map.Entry<String, TaskConfig> entry:entrySet)
        {
               TaskConfig task=entry.getValue();
              
               DependsConfig dependsConfig=task.getDependsConfig();

               String depends=null;
               
               if(dependsConfig!=null)
               {
            	   List<DependTaskConfig> dependTaskList=dependsConfig.getConfig();
            	   
            	   if(!dependTaskList.isEmpty())
            		   depends=dependTaskList.get(0).getName();
               }
               
               if(depends!=null)
               {
            	   if(!taskConfigMap.containsKey(depends))
            		   throw new AutomationConfigException("Invalid depends configuration ..."+task.getName()+"=>"+depends);
               }
               
               TaskNode childNode=taskNodeMap.get(task.getName());
            
               if(childNode==null)
               {
                   childNode=new TaskNode();
                   childNode.setName(task.getName());
                   taskNodeMap.put(task.getName(), childNode);
               }
               
               String parentName=(depends!=null)?depends:"root";

               childNode.setDepends(parentName);
               
               TaskNode parentNode=taskNodeMap.get(parentName);
              
               if(parentNode==null)
               {
            	     String name=null;
            	     
                     if(parentName.equalsIgnoreCase("root"))
                            name=parentName;
                     else
                            name=taskConfigMap.get(parentName).getName();
                    
                     parentNode=new TaskNode();
                     parentNode.setName(name);
               }
               
               // pre-checks for cyclic dependencies
               validateHierarchy(parentNode, task.getName());
              
               parentNode.addTaskNode(childNode);
               
               childNode.setParent(parentNode);
              
               taskNodeMap.put(parentName, parentNode);
        }
       
        this.root=taskNodeMap.get("root");
       
        taskNodeMap.clear();
        
        taskNodeMap=null;
	}
	
	public void validateHierarchy(TaskNode node, String childName)
	{
		while(node!=null)
		{
			String depends=node.getDepends();
			
			if(depends!=null && depends.equalsIgnoreCase(childName))
			{
				StringBuilder cycle=new StringBuilder(childName).append("=>").append(node.getName()).append("=>").append(childName);
				throw new AutomationConfigException("Cycle detected in Task Dependency Configuration..."+cycle.toString());
			}
			
			node=node.getParent();
		}
	}
	
	public TaskNode getHierarchy()
	{
		return this.root;
	}
	
	public String display()
	{
		return display(this.root, 0);
	}
	
	public String display(TaskNode node)
	{
		return display(node, 0);
	}
	
    public String display(TaskNode node, int tabCount)
    {
           String currentNodeName=node.getName();
          
           StringBuilder builder=new StringBuilder();
          
           builder.delete(0, builder.length());
          
           for(int i=1;i<=tabCount;i++)
                  builder.append("   ");
          
           builder.append(">");
           builder.append(currentNodeName);
           builder.append("\n");
          
           List<TaskNode> childs=node.getChildren();
          
           for(TaskNode child: childs)
           {
                  builder.append(display(child, tabCount+1));
           }
          
           return builder.toString();
    }
	
}
