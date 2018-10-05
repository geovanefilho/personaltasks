/**
 * 
 */
package com.generic.retailer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.generic.retailer.model.enums.DiscountAvailability;
import com.generic.retailer.model.enums.DiscountType;

/**
 * 
 * Model of a discount information about the kind of a product
 * 
 * type - Type of discount (Percentage or price)
 * amountRelated - Minimum quantity for the discount
 * value - Value of the discount (Could be in percentage or price, depending on the type)
 * availability - Type of availability of the discount (Before or after a time, day of month, day of week or month)
 * availableAt - The exact availability of the discount
 * 
 * Obs.: Class is not mapped due that the test task didn't ask to implement service to save in database
 * 
 * @author geovanefilho
 *
 */
public class Discount implements Serializable {

	private static final long serialVersionUID = 1723529822080726213L;

	private Long id;
	
	private String discountName;
	
	private DiscountType type;
	
	private Integer amountRelated;
	
	private BigDecimal value;
	
	private DiscountAvailability availability;
	
	// Null if it is always
	private Integer availableAt;

	/**
	 * 
	 * Discount with availability for everyday
	 * 
	 * @param discountName
	 * @param type
	 * @param amountRelated
	 * @param value
	 */
	public Discount(String discountName, DiscountType type, Integer amountRelated, BigDecimal value) {
		super();
		this.discountName = discountName;
		this.type = type;
		this.amountRelated = amountRelated;
		this.value = value;
	}

	/**
	 * 
	 * Discount with specific availability
	 * 
	 * @param discountName
	 * @param type
	 * @param amountRelated
	 * @param value
	 * @param availability
	 * @param availableAt
	 */
	public Discount(String discountName, DiscountType type, Integer amountRelated, BigDecimal value,
			DiscountAvailability availability, Integer availableAt) {
		super();
		this.discountName = discountName;
		this.type = type;
		this.amountRelated = amountRelated;
		this.value = value;
		this.availability = availability;
		this.availableAt = availableAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public DiscountType getType() {
		return type;
	}

	public void setType(DiscountType type) {
		this.type = type;
	}

	public Integer getAmountRelated() {
		return amountRelated;
	}

	public void setAmountRelated(Integer amountRelated) {
		this.amountRelated = amountRelated;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public DiscountAvailability getAvailability() {
		return availability;
	}

	public void setAvailability(DiscountAvailability availability) {
		this.availability = availability;
	}

	public Integer getAvailableAt() {
		return availableAt;
	}

	public void setAvailableAt(Integer availableAt) {
		this.availableAt = availableAt;
	}
	
}
