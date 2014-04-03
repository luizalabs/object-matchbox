package br.com.buzungobbm.matchbox;

public enum Operator {
	EQUALS_TO("="), 
	LOWER_THAN("<"), 
	LOWER_THAN_OR_EQUALS_TO("<="), 
	GREATER_THAN(">"),
	GREATER_THAN_OR_EQUALS_TO(">=");
	
	@SuppressWarnings("unused")
	private String value;
	private Operator(String value) {
		this.value = value;
	}
}