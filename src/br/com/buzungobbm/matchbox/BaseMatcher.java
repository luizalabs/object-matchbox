package br.com.buzungobbm.matchbox;

import java.util.List;
import java.util.Map;

public abstract class BaseMatcher {
	
	public List<BaseType> filters;

	public abstract Map<String, String> matchObject();


	public List<BaseType> getFilters() {
		return this.filters;
	}
	public void setFilters(List<BaseType> filters) {
		this.filters = filters;
	}
	
}