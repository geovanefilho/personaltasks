package com.hooyu.exercise.helper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hooyu.exercise.customers.domain.Customer;

import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;

/**
 * A view helper with all the information necessary to do searches in the system.
 * A search request must have some information to be matches in and a valid customer signed in.
 * 
 * @author geovanefilho
 *
 */
public class SearchRequest {
	private SimpleSurnameAndPostcodeQuery query;
	private Customer customer;
	
	public SearchRequest(SimpleSurnameAndPostcodeQuery query, Customer customer) {
		this.query = query;
		this.customer = customer;
	}

	public SimpleSurnameAndPostcodeQuery getQuery() {
		return query;
	}

	public Customer getCustomer() {
		return customer;
	}
	
	@Override
	public boolean equals(Object obj) {	
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {		
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}