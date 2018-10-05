/**
 * 
 */
package com.generic.retailer.service;

import com.generic.retailer.model.Product;
import com.generic.retailer.model.Trolley;

/**
 * 
 * Service available to control trolleys from the clients.
 * It would be better used if the Trolley were made to save into database
 * 
 * @author geovanefilho
 *
 */
public interface TrolleyService {

	/**
	 * Method responsible for add a product in a client trolley calculating the value and discounts of the products
	 * 
	 * @param trolley
	 * @param product
	 * @param amount
	 */
	public void addProduct(Trolley trolley, Product product, Long amount);
	
}
