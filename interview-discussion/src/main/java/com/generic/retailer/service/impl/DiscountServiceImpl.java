/**
 * 
 */
package com.generic.retailer.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.ValidationException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.generic.retailer.model.ClientProduct;
import com.generic.retailer.model.Discount;
import com.generic.retailer.model.Product;
import com.generic.retailer.model.ProductDiscount;
import com.generic.retailer.model.Trolley;
import com.generic.retailer.model.enums.DiscountAvailability;
import com.generic.retailer.model.enums.DiscountType;
import com.generic.retailer.service.DiscountService;
import com.generic.retailer.service.validation.DiscountValidator;

/**
 * 
 * Basic implementation for the services of discount
 * 
 * @author geovanefilho
 *
 */
public class DiscountServiceImpl extends GenericServiceImpl<Discount> implements DiscountService {

	private static final int SCALE_2 = 2;
	private static final int ONE_ELEMENT = 1;
	private static final BigDecimal CALCULATE_PERCENT = new BigDecimal(100);
	final Logger logger = LogManager.getLogger(DiscountServiceImpl.class);

	@Autowired
	public DiscountServiceImpl() {
		this.validator = new DiscountValidator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void calculateDiscount(Trolley trolley) {
		if (trolley.getProducts() == null || trolley.getProducts().isEmpty()) {
			// Message should be internationalized
			logger.log(Level.WARN, "Could not calculate the discount due the empty trolley!");
		}
		for (ClientProduct clientProduct : trolley.getProducts()) {
			Product product = clientProduct.getProduct();
			BigDecimal totalValue = clientProduct.getAmount().multiply(product.getPrice());
			clientProduct.setTotalValue(totalValue);

			if (clientProduct.getDiscounts() == null || clientProduct.getDiscounts().isEmpty()) {
				clientProduct.setFinalValue(totalValue);
				if (product.getDiscounts() != null && !product.getDiscounts().isEmpty()) {
					calculate(trolley, clientProduct, product.getDiscounts(), clientProduct.getAmount());
				} else {
					clientProduct.setTotalDiscount(BigDecimal.ZERO);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void calculate(Trolley trolley, ClientProduct clientProduct, Collection<Discount> discounts,
			BigDecimal amount) {
		Map<Discount, BigDecimal> availableDiscounts = calculateDiscountValues(trolley, discounts, amount,
				clientProduct.getProduct().getPrice());

		if (availableDiscounts.size() == ONE_ELEMENT) { // if just one discount available
			Discount disc = availableDiscounts.keySet().iterator().next();
			BigDecimal totalDiscountPrice = availableDiscounts.get(disc);

			addTotalDiscount(clientProduct, disc, totalDiscountPrice);

		} else if (!availableDiscounts.isEmpty()) {
			BigDecimal productAmount = BigDecimal.ZERO;
			
			Discount discSet = availableDiscounts.entrySet().stream().max(Comparator.comparing(Entry<Discount,BigDecimal>::getValue)).get().getKey();
			BigDecimal biggerDiscount = availableDiscounts.get(discSet);
			
			clientProduct.setTotalDiscount(clientProduct.getTotalDiscount().add(biggerDiscount));
			clientProduct.setFinalValue(clientProduct.getFinalValue().subtract(biggerDiscount));

			BigDecimal quantityDiscount = BigDecimal.ZERO;
			if (discSet != null) {
				quantityDiscount = amount.divideToIntegralValue(new BigDecimal(discSet.getAmountRelated()));
				productAmount = quantityDiscount.multiply(new BigDecimal(discSet.getAmountRelated()));
			}

			if (clientProduct.getDiscounts() == null) {
				clientProduct.setDiscounts(new ArrayList<ProductDiscount>());
			}
			clientProduct.getDiscounts().add(new ProductDiscount(clientProduct, discSet, biggerDiscount));

			Collection<Discount> discs = availableDiscounts.keySet();
			discs.remove(discSet);
			BigDecimal amountLeft = amount.subtract(productAmount);

			if (!discs.isEmpty() && amountLeft.compareTo(BigDecimal.ZERO) > 0) {
				calculate(trolley, clientProduct, discs, amountLeft);
			}
		}
	}

	/**
	 * Add the total discount price of a product with a specific discount
	 * 
	 * @param clientProduct
	 * @param disc
	 * @param totalDiscountPrice
	 */
	private void addTotalDiscount(ClientProduct clientProduct, Discount disc, BigDecimal totalDiscountPrice) {
		if (clientProduct.getDiscounts() == null) {
			clientProduct.setDiscounts(new ArrayList<ProductDiscount>());
		}
		
		clientProduct.setTotalDiscount(clientProduct.getTotalDiscount().add(totalDiscountPrice));
		clientProduct.setFinalValue(clientProduct.getFinalValue().subtract(totalDiscountPrice));
		clientProduct.getDiscounts().add(new ProductDiscount(clientProduct, disc, totalDiscountPrice));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Discount, BigDecimal> calculateDiscountValues(Trolley trolley, Collection<Discount> discounts,
			BigDecimal productsAmount, BigDecimal productPrice) {
		Map<Discount, BigDecimal> discountsValues = new HashMap<Discount, BigDecimal>();

		for (Discount discount : discounts) {
			if (isAvailable(trolley, discount)) { // if discount available calculate the value
				BigDecimal integralValue = productsAmount
						.divideToIntegralValue(new BigDecimal(discount.getAmountRelated()));

				if (!integralValue.equals(BigDecimal.ZERO)) { // if the amount of product is applied for discount

					if (discount.getType().code().equals(DiscountType.PERCENTAGE.code())) { // calculate the discount if
																							// its a percentage
						BigDecimal totalAmountDiscount = integralValue
								.multiply(new BigDecimal(discount.getAmountRelated()));
						BigDecimal totalValueAmount = totalAmountDiscount.multiply(productPrice);

						discountsValues.put(discount,
								totalValueAmount.multiply(discount.getValue().divide(CALCULATE_PERCENT))
										.setScale(SCALE_2, RoundingMode.HALF_UP));

					} else if (discount.getType().code().equals(DiscountType.PRICE.code())) { // calculate the discount
																								// if its a price
						discountsValues.put(discount,
								integralValue.multiply(discount.getValue()).setScale(SCALE_2, RoundingMode.HALF_UP));
						
					} else { // Discount unknown
						String msg = "Discount type unknown for code: " + discount.getType().code();
						logger.log(Level.ERROR, msg); // Message should be internationalized
						throw new ValidationException(msg);
					}
				}
			}
		}
		return discountsValues;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAvailable(Trolley trolley, Discount discount) {
		Calendar actualDate = Calendar.getInstance();
		if (trolley != null) {
			actualDate = trolley.getPurchaseDate();
		}

		if (discount == null) {
			return Boolean.FALSE;
		} else if (discount.getAvailability() == null || discount.getAvailableAt() == null) {
			return Boolean.TRUE;
		}

		if (discount.getAvailability().code().equals(DiscountAvailability.AFTER_TIME.code())) {
			return isAvailableAfterTime(discount, actualDate);
		} else if (discount.getAvailability().code().equals(DiscountAvailability.BEFORE_TIME.code())) {
			return isAvailableBeforeTime(discount, actualDate);
		} else if (discount.getAvailability().code().equals(DiscountAvailability.DAY_OF_MONTH.code())) {
			return isAvailableAtDay(discount, actualDate);
		} else if (discount.getAvailability().code().equals(DiscountAvailability.DAY_OF_WEEK.code())) {
			return isAvailableAtDayOfWeek(discount, actualDate);
		} else if (discount.getAvailability().code().equals(DiscountAvailability.MONTH_OF_YEAR.code())) {
			return isAvailableAtMonth(discount, actualDate);
		} else {
			String msg = "Validation unknown for availability of code: " + discount.getAvailability().code();
			logger.log(Level.ERROR, msg); // Message should be internationalized
			throw new ValidationException(msg);
		}
	}

	/**
	 * Verify if it's available in an specific month
	 * 
	 * @param discount
	 * @param actualDate
	 * @return
	 */
	private boolean isAvailableAtMonth(Discount discount, Calendar actualDate) {
		if (actualDate.get(Calendar.MONTH) == discount.getAvailableAt()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * Verify if it's available in an specific day of week
	 * 
	 * @param discount
	 * @param actualDate
	 * @return
	 */
	private boolean isAvailableAtDayOfWeek(Discount discount, Calendar actualDate) {
		if (actualDate.get(Calendar.DAY_OF_WEEK) == discount.getAvailableAt()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * Verify if it's available in an specific day of month
	 * 
	 * @param discount
	 * @param actualDate
	 * @return
	 */
	private boolean isAvailableAtDay(Discount discount, Calendar actualDate) {
		if (actualDate.get(Calendar.DAY_OF_MONTH) == discount.getAvailableAt()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * Verify if it's available before some time
	 * 
	 * @param discount
	 * @param actualDate
	 * @return
	 */
	private boolean isAvailableBeforeTime(Discount discount, Calendar actualDate) {
		if (actualDate.get(Calendar.HOUR_OF_DAY) < discount.getAvailableAt()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * Verify if it's available after some time
	 * 
	 * @param discount
	 * @param actualDate
	 * @return
	 */
	private boolean isAvailableAfterTime(Discount discount, Calendar actualDate) {
		if (actualDate.get(Calendar.HOUR_OF_DAY) >= discount.getAvailableAt()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

}
