package br.com.buzungobbm.matchbox.mocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestObject {

	private String name;
	private String color;
	private double price;
	private List<String> fakeList;
	private List<NestedClass> fakeNestedObjectList;
	private Map<String, Integer> fakeMap;
	private Map<String, NestedClass> fakeNestedObjectMap;
	private NestedClass nestedObject;
	
	public TestObject () {
	}

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

	public List<String> getFakeList() { return this.fakeList; }
	public void setFakeList(List<String> fakeList) { this.fakeList = fakeList; }
	public void addFakeList(String fakeList) { this.fakeList.add(fakeList); }

	public List<NestedClass> getFakeNestedObjectList() { return this.fakeNestedObjectList; }
	public void setFakeNestedObjectList(List<NestedClass> fakeList) { this.fakeNestedObjectList = fakeList; }
	public void addFakeNestedObjectList(NestedClass fakeItem) { this.fakeNestedObjectList.add(fakeItem); }

	public Map<String, Integer> getFakeMap() { return this.fakeMap; }
	public void setFakeMap(Map<String, Integer> fakeMap) { this.fakeMap = fakeMap; }

	public Map<String, NestedClass> getFakeNestedObjectMap() { return this.fakeNestedObjectMap; }
	public void setFakeNestedObjectMap(Map<String, NestedClass> fakeNestedObjectMap) { this.fakeNestedObjectMap = fakeNestedObjectMap; }
	public void putFakeNestedObjectMap(String key, NestedClass nestedObjectInstance) { 
		if (this.fakeNestedObjectMap != null) {
			this.fakeNestedObjectMap = new HashMap<String, NestedClass>();
		}
		this.fakeNestedObjectMap.put(key, nestedObjectInstance); 
	}

	public NestedClass getNestedObject() { return this.nestedObject; }
	public void setNestedObject(NestedClass nestedObject) { this.nestedObject = nestedObject; }

}