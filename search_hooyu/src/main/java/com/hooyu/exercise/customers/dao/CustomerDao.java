package com.hooyu.exercise.customers.dao;

import com.hooyu.exercise.customers.domain.Customer;
import com.hooyu.exercise.exception.CustomerNotFoundException;

/**
 * Dao interface for Customer services.
 * It connects with the repository and do searchs regards to customers. 
 * 
 * @author geovanefilho
 *
 */
public interface CustomerDao {

	/**
	 * Find a customer with the email address provided.
	 * 
	 * @param email Email address
	 * @return A customer with all his information
	 * @throws CustomerNotFoundException Launched if no customer was found with the email address.
	 */
	Customer findCustomerByEmailAddress(String email) throws CustomerNotFoundException;
}
