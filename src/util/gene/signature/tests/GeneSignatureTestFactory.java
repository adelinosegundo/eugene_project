/**
 * 
 */
package util.gene.signature.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Wendell P. Barreto (wendellp.barreto@gmail.com) 
 * @role Full Stack Developer
 * @formation Informatics Technician | IFRN
 * @formation Bachelor of Software Engineering (on going) | UFRN
 * @date May 30, 2015
 *
 */
public class GeneSignatureTestFactory {
	private static GeneSignatureTestFactory instance = new GeneSignatureTestFactory();
	private HashMap<String, IGeneSignatureTest> registeredGeneSignatureTests;
	public final String[] testTypes = {
		"variance", // TestType 1
		"wilcoxon", // TestType 2
		"ttest",  // TestType 3
		"kolmogorov",  // TestType 3
	};
	
	private GeneSignatureTestFactory() {
		this.registeredGeneSignatureTests = new HashMap<String, IGeneSignatureTest>();
		this.register(testTypes[0], (IGeneSignatureTest) new VarianceTestAdapter());
		this.register(testTypes[1], new WilcoxonTestAdapter());
		this.register(testTypes[2].toString(), (IGeneSignatureTest) new TTestAdapter());
		this.register(testTypes[3].toString(), (IGeneSignatureTest) new KolmogorovSmirnovTestAdapter());
		
		System.out.println("GeneSignatureTestFactory initialized!");
		
		for (Map.Entry<String, IGeneSignatureTest> geneSignatureTest : this.registeredGeneSignatureTests.entrySet()) {
			System.out.println(" >>> " + geneSignatureTest.getKey());
		}
	}
	
	public static GeneSignatureTestFactory getInstance() {
		return instance;
	}
	
	public void register(String geneSignatureTestName, IGeneSignatureTest geneSignatureTest){
		this.registeredGeneSignatureTests.put(geneSignatureTestName, geneSignatureTest);	
	}
	
	public static IGeneSignatureTest getRegisteredGeneSignatureTest(String testType) {
		return GeneSignatureTestFactory.getInstance().registeredGeneSignatureTests.get(testType);
	}
}