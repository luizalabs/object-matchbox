package br.com.buzungobbm.matchbox.comparator;

import br.com.buzungobbm.matchbox.util.Operator;

public abstract class BaseType {

	private Class<?> className;
	private Class<?> attributeName;
	private String value;
	private String description;
	private Operator operator;
	
	public Class<?> getClassName() {
		return className;
	}

	public void setClassName(Class<?> className) {
		this.className = className;
	}

	public Class<?> getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(Class<?> attributeName) {
		this.attributeName = attributeName;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Operator getOperator() {
		return operator;
	}
	
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

}