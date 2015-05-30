package controller;
import models.Collection;
import models.Gene;
import models.Kind;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class GeneController {
	
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
