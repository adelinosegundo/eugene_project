package controller;
import models.Collection;
import models.Gene;
import models.Group;
import models.Sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.commons.lang3.*;

import util.gene.signature.tests.GeneSignatureTestFacade;


public class GeneController {
	private static double MIN_PVALUE = 0;
	private static double MAX_PVALUE = 0.05;
	
	public void validateGenesByPvalue(Collection collection, String testType) {
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
//		collection.print();
	}
	
	
	public void processSamples(Collection collection, String samplesFileName, String controlsFileName){ // NEEDS REFACTORING
        String line = null;

		try {
            FileReader fileReader = new FileReader(controlsFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            System.out.println("Reading file " + controlsFileName + " ...\n");
            
            while((line = bufferedReader.readLine()) != null) {
                String[] sampleMarkerAndGroup = line.split(" ");
                
                
                System.out.println("'" + sampleMarkerAndGroup[0] + "'");
                System.out.println("'" + sampleMarkerAndGroup[1] + "'");
                
                String sampleName = sampleMarkerAndGroup[0];
                String groupName = sampleMarkerAndGroup[1];
                
                Group group = collection.addAndReturnGroup(groupName);
                
                collection.addSample(sampleName, group);   
            }
            bufferedReader.close();   
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + controlsFileName + "'");                
        } catch(IOException ex) {
            System.out.println("Error reading file '" + controlsFileName + "'");
        }  
		
		try {
            FileReader fileReader = new FileReader(samplesFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            System.out.println("Reading file " + samplesFileName + " ...\n");
            
            while((line = bufferedReader.readLine()) != null) {
            	String geneMarker = line.split(" ")[0].contains("	") ? line.split("	")[0] : line.split(" ")[0]; // Avoiding tabs as separator
            	
                Scanner scanner = new Scanner(line);
                Double expression;
                int counter = 0;
                
                while (scanner.hasNext()) {
	                if (scanner.hasNextDouble()){
	                	expression = scanner.nextDouble();
	                	
	                	Gene gene = new Gene(geneMarker, expression);
	                	collection.getSamples().get(counter).addGene(gene);
	                	
	                	counter++;
	                } else {
	                	scanner.next();
	                }
                }
            }
            
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + samplesFileName + "'");                
        } catch(IOException ex) {
            System.out.println("Error reading file '" + samplesFileName + "'");
        }  
	}
	
	public void saveToFile(Collection collection, String filePath){
		PrintWriter writer;
		try {
			writer = new PrintWriter(filePath+"AnalisysResult", "UTF-8");
			writer.println(collection.toFileString());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
