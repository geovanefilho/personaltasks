/**
 * 
 */
package com.hooyu.exercise.service;

import java.util.Collection;

import com.hooyu.exercise.exception.CustomerNotFoundException;

import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;

/**
 * The service interface containing all the possibles methods that an search implementation must
 * have in the system.
 * 
 * @author geovanefilho
 *
 */
public interface SearchService {

	/**
	 * The search method do searches in all the sources that the system has connection with.
	 * The result of the search will depend of the information provided as the query information
	 * and the customer email address. 
	 * 
	 * @param query Should contain the key needed to search the data from people.
	 * @param emailAddress Email address from the customer signed in.
	 * @return A Collection<Record> with all the records that could be sent for the customer.
	 */
	public Collection<Record> search(SimpleSurnameAndPostcodeQuery query, String emailAddress) throws CustomerNotFoundException;
	
}
