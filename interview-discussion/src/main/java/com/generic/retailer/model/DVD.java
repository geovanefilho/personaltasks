package com.generic.retailer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * 
 * Model of a DVD
 * 
 * Obs.: Class is not mapped due that the test task didn't ask to implement service to save in database
 * 
 * @author geovanefilho
 *
 */
public class DVD extends Product implements Serializable {
	
	private static final long serialVersionUID = 5485105692535608296L;

	private String title;
	
	private Calendar releaseDate;

	/**
	 * @param title
	 * @param sotckQuantity
	 * @param price
	 * @param discounts
	 */
	public DVD(String title, Calendar releaseDate, Integer sotckQuantity, BigDecimal price, Discount... discounts) {
		super(title, sotckQuantity, price, discounts);
		this.title = title;
		this.releaseDate = releaseDate;
	}

	/**
	 * @param title
	 * @param releaseDate
	 * @param sotckQuantity
	 * @param price
	 */
	public DVD(String title, Calendar releaseDate, Integer sotckQuantity, BigDecimal price) {
		super(title, sotckQuantity, price);
		this.title = title;
		this.releaseDate = releaseDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Calendar getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Calendar releaseDate) {
		this.releaseDate = releaseDate;
	}
	
}
