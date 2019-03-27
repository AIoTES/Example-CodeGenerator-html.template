/*******************************************************************************
 * Copyright 2019 Universidad Politécnica de Madrid UPM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package es.upm.tfo.lst;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.URLResourceLoader;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests showcasing the basics of Velocity Template Language
 * @author amedrano
 * http://velocity.apache.org/engine/2.0/translations/user-guide_es.html#Set
 *
 * @see <a href=http://velocity.apache.org/engine/2.0/translations/user-guide_es.html>Velocity user guide</a>
 */
public class VelocityTest {
	
	VelocityContext context=null;
	Template template = null;
	VelocityEngine engine = null;
	Properties props = null;
	FileWriter writer = null;
	
	@Before
	public void init() {
		try {
			this.context = new VelocityContext();
			this.engine = new VelocityEngine();
			this.props = new Properties();
			props.put("file.resource.loader.path", "src/test/resources/");	
			this.engine.init(this.props);
			this.writer = new FileWriter(new File("target/output.txt"));
			String [] arrayOfMonths = {"January","February","March","April","May"};
			context.put("key1", "code generator");
			context.put("key2", "velocity test");
			context.put("key3", "universidad politecnica de maidrid");
			context.put("key4", "java projects");
			context.put("key5", "how to use velocity");
			context.put("key6", "Madrid, Spain");
			context.put("key7", "Maven Projects");
			context.put("arrayOfMonths", arrayOfMonths);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	@Test
	public void VelocityTricks() {
		try {
			this.template = engine.getTemplate("/uAAl/Class.java.vm");
			this.template.merge(context, writer);
			this.writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	
		
	}
	
	
	@Test
	public void variablesTest() {
		//TODO ver directamente la plantilla de velocity?
	}
	
	@Test
	public void ifStatementTest() throws IOException {
		this.template = engine.getTemplate("ifStatementTemplate.vm");
		this.template.merge(context, writer);
		this.writer.close();
	}
	
	@Test
	public void macroTest() throws IOException{
		this.template = engine.getTemplate("macroTest.vm");
		this.template.merge(context, writer);
		this.writer.close();
	}

	@Test
	public void importExistentTemplate() {
		
	}
	
	@Test
	public void usingContextContentTest() throws IOException{
		this.template = engine.getTemplate("contextContent.vm");
		this.template.merge(context, writer);
		this.writer.close();
	}

}
