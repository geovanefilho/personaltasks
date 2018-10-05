package com.generic.retailer.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * 
 * Contains the information of the products that the client is buying
 * 
 * Obs.: Class is not mapped due that the test task didn't ask to implement service to save in database
 * 
 * @author geovanefilho
 *
 */
public final class Trolley {

	//Could contain a model of the client with more information, like RN, email payment methods and etc
	private String client;
	
	private Calendar purchaseDate = Calendar.getInstance();
	
	private Collection<ClientProduct> products;
	
	/**
	 * 
	 * Creates an trolley for a client
	 * 
	 * @param client
	 */
	public Trolley(String client) {
		super();
		this.client = client;
	}
	
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Collection<ClientProduct> getProducts() {
		return products;
	}

	public void setProducts(Collection<ClientProduct> products) {
		this.products = products;
	}

	public Calendar getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Calendar purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * Add an amount of a product to a client trolley
	 * 
	 * @param product
	 * @param amount
	 */
	public void addProduct(Product product, BigDecimal amount) {
		if (this.products == null) {
			this.products = new ArrayList<ClientProduct>();
		}
		this.products.add(new ClientProduct(this.client, product, amount));
	}

	/**
	 * Get the total value of the products with discounts applied
	 * 
	 * @return
	 */
	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		for (ClientProduct clientProduct : this.products) {
			total = total.add(clientProduct.getFinalValue());
		}
		return total;
	}
	
}
