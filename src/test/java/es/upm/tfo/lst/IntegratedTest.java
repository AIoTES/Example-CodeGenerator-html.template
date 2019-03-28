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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.FieldMethodizer;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.implement.EscapeHtmlReference;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

/**
 * @author amedrano
 *
 */
public class IntegratedTest {
	
	private static final String ONT_URL = "https://protege.stanford.edu/ontologies/pizza/pizza.owl";
	OWLOntology ontology=null;
	OWLReasonerFactory reasonerFactory = null;
	OWLOntologyManager ontManager=null;
	VelocityContext context=null;
	Template template = null;
	VelocityEngine engine = null;
	Properties props = null;
	FileWriter writer = null;
	Reader templateReader ;
	
	@Before
	public void init() throws OWLOntologyCreationException, IOException {
		ontManager = OWLManager.createOWLOntologyManager();
		//this.ontology = ontManager.loadOntologyFromOntologyDocument(this.getClass().getClassLoader().getResource("games.owl").openStream());
		this.ontology = ontManager.loadOntologyFromOntologyDocument(new URL(ONT_URL).openStream());
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
			templateReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResource("Class.java.vm").openStream()));
			OWLDataFactory manager = ontManager.getOWLDataFactory();
			OWLClass cls = manager.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza"));
			this.context = new VelocityContext();
			this.context.put("PackageBase","lst.tfo.upm.es");
			this.context.put("class", cls);
			this.context.put("ontology", this.ontology);
			this.context.put("Date", new Date());
			this.context.put("AxiomType",new FieldMethodizer("org.semanticweb.owlapi.model.AxiomType"));
			this.writer = new FileWriter(new File("target/class.java"));
//			Velocity.evaluate(context, writer, "", templateReader);
			//aqui para cambiar el .vm y probar otras templates 
			this.template = engine.getTemplate("Class.java.vm");
			this.template.merge(context, writer);
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void ontologyTest() {
		try {
			OWLDataFactory manager = ontManager.getOWLDataFactory();
			OWLClass cls = manager.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza"));
			this.context = new VelocityContext();
			this.context.put("PackageBase","lst.tfo.upm.es");
			this.context.put("ontology", this.ontology);
			this.context.put("Date", new Date());
			this.context.put("AxiomType",new FieldMethodizer("org.semanticweb.owlapi.model.AxiomType"));
			this.writer = new FileWriter(new File("target/ontology.java"));
			//aqui para cambiar el .vm y probar otras templates 
			this.template = engine.getTemplate("Ontology.java.vm");
			this.template.merge(context, writer);
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void activatorTest() {
		try {
			OWLDataFactory manager = ontManager.getOWLDataFactory();
			OWLClass cls = manager.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza"));
			this.context = new VelocityContext();
			this.context.put("PackageBase","lst.tfo.upm.es");
			this.context.put("class", cls);
			this.context.put("ontology", this.ontology);
			this.context.put("Date", new Date());
			this.context.put("AxiomType",new FieldMethodizer("org.semanticweb.owlapi.model.AxiomType"));
			this.writer = new FileWriter(new File("target/activator.java"));
			//aqui para cambiar el .vm y probar otras templates 
			this.template = engine.getTemplate("Activator.java.vm");
			this.template.merge(context, writer);
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void enumerationTest(){
		try {
			OWLDataFactory manager = ontManager.getOWLDataFactory();
			OWLClass cls = manager.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza"));
			this.context = new VelocityContext();
			this.context.put("PackageBase","lst.tfo.upm.es");
			this.context.put("class", cls);
			this.context.put("ontology", this.ontology);
			this.context.put("Date", new Date());
			this.context.put("AxiomType",new FieldMethodizer("org.semanticweb.owlapi.model.AxiomType"));
			this.writer = new FileWriter(new File("target/enumeration.java"));
			//aqui para cambiar el .vm y probar otras templates 
			this.template = engine.getTemplate("Enumeration.java.vm");
			this.template.merge(context, writer);
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void factoryTest() {
			try {
				OWLDataFactory manager = ontManager.getOWLDataFactory();
				OWLClass cls = manager.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza"));
				this.context = new VelocityContext();
				this.context.put("PackageBase","lst.tfo.upm.es");
				this.context.put("class", cls);
				this.context.put("ontology", this.ontology);
				this.context.put("Date", new Date());
				this.context.put("AxiomType",new FieldMethodizer("org.semanticweb.owlapi.model.AxiomType"));
				this.writer = new FileWriter(new File("target/factory.java"));
				//aqui para cambiar el .vm y probar otras templates 
				this.template = engine.getTemplate("Factory.java.vm");
				this.template.merge(context, writer);
				this.writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	@Test
	public void createClassTest() {
		try {
			OWLDataFactory manager = ontManager.getOWLDataFactory();
			OWLClass cls = manager.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza"));
			this.context = new VelocityContext();
			this.context.put("PackageBase","lst.tfo.upm.es");
			this.context.put("class", cls);
			this.context.put("ontology", this.ontology);
			this.context.put("Date", new Date());
			this.context.put("AxiomType",new FieldMethodizer("org.semanticweb.owlapi.model.AxiomType"));
			this.writer = new FileWriter(new File("target/createClass.java"));
			//aqui para cambiar el .vm y probar otras templates 
			this.template = engine.getTemplate("createClass.vm");
			this.template.merge(context, writer);
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void pomTest(){
		try {
			OWLDataFactory manager = ontManager.getOWLDataFactory();
			OWLClass cls = manager.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza"));
			this.context = new VelocityContext();
			this.context.put("PackageBase","lst.tfo.upm.es");
			this.context.put("class", cls);
			this.context.put("ontology", this.ontology);
			this.context.put("Date", new Date());
			this.context.put("AxiomType",new FieldMethodizer("org.semanticweb.owlapi.model.AxiomType"));
			this.writer = new FileWriter(new File("target/pom.java"));
			//aqui para cambiar el .vm y probar otras templates 
			this.template = engine.getTemplate("pom.xml.vm");
			this.template.merge(context, writer);
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void webpage() {
		try {
			templateReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResource("Class.java.vm").openStream()));
			OWLDataFactory manager = ontManager.getOWLDataFactory();
			OWLClass cls = manager.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza"));
			this.context = new VelocityContext();
			this.context.put("PackageBase","lst.tfo.upm.es");
			this.context.put("class", cls);
			this.context.put("ontology", this.ontology);
			this.context.put("Date", new Date());
			this.context.put("AxiomType",new FieldMethodizer("org.semanticweb.owlapi.model.AxiomType"));
			
			this.writer = new FileWriter(new File("target/template.html"));
//			Velocity.evaluate(context, writer, "", templateReader);
			//aqui para cambiar el .vm y probar otras templates 
			this.template = engine.getTemplate("WebsiteTemplate.vm");
			this.template.merge(context, writer);
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
