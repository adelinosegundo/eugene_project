package util.clustering;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import util.Fatorial;
import models.Sample;
import models.SamplesDistance;

public class HierarchyBuilder {

    private PriorityQueue<SamplesDistance> distances;
    Comparator<SamplesDistance> comparator;
    private List<Sample> samples;

    public HierarchyBuilder(List<Sample> clusters) {
        this.samples = clusters;
        comparator = new SamplesDistanceComparator();
        this.distances = new PriorityQueue<SamplesDistance>(getSamplesDistancesAmount(), comparator);
    }
    
    public void generateClusters(){
		
		for(int i = 0; i<samples.size();i++){
			for(int j=i+1;j<samples.size();j++){
				if (i!=j)
					distances.add(samples.get(i).getDistance(samples.get(j)));
			}
		}
	}

    public void agglomerate() {
        SamplesDistance minDistLink = distances.remove();
        if (minDistLink != null) {
            samples.remove(minDistLink.getFrom());
            samples.remove(minDistLink.getTo());

            Sample oldClusterL = minDistLink.getTo();
            Sample oldClusterR = minDistLink.getFrom();
            Sample newCluster = minDistLink.agglomerate(null);

            for (Sample iClust : samples) {
            	SamplesDistance link1 = findByClusters(iClust, oldClusterL);
            	SamplesDistance link2 = findByClusters(iClust, oldClusterR);
            	
                double[] distanceValues = new double[2];

                if (link1 != null) {
                    distanceValues[0] = link1.getDistance();;
                    distances.remove(link1);
                }
                if (link2 != null) {
                	distanceValues[1] = link2.getDistance();;
                    distances.remove(link2);
                }
                
                double newDistance = 0;
                
                if (distanceValues[0] < distanceValues[1])
                	newDistance = distanceValues[0];
                else
                	newDistance = distanceValues[1];
                
                SamplesDistance newSampleDistance = new SamplesDistance(iClust, newCluster, newDistance);
                distances.add(newSampleDistance);

            }
            samples.add(newCluster);
        }
    }

    private SamplesDistance findByClusters(Sample c1, Sample c2) {
        for(SamplesDistance distance : distances){
        	if ((distance.getFrom() == c1 && distance.getTo() == c2)
        			||(distance.getFrom() == c2 && distance.getTo() == c1))
        		return distance;
        }
        return null;
    }

    public boolean isTreeComplete() {
        return samples.size() == 1;
    }

    public Cluster getRootCluster() {
        if (!isTreeComplete()) {
            throw new RuntimeException("No root available");
        }
        return samples.get(0);
    }
    
    public int getSamplesDistancesAmount(){
		int n = samples.size();
		int p = 2;
		return Fatorial.fatorial(n)/(Fatorial.fatorial(p)*Fatorial.fatorial(n-p));
	}

	/**
	 * 
	 */
	public void toConsole() {
		samples.get(0).toConsole(0);
	}
	

}