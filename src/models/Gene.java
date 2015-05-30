package models;

public class Gene {
	private	String marker;
	private Double expression;
	
	public Gene(String marker, Double expression){
		this.setMarker(marker);
		this.setExpression(expression);
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
}
