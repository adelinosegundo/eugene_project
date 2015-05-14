package models;

import util.Heapable;

public class SamplesDistance implements Heapable{
	private Sample from;
	private Sample to;
	private Double distance;
	
	public SamplesDistance(Sample from, Sample to, Double distance){
		this.from = from;
		this.to = to;
		this.distance = distance;
	}
	
	@Override
	public double get_priority() {
		// TODO Auto-generated method stub
		return distance;
	}
	
}
