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

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
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

	static OWLOntology ontology, localOntology;
	static OWLReasonerFactory reasonerFactory = null;
	static OWLReasoner reasoner = null;
	static OWLOntologyManager ontManager;

    @Rule public TestName name = new TestName();

	@BeforeClass
	static public void init() throws OWLOntologyCreationException, IOException {
		ontManager = OWLManager.createOWLOntologyManager();
		ontology = ontManager.loadOntologyFromOntologyDocument(new URL(ONT_URL).openStream());
		// localOntology=
		// ontManager.loadOntologyFromOntologyDocument(this.getClass().getClassLoader().getResource("games.owl").openStream());
		reasonerFactory = new JFactFactory();
		reasoner = reasonerFactory.createReasoner(ontology);
	}

	@Before
	public void start() {
		System.err.flush();
		System.out.flush();
		System.err.println("");
		System.err.println("================ Start of " + name.getMethodName() + " ================");

	}
	
	@After
	public void end() {
		System.out.flush();
		System.err.flush();
		System.err.println("================= End of " + name.getMethodName() + " =================");
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
		for (OWLAxiom a : AxiomType.getAxiomsOfTypes(ontology.getAxioms(), AxiomType.DECLARATION)) {
				System.out.println(a);
		}

		for (OWLAxiom a : AxiomType.getAxiomsWithoutTypes(ontology.getAxioms(), AxiomType.DECLARATION)) {
				System.out.println(a);
		}
	}

	@Test
	public void ontologyAnnotations() {
		for (OWLAnnotation a : ontology.getAnnotations()) {
			//System.out.println(a);
			System.out.println(a.getProperty().getIRI());
			System.out.println(((OWLLiteral) a.getValue()).getLiteral());
		}
	}

	@Test
	public void ontologyClassDeclarations() {
		System.out.println("ontology.getAxioms()");
	
		//en este for tambien da los names individuals y los object properties y las anotations
		for (OWLAxiom a : AxiomType.getAxiomsOfTypes(ontology.getAxioms(), AxiomType.DECLARATION)) {
			a.getClassesInSignature().stream().forEach(f->System.out.println(f.getIRI().getFragment()));
			
	}

	}
	@Test
	public void listInstances() {
		//get instances without using a reasoner
		for (OWLNamedIndividual individual: this.ontology.getIndividualsInSignature()) {
			System.out.println("individual "+individual.getIRI().getFragment());
		}


	}
	@Test
	public void listClasses() {
		for (OWLClass cls : ontology.getClassesInSignature()) {
			System.out.println(cls.getIRI());
		}
	}

	@Test
	public void getDataPropertiesTest() {

		for (OWLDataPropertyDomainAxiom item : ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN)) {
			System.out.println("DATA_PROPERTY_DOMAIN "+item);
		}
		System.out.println("------");
		for (OWLDataPropertyAssertionAxiom item : ontology.getAxioms(AxiomType.DATA_PROPERTY_ASSERTION)) {
			System.out.println("DATA_PROPERTY_ASSERTION "+item);
		}
		System.out.println("------");
		for (OWLDataPropertyRangeAxiom item : ontology.getAxioms(AxiomType.DATA_PROPERTY_RANGE)) {
			System.out.println("DATA_PROPERTY_RANGE "+item);
		}





	}

	@Test
	public void getObjectPropertiesTest() {
		System.out.println("------");
		OWLDataFactory manager = ontManager.getOWLDataFactory();
		OWLClass cls = manager.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Country"));
		for (OWLObjectPropertyDomainAxiom item : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
			System.out.println("OBJECT_PROPERTY_DOMAIN "+item);
	
		}
		System.out.println("------");
		for (OWLObjectPropertyAssertionAxiom item: ontology.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION)) {
			System.out.println("OBJECT_PROPERTY_ASSERTION "+item);
		}
		System.out.println("------");
		for (OWLObjectPropertyRangeAxiom item : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE)) {
			System.out.println("OBJECT_PROPERTY_RANGE "+item);
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

	@Test
	public void listAxioms4Classe() {
		OWLClass cls = (OWLClass) ontology.getClassesInSignature().toArray()[3];
		for (OWLAxiom a : ontology.getAxioms(cls)) {
			System.out.println(a);
		}
	}

}
