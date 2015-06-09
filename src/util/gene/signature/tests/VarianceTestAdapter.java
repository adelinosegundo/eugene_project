/**
 * 
 */
package util.gene.signature.tests;

/**
 * @author Wendell P. Barreto (wendellp.barreto@gmail.com) 
 * @role Full Stack Developer
 * @formation Informatics Technician | IFRN
 * @formation Bachelor of Software Engineering (on going) | UFRN
 * @date May 30, 2015
 *
 */
public class VarianceTestAdapter implements IGeneSignatureTest {
	private VarianceTest varianceTest;
	
	public VarianceTestAdapter() {
		this.varianceTest = new VarianceTest();
	}
	
	/* (non-Javadoc)
	 * @see util.geneSignatureTests.IGeneSignatureTest#requestValue()
	 */
	@Override
	public double requestValue(double[] sample1, double[] sample2) {
		
		return this.varianceTest.varianceTestBetweenSamples(sample1, sample2);
	}
}
