package br.com.buzungobbm.test.beans;

public class Category {
	
	private int categoryId;
	private String name;
	private String description;
	
	public Category (int id, String name, String description) {
		this.categoryId = id;
		this.name = name;
		this.description = description;
		System.out.println("Category created! " + String.valueOf(id) + ":" + name + ":" + description);
	}

	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
