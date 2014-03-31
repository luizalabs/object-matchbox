package br.com.buzungobbm.test.beans;

public class Product {

	private int productId;
	private String name;
	private String description;
	private String brand;
	private double price;
	
	private Category category;
	
	public Product (int id, String name, String description, String brand, double price) {
		this.productId = id;
		this.name = name;
		this.description = description;
		this.brand = brand;
		this.price = price;
		System.out.println("Product created! " + String.valueOf(id) + ":" + name + ":" + description + ":" + brand + ":" + String.valueOf(price));
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
