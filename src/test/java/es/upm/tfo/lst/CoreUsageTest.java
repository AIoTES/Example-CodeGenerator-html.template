package es.upm.tfo.lst;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import es.upm.tfo.lst.CodeGenerator.GenerateProject;
import es.upm.tfo.lst.CodeGenerator.model.TemplateDataModel;
import es.upm.tfo.lst.CodeGenerator.owl.OntologyLoader;
import es.upm.tfo.lst.CodeGenerator.xmlparser.XmlParser;

public class CoreUsageTest {
	
	private XmlParser parser=null;
	private TemplateDataModel model=null;
	private GenerateProject genPro=null;
	private OntologyLoader ontologyLoader=null;
	//----constants
	private final String ontologyBasePath="src/test/resources/ontologies/";
	//private final String webOntology ="https://protege.stanford.edu/ontologies/pizza/pizza.owl";
	//private final String webOntology ="https://raw.githubusercontent.com/monarch-initiative/GENO-ontology/develop/src/ontology/geno.owl";
	private final String webOntology ="https://raw.githubusercontent.com/EuPath-ontology/EuPath-ontology/2019-04-02/eupath.owl";
	
	private final String baseOutput="target/generated/";
	private OWLOntologyManager ontManager;

	@Before
	public void init() {
		ontManager = OWLManager.createOWLOntologyManager();
		PropertyConfigurator.configure("src/test/resources/log4jConfigFile/log4j.properties");
		this.parser = new XmlParser();
		this.ontologyLoader = new OntologyLoader();
		this.genPro = new GenerateProject();
	}

	@Test
	public void CodeGeneratorCoreUsageTest() {

		 try {
		 	
			 	this.model=this.parser.generateXMLCoordinator("src/main/resources/coordinator.xml");
				this.genPro.setMainModel(this.model);
				//set the ontology to project and recursive state
				this.genPro.addOntology(this.ontologyLoader.loadOntology(this.webOntology), true);
				//set output directory
				this.genPro.setOutputFolder(this.baseOutput);
				//optional: add value to variables. You can add extra variable plus the variables provided into XML file
				this.genPro.setVariable("targetOperatingSystem","Linux");
				this.genPro.setVariable( "date_expire", "9-5-2019");				
				this.genPro.setVariable( "timezone", "(GMT+01:00)");
				File f = new File(baseOutput);
				f.mkdirs();
				genPro.process();
		} catch (Exception e) {
			e.printStackTrace();
			genPro.addError(e);
		}

		assertTrue(genPro.getErrors().isEmpty());
		}
}
