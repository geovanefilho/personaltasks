/**
 * 
 */
package com.generic.retailer.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.ValidationException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.generic.retailer.model.ClientProduct;
import com.generic.retailer.model.Product;
import com.generic.retailer.model.Trolley;
import com.generic.retailer.service.DiscountService;
import com.generic.retailer.service.TrolleyService;

/**
 * 
 * Implementation for TrolleyService
 * 
 * @author geovanefilho
 *
 */
public class TrolleyServiceImpl implements TrolleyService {

	final Logger logger = LogManager.getLogger(TrolleyServiceImpl.class);
	
	@Autowired
	private DiscountService discountService;
	
	@Autowired
	public TrolleyServiceImpl() {
    	this.discountService = new DiscountServiceImpl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addProduct(Trolley trolley, Product product, Long amount) {
		validate(trolley, product, amount);

		ClientProduct clientProduct = null;

		if (trolley.getProducts() != null) {
			Optional<ClientProduct> cPStream = trolley.getProducts().stream().parallel()
					.filter(cP -> cP.getProduct().equals(product)).findFirst();

			try {
				clientProduct = cPStream.get();
				clientProduct.setAmount(clientProduct.getAmount().add(new BigDecimal(amount)));
				if (clientProduct.getDiscounts() != null) {
					clientProduct.getDiscounts().clear();
				}
				clientProduct.setTotalDiscount(BigDecimal.ZERO);
				clientProduct.setFinalValue(product.getPrice().multiply(clientProduct.getAmount()));
				this.discountService.calculate(trolley, clientProduct, product.getDiscounts(), clientProduct.getAmount());
			} catch (NoSuchElementException nse) {
				clientProduct = new ClientProduct(trolley.getClient(), product, new BigDecimal(amount));
				trolley.getProducts().add(clientProduct);
			}
		} else {
			trolley.setProducts(new ArrayList<ClientProduct>());
			clientProduct = new ClientProduct(trolley.getClient(), product, new BigDecimal(amount));
			trolley.getProducts().add(clientProduct);
		}

		this.discountService.calculateDiscount(trolley);
	}

	/**
	 * Validate the information necessary to add a product into a trolley
	 * 
	 * @param trolley
	 * @param product
	 * @param amount
	 */
	private void validate(Trolley trolley, Product product, Long amount) {
		String msg = "";
		if (trolley == null) { // Message should be internationalized
			msg += "A client trolley is required! ";
		}
		if (product == null) {
			msg += "A product must be provided! "; // Message should be internationalized
		}
		if (amount == null) { // Message should be internationalized
			msg += "The quantity to add is required! ";
		}

		if (!msg.isEmpty()) {
			logger.log(Level.ERROR, msg);
			throw new ValidationException(msg);
		}
	}

}
