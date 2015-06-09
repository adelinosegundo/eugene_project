package util.clustering;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import util.Fatorial;
import util.graphviz.GraphViz;

public class DendogramBuilder {

    private PriorityQueue<ClusterPair> distances;
    Comparator<ClusterPair> comparator;
    private List<Clusterable> clusters;

    public DendogramBuilder(List<Clusterable> clusters) {
        this.clusters = clusters;
        comparator = new ClusterDistanceComparator();
        this.distances = new PriorityQueue<ClusterPair>(getSamplesDistancesAmount(), comparator);
    }
    
    public void generateClusters(){
		for(int i = 0; i<clusters.size();i++){
			for(int j=i+1;j<clusters.size();j++){
				if (i!=j)
					distances.add(clusters.get(i).getDistance(clusters.get(j)));
			}
		}
	}

    public void agglomerate() {
        ClusterPair minDistLink = distances.remove();
        if (minDistLink != null) {
            clusters.remove(minDistLink.getFrom());
            clusters.remove(minDistLink.getTo());

            Clusterable oldClusterL = minDistLink.getTo();
            Clusterable oldClusterR = minDistLink.getFrom();
            Clusterable newCluster = minDistLink.agglomerate(null);

            for (Clusterable iClust : clusters) {
            	ClusterPair link1 = findByClusters(iClust, oldClusterL);
            	ClusterPair link2 = findByClusters(iClust, oldClusterR);
            	
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
                
                ClusterPair newSampleDistance = new ClusterPair(iClust, newCluster, newDistance);
                distances.add(newSampleDistance);

            }
            clusters.add(newCluster);
        }
    }

    private ClusterPair findByClusters(Clusterable c1, Clusterable c2) {
        for(ClusterPair distance : distances){
        	if ((distance.getFrom() == c1 && distance.getTo() == c2)
        			||(distance.getFrom() == c2 && distance.getTo() == c1))
        		return distance;
        }
        return null;
    }

    public boolean isTreeComplete() {
        return clusters.size() == 1;
    }

    public Clusterable getRootCluster() {
        if (!isTreeComplete()) {
            throw new RuntimeException("No root available");
        }
        return clusters.get(0);
    }
    
    public int getSamplesDistancesAmount(){
		int n = clusters.size();
		int p = 2;
		return Fatorial.fatorial(n)/(Fatorial.fatorial(p)*Fatorial.fatorial(n-p));
	}

	/**
	 * 
	 */
	public void toConsole() {
		clusters.get(0).toConsole(0);
	}
	
	public void toGraphviz(){
		GraphViz gv = new GraphViz();
	    gv.addln(gv.start_graph());
	    gv.addln("overlap = false; splines = false;");
	    ArrayList<Clusterable> nivel = new ArrayList<Clusterable>();
	    nivel.add(clusters.get(0));
		Cluster.toGraphviz(gv, 0, nivel);
		gv.addln(gv.end_graph());
	    System.out.println(gv.getDotSource());
	      

	    String type = "png";
	    File out = new File("/tmp/out." + type);
	    gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	}

	/**
	 * 
	 */
	public void generateDendogram() {
		while(!this.isTreeComplete())
			this.agglomerate();
	}
	

}