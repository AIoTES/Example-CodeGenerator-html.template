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
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import uk.ac.manchester.cs.jfact.JFactFactory;

/**
 * @author amedrano
 *
 */
public class IntegratedTest {
	
	
	OWLOntology ontology=null;
	OWLReasonerFactory reasonerFactory = null;
	OWLOntologyManager ontManager=null;
	VelocityContext context=null;
	Template template = null;
	VelocityEngine engine = null;
	Properties props = null;
	FileWriter writer = null;
	
	@Before
	public void init() throws OWLOntologyCreationException, IOException {
		ontManager = OWLManager.createOWLOntologyManager();
		this.ontology = ontManager.loadOntologyFromOntologyDocument(this.getClass().getClassLoader().getResource("games.owl").openStream());
		this.engine = new VelocityEngine();
		this.props = new Properties();
		props.put("file.resource.loader.path", "src/test/resources/");	
		this.engine.init(this.props);
		this.writer = new FileWriter(new File("target/output.txt"));
	}
	
	
	@Test
	public void classAccessTest() {
		//aplicar la template de clases para este caso
		try {
			OWLDataFactory manager = ontManager.getOWLDataFactory();
			OWLClass cls = manager.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#American"));
			this.context = new VelocityContext();
			this.context.put("class", cls);
			this.context.put("ontology", this.ontology);
			this.context.put("Date", new Date());
			this.writer = new FileWriter(new File("target/class.java"));
			//aqui para cambiar el .vm y probar otras templates 
			this.template = engine.getTemplate("/uAAl/Class.java.vm");
			this.template.merge(context, writer);
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
