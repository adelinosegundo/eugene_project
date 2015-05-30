package models;

import util.clustering.Cluster;
import util.clustering.SamplesDistanceComparator;

public class SamplesDistance{
	private Sample from;
	private Sample to;
	private Double distance;
	
	public SamplesDistance(Sample from, Sample to, Double distance){
		this.from = from;
		this.to = to;
		this.distance = distance;
	}

	public Sample getFrom() {
		return from;
	}

	public void setFrom(Sample from) {
		this.from = from;
	}

	public Sample getTo() {
		return to;
	}

	public void setTo(Sample to) {
		this.to = to;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

    public Sample agglomerate(String name) {
        if (name == null) {
            name = "clstr#";
        }
        Sample cluster = new Sample(name, new Kind("none", -1));
        cluster.setDistance(distance);
        cluster.addChild(from);
        cluster.addChild(to);
        from.setParent(cluster);
        to.setParent(cluster);

        return cluster;
    }
	
}
