package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Sale {
	String client;
	ArrayList<Product> products;
        int indice;
        
	double amount;

	public Sale(String client, ArrayList<Product> products, double amount, int indice) {
		super();
		this.client = client;
		this.products = products;
		this.amount = amount;
                this.indice = indice;
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

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
        
        
        
	@Override
	public String toString() {
		return "Sale [client=" + client + ", products=" + products.toString() + ", amount=" + amount + "]";
	}
        public String aux(){
            String productsS = "";
            for (int i = 0; i < products.size(); i++) {
                productsS = productsS + products.get(i).toStringF();
            }
            return productsS;
        
        }
        
        
        public String toStringFileFormat(){
        return indice + ";Client=" + client + ";\n" + indice + ";Products=" + aux() + "\n" + indice + ";Amount=" + amount + ";\n";
        
        }

}