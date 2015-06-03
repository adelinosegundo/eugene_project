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
public class KolmogorovSmirnovTestAdapter implements IGeneSignatureTest {
	private KolmogorovSmirnovTest kolmogorovSmirnovTest;
	
	public KolmogorovSmirnovTestAdapter() {
		this.kolmogorovSmirnovTest = new KolmogorovSmirnovTest();
	}
	
	/* (non-Javadoc)
	 * @see util.geneSignatureTests.IGeneSignatureTest#requestValue(double, double)
	 */
	@Override
	public double requestValue(double[] sample1, double[] sample2) {
		
		return this.kolmogorovSmirnovTest.kolmogorovSmirnovStatistic(sample1, sample2);
	}
}
