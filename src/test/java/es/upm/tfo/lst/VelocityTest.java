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
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests showcasing the basics of Velocity Template Language
 * @author amedrano
 *
 * @see <a href=http://velocity.apache.org/engine/1.7/user-guide.html>Velocity user guide</a>
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
			this.template = engine.getTemplate("ifStatementTemplate.vm");
			props.put("file.resource.loader.path", "src/test/resources/");
			this.writer = new FileWriter(new File("target/output.txt"));
			context.put("key1", "code generator");
			context.put("key2", "velocity test");
			context.put("key 3", "universidad politecnica de maidrid");
			context.put("key4", "java projects");
			context.put("key5", "how to use velocity");
			context.put("key6", "Madrid, Spain");
			context.put("key7", "Maven Projects");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	@Test
	public void ifStatementTest() {
		this.template.merge(context, writer);
	}
	
	@Test
	public void macroTest() {
		
	}
	
	@Test
	public void variablesTest() {
		
	}
	
	@Test
	public void importExistentTemplate() {
		
		
	}

}
