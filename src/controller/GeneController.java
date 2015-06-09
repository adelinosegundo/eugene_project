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
	}
}
