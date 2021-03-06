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
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import uk.ac.manchester.cs.jfact.JFactFactory;

/**
 * @author amedrano
 *
 */
public class OWLAPITest {

	//private static final String ONT_URL = "https://protege.stanford.edu/ontologies/pizza/pizza.owl";
	//private static final String ONT_URL = "	http://svn.code.sf.net/p/oae/code/trunk/src/ontology/CTCAE-OAEview.owl";
	private static final String ONT_URL = "https://raw.githubusercontent.com/EuPath-ontology/EuPath-ontology/2019-04-02/eupath.owl";
	//private static final String ONT_URL = "https://raw.githubusercontent.com/monarch-initiative/GENO-ontology/develop/src/ontology/geno.owl";
	
	
	static OWLOntology ontology;
	static OWLReasonerFactory reasonerFactory = null;
	static OWLReasoner reasoner = null;
	static OWLOntologyManager ontManager;

    @Rule public TestName name = new TestName();

	@BeforeClass
	static public void init() throws OWLOntologyCreationException, IOException {
		ontManager = OWLManager.createOWLOntologyManager();
		ontology = ontManager.loadOntologyFromOntologyDocument(new URL(ONT_URL).openStream());
		
		reasonerFactory = new JFactFactory();
		reasoner = reasonerFactory.createReasoner(ontology);
	}

	@Before
	public void start() throws Exception, IOException {
		
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
			System.out.println("getAxioms " + ontology.getAxioms(Imports.EXCLUDED).size());
			System.out.println("DATA_PROPERTY_ASSERTION " + ontology.getAxioms(AxiomType.DATA_PROPERTY_ASSERTION,Imports.EXCLUDED).size());
			System.out.println("DATA_PROPERTY_DOMAIN " + ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN,Imports.EXCLUDED).size());
			System.out.println("DATA_PROPERTY_RANGE " + ontology.getAxioms(AxiomType.DATA_PROPERTY_RANGE,Imports.EXCLUDED).size());
			System.out.println("DATATYPE_DEFINITION " + ontology.getAxioms(AxiomType.DATATYPE_DEFINITION,Imports.EXCLUDED).size());
			System.out.println("ANNOTATION_PROPERTY_DOMAIN " + ontology.getAxioms(AxiomType.ANNOTATION_PROPERTY_DOMAIN,Imports.EXCLUDED).size());
			System.out.println("ANNOTATION_PROPERTY_RANGE " + ontology.getAxioms(AxiomType.ANNOTATION_PROPERTY_RANGE,Imports.EXCLUDED).size());
			System.out.println("ANNOTATION_ASSERTION " + ontology.getAxioms(AxiomType.ANNOTATION_ASSERTION,Imports.EXCLUDED).size());
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@Test
	public void ontologyAxioms() {

	
	for (OWLAxiom a : AxiomType.getAxiomsOfTypes(ontology.getAxioms(), AxiomType.DECLARATION)) {
			//System.out.println(a.getSignature().iterator().next().getClassesInSignature());
		if(a.getSignature().iterator().next().isOWLClass()) {
			for (OWLObjectPropertyDomainAxiom item : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
				OWLClass p = a.getSignature().iterator().next().getClassesInSignature().iterator().next();				
				if(item.containsEntityInSignature(p)) {
					System.out.println("OBJECT_PROPERTY_DOMAIN "+item.getProperty());
				}
		
				}
			for (OWLObjectPropertyRangeAxiom item : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE)) {
				OWLClass p = a.getSignature().iterator().next().getClassesInSignature().iterator().next();				
				if(item.containsEntityInSignature(p)) {
					System.out.println("OBJECT_PROPERTY_RANGE property: "+item.getProperty()+" range:"+ item.getRange());
				}
		
				}
			
			for (OWLDataPropertyDomainAxiom item : ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN)) {
				OWLClass p = a.getSignature().iterator().next().getClassesInSignature().iterator().next();				
				if(item.containsEntityInSignature(p)) {
					System.out.println("DATA_PROPERTY_DOMAIN "+item.getProperty());
				}
		
				}
			
			for (OWLDataPropertyRangeAxiom item : ontology.getAxioms(AxiomType.DATA_PROPERTY_RANGE)) {
				OWLClass p = a.getSignature().iterator().next().getClassesInSignature().iterator().next();				
				if(item.containsEntityInSignature(p)) {
					System.out.println("DATA_PROPERTY_RANGE"+item.getProperty());
				}
		
				}
			}
		}


	}

	@Test
	public void ontologyAnnotations() {
		
		
		for (OWLAnnotationAssertionAxiom a : ontology.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
			System.out.println(a);
			System.out.println("a.getAnnotation().getProperty()="+a.getAnnotation().getProperty());
			System.out.println("a.getAnnotation().getSignature()="+a.getAnnotation().getSignature());
			System.out.println("a.getAnnotation().getValue()="+a.getAnnotation().getValue());
			System.out.println("a.getAnnotation().getSubject()="+a.getSubject());
			//System.out.println(((OWLLiteral) a.getValue()).getLiteral());
		}
	}

	@Test
	public void ontologyClassDeclarations() {
	

		for (OWLAxiom a : AxiomType.getAxiomsOfTypes(ontology.getAxioms(), AxiomType.DECLARATION)) {
			if(a.getSignature().iterator().next().isOWLClass()) {
				System.out.println(a);
			}
			
		
		}

	}
	@Test
	public void NamedIndividuals() {

		for (OWLNamedIndividual individual: this.ontology.getIndividualsInSignature()) {
			
			System.out.println("individual "+individual.getIRI().getFragment());
		}
	}

	
	@Test
	public void NamedIndividualsByAxioms() {

		for (OWLDeclarationAxiom individual : ontology.getAxioms(AxiomType.DECLARATION)) {

			for (OWLNamedIndividual iterable_element : individual.getIndividualsInSignature()) {
				System.out.println(iterable_element.getIRI().getFragment());
			}

			
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
				System.out.println("DATA_PROPERTY_DOMAIN item.getDomain()="+item.getDomain());
				System.out.println("DATA_PROPERTY_DOMAIN item.getProperty()="+item.getProperty());
			
				for (OWLDataPropertyRangeAxiom item2 : ontology.getAxioms(AxiomType.DATA_PROPERTY_RANGE)) {
					
					if(item.getProperty().equals(item2.getProperty())) {
						System.out.println("OUTPUT = "+item.getProperty().getSignature().iterator().next().getIRI().getFragment());
						System.out.println("OUTPUT RANGE= "+item2.getRange().getSignature().iterator().next().getIRI());
					}
//					System.out.println("DATA_PROPERTY_RANGE item.getProperty() "+item2.getProperty());
//					System.out.println("DATA_PROPERTY_RANGE item.getRange() "+item2.getRange());
//					
				}
		}
//		System.out.println("------");
//		for (OWLDataPropertyAssertionAxiom item : ontology.getAxioms(AxiomType.DATA_PROPERTY_ASSERTION)) {
//			System.out.println("DATA_PROPERTY_ASSERTION "+item);
//		}
//		System.out.println("------");
//		for (OWLDataPropertyRangeAxiom item : ontology.getAxioms(AxiomType.DATA_PROPERTY_RANGE)) {
//			System.out.println("DATA_PROPERTY_RANGE item.getProperty() "+item.getProperty());
//			System.out.println("DATA_PROPERTY_RANGE item.getRange() "+item.getRange());
//			
//		}
		
		



	}

	@Test
	public void getObjectPropertiesTest() {
		
		
		for (OWLObjectPropertyDomainAxiom item : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
//			System.out.println("OBJECT_PROPERTY_DOMAIN "+item);
//			System.out.println("item.getDomain() "+item.getDomain());
//			System.out.println("item.getProperty() "+item.getProperty());
			for (OWLObjectPropertyRangeAxiom item2 : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE)) {
				if(item.getProperty().equals(item2.getProperty())) {
					System.out.println("prop="+item2.getProperty().getSignature().iterator().next().getIRI().getFragment());
					System.out.println("range="+item2.getRange().getSignature().iterator().next().getIRI().getFragment());
				}
				
//				System.out.println("OBJECT_PROPERTY_RANGE "+item2);
//				System.out.println("item.getRange() "+item2.getRange());
//				System.out.println("item.getProperty() "+item2.getProperty());
			}
		}
//		System.out.println("------");
//		for (OWLObjectPropertyAssertionAxiom item: ontology.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION)) {
//			System.out.println("OBJECT_PROPERTY_ASSERTION "+item);
//		}
//		System.out.println("------");
//		for (OWLObjectPropertyRangeAxiom item : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE)) {
//			System.out.println("OBJECT_PROPERTY_RANGE "+item);
//			System.out.println("item.getRange() "+item.getRange());
//			System.out.println("item.getProperty() "+item.getProperty());
//		}

	}

	@Test
	public void listAllEnumerations() {
		for (OWLAxiom a : ontology.getAxioms()) {
			if (a.isOfType(AxiomType.EQUIVALENT_CLASSES))
				for (OWLClassExpression ce : ((OWLEquivalentClassesAxiom) a).getClassExpressions()) {
					if (ce instanceof OWLObjectOneOf) {
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
		for (OWLAxiom a : ontology.getAxioms(cls,Imports.EXCLUDED)) {
			System.out.println(a);
		}
	}
	
	@Test
	public void declarationsTest() {
		for (OWLDeclarationAxiom iterable_element : ontology.getAxioms(AxiomType.DECLARATION)) {
			
			for (OWLObjectProperty iterable_element2 : iterable_element.getObjectPropertiesInSignature()) {
				System.out.println(iterable_element2.getObjectPropertiesInSignature());
			}
		}
		System.out.println("-------------------");
		for (OWLObjectPropertyDomainAxiom iterable_element : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
			System.out.println(iterable_element );
		}
		System.out.println("-------------------");
		for (OWLObjectPropertyRangeAxiom iterable_element : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE)) {
			System.out.println(iterable_element );
		}
	}
	
	@Test
	public void EntitySearchClassTest() throws OWLOntologyCreationException, IOException {
		
	
		for (OWLAxiom iterable_element : ontology.getAxioms(Imports.EXCLUDED)) {
			if(iterable_element.isOfType(AxiomType.DECLARATION) && iterable_element.getSignature().iterator().next().isOWLClass()) {
				OWLClass aux = iterable_element.getSignature().iterator().next().asOWLClass();
				System.out.println(EntitySearcher.getAnnotationObjects(aux, ontology).size());
//				System.out.println("EntitySearcher.getAnnotationObjects "+EntitySearcher.getAnnotationObjects(aux, ontology).size());
				for (OWLAnnotation iterable_element2 : EntitySearcher.getAnnotationObjects(aux, ontology)) {
					System.out.println("clase="+aux.getIRI());
					System.out.println("iterable_element2="+iterable_element2);
					System.out.println("value="+iterable_element2.getValue());
					System.out.println("property="+iterable_element2.getProperty());
					System.out.println("signature="+iterable_element2.getSignature().iterator().next().getIRI());
				}
//				System.out.println("EntitySearcher.getInstances "+EntitySearcher.getInstances(aux, ontology).size());
//				System.out.println("EntitySearcher.getIndividuals "+EntitySearcher.getIndividuals(aux, ontology).size());
					
	
			}
		
		}
	}

	@Test
	public void DataPropsViaEntitySearchTest() throws Exception {
		
		for (OWLAxiom iterable_element : ontology.getAxioms(AxiomType.DECLARATION)) {
			
				if(iterable_element.getDataPropertiesInSignature().size()!=0) {
					System.out.println(iterable_element);
				}
		}
		

		
		
	}

	@Test
	public void hasMemberOfTypeTest() {
		
		
		for (OWLDeclarationAxiom iterable_element : this.ontology.getAxioms(AxiomType.DECLARATION)) {
			if(iterable_element.getSignature().iterator().next().isOWLClass()) {
				OWLClass cls =iterable_element.getSignature().iterator().next().asOWLClass();
				for (OWLClassExpression iterable: EntitySearcher.getSuperClasses(cls, ontology)) {
					System.out.println(iterable.isClassExpressionLiteral()+"  "+iterable);
				}
			}
		}
	}


	
	@Test
	public void OntologyPrefixes() {
		
		OWLDocumentFormat format = ontManager.getOntologyFormat(ontology);
		for (String iterable_element : format.asPrefixOWLOntologyFormat().getPrefixNames()) {
			System.out.println("prefix="+iterable_element+" value="+format.asPrefixOWLOntologyFormat().getPrefix(iterable_element));
		}
	}
}

