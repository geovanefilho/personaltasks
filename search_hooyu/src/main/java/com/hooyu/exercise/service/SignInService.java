/**
 * 
 */
package com.hooyu.exercise.service;

import com.hooyu.exercise.exception.CustomerNotFoundException;

/**
 * The service interface containing all the possibles methods that a Sign In service implementation
 * must have in the system.
 * 
 * @author geovanefilho
 *
 */
public interface SignInService {

	/**
	 * The signIn method will verify the existence of the customer by his email address and
	 * put him in the session if its valid.
	 * 
	 * @param emailAddress Email address from the customer.
	 * @throws CustomerNotFoundException Exceptions launched if does not exists an customer with
	 * the email address provided.
	 */
	public void signIn(String emailAddress) throws CustomerNotFoundException;
	
 }
