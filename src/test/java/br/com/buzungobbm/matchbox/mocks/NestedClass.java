package br.com.buzungobbm.matchbox.mocks;

import java.util.List;
import java.util.Map;

public class NestedClass {

	private String name;
	private double price;
	private int quantity;
	private List<String> fakeList;
	private List<NestedClass> fakeNestedObjectList;
	private Map<String, Integer> fakeMap;

	public NestedClass (String name, Double price, Integer quantity) { 
		this.name = name; 
		this.price = price; 
		this.quantity = quantity; 
	}

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }

	public Integer getQuantity() { return this.quantity; }
	public void setQuantity(Integer quantity) { this.quantity = quantity; }

	public Double getPrice() { return this.price; }
	public void setPrice(Double price) { this.price = price; }

	public List<NestedClass> getFakeNestedObjectList() { return this.fakeNestedObjectList; }
	public void setFakeNestedObjectList(List<NestedClass> fakeList) { this.fakeNestedObjectList = fakeList; }
	public void addFakeNestedObjectList(NestedClass fakeItem) { this.fakeNestedObjectList.add(fakeItem); }

	public List<String> getFakeList() { return this.fakeList; }
	public void setFakeList(List<String> fakeList) { this.fakeList = fakeList; }
	public void addFakeList(String fakeList) { this.fakeList.add(fakeList); }

	public Map<String, Integer> getFakeMap() { return this.fakeMap; }
	public void setFakeMap(Map<String, Integer> fakeMap) { this.fakeMap = fakeMap; }

}