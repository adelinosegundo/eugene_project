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
    private Clusterable root;

    public DendogramBuilder(List<Clusterable> clusters) {
        this.clusters = clusters;
        comparator = new ClusterDistanceComparator();
    }
    
    public void generateClustersDistances(){
    	this.distances = new PriorityQueue<ClusterPair>(getSamplesDistancesAmount(), comparator);
		for(int i = 0; i<clusters.size();i++){
			for(int j=i+1;j<clusters.size();j++){
				if (i!=j)
					distances.add(clusters.get(i).getDistance(clusters.get(j)));
			}
		}
	}

    public void agglomerate() {
    	if (distances.size() > 0){
    		List<Clusterable> tree_clusters = new ArrayList<Clusterable>();
    		tree_clusters.addAll(clusters);
    		while(!(tree_clusters.size() == 1)){
    			ClusterPair minDistLink = distances.remove();
    	        if (minDistLink != null) {
    	            tree_clusters.remove(minDistLink.getFrom());
    	            tree_clusters.remove(minDistLink.getTo());

    	            Clusterable oldClusterL = minDistLink.getTo();
    	            Clusterable oldClusterR = minDistLink.getFrom();
    	            Clusterable newCluster = minDistLink.agglomerate(null);

    	            for (Clusterable iClust : tree_clusters) {
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
    	            tree_clusters.add(newCluster);
    	        }
    		}
    		this.root = tree_clusters.get(0);
    		this.generateClustersDistances();
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
        return root != null;
    }

    public Clusterable getRootCluster() {
        if (!isTreeComplete()) {
            throw new RuntimeException("No root available");
        }
        return root;
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
		root.toConsole(0);
	}
	
	public void toGraphviz(File file){
		GraphViz gv = new GraphViz();
	    gv.addln(gv.start_graph());
	    gv.addln("overlap = false; splines = false;");
	    ArrayList<Clusterable> nivel = new ArrayList<Clusterable>();
	    nivel.add(root);
		Cluster.toGraphviz(gv, 0, nivel);
		gv.addln(gv.end_graph());
	    String type = "png";
	    gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), file );
	}

	/**
	 * 
	 */
	public void generateDendogram() {
		while(!this.isTreeComplete())
			this.agglomerate();
	}
	

}