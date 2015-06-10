package models;

public class Gene {
	private	String marker;
	private Double expression;
	private boolean valid;
	private Group group;
	private double pValue;
	
	public Gene(String marker, Double expression){
		this.setMarker(marker);
		this.setExpression(expression);
		this.setValid(true);
		this.setPValue((double) 0);
	}
	
	public Gene(String marker, Double expression, Group group){
		this.setMarker(marker);
		this.setExpression(expression);
		this.setValid(false);
		this.setGroup(group);
		this.setPValue((double) 0);
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker.toLowerCase().trim();
	}

	public Double getExpression() {
		return expression;
	}

	public void setExpression(Double expression) {
		this.expression = expression;
	}
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	public double getPValue() {
		return pValue;
	}

	public void setPValue(double value) {
		this.pValue = value;
	}

}
