package tests;

import static org.junit.Assert.*;
import models.Gene;

import org.junit.Test;

public class GeneTest {
	Gene gene;
	
	@Test
	public void testValid() {
		double[] samples = {100, 110, 90, 100, 100}; 
		gene =  new Gene("g1",samples, 3, 4);
		gene.process();
	}
	
	@Test
	public void testInvalid() {
		double[] samples = {0, 0, 0, 100, 100}; 
		gene =  new Gene("g1",samples, 3, 4);
		gene.process();
	}

}
