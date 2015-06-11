/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.List;

import util.clustering.Clusterable;
import util.clustering.DendogramBuilder;

/**
 * @author wendellpbarreto
 *
 */
public class Collection {
	private List<Sample> samples;
	private List<Group> groups;
	private DendogramBuilder dendogramBuilder;

	public Collection(){
		this.setSamples(new ArrayList<Sample>());
		this.setGroups(new ArrayList<Group>());
	}
	
	public boolean buildDendogram(){
		if (samples.size() > 0) {
			List<Clusterable> clusters = new ArrayList<Clusterable>();
			clusters.addAll(this.getSamples());
			dendogramBuilder = new DendogramBuilder(clusters);
			dendogramBuilder.generateClustersDistances();
			dendogramBuilder.generateDendogram();
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validateDendogram(){
		if (dendogramBuilder != null)
			return SampleClusteringValidator.ValidateDendogram(this.getDendogramBuilder().getRootCluster(), this.getGroups());
		else
			return false;
	}
	
	public boolean leaveOneOutValidation(){
		boolean valid = true;
		List<Clusterable> clusters = new ArrayList<Clusterable>();
		clusters.addAll(this.getSamples());
		for(Clusterable cluster : clusters){
			List<Clusterable> leaveOneOutClusters = new ArrayList<Clusterable>();
			leaveOneOutClusters.addAll(clusters);
			leaveOneOutClusters.remove(cluster);
			if (leaveOneOutClusters.size() > 1){
				dendogramBuilder = new DendogramBuilder(leaveOneOutClusters);
				dendogramBuilder.generateClustersDistances();
				dendogramBuilder.generateDendogram();
				if(!SampleClusteringValidator.ValidateDendogram(dendogramBuilder.getRootCluster(), this.getGroups()))
					valid = false;
			}
		}
		return valid;
	}
	
 	public Group addAndReturnGroup(String name) { // TRASH CODE!
		boolean alreadyExists = false;
		int groupCounter = 0;
		Group atualGroup = null, newGroup = null;
		
		for (Group group : this.groups) {
			alreadyExists = group.getName().equals(name) ? true : false;
			
			if (alreadyExists) {
				atualGroup = group;
				
				break;
			} else {
				groupCounter++;
			}
		}
		
		if (alreadyExists) {
			return atualGroup;
		} else {
			newGroup = new Group(name, groupCounter);
			this.groups.add(newGroup);
			
			return newGroup;
		}
	}
	
	public void addSample(String name, Group group) { // TRASH CODE!
		boolean alreadyExists = false;
		int sampleCounter = 0;
		
		for (Sample sample : this.samples) {
			alreadyExists = sample.getName().equals(name) ? true : false;
			sampleCounter++;
		}
		
		if (!alreadyExists) {
			Sample newSample = new Sample(name, group);
			this.samples.add(newSample);
		}
	}
	
	public ArrayList<Sample> getSamplesOfGroup(Group group) {
		ArrayList<Sample> samples = new ArrayList<Sample>();
		
		for (Sample sample : this.samples) {
			if (sample.getGroup() == group) {
				samples.add(sample);
			}
		}
		
		return samples;
	}
	
	public void setGenesInvalidByMarker(String marker) {
		for (Sample sample : this.samples) {
			for (Gene gene : sample.getGenes()) {
				if (gene.getMarker().equals(marker)) {
					gene.setValid(false);
				}
			}
		}
	}
	
	public void setGenesValidTrue() {
		for (Sample sample : this.samples) {
			for (Gene gene : sample.getGenes()) {
				gene.setValid(true);
			}
		}
	}

	public void setGenesValidFalse() {
		for (Sample sample : this.samples) {
			for (Gene gene : sample.getGenes()) {
				gene.setValid(false);
			}
		}
	}
	
	public void print() {
		System.out.println("\n\n - -- --- ---- COLLECTION ---- --- -- - \n");
		
		System.out.println(" * Groups [<id>] : '<name>'");
		for (Group group : this.groups) {
			System.out.println("[" + group.getID() + "] : '" + group.getName() + "'");
		}
		System.out.println("");
		
		System.out.println(" * Samples <name> | <group.name>");
		for (Sample sample : this.samples) {
			System.out.println("" + sample.getName() + " | " + sample.getGroup().getName());
		}
		System.out.println("");
		
		System.out.println(" * Gene <sample.name> | <marker> | <expression> | <group> | valid? <valid>");
		for (Sample sample : this.samples) {
			for (Gene gene : sample.getGenes()) {
				System.out.println(sample.getName() + " | " + gene.getMarker() + " | " + gene.getExpression() + " | " + gene.getGroup().getName() + " | " + gene.isValid());
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
		
		for (Group group : this.groups) {
			result += group.getName() + " ";
		}
		
		return result;
	}
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Sample> getSamples() {
		return samples;
	}

	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}
	
	public DendogramBuilder getDendogramBuilder() {
		return dendogramBuilder;
	}

	public void setDendogramBuilder(DendogramBuilder dendogramBuilder) {
		this.dendogramBuilder = dendogramBuilder;
	}

	public String distanceMatrixToFileString() {
		if (dendogramBuilder == null)
			this.buildDendogram();
		return dendogramBuilder.getDistanceMatrixString();
	}

	public String pValuesToFileString() {
		// TODO Auto-generated method stub
		return samples.get(0).getPValuesString();
	}
	
}
