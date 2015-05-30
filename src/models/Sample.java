package models;

import java.util.ArrayList;
import java.util.List;

public class Sample {
	private String name;
	private List<Gene> genes;
	private Kind kind;
	private boolean valid;

	public Sample(String name, Kind kind){
		this.setName(name);
		this.setKind(kind);
		this.setValid(true);
		this.genes = new ArrayList<Gene>();
	}

	public Sample(Kind kind, ArrayList<Gene> genes, String name){
		this.setKind(kind);
		this.setValid(true);
		this.setGenes(genes);
		this.setName(name);
	}

	public Sample(Kind kind, ArrayList<Gene> genes, boolean valid, String name){
		this.setKind(kind);
		this.setValid(valid);
		this.setGenes(genes);
		this.setName(name);
	}

	public List<Gene> getGenes() {
		return genes;
	}

	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
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

}
