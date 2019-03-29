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
		props.put("file.resource.loader.path", "src/main/resources/");
		//TODO: should use class loader instead, everything in this dir should be accessible with this.getClass().getClassLoader().getResource(name)
		this.engine.init(this.props);
		this.writer = new FileWriter(new File("target/output.txt"));
		this.context = new VelocityContext();
	}

	protected void runProject(String velociMacro) throws IOException{
		// templateReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResource(velociMacro).openStream()));
		this.context.put("Date", new Date());
		this.template = engine.getTemplate(velociMacro);
		this.template.merge(context, writer);
		this.writer.close();
	}

	protected void runOntology(String velociMacro) throws IOException {
		this.context.put("ontology", this.ontology);
		runProject(velociMacro);
	}

	protected void runClass(String velociMacro) throws IOException {
		this.context.put("ontology", this.ontology);
		OWLDataFactory manager = ontManager.getOWLDataFactory();
		OWLClass cls = manager.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza"));
		this.context.put("class", cls);
		runOntology(velociMacro);
	}
	// TODO: the same for the other Entities

	@Test
	public void classAccessTest() throws IOException {
		this.context.put("PackageBase","lst.tfo.upm.es");
		this.context.put("AxiomType",new FieldMethodizer("org.semanticweb.owlapi.model.AxiomType"));
		this.writer = new FileWriter(new File("target/class_Pizza.html"));
		runClass("class_html");
	}

	@Test
	public void ontologyTest() throws IOException {
			this.context.put("AxiomType",new FieldMethodizer("org.semanticweb.owlapi.model.AxiomType"));
			this.writer = new FileWriter(new File("target/ontology_Pizza.html"));
			runOntology("Ontology.java.vm");
	}

	@Test
	public void webpage() throws IOException {
			this.context.put("PackageBase","lst.tfo.upm.es");
			this.context.put("AxiomType",new FieldMethodizer("org.semanticweb.owlapi.model.AxiomType"));
			this.writer = new FileWriter(new File("target/template.html"));
			runProject("WebsiteTemplate.vm");
	}
	//TODO: the same for the other velociMacros..

}
