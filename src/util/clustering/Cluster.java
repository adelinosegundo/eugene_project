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
    
    public void toGraphviz(GraphViz gv, int indent) {
    	gv.addln(gv.start_subgraph("root" + indent));
    	gv.addln("rank = same;");
    	for (int i = 0; i < indent + 1; i++) {
           gv.addln((isLeaf() ? " (Leaf)" : "NA") + indent);
        }
    	gv.addln(gv.end_subgraph());
    	
        String name = getName() + (isLeaf() ? " (leaf)" : "") + (distance > 0 ? "  distance: " + distance : "");
        System.out.println(name);
        for (Clusterable child : getChildren()) {
            child.toGraphviz(gv, indent + 1);
        }
    }
    
//    gv.addln(gv.start_subgraph("root"));
//    gv.addln("rank = same;");
//    gv.addln("Root [shape = point];");
//    gv.addln(gv.end_subgraph());	
//    gv.addln(gv.start_subgraph("root2"));
//    gv.addln("rank = same;");
//    gv.addln("Root1 [shape = point];");
//    gv.addln("Root2 [shape = point];");
//    gv.addln("Root3 [shape = point];");
//    gv.addln("Root1 -> Root2 [dir = none];");
//    gv.addln("Root2 -> Root3 [dir = none];");
//    gv.addln(gv.end_subgraph());
//    gv.addln("Root -> Root2 [dir = none]");
 

}