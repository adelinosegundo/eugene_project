/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Wendell P. Barreto (wendellp.barreto@gmail.com) 
 * @role Full Stack Developer
 * @formation Informatics Technician | IFRN
 * @formation Bachelor of Software Engineering (on going) | UFRN
 * @date May 30, 2015
 *
 */
public class GeneSignature {
	public Double getVariance() {
		return variance;
	}

	public void setVariance(Double variance) {
		this.variance = variance;
	}
	private Gene baseGene;
	private Double variance;
	private ArrayList<Double> sample1;
	private ArrayList<Double> sample2;
	
	public GeneSignature(Gene baseGene, ArrayList<Double> sample1, ArrayList<Double> sample2) {
		this.baseGene = baseGene;
		this.sample1 = sample1;
		this.sample2 = sample2;
		this.variance = (double) 0;
	}
	
	public GeneSignature(){
		
	}
	
	public Gene getBaseGene() {
		return baseGene;
	}
	public void setBaseGene(Gene baseGene) {
		this.baseGene = baseGene;
	}
	public ArrayList<Double> getSample1() {
		return sample1;
	}
	public void setSample1(ArrayList<Double> sample1) {
		this.sample1 = sample1;
	}
	public ArrayList<Double> getSample2() {
		return sample2;
	}
	public void setSample2(ArrayList<Double> sample2) {
		this.sample2 = sample2;
	}


}
