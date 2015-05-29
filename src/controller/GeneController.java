package controller;
import models.Gene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class GeneController {
	List<Gene> genes;
	public GeneController(){
		genes = new ArrayList<Gene>();
	} 
	public void processSamples(String controlFileName, String samplesFileName){
        // Read control file
        String line = null;
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> kinds = new ArrayList<String>();
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(controlFileName);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                String[] nameAndKind = line.split(" ");
            	names.add(nameAndKind[0]);
                kinds.add(nameAndKind[1]);
            }
            // Always close files.
            bufferedReader.close();            
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                	controlFileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + controlFileName + "'");
        }
        
        //Read samples file
        Scanner scanner;
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(samplesFileName);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                Gene gene = new Gene(line.split(" ")[0]);
                scanner = new Scanner(line);
                Double expression;
                int index = 0;
                while(scanner.hasNextDouble()){
                	expression = scanner.nextDouble();
                	String kind = kinds.get(index);
            		if (kind.equals("controle")){
            			gene.addSample(expression, true);
            		} else {
            			gene.addSample(expression, false);
            		}
                	index++;
                }
                genes.add(gene);
            }
            // Always close files.
            bufferedReader.close();            
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                	controlFileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + controlFileName + "'");
        }
        for(Gene gene : genes){
        	gene.print();
        }
		//process genes one by one
		//generate file from database
	}
}
