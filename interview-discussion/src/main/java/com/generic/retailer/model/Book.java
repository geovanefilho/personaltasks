package com.generic.retailer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Model of a Book based at some information found at ISO 690:2010
 * 
 * Obs.: Class is not mapped due that the test task didn't ask to implement service to save in database
 * 
 * @author geovanefilho
 *
 */
public final class Book extends Product implements Serializable {
	
	private static final long serialVersionUID = 8571621942175676128L;

	private String title;
	
	private Integer edition;
	
	private Calendar releaseDate;

	/**
	 * @param title
	 * @param edition
	 * @param sotckQuantity
	 * @param price
	 * @param discounts
	 */
	public Book(String title, Integer edition, Calendar releaseDate, Integer sotckQuantity, BigDecimal price, Discount... discounts) {
		super(title, sotckQuantity, price, discounts);
		this.title = title;
		this.edition = edition;
		this.releaseDate = releaseDate;
	}

	/**
	 * @param title
	 * @param edition
	 * @param releaseDate
	 * @param sotckQuantity
	 * @param price
	 */
	public Book(String title, Integer edition, Calendar releaseDate, Integer sotckQuantity, BigDecimal price) {
		super(title, sotckQuantity, price);
		this.title = title;
		this.edition = edition;
		this.releaseDate = releaseDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getEdition() {
		return edition;
	}

	public void setEdition(Integer edition) {
		this.edition = edition;
	}

	public Calendar getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Calendar releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	
	
}
