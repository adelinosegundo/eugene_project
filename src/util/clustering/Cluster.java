package util.clustering;

import java.util.ArrayList;
import java.util.List;

import util.graphviz.GraphViz;

public class Cluster implements Clusterable{

    private String name;

    private Clusterable parent;

    private List<Clusterable> children;

	private double distance;

    public double getDistance() {
        return distance;
    }
    
    public ClusterPair getDistance(Clusterable cluster) {
        return new ClusterPair(this, cluster, Math.abs(distance-cluster.getDistance()));
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<Clusterable> getChildren() {
        if (children == null) {
            children = new ArrayList<Clusterable>();
        }

        return children;
    }

    public void setChildren(List<Clusterable> children) {
        this.children = children;
    }

    public Clusterable getParent() {
        return parent;
    }

    public void setParent(Clusterable parent) {
        this.parent = parent;
    }

    public Cluster(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addChild(Clusterable from) {
        getChildren().add(from);

    }

    public boolean contains(Clusterable cluster) {
        return getChildren().contains(cluster);
    }

    @Override
    public String toString() {
        return "Cluster " + name;
    }

    @Override
    public int hashCode() {
        return (name == null) ? 0 : name.hashCode();
    }

    public boolean isLeaf() {
        return getChildren().size() == 0;
    }

    public int countLeafs() {
        return countLeafs(this, 0);
    }

    public int countLeafs(Clusterable node, int count) {
        if (node.isLeaf()) count++;
        for (Clusterable child : node.getChildren()) {
            count += child.countLeafs();
        }
        return count;
    }
    
    public void toConsole(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("_");

        }
        String name = getName() + (isLeaf() ? " (leaf)" : "") + (distance > 0 ? "  distance: " + distance : "");
        System.out.println(name);
        for (Clusterable child : getChildren()) {
            child.toConsole(indent + 1);
        }
    }
    
    public static void toGraphviz(GraphViz gv, int level, ArrayList<Clusterable> levelClusters) {
		ArrayList<Clusterable> newNivel = new ArrayList<Clusterable>();
   
    	gv.addln(gv.start_subgraph("root" + level));
    	gv.addln("rank = same;");
    	
    	for (int i = 0; i < levelClusters.size(); i++) {
    		Clusterable clstr = levelClusters.get(i);
    		String clusterName = "\"" + clstr.getName() + "lvl" + level + "." + i + "\"";
    		String clusterConnector1Name = "\"" + "conn" + "lvl" + level + "." + i + "-c1\"";
			String clusterConnector2Name = "\"" + "conn" + "lvl" + level + "." + i + "-c2\"";
			
    		if (clstr.isLeaf()) {
    			gv.addln(clusterName + " [shape = box, regular = 1];");
    		} else {
    			gv.addln(clusterConnector1Name + " [shape = point];");
    			gv.addln(clusterName + " [shape = point];");
    			gv.addln(clusterConnector2Name + " [shape = point];");
    			
    			gv.addln(clusterConnector1Name + " -> " + clusterName + " [dir = none]");
    			gv.addln(clusterName + " -> " + clusterConnector2Name + " [dir = none]");
    			
    			for (int j = 0; j < clstr.getChildren().size(); j++) {
    				Clusterable clusterChildren = clstr.getChildren().get(j);
    				
    				newNivel.add(clusterChildren);
    			}
    		}
    	}
    	
    	gv.addln(gv.end_subgraph());
    	
    	int counter = 0;
    	for (int i = 0; i < levelClusters.size(); i++) {
    		Clusterable clstr = levelClusters.get(i);
    		String clusterConnector1Name = "\"" + "conn" + "lvl" + level + "." + i + "-c1\"";
			String clusterConnector2Name = "\"" + "conn" + "lvl" + level + "." + i + "-c2\"";
			
    		for (int j = 0; j < clstr.getChildren().size(); j++) {
				Clusterable clusterChildren = clstr.getChildren().get(j);
				String clusterChildrenName = "\"" + clusterChildren.getName() + "lvl" + (level + 1) + "." + counter + "\"" ;

				if (j == 0) gv.addln(clusterConnector1Name + " -> " + clusterChildrenName + " [dir = none]");
				if (j == 1) gv.addln(clusterConnector2Name + " -> " + clusterChildrenName + " [dir = none]");
				
				counter++;
			}
    	}

    	if (!newNivel.isEmpty()) {
    		Cluster.toGraphviz(gv, level + 1, newNivel);
        }
    	
    	System.out.println(gv.getDotSource());
	}
}