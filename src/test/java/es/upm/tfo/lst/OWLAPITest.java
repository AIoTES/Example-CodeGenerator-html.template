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
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
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
public class OWLAPITest {

	private static final String ONT_URL = "https://protege.stanford.edu/ontologies/pizza/pizza.owl";

	OWLOntology ontology, localOntology;
	OWLReasonerFactory reasonerFactory = null;
	OWLReasoner reasoner = null;
	OWLOntologyManager ontManager;

	@Before
	public void init() throws OWLOntologyCreationException, IOException {
		ontManager = OWLManager.createOWLOntologyManager();
		ontology = ontManager.loadOntologyFromOntologyDocument(new URL(ONT_URL).openStream());
		// localOntology=
		// ontManager.loadOntologyFromOntologyDocument(this.getClass().getClassLoader().getResource("games.owl").openStream());
		reasonerFactory = new JFactFactory();
		reasoner = reasonerFactory.createReasoner(ontology);
	}

	@Test
	public void ontologyStats() {

		try {
			System.out.println(ontology.getOntologyID().getOntologyIRI().get());
			System.out.println("getClassesInSignature() " + ontology.getClassesInSignature().size());
			System.out.println("getDataPropertiesInSignature() " + ontology.getDataPropertiesInSignature().size());
			System.out.println("getDatatypesInSignature() " + ontology.getDatatypesInSignature().size());
			System.out.println("getAxioms " + ontology.getAxioms(AxiomType.DATA_PROPERTY_RANGE).size());
			System.out
					.println("DATA_PROPERTY_ASSERTION " + ontology.getAxioms(AxiomType.DATA_PROPERTY_ASSERTION).size());
			System.out.println("DATA_PROPERTY_DOMAIN " + ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN).size());
			System.out.println("DATA_PROPERTY_RANGE " + ontology.getAxioms(AxiomType.DATA_PROPERTY_RANGE).size());
			System.out.println("DATATYPE_DEFINITION " + ontology.getAxioms(AxiomType.DATATYPE_DEFINITION).size());
			System.out.println(
					"ANNOTATION_PROPERTY_DOMAIN " + ontology.getAxioms(AxiomType.ANNOTATION_PROPERTY_DOMAIN).size());
			System.out.println(
					"ANNOTATION_PROPERTY_RANGE " + ontology.getAxioms(AxiomType.ANNOTATION_PROPERTY_RANGE).size());
			System.out.println("ANNOTATION_ASSERTION " + ontology.getAxioms(AxiomType.ANNOTATION_ASSERTION).size());
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@Test
	public void ontologyAxioms() {
		for (OWLAxiom a : ontology.getAxioms()) {
			if (a.isOfType(AxiomType.SUBCLASS_OF)) {
				System.out.println(a);
			}
		}
	}

	@Test
	public void ontologyAnnotations() {
		for (OWLAnnotation a : ontology.getAnnotations()) {
			System.out.println(a);
		}
	}

	@Test
	public void ontologyClassDeclarations() {
		for (OWLAxiom a : ontology.getAxioms()) {
			if (a.isOfType(AxiomType.DECLARATION) && ((OWLDeclarationAxiom) a).getEntity().isOWLClass()) {
				OWLClass cls = (OWLClass) ((OWLDeclarationAxiom) a).getEntity();
				System.out.println(cls.getIRI().getFragment());
			}
		}
	}
<<<<<<< HEAD
	@Test
	public void listInstances() {
		
		//to get individuals we need a reasoner. Will be use Jfact
		OWLClass cls = ontManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Country"));
		for (Node<OWLNamedIndividual> instance : reasoner.getInstances(cls, true)) {
			System.out.println(instance);
		}
		
		//get instances without using a reasoner
		for (OWLNamedIndividual individual: this.ontology.getIndividualsInSignature()) {
			System.out.println("individual "+individual.getIRI().getFragment());
		}
=======
>>>>>>> bb5197699d792c4f667030ae47c7f6c663cdfbdd

	@Test
	public void listClasses() {
		for (OWLClass cls : ontology.getClassesInSignature()) {
			System.out.println(cls.getIRI());
		}
	}

	@Test
	public void listInstances() {
		// to get individuals we need a reasoner. Will be use Jfact
//		OWLClass cls = ontManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Country"));
//		for (Node<OWLNamedIndividual> instance : reasoner.getInstances(cls, true)) {
//			System.out.println(instance);
//		}
		// it is not necesary to have a reasoner if we just want to list all instances,
		// independently of their class.
		for (OWLAxiom a : ontology.getAxioms()) {
			if (a.isOfType(AxiomType.DECLARATION) && ((OWLDeclarationAxiom) a).getEntity().isOWLNamedIndividual()) {
				OWLNamedIndividual namedI = (OWLNamedIndividual) ((OWLDeclarationAxiom) a).getEntity();
				System.out.println(namedI.getIRI().getFragment());
			}
		}
	}

	@Test
	public void listAllEnumerations() {
		for (OWLAxiom a : ontology.getAxioms()) {
			if (a.isOfType(AxiomType.EQUIVALENT_CLASSES))
				for (OWLClassExpression ce : ((OWLEquivalentClassesAxiom) a).getClassExpressions()) {
					if (ce instanceof OWLObjectOneOf) {
						// https://stackoverflow.com/questions/3087083/velocity-test-instanceof
						Set<OWLIndividual> ind = ((OWLObjectOneOf) ce).getIndividuals();
						System.out.println(a);
						for (OWLIndividual i : ind) {
							System.out.println("\t" + i.toStringID());
						}
					}
				}
		}
	}

	@Test
	public void assertionAnnotations() {
		for (OWLAnnotationAssertionAxiom element : ontology.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
			System.out.println(element);
		}

	}

}
