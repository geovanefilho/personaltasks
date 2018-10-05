/**
 * 
 */
package com.hooyu.exercise.service;

import java.util.Collection;

import com.hooyu.exercise.customers.domain.Customer;

import net.icdpublishing.exercise2.myapp.charging.services.ChargingService;
import net.icdpublishing.exercise2.searchengine.domain.Record;

/**
 * Interface with services to verify the needed of charges in a customer account
 * 
 * @author geovanefilho
 *
 */
public interface SearchChargingService extends ChargingService {

	/**
	 * Verify the credit amount needed to charge in a result search of a customer
	 * A customer that is Non Paying is never charged, so always will return zero credits.
	 * It will charge premium customers 1 credit per record returned if
	 * the record contain at least one data source different from "BT".
	 * 
	 * @param resultSearch The result of a search to validate the credit amount
	 * @param customer Customer info
	 * @return Quantity of credit needed to be charged
	 */
	public int creditNeeded(Collection<Record> resultSearch, Customer customer);

	/**
	 * Verify the records to see if needs to charge and charge if needed.
	 * 
	 * @param resultSearch The result of a search to validate the credit amount
	 * @param cust Customer info
	 */
	public void charge(Collection<Record> resultSearch, Customer cust);
}
