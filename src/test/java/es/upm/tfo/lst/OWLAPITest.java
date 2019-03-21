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

import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import uk.ac.manchester.cs.jfact.JFactFactory;


/**
 * @author amedrano
 *
 */
public class OWLAPITest {

	private static final String ONT_URL = "https://protege.stanford.edu/ontologies/pizza/pizza.owl";

	OWLOntology ontology,localOntology;
	OWLReasonerFactory reasonerFactory = null;
	OWLReasoner reasoner = null;
	OWLOntologyManager ontManager;

	@Before
	public void init() throws OWLOntologyCreationException, IOException {
		ontManager = OWLManager.createOWLOntologyManager();
		ontology = ontManager.loadOntologyFromOntologyDocument(new URL(ONT_URL).openStream());
		//localOntology= ontManager.loadOntologyFromOntologyDocument(this.getClass().getClassLoader().getResource("games.owl").openStream());
		reasonerFactory = new JFactFactory();
		reasoner = reasonerFactory.createReasoner(ontology);
	}

	@Test
	public void ontologyStats() {

		try {
			System.out.println(ontology.getOntologyID().getOntologyIRI().get());
			System.out.println("getClassesInSignature() "+ontology.getClassesInSignature().size());
			System.out.println("getDataPropertiesInSignature() "+ontology.getDataPropertiesInSignature().size());
			System.out.println("getDatatypesInSignature() "+ontology.getDatatypesInSignature().size());
			System.out.println("getAxioms "+ontology.getAxioms(AxiomType.DATA_PROPERTY_RANGE).size());
			System.out.println("DATA_PROPERTY_ASSERTION "+ontology.getAxioms(AxiomType.DATA_PROPERTY_ASSERTION).size());
			System.out.println("DATA_PROPERTY_DOMAIN "+ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN).size());
			System.out.println("DATA_PROPERTY_RANGE "+ontology.getAxioms(AxiomType.DATA_PROPERTY_RANGE).size());
			System.out.println("DATATYPE_DEFINITION "+ontology.getAxioms(AxiomType.DATATYPE_DEFINITION).size());
			System.out.println("ANNOTATION_PROPERTY_DOMAIN "+ontology.getAxioms(AxiomType.ANNOTATION_PROPERTY_DOMAIN).size());
			System.out.println("ANNOTATION_PROPERTY_RANGE "+ontology.getAxioms(AxiomType.ANNOTATION_PROPERTY_RANGE).size());
			System.out.println("ANNOTATION_ASSERTION "+ontology.getAxioms(AxiomType.ANNOTATION_ASSERTION).size());
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@Test
	public void ontologyAxioms() {
		for (OWLAxiom a : ontology.getAxioms()) {
			System.out.println(a);
		}
	}
	@Test
	public void listClasses() {
		for (OWLAxiom a : ontology.getAxioms()) {
			if (a.isOfType(AxiomType.DECLARATION)  && ((OWLDeclarationAxiom) a).getEntity().isOWLClass()) {
				OWLClass cls = (OWLClass)a.getClassesInSignature().toArray()[0];
				System.out.println(cls.getIRI().getFragment());
			}

		}
		
	}
	@Test
	public void listInstances() {
		//to get individuals we need a reasoner. Will be use Jfactç
		
//		OWLClass cls = ontManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Country"));
		OWLClass cls = ontManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.semanticweb.org/eduardo/ontologies/2019/1/untitled-ontology-76#incidence_details"));
		for (Node<OWLNamedIndividual> instance : reasoner.getInstances(cls, true)) {
			System.out.println(instance);
		}

	}
	
	@Test
	public void listPropertiesOfClass() {
		OWLClass cls = ontManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.semanticweb.org/eduardo/ontologies/2019/1/untitled-ontology-76#incidence_details"));
		for (OWLDataPropertyRangeAxiom element : ontology.getAxioms(AxiomType.DATA_PROPERTY_RANGE)) {	
			System.out.println(element);

		}
	}
	@Test
	public void listObjectPropertiesForClass() {
		
		//of all classes
		for (OWLObjectPropertyDomainAxiom element : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
			System.out.println(element);
		}
		
		OWLClass cls = ontManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.semanticweb.org/eduardo/ontologies/2019/1/untitled-ontology-76#incidence_details"));
		//of a certain class
		for (OWLObjectPropertyDomainAxiom element : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
			System.out.println(element);
			if(element.getDomain().equals(cls)) {
				System.out.println(element);				
			}
		}
	}

	@Test
	public void ontologyAccessExample() {
		OWLOntology ontology;
		OWLClass c;
		OWLNamedIndividual v;


	}

}

