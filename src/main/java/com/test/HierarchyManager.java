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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * @author Rajesh Putta
 */
public class HierarchyManager {
      
       private static Map<String, TaskConfig> taskConfigMap=new HashMap<String, TaskConfig>();
       private static Map<String, TaskNode> taskNodeMap=new HashMap<String, TaskNode>();
      
       static class TaskConfig {
              private String name;
              private String depends;
             
              public TaskConfig(String name, String depends) {
                     this.name=name;
                     this.depends=depends;
              }
             
              public String getName() {
                     return name;
              }
             
              public String getDepends() {
                     return depends;
              }
       }
      
       static class TaskNode {
              private String name;
              private List<TaskNode> children=new ArrayList<TaskNode>();
             
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
             
       }
      
       static{
              taskConfigMap.put("task1", new TaskConfig("task1", null));
              taskConfigMap.put("task2", new TaskConfig("task2", null));
              taskConfigMap.put("task3", new TaskConfig("task3", "task1"));
              taskConfigMap.put("task4", new TaskConfig("task4", "task1"));
              taskConfigMap.put("task5", new TaskConfig("task5", "task3"));
              taskConfigMap.put("task6", new TaskConfig("task6", "task3"));
       }
      
       public static void main(String[] args) {
             
              Set<Map.Entry<String, TaskConfig>> entrySet = taskConfigMap.entrySet();
             
              for(Map.Entry<String, TaskConfig> entry:entrySet)
              {
                     TaskConfig task=entry.getValue();
                    
                     String depends=task.getDepends();
                    
                     TaskNode childNode=new TaskNode();
                     childNode.setName(task.getName());
                    
                     taskNodeMap.put(task.getName(), childNode);
                    
                     String parentName=(depends!=null)?depends:"root";
                    
                     TaskNode parentNode=taskNodeMap.get(parentName);
                    
                     if(parentNode==null)
                     {
                           TaskConfig parent=null;
                           if(parentName.equalsIgnoreCase("root"))
                                  parent=new TaskConfig("root",null);
                           else
                                  parent=taskConfigMap.get(parentName);
                          
                           parentNode=new TaskNode();
                           parentNode.setName(parent.getName());
                     }
                    
                     parentNode.addTaskNode(childNode);
                    
                     taskNodeMap.put(parentName, parentNode);
              }
             
              TaskNode rootNode=taskNodeMap.get("root");
             
              taskNodeMap.clear();
             
              System.out.println(display(rootNode,0));
       }
      
       private static String display(TaskNode node, int tabCount)
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
