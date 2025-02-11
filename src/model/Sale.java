package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Sale {
	String client;
	ArrayList<Product> products;
        
	double amount;

	public Sale(String client, ArrayList<Product> products, double amount) {
		super();
		this.client = client;
		this.products = products;
		this.amount = amount;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public ArrayList <Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
        
        public double getname() {
		return amount;
	}

	@Override
	public String toString() {
		return "Sale [client=" + client + ", products=" + products.toString() + ", amount=" + amount + "]";
	}

}