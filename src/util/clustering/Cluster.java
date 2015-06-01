package util.clustering;

import java.util.ArrayList;
import java.util.List;

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
            System.out.print("  ");

        }
        String name = getName() + (isLeaf() ? " (leaf)" : "") + (distance > 0 ? "  distance: " + distance : "");
        System.out.println(name);
        for (Clusterable child : getChildren()) {
            child.toConsole(indent + 1);
        }
    }

}