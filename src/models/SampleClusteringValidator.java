/**
 * 
 */
package models;

import java.util.List;
import java.util.LinkedList;

import util.clustering.Clusterable;

/**
 * @author Adelino Segundo (adelinosegundo@gmail.com)
 */
public class SampleClusteringValidator {
	public static boolean ValidateDendogram(Clusterable cluster, List<Group> groups){
		cluster.toConsole(0);
		boolean valid = true;
		List<Clusterable> queue = new LinkedList<Clusterable>();
		for(Clusterable childCluster: cluster.getChildren()){
			if (childCluster.isLeaf())
				queue.add(childCluster);
			queue.addAll(childCluster.getChildren());
			int group_id = -1;
			while(!queue.isEmpty()){
				Clusterable subCluster = queue.remove(0);
				if (subCluster.isLeaf()){
					Sample sample = (Sample) subCluster;
//					System.out.println(sample.getName() + sample.getGroup().getName() + sample.getGroup().getID());
					if(group_id == -1)
						group_id = sample.getGroup().getID();
					else if (sample.getGroup().getID() != group_id)
						valid = false;
				}else{
					queue.addAll(subCluster.getChildren());
				}
			}
		}
		System.out.println(valid);
		System.out.println("-----------------------");
		return valid;
	}
}
