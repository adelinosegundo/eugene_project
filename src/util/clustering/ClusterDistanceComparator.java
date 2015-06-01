/**
 * 
 */
package util.clustering;

import java.util.Comparator;

/**
 * @author Adelino Segundo (adelinosegundo@gmail.com)
 */
public class ClusterDistanceComparator implements Comparator<ClusterPair>{
	@Override
	public int compare(ClusterPair clusterPair1, ClusterPair clusterPair2) {
		// TODO Auto-generated method stub
		if(clusterPair1.getDistance() > clusterPair2.getDistance())
			return 1;
		else if(clusterPair1.getDistance() < clusterPair2.getDistance())
			return -1;
		else
			return 0;
	}
}
