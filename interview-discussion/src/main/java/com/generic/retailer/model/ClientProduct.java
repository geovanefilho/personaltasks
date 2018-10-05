/**
 * 
 */
package com.generic.retailer.model;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * 
 * The information regarding the product that a client is buying
 * 
 * Obs.: Class is not mapped due that the test task didn't ask to implement service to save in database
 * 
 * @author geovanefilho
 *
 */
public class ClientProduct {

	//Could contain a model of the client with more information, like RN, email payment methods and etc
	private String client;
	
	private Product product;
	
	private BigDecimal amount;
	
	private BigDecimal totalValue = BigDecimal.ZERO;
	
	private Collection<ProductDiscount> discounts;
	
	private BigDecimal totalDiscount = BigDecimal.ZERO;
	
	private BigDecimal finalValue = BigDecimal.ZERO;

	/**
	 * 
	 * Add an amount of a product at the list of the client 
	 * 
	 * @param client
	 * @param product
	 * @param amount
	 */
	public ClientProduct(String client, Product product, BigDecimal amount) {
		super();
		this.client = client;
		this.product = product;
		this.amount = amount;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public Collection<ProductDiscount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Collection<ProductDiscount> discounts) {
		this.discounts = discounts;
	}

	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(BigDecimal totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public BigDecimal getFinalValue() {
		return finalValue;
	}

	public void setFinalValue(BigDecimal finalValue) {
		this.finalValue = finalValue;
	}
	
}
