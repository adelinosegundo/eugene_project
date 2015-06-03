/**
 * 
 */
package util.gene.signature.tests;

import models.Collection;

/**
 * @author Wendell P. Barreto (wendellp.barreto@gmail.com) 
 * @role Full Stack Developer
 * @formation Informatics Technician | IFRN
 * @formation Bachelor of Software Engineering (on going) | UFRN
 * @date May 30, 2015
 *
 */
public class GeneSignatureTestFacade {	
	private IGeneSignatureTest geneSignatureTest;
	
	public GeneSignatureTestFacade(String testType) {
		
		GeneSignatureTestFactory.getInstance();
		this.geneSignatureTest = GeneSignatureTestFactory.getRegisteredGeneSignatureTest(testType);
	}
	public double requestValue(double sample1[], double sample2[]) {
		return geneSignatureTest.requestValue(sample1, sample2);
	}
}
