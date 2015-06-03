package controller;
import models.Collection;
import models.Gene;
import models.Kind;
import models.Sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang3.*;

import util.gene.signature.tests.GeneSignatureTestFacade;


public class GeneController {
	private static double MAX_PVALUE = 0.005;
	
	public void validateGenesByPvalue(Collection collection, String testType) {
		GeneSignatureTestFacade geneSignatureTest = new GeneSignatureTestFacade(testType);

		for (int i = 0;i < collection.getSamples().get(0).getGenes().size(); i++) {
			List<Double> sample1 = new ArrayList<Double>();
			List<Double> sample2 = new ArrayList<Double>();
			
			for (int j = 0;j < collection.getSamples().size(); j++) {
				if (collection.getSamples().get(j).getGenes().get(i).getKind().getName().equals("controle")) {
					sample1.add(collection.getSamples().get(j).getGenes().get(i).getExpression());
				} else if (collection.getSamples().get(j).getGenes().get(i).getKind().getName().equals("caso")) {
					sample2.add(collection.getSamples().get(j).getGenes().get(i).getExpression());
				}
			}
			
			
			double[] sample1double = new double[sample1.size()];
			double[] sample2double = new double[sample2.size()];
			for (int v = 0; v < sample1.size(); v++) {
				sample1double[v] = sample1.get(v);
			}
			for (int v = 0; v < sample2.size(); v++) {
				sample2double[v] = sample2.get(v);
			}
			
			double value = geneSignatureTest.requestValue(sample1double, sample2double);
			System.out.println(value);
			if (value <= MAX_PVALUE) {
				collection.setGenesValidByMarker(collection.getSamples().get(0).getGenes().get(i).getMarker());
			}
		}
		collection.print();
	}
	
	
	public void processSamples(Collection collection, String samplesFileName, String controlsFileName){ // NEEDS REFACTORING
        String line = null;

		try {
            FileReader fileReader = new FileReader(controlsFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            System.out.println("Reading file " + controlsFileName + " ...\n");
            
            while((line = bufferedReader.readLine()) != null) {
                String[] sampleMarkerAndKind = line.split(" ");
                
                
                System.out.println("'" + sampleMarkerAndKind[0] + "'");
                System.out.println("'" + sampleMarkerAndKind[1] + "'");
                
                String sampleName = sampleMarkerAndKind[0];
                String kindName = sampleMarkerAndKind[1];
                
                Kind kind = collection.addAndReturnKind(kindName);
                
                collection.addSample(sampleName, kind);   
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
}
