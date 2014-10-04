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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.codesnippets4all.automationframework.core.config.handlers.TaskConfig;

/*
 * @author Rajesh Putta
 */
public class ExecutorServiceLifecycle implements ThreadPoolLifecycle {

       private ExecutorService executorService=null;

       public void init() {
	        final int numberOfThreads=10;
	        this.executorService=Executors.newFixedThreadPool(numberOfThreads);
       }

       public Future<TaskStatus> submitTask(Callable<TaskStatus> callable) {
              return this.executorService.submit(callable);
       }

       public Future<TaskStatus> submitTask(TaskConfig task, int timeout) {
              return null;
       }      

       public void destory() {
              this.executorService.shutdown();
       }
}
