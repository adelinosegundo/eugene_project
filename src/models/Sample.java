package models;

import java.util.ArrayList;
import java.util.List;

import util.clustering.Cluster;
import util.clustering.ClusterPair;
import util.clustering.Clusterable;


public class Sample extends Cluster{
	private String name;
	private List<Gene> genes;
	private Group group;

	public Sample(String name, Group group){
		super(name);
		this.setName(name);
		this.setGroup(group);
		this.genes = new ArrayList<Gene>();
	}

	public Sample(Group group, ArrayList<Gene> genes, String name){
		super(name);
		this.setGroup(group);
		this.setGenes(genes);
		this.setName(name);
	}

	public Sample(Group group, ArrayList<Gene> genes, boolean valid, String name){
		super(name);
		this.setGroup(group);
		this.setGenes(genes);
		this.setName(name);
	}

	public List<Gene> getGenes() {
		return genes;
	}

	public List<Gene> getGenesByMarker(String marker) {
		List<Gene> genes = new ArrayList<Gene>();
		
		for(Gene gene : this.genes) {
			if (gene.getMarker().equals(marker)) {
				genes.add(gene);
			}
		}
		
		return genes;
	}

	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	public void addGene(Gene gene){
		this.genes.add(gene);
	}
	
	public void addGene(String marker, Double expression){
		this.genes.add(new Gene(marker, expression));
	}
	
	public void addGenes(ArrayList<Gene> genes){
		for (Gene gene : genes) {
			this.genes.add(gene);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase().trim();
	}
	
	@Override
	public ClusterPair getDistance(Clusterable cluster){
		Sample sample = (Sample) cluster;
		double distance = 0;
		
		for(Gene gene : validGenes()){
			Gene correspondingGene = sample.getGeneByMarker(gene.getMarker());
			double distValue = (gene.getExpression()-correspondingGene.getExpression());
			distValue *= distValue;
			distance += distValue;
		}
		distance = (Math.round(Math.sqrt(distance)*100.0)/100.0);
		return new ClusterPair(this, sample, distance);
	}

	/**
	 * @param gene
	 * @return Gene by marker
	 */
	private Gene getGeneByMarker(String marker) {
		for(Gene gene : genes){
			if (gene.getMarker().equals(marker))
				return gene;
		}
		return null;
	}

	/**
	 * @return
	 */
	private ArrayList<Gene> validGenes() {
		ArrayList<Gene> validGenes = new ArrayList<Gene>();
		for(Gene gene : genes){
			if (gene.isValid()) 
				validGenes.add(gene);
		}
		return validGenes;
	}

	public void toFileString(boolean print_gene_names) {
		// TODO Auto-generated method stub
		
	}

	public String getPValuesString() {
		String resultString = "";
	    for(Gene gene : genes){
	    	resultString += gene.getMarker()+"  "+gene.getPValue()+"\n";
	    }
		return resultString;
	}
}
