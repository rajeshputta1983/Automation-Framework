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

import com.codesnippets4all.automationframework.core.mappers.IExceptionMapper;

/*
 * @author Rajesh Putta
 */
public class ExceptionMapper implements Serializable{

	private static final long serialVersionUID = 473748102497322419L;

	private String name;
	
	private IExceptionMapper exceptionMapper=null;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setExceptionMapper(IExceptionMapper exceptionMapper) {
		this.exceptionMapper = exceptionMapper;
	}
	
	public IExceptionMapper getExceptionMapper() {
		return exceptionMapper;
	}
}