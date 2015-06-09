/**
 * 
 */
package util.clustering;

import java.util.ArrayList;
import java.util.List;

import util.graphviz.GraphViz;

/**
 * @author Adelino Segundo (adelinosegundo@gmail.com)
 */

public interface Clusterable {
	    double getDistance();
	    ClusterPair getDistance(Clusterable cluster);
	    void setDistance(double distance);
	    List<Clusterable> getChildren();
	    void setChildren(List<Clusterable> children);
	    Clusterable getParent();
	    void setParent(Clusterable parent);
	    String getName();
	    void setName(String name);
	    void addChild(Clusterable from);
	    boolean contains(Clusterable cluster);
	    String toString();
	    int hashCode();
	    boolean isLeaf();
	    int countLeafs();
	    int countLeafs(Clusterable node, int count);
	    void toConsole(int indent);
		
}
