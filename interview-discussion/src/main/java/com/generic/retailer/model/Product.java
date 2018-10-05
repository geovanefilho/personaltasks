/**
 * 
 */
package com.generic.retailer.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 
 * Model with essential information about a product of the retailer
 * 
 * Obs.: Class is not mapped due that the test task didn't ask to implement service to save in database
 * 
 * @author geovanefilho
 *
 */
public abstract class Product {

	private Long id;
	
	private String name;
	
	private Integer sotckQuantity = 0;
	
	private BigDecimal price = BigDecimal.ZERO;
	
	private Collection<Discount> discounts;

	/**
	 * 
	 * Product without discount
	 * 
	 * @param name
	 * @param sotckQuantity
	 * @param price
	 */
	public Product(String name, Integer sotckQuantity, BigDecimal price) {
		super();
		this.name = name;
		this.sotckQuantity = sotckQuantity;
		this.price = price;
	}

	/**
	 * 
	 * Product with an specific discount
	 * 
	 * @param name
	 * @param sotckQuantity
	 * @param price
	 * @param discount
	 */
	public Product(String name, Integer sotckQuantity, BigDecimal price, Discount ... discounts) {
		super();
		this.name = name;
		this.sotckQuantity = sotckQuantity;
		this.price = price;
		if (discounts != null) {
			this.discounts = Arrays.stream(discounts).collect(Collectors.toList());
		}
	}

	public Integer getSotckQuantity() {
		return sotckQuantity;
	}

	public void setSotckQuantity(Integer sotckQuantity) {
		this.sotckQuantity = sotckQuantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Collection<Discount> discounts) {
		this.discounts = discounts;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((sotckQuantity == null) ? 0 : sotckQuantity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (sotckQuantity == null) {
			if (other.sotckQuantity != null)
				return false;
		} else if (!sotckQuantity.equals(other.sotckQuantity))
			return false;
		return true;
	}
	
}
