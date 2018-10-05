/**
 * 
 */
package com.generic.retailer.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import com.generic.retailer.model.ClientProduct;
import com.generic.retailer.model.Discount;
import com.generic.retailer.model.Trolley;

/**
 * 
 * Services available for discounts, create and update discounts, calculate and applied them in the products
 * 
 * 
 * @author geovanefilho
 *
 */
public interface DiscountService extends GenericService<Discount> {

	/**
	 * Calulate the discount of the products in a trolley
	 * 
	 * @param trolley
	 */
	public void calculateDiscount(Trolley trolley);

	/**
	 * Calculate and apply the best discount given for the product
	 *  
	 * @param trolley
	 * @param clientProduct
	 * @param discounts
	 * @param amount
	 */
	public void calculate(Trolley trolley, ClientProduct clientProduct, Collection<Discount> discounts, BigDecimal amount);

	/**
	 * Calculate the values of each discount available for the amount of a product.
	 * 
	 * @param trolley
	 * @param clientProduct
	 * @return Return just the discounts and its total discount price of the
	 *         available discounts that its price is bigger than zero.
	 */
	public Map<Discount, BigDecimal> calculateDiscountValues(Trolley trolley, Collection<Discount> discounts, BigDecimal productsAmount,
			BigDecimal productPrice);

	/**
	 * Verify if an discount is applicable at the current day and time
	 * 
	 * @param trolley
	 * @param discount
	 * @return
	 */
	public boolean isAvailable(Trolley trolley, Discount discount);

	
}
