package models;

import java.util.Iterator;
import java.util.List;

import util.Heap;

public class Gene {
	private	String marker;
	private List<Sample> samples;
	private List<Sample> invalid_samples;
	private Heap heap;
	
	public String getMarker() {
		return marker;
	}
	
	public Gene(String maker, double[] expressions, int control_start, int control_end){
		//Add Samples
		this.marker = marker;
		for(int i = 0; i<expressions.length; i++){
			if (i >= control_start && i <= control_end)
				samples.add(new Sample(expressions[i], true));
			else
				samples.add(new Sample(expressions[i]));
		}
	}
	
	public void process(){
		this.validateSamples(); //Leave only valid samples in samples array
		this.calculateDistances(); //Construt heap and dendogram
		this.validateSamplesLeaveOneOut(); //Validate dendogram
	}
	
	private void validateSamples(){
		//Use statistic methods
	}
	
	private void calculateDistances(){
		//Use heap
		//Generate Dendogram
	}
	
	private void validateSamplesLeaveOneOut(){
		//Validate Dendogram
	}
}
