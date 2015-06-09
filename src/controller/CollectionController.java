package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import util.gene.signature.tests.GeneSignatureTestFacade;
import models.Collection;
import models.Gene;
import models.Group;
import models.Sample;

public class CollectionController {
	private static double MIN_PVALUE = 0;
	private static double MAX_PVALUE = 0.05;
	
	public void performGenesValidation(Collection collection, String testType) {
		GeneSignatureTestFacade geneSignatureTest = new GeneSignatureTestFacade(testType);
		Sample baseSample = collection.getSamples().get(0);
		ArrayList<Group> groups = (ArrayList<Group>) collection.getGroups();

		for (int i = 0; i < baseSample.getGenes().size(); i++) {
			HashMap<Group, ArrayList<Double>> expressionsByGroups = new HashMap<Group, ArrayList<Double>>();
			ArrayList<Sample> samples = (ArrayList<Sample>) collection.getSamples();
			boolean isValid = true;
			double pValue = 0;
			
			for (Group group : groups) {
				expressionsByGroups.put(group, new ArrayList<Double>());
			}
			
			for (int j = 0; j < samples.size(); j++) {
				Sample currentSample = samples.get(j);
				Gene currentGene = currentSample.getGenes().get(i);
				
				expressionsByGroups.get(currentGene.getGroup()).add(currentGene.getExpression());
			}
			
			for (int j = 0; j < groups.size(); j++) {
				double[] firstSample = null;
				double[] secondSample = null;
				Group firstGroup = groups.get(j);
				ArrayList<Double> firstGroupExpressions = expressionsByGroups.get(firstGroup);
				firstSample = new double[firstGroupExpressions.size()];
					
				System.out.print(baseSample.getGenes().get(i).getMarker() + " | " + firstGroup.getName());
				for (int k = 0; k < firstGroupExpressions.size(); k++) {
					firstSample[k] = firstGroupExpressions.get(k);
					
					System.out.print(" [" + firstGroupExpressions.get(k) + "]");
				}
				System.out.println("");	
			
				for (int l = j + 1; l < groups.size(); l++) {
					Group secondGroup = groups.get(l);
					ArrayList<Double> secondGroupExpressions = expressionsByGroups.get(secondGroup);
					secondSample = new double[secondGroupExpressions.size()];
					
					System.out.print(baseSample.getGenes().get(i).getMarker() + " | " + secondGroup.getName());
					for (int m = 0; m < secondGroupExpressions.size(); m++) {
						secondSample[m] = secondGroupExpressions.get(m);
						
						System.out.print(" [" + secondGroupExpressions.get(m) + "]");
					}
				}
				
				if (firstSample != null && secondSample != null) {
					pValue = geneSignatureTest.requestValue(firstSample, secondSample);
					System.out.println(" p-value: " + pValue);
					
					if (pValue < MIN_PVALUE || pValue > MAX_PVALUE) {
						isValid = false;
						
						break;
					}
				}
			}
			
			if (isValid) {
				collection.setGenesValidByMarker(baseSample.getGenes().get(i).getMarker());

				System.out.println(" ** VALID ");
			} 
		}
		
	}
	
	public boolean performLeaveOneOutValidation(Collection collection) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean performGroupValidation(Collection collection) {
		// TODO Auto-generated method stub
		return false;
	}


	public void writeDistanceMatrix(Collection collection, File file) {
		// TODO Auto-generated method stub
		
	}

	public void drawDendogram(Collection collection, File file) {
		// TODO Auto-generated method stub
		
	}

	public void writePvalues(Collection collection, File file) {
		// TODO Auto-generated method stub
		
	}




}
