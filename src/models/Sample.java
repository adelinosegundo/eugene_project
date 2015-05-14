package models;

public class Sample {
	private Double expression;
	private int kind;
	private boolean valid;
	
	private static final String[] kinds = {"Control", "Subject"};
	
	
	
	public Sample(Double expression){
		this.setValid(true);
		this.kind = 1;
		this.expression = expression;
	}
	
	
	//
	public Sample(Double expression, boolean control){
		this.setValid(true);
		if (control)
			this.kind = 0;
		this.expression = expression;
	}
	
	public String getKindName() {
		return kinds[kind];
	}
	
	public int getKind(){
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
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
}
