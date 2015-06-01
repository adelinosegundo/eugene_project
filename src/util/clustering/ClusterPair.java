/**
 * 
 */
package util.clustering;

/**
 * @author Wendell P. Barreto (wendellp.barreto@gmail.com)
 * @author Adelino Neto Segundo (adelinosegundo@gmail.com) 
 * @role Full Stack Developer
 * @formation Informatics Technician | IFRN
 * @formation Bachelor of Software Engineering (on going) | UFRN
 * @date Jun 1, 2015
 *
 */

public class ClusterPair {
	
	private Clusterable from;
	private Clusterable to;
	private Double distance;
	
	public ClusterPair(Clusterable from, Clusterable to, Double distance){
		this.from = from;
		this.to = to;
		this.distance = distance;
	}

	public Clusterable getFrom() {
		return from;
	}

	public void setFrom(Clusterable from) {
		this.from = from;
	}

	public Clusterable getTo() {
		return to;
	}

	public void setTo(Clusterable to) {
		this.to = to;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

    public Clusterable agglomerate(String name) {
        if (name == null) {
            name = "clstr#";
        }
        Cluster cluster = new Cluster(name);
        cluster.setDistance(distance);
        cluster.addChild(from);
        cluster.addChild(to);
        from.setParent(cluster);
        to.setParent(cluster);

        return cluster;
    }
}
