package br.com.buzungobbm.matchbox;

public interface BaseType {

	public Class<?> getClassName();
	public void setClassName(Class<?> className);

	public String getAttributeName();
	public void setAttributeName(String attributeName);

	public String getValue();
	public void setValue(String value);

	public String getDescription();
	public void setDescription(String description);

	public Operator getOperator();
	public void setOperator(Operator operator);

	public boolean isApplyable();
	public void setApplyable(boolean applyable);

	public MatchingOptions getOption();
	public void setOption(MatchingOptions options);

}