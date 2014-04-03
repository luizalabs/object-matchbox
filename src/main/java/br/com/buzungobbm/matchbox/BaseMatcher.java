package br.com.buzungobbm.matchbox;

import java.util.List;
import java.util.Map;

public abstract class BaseMatcher {
	
	public List<BaseFilter> filters;

	public abstract Map<String, String> matchObject();


	public List<BaseFilter> getFilters() {
		return this.filters;
	}
	public void setFilters(List<BaseFilter> filters) {
		this.filters = filters;
	}
	
}