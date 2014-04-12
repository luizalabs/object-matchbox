package br.com.buzungobbm.matchbox.mocks;

import java.util.HashMap;
import java.util.List;

public class NestedClass extends TestObject {

	private String name;
	private double price;
	private int quantity;
	private List<String> fakeList;
	private List<NestedClass> fakeNestedObjectList;
	private HashMap<String, Integer> fakeMap;
	private HashMap<String, NestedClass> fakeNestedObjectMap;

	public NestedClass (String name, Double price, Integer quantity) { 
		super();
		this.name = name; 
		this.price = price; 
		this.quantity = quantity; 
	}

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }

	public Integer getQuantity() { return this.quantity; }
	public void setQuantity(Integer quantity) { this.quantity = quantity; }

	public double getPrice() { return this.price; }
	public void setPrice(Double price) { this.price = price; }

	public List<String> getFakeList() { return this.fakeList; }
	public void setFakeList(List<String> fakeList) { this.fakeList = fakeList; }
	public void addFakeList(String fakeList) { this.fakeList.add(fakeList); }

	public List<NestedClass> getFakeNestedObjectList() { return this.fakeNestedObjectList; }
	public void setFakeNestedObjectList(List<NestedClass> fakeList) { this.fakeNestedObjectList = fakeList; }
	public void addFakeNestedObjectList(NestedClass fakeItem) { this.fakeNestedObjectList.add(fakeItem); }

	public HashMap<String, Integer> getFakeMap() { return this.fakeMap; }
	public void setFakeMap(HashMap<String, Integer> fakeMap) { this.fakeMap = fakeMap; }

	public HashMap<String, NestedClass> getFakeNestedObjectMap() { return this.fakeNestedObjectMap; }
	public void setFakeNestedObjectMap(HashMap<String, NestedClass> fakeMap) { this.fakeNestedObjectMap = fakeMap; }
	public void addFakeNestedObjectMap(String key, NestedClass fakeItem) { this.fakeNestedObjectMap.put(key, fakeItem); }

}