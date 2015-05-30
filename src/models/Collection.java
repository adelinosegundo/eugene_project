/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.List;

import util.Fatorial;
import util.clustering.SamplesDistanceComparator;

/**
 * @author wendellpbarreto
 *
 */
public class Collection {
	private List<Sample> samples;
	private List<Kind> kinds;
	
	public Collection(){
		this.setSamples(new ArrayList<Sample>());
		this.setKinds(new ArrayList<Kind>());
	}
	
	public Kind addAndReturnKind(String name) { // TRASH CODE!
		boolean alreadyExists = false;
		int kindCounter = 0;
		Kind atualKind = null, newKind = null;
		
		for (Kind kind : this.kinds) {
			alreadyExists = kind.getName().equals(name) ? true : false;
			
			if (alreadyExists) {
				atualKind = kind;
				
				break;
			} else {
				kindCounter++;
			}
		}
		
		if (alreadyExists) {
			return atualKind;
		} else {
			newKind = new Kind(name, kindCounter);
			this.kinds.add(newKind);
			
			return newKind;
		}
	}
	
	public void addSample(String name, Kind kind) { // TRASH CODE!
		boolean alreadyExists = false;
		int sampleCounter = 0;
		
		for (Sample sample : this.samples) {
			alreadyExists = sample.getName().equals(name) ? true : false;
			sampleCounter++;
		}
		
		if (!alreadyExists) {
			Sample newSample = new Sample(name, kind);
			this.samples.add(newSample);
		}
	}
	
	public void print() {
		System.out.println("\n\n - -- --- ---- COLLECTION ---- --- -- - \n");
		
		System.out.println(" * Kinds [<id>] : '<name>'");
		for (Kind kind : this.kinds) {
			System.out.println("[" + kind.getID() + "] : '" + kind.getName() + "'");
		}
		System.out.println("");
		
		System.out.println(" * Samples <name> | <kind.name> | valid? <valid>");
		for (Sample sample : this.samples) {
			System.out.println("" + sample.getName() + " | " + sample.getKind().getName());
		}
		System.out.println("");
		
		System.out.println(" * Gene <sample.name> | <marker> | <expression>");
		for (Sample sample : this.samples) {
			for (Gene gene : sample.getGenes()) {
				System.out.println(sample.getName() + " | " + gene.getMarker() + " | " + gene.getExpression());
			}
		}
		System.out.println("");
		
	}
	
	public List<Kind> getKinds() {
		return kinds;
	}

	public void setKinds(List<Kind> kinds) {
		this.kinds = kinds;
	}

	public List<Sample> getSamples() {
		return samples;
	}

	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}
	
}
