package br.com.buzungobbm.test.beans;

import java.util.ArrayList;
import java.util.List;

public class Order {

	private List<Product> products = new ArrayList<Product>();
	private long totalPrice;

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void addProduct(Product product) {
		this.products.add(product);
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
