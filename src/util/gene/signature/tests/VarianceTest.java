/**
 * Adapted from http://en.wikipedia.org/wiki/Algorithms_for_calculating_variance
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
public class VarianceTest {

	public double varianceTestBetweenSamples(double[] sample1, double[] sample2) {
		double varianceSample1 = varianceTest(sample1);
		double varianceSample2 = varianceTest(sample2);
		
		if (varianceSample1 > varianceSample2) {
			return varianceSample1 - varianceSample2;
		} else {
			return varianceSample2 - varianceSample1;
		}
	}
	
	private double varianceTest(double[] sample) {
		double k = sample[0];
		int n = 0;
		double sum = 0;
		double sum_sqr = 0;
		
		for (int i = 0; i < sample.length; i++) {
			n++;
			sum += sample[i] - k;
			sum_sqr += (sample[i] - k) * (sample[i] - k);
		}
		
		double result = (sum_sqr - (sum * sum) / n) / (n);
		
		return result;
	}

}
