package controller;

/**
 * @author Wendell P. Barreto (wendellp.barreto@gmail.com) 
 * @author Adelino Neto Segundo (adelinosegundo@gmail.com) 
 * @role Full Stack Developer
 * @formation Informatics Technician | IFRN
 * @formation Bachelor of Software Engineering (on going) | UFRN
 * @date May 30, 2015
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import util.gene.signature.tests.GeneSignatureTestFacade;
import models.Collection;
import models.Gene;
import models.GeneSignature;
import models.GeneSignatureComparator;
import models.Group;
import models.Sample;

public class CollectionController {
	
	public void performGenesValidationByVariation(Collection collection, int quantity) {
		ArrayList<GeneSignature> genesSignatures = prepareGenesSignatures(collection);
		
		collection.setGenesValidFalse();
		
		for (GeneSignature geneSignature : genesSignatures) {
			Gene currentGene = geneSignature.getBaseGene();
			ArrayList<Double> sample1 = geneSignature.getSample1();
			ArrayList<Double> sample2 = geneSignature.getSample2();
			double[] sample1double = new double[sample1.size()];
			double[] sample2double = new double[sample2.size()];
			
			System.out.print(currentGene.getMarker() + " | " + currentGene.getGroup().getName());
			for (int k = 0; k < sample1.size(); k++) {
				sample1double[k] = sample1.get(k);
				
				System.out.print(" [" + sample1.get(k) + "]");
			}
			System.out.println("");	

			System.out.print(currentGene.getMarker()  + " | " + currentGene.getGroup().getName());
			for (int m = 0; m < sample2.size(); m++) {
				sample2double[m] = sample2.get(m);
				
				System.out.print(" [" + sample2.get(m) + "]");
			}

			GeneSignatureTestFacade geneSignatureTest = new GeneSignatureTestFacade("variance");
			double value = geneSignatureTest.requestValue(sample1double, sample2double);
			
			geneSignature.setVariance(value);
		}
		
		Collections.reverseOrder(new GeneSignatureComparator());
		Collections.sort(genesSignatures, new GeneSignatureComparator());
		
		for (int i = 0; i < quantity; i++) {
			genesSignatures.get(i).getBaseGene().setValid(true);
		}
	}

	@SuppressWarnings("null")
	public void performGenesValidationByPValue(Collection collection, String testType, double min, double max) {
		ArrayList<GeneSignature> genesSignatures = prepareGenesSignatures(collection);
		
		collection.setGenesValidTrue();
		
		for (GeneSignature geneSignature : genesSignatures) {
			Gene currentGene = geneSignature.getBaseGene();
			ArrayList<Double> sample1 = geneSignature.getSample1();
			ArrayList<Double> sample2 = geneSignature.getSample2();
			double[] sample1double = null;
			double[] sample2double = null;
			
			System.out.print(currentGene.getMarker() + " | " + currentGene.getGroup().getName());
			for (int k = 0; k < sample1.size(); k++) {
				sample1double[k] = sample1.get(k);
				
				System.out.print(" [" + sample1.get(k) + "]");
			}
			System.out.println("");	

			System.out.print(currentGene.getMarker()  + " | " + currentGene.getGroup().getName());
			for (int m = 0; m < sample2.size(); m++) {
				sample2double[m] = sample2.get(m);
				
				System.out.print(" [" + sample2.get(m) + "]");
			}

			GeneSignatureTestFacade geneSignatureTest = new GeneSignatureTestFacade(testType);
			double pValue = geneSignatureTest.requestValue(sample1double, sample2double);
			geneSignature.getBaseGene().setPValue(pValue);
			
			 if (pValue < min || pValue > max) {
				 collection.setGenesInvalidByMarker(geneSignature.getBaseGene().getMarker());
			 }
		}
	}
	


	public ArrayList<GeneSignature> prepareGenesSignatures(Collection collection) {
		Sample baseSample = collection.getSamples().get(0);
		ArrayList<Group> groups = (ArrayList<Group>) collection.getGroups();
		ArrayList<GeneSignature> geneSignatures = new ArrayList<GeneSignature>();

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
				Group firstGroup = groups.get(j);
				ArrayList<Double> firstGroupExpressions = expressionsByGroups.get(firstGroup);

				for (int l = j + 1; l < groups.size(); l++) {
					Group secondGroup = groups.get(l);
					ArrayList<Double> secondGroupExpressions = expressionsByGroups.get(secondGroup);
					
					if (firstGroupExpressions != null && secondGroupExpressions != null) {
						geneSignatures.add(new GeneSignature(baseSample.getGenes().get(i), firstGroupExpressions, secondGroupExpressions));
					}
				}
			} 
		}
		
		return geneSignatures;
	}
	
	public void performGenesValidationByVariation(Collection collection, String string) {
		// TODO Auto-generated method stub
	}
	
	public boolean performLeaveOneOutValidation(Collection collection) {
		return collection.leaveOneOutValidation();
	}

	public boolean performGroupValidation(Collection collection) {
		return collection.validateDendogram();
	}


	public void writeDistanceMatrix(Collection collection, File file) {
		// TODO Auto-generated method stub
		PrintWriter writer;
		try {
			writer = new PrintWriter(file, "UTF-8");
			writer.println(collection.distanceMatrixToFileString());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void drawDendogram(Collection collection, File file) {
		if(collection.buildDendogram()) collection.getDendogramBuilder().toGraphviz(file);
			
	}

	public void writePvalues(Collection collection, File file) {
		// TODO Auto-generated method stub
		PrintWriter writer;
		try {
			writer = new PrintWriter(file, "UTF-8");
			writer.println(collection.pValuesToFileString());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




}
