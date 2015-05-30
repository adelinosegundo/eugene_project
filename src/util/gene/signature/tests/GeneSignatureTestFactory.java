/**
 * 
 */
package util.gene.signature.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	private HashMap<String, IGeneSignatureTest> registeredGeneSignatureTests = new HashMap<String, IGeneSignatureTest>();
	public static final String[] testTypes = {
		"variance", // TestType 1
		"wilcoxon", // TestType 2
		"ttest",  // TestType 3
		"chisquare",  // TestType 3
	};
	
	private GeneSignatureTestFactory() {
		GeneSignatureTestFactory.getInstance().register(testTypes[0], new VarianceTestAdapter());
		GeneSignatureTestFactory.getInstance().register(testTypes[1], new WilcoxonTestAdapter());
		GeneSignatureTestFactory.getInstance().register(testTypes[2], new TTestAdapter());
		GeneSignatureTestFactory.getInstance().register(testTypes[3], new ChiSquareTestAdapter());
		
		System.out.println("GeneSignatureTestFactory initialized!");
	}
	
	public static GeneSignatureTestFactory getInstance() {
		return instance;
	}
	
	public void register(String geneSignatureTestName, IGeneSignatureTest geneSignatureTest){
		this.registeredGeneSignatureTests.put(geneSignatureTestName, geneSignatureTest);
		
	}
}