package br.com.buzungobbm.matchbox.mocks;

import java.util.List;
import java.util.Map;

public class TestObject {

	private String name;
	private String color;
	private double price;
	private List<String> fakeList;
	private List<NestedClass> fakeNestedObjectList;
	private Map<String, Integer> fakeMap;
	private NestedClass nestedObject;

	public TestObject (String name, String color, Double price, NestedClass nestedObject) { 
		this.name = name; 
		this.color = color; 
		this.price = price; 
		this.nestedObject = nestedObject; 
	}

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }

	public String getColor() { return this.color; }
	public void setColor(String color) { this.color = color; }

	public double getPrice() { return this.price; }
	public void setPrice(double price) { this.price = price; }

	public List<NestedClass> getFakeNestedObjectList() { return this.fakeNestedObjectList; }
	public void setFakeNestedObjectList(List<NestedClass> fakeList) { this.fakeNestedObjectList = fakeList; }
	public void addFakeNestedObjectList(NestedClass fakeItem) { this.fakeNestedObjectList.add(fakeItem); }

	public List<String> getFakeList() { return this.fakeList; }
	public void setFakeList(List<String> fakeList) { this.fakeList = fakeList; }
	public void addFakeList(String fakeList) { this.fakeList.add(fakeList); }

	public Map<String, Integer> getFakeMap() { return this.fakeMap; }
	public void setFakeMap(Map<String, Integer> fakeMap) { this.fakeMap = fakeMap; }

	public NestedClass getNestedObject() { return this.nestedObject; }
	public void setNestedObject(NestedClass nestedObject) { this.nestedObject = nestedObject; }

}