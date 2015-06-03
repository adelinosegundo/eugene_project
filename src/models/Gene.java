package models;

public class Gene {
	private	String marker;
	private Double expression;
	private boolean valid;
	private Kind kind;
	
	public Gene(String marker, Double expression){
		this.setMarker(marker);
		this.setExpression(expression);
		this.setValid(false);
	}
	
	public Gene(String marker, Double expression, Kind kind){
		this.setMarker(marker);
		this.setExpression(expression);
		this.setValid(false);
		this.setKind(kind);
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
	
	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}
}
