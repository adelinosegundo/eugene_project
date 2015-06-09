package tests.controllers;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import models.Collection;
import models.SampleClusteringValidator;

import org.junit.Before;
import org.junit.Test;

import util.clustering.Cluster;
import util.clustering.Clusterable;
import util.clustering.DendogramBuilder;
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
		collection.buildDendogram();
		System.out.println(collection.getDendogramBuilder().getDistanceMatrixString());
//		if(collection.buildDendogram())
//			collection.getDendogramBuilder().toGraphviz(new File("/Users/adelinosegundo/Downloads/out.png"));
		
//		collection.validateDendogram();
//		collection.leaveOneOutValidation();
	}

}
