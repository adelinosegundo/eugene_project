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

import util.gene.signature.tests.GeneSignatureTestFacade;


public class GeneController {
	private static double MAX_PVALUE = 0.005;
	
	public void validateGenesByPvalue(Collection collection, String testType) {
		ArrayList<Sample> tempSamples = new ArrayList<Sample>();
		GeneSignatureTestFacade geneSignatureTest = new GeneSignatureTestFacade(testType);
		
		for (Kind kind : collection.getKinds()) {
			Sample sample = new Sample(kind.getName(), kind);
			
			ArrayList<Sample> samplesOfKind = collection.getSamplesOfKind(kind);
			
			Sample firstSampleOfKind = samplesOfKind.get(0);
			sample.setGenes(firstSampleOfKind.getGenes());
			
			for (Sample sampleOfKind : samplesOfKind) {
				Iterator<Gene> sampleIterator = sample.getGenes().iterator();
				Iterator<Gene> atualSampleOfKindIterator = sampleOfKind.getGenes().iterator();
				
				while (sampleIterator.hasNext() &&  
							atualSampleOfKindIterator.hasNext()) {
					Gene geneBase = sampleIterator.next();
					Gene geneAtual = atualSampleOfKindIterator.next();
					
					double expressionValue = (geneBase.getExpression() + geneAtual.getExpression()) / 2 ;
					
					geneBase.setExpression(expressionValue);
				}
			}
			
			tempSamples.add(sample);
		}
		
		Sample firstSampleOfTemp = tempSamples.get(0);
		
		for (Sample sample : tempSamples) {
			Iterator<Gene> firstSampleIterator = firstSampleOfTemp.getGenes().iterator();
			Iterator<Gene> atualSampleOfTempIterator = sample.getGenes().iterator();
			
			while(firstSampleIterator.hasNext() &&
					atualSampleOfTempIterator.hasNext()) {
				Gene firstGene = firstSampleIterator.next();
				Gene atualGene = atualSampleOfTempIterator.next();
				Double value;
				
				value = geneSignatureTest.requestValue(firstGene.getExpression(), atualGene.getExpression());
				
				if (value <= MAX_PVALUE) {
					collection.setGenesValidByMarker(firstGene.getMarker());
				}
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
