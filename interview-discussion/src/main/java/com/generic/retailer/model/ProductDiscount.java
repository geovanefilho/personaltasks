/**
 * 
 */
package com.generic.retailer.model;

import java.math.BigDecimal;

/**
 * 
 * Discount applied to a product bought.
 * 
 * Obs.: Class is not mapped due that the test task didn't ask to implement service to save in database
 * 
 * @author geovanefilho
 *
 */
public class ProductDiscount {

	private ClientProduct clientProduct;
	
	private Discount discount;
	
	private BigDecimal totalPrice;

	/**
	 * @param clientProduct
	 * @param discount
	 * @param totalPrice
	 */
	public ProductDiscount(ClientProduct clientProduct, Discount discount, BigDecimal totalPrice) {
		super();
		this.clientProduct = clientProduct;
		this.discount = discount;
		this.totalPrice = totalPrice;
	}

	public ClientProduct getClientProduct() {
		return clientProduct;
	}

	public void setClientProduct(ClientProduct clientProduct) {
		this.clientProduct = clientProduct;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
