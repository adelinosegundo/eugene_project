/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.List;

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
	
	public ArrayList<Sample> getSamplesOfKind(Kind kind) {
		ArrayList<Sample> samples = new ArrayList<Sample>();
		
		for (Sample sample : this.samples) {
			if (sample.getKind() == kind) {
				samples.add(sample);
			}
		}
		
		return samples;
	}
	
	public void setGenesValidByMarker(String marker) {
		for (Sample sample : this.samples) {
			for (Gene gene : sample.getGenes()) {
				if (gene.getMarker().equals(marker)) {
					gene.setValid(true);
				}
			}
		}
	}
	
	public void print() {
		System.out.println("\n\n - -- --- ---- COLLECTION ---- --- -- - \n");
		
		System.out.println(" * Kinds [<id>] : '<name>'");
		for (Kind kind : this.kinds) {
			System.out.println("[" + kind.getID() + "] : '" + kind.getName() + "'");
		}
		System.out.println("");
		
		System.out.println(" * Samples <name> | <kind.name>");
		for (Sample sample : this.samples) {
			System.out.println("" + sample.getName() + " | " + sample.getKind().getName());
		}
		System.out.println("");
		
		System.out.println(" * Gene <sample.name> | <marker> | <expression> | valid? <valid>");
		for (Sample sample : this.samples) {
			for (Gene gene : sample.getGenes()) {
				System.out.println(sample.getName() + " | " + gene.getMarker() + " | " + gene.getExpression() + " | " + gene.isValid());
			}
		}
		System.out.println("");
		
	}
	
	public int getSamplesQuantity() {
		return this.samples.size();
	}
	
	public int getExpressionsQuantity() {
		int result = 0;
		for (Sample sample : this.samples) {
			result += sample.getGenes().size();
		}
		
		return result;
	}
	
	public int getValidGenesQuantity() {
		int result = 0;
		
		if (this.samples.size() > 0) {
			for (Gene gene : this.samples.get(0).getGenes()) {
				result += gene.isValid() ? 1 : 0;
			}
		}
		
		return result;
	}
	
	public int getInvaliGenesQuantity() {
		int result = 0;
		
		if (this.samples.size() > 0){
			for (Gene gene : this.samples.get(0).getGenes()) {
				result += gene.isValid() ? 0 : 1;
			}
		}
		
		return result;
	}

	public String getGroupsNames() {
		String result = "";
		
		for (Kind kind : this.kinds) {
			result += kind.getName() + " ";
		}
		
		return result;
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
