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
	public static boolean ValidateDendogram(Clusterable cluster, List<Kind> kinds){
		boolean valid = true;
		List<Clusterable> queue = new LinkedList<Clusterable>();
		for(Clusterable childCluster: cluster.getChildren()){
			if (childCluster.isLeaf())
				queue.add(childCluster);
			queue.addAll(childCluster.getChildren());
			int kind_id = -1;
			while(!queue.isEmpty()){
				Clusterable subCluster = queue.remove(0);
				if (subCluster.isLeaf()){
					Sample sample = (Sample) subCluster;
					System.out.println(sample.getName() + sample.getKind().getName() + sample.getKind().getID());
					if(kind_id == -1)
						kind_id = sample.getKind().getID();
					else if (sample.getKind().getID() != kind_id)
						valid = false;
				}else{
					queue.addAll(subCluster.getChildren());
				}
			}
			System.out.println(valid);
		}
		System.out.println("-----------------------");
		return valid;
	}
}
