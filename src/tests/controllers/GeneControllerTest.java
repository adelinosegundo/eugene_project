package tests.controllers;

import static org.junit.Assert.*;
import models.Collection;

import org.junit.Before;
import org.junit.Test;

import util.clustering.HierarchyBuilder;
import controller.GeneController;

public class GeneControllerTest {
	Collection collection;
	
	@Before
	public void setUpBeforeClass() {
		this.collection = new Collection();
	}
	
	@Test
	public void test() {
		GeneController geneController =  new GeneController();
		geneController.processSamples(this.collection, "/Users/adelinosegundo/Downloads/File1-ex-input.txt", "/Users/adelinosegundo/Downloads/File2-ex-input.txt");
		HierarchyBuilder dendogram = new HierarchyBuilder(this.collection.getSamples());
		dendogram.generateClusters();
		while(!dendogram.isTreeComplete())
			dendogram.agglomerate();
		dendogram.toConsole();
		
	}

}
