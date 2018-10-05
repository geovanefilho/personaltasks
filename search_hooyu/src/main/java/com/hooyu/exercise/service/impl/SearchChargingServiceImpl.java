/**
 * 
 */
package com.hooyu.exercise.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hooyu.exercise.customers.domain.Customer;
import com.hooyu.exercise.customers.domain.CustomerType;
import com.hooyu.exercise.exception.ChargingValidationException;
import com.hooyu.exercise.service.SearchChargingService;

import net.icdpublishing.exercise2.myapp.charging.services.ImaginaryChargingService;
import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.domain.SourceType;

/**
 * Implementation of the search charging services.
 * 
 * @author geovanefilho
 *
 */
@Service
public class SearchChargingServiceImpl extends ImaginaryChargingService implements SearchChargingService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int creditNeeded(Collection<Record> resultSearch, Customer customer) throws ChargingValidationException {
		int credits = 0;
		
		if (customer != null) {
			if (resultSearch != null && !resultSearch.isEmpty() &&
					customer.getCustomType() == CustomerType.PREMIUM) {
				
				int amountRecordsToCharge = resultSearch.stream().filter(rec -> {
					if (rec.getSourceTypes().stream().filter(source -> !source.equals(SourceType.BT)).findAny().orElse(null) == null) {
						return false;
					} else {
						return true;
					}
				}).collect(Collectors.toList()).size();
				
				credits = amountRecordsToCharge;
			}
		} else {
			final Logger logger = LogManager.getLogger(SearchChargingServiceImpl.class);
			String msg = "You need to provide a valid customer to validate charging!"; //Messages should be internationalized
        	logger.log(Level.ERROR, msg);
			throw new ChargingValidationException(msg);
		}
		
		return credits;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void charge(Collection<Record> resultSearch, Customer cust) {
		int credit = this.creditNeeded(resultSearch, cust);
		this.charge(cust.getEmailAddress(), credit);
	}

}
