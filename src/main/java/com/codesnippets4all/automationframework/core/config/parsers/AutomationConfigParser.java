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

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.codesnippets4all.automationframework.core.config.handlers.AutomationConfig;
import com.codesnippets4all.automationframework.core.exceptions.AutomationConfigException;
import com.codesnippets4all.automationframework.core.utils.Constants;
import com.codesnippets4all.automationframework.core.utils.Utility;

/*
 * @author Rajesh Putta
 */
public class AutomationConfigParser extends DefaultHandler {

	private Stack<String> tagStack = new Stack<String>();
	
	private String configFilePath = null;
	
	private AutomationConfigParserHelper helper=new AutomationConfigParserHelper();

	public AutomationConfigParser()
	{
	}
	
	public AutomationConfigParser(String configFilePath) {
		this.configFilePath=configFilePath;
	}
	
	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}
	
	public void parse()
	{
		if(this.configFilePath == null || configFilePath.trim().equals(Constants.EMPTY_STRING))
			throw new AutomationConfigException("configuration file path is not set...");
		
		InputStream iStream=Utility.loadStream(this.configFilePath);
		
		try {
			SAXParser parser=setupParser();
			
			parser.parse(iStream, this);
			
		} catch (ParserConfigurationException e) {
			throw new AutomationConfigException(e);
		} catch (SAXException e) {
			throw new AutomationConfigException(e);
		} catch (IOException e) {
			throw new AutomationConfigException(e);
		}
		finally{
			Utility.closeStream(iStream);
		}
	}
	
	private SAXParser setupParser() throws ParserConfigurationException, SAXException
	{
		SAXParserFactory factory=SAXParserFactory.newInstance();
		SAXParser parser=factory.newSAXParser();
		
		return parser;
	}
	
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		tagStack.push(qName);
		
		helper.processStartElement(qName, attributes, tagStack);
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		helper.processEndElement(qName, null, tagStack);
		
		tagStack.pop();
	}
	
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		
		tagStack.clear();
	}
	
	public AutomationConfig getAutomationConfiguration()
	{
		return this.helper.getAutomationConfiguration();
	}
	
}
