package tests.controllers;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.GeneController;

public class GeneControllerTest {

	@Test
	public void test() {
		GeneController geneController =  new GeneController();
		geneController.processSamples("/Users/adelinosegundo/Downloads/File2-ex-input.txt", "/Users/adelinosegundo/Downloads/File1-ex-input.txt");
	}

}
