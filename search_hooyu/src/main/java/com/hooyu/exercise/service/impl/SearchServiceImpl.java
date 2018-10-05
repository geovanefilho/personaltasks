/**
 * 
 */
package com.hooyu.exercise.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hooyu.exercise.customers.dao.CustomerDao;
import com.hooyu.exercise.customers.dao.impl.HardcodedListOfCustomersImpl;
import com.hooyu.exercise.customers.domain.Customer;
import com.hooyu.exercise.customers.domain.CustomerType;
import com.hooyu.exercise.exception.CustomerNotFoundException;
import com.hooyu.exercise.service.SearchChargingService;
import com.hooyu.exercise.service.SearchService;

import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.domain.SourceType;
import net.icdpublishing.exercise2.searchengine.loader.DataLoader;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;
import net.icdpublishing.exercise2.searchengine.services.DummyRetrievalServiceImpl;
import net.icdpublishing.exercise2.searchengine.services.SearchEngineRetrievalService;

/**
 * Class implementation for the SearchService that uses the DummyRetrievalServiceImpl
 * to made searches in the simulated engine.
 * 
 * @author geovanefilho
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private SearchChargingService searchChargingService;

	//Simulate an engine that do the searching in different sources.
	private SearchEngineRetrievalService retrievalService;
	
	public SearchServiceImpl() {
		retrievalService = new DummyRetrievalServiceImpl(new DataLoader());
	}
	
	public SearchServiceImpl(SearchEngineRetrievalService retrievalService, CustomerDao customerDao, SearchChargingService searchChargingService) {
		if (retrievalService == null) {
			retrievalService = new DummyRetrievalServiceImpl(new DataLoader());
		} else {
			this.retrievalService = retrievalService;
		}
		if (customerDao == null) {
			this.customerDao = new HardcodedListOfCustomersImpl();
		} else {
			this.customerDao = customerDao;
		}
		this.searchChargingService = searchChargingService;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Record> search(SimpleSurnameAndPostcodeQuery query, String emailAddress) throws CustomerNotFoundException {
		Customer cust = this.customerDao.findCustomerByEmailAddress(emailAddress);
		
		Collection<Record> result = retrievalService.search(query);
		
		this.searchChargingService.charge(result, cust);
		
		if (cust.getCustomType().equals(CustomerType.PREMIUM)) {
			return result;
		} else {
			return filterForNonPaying(result);
		}
	}

	/**
	 * The filterForNonPaying method filter a collection of records and return just
	 * records that could be showed for non paying users.
	 * This method will return just records EXCLUSIVE from the Source BT.
	 * 
	 * @param records The collection with the records to be filtered.
	 * @return A Collection<Record> with filtered records.
	 */
	private Collection<Record> filterForNonPaying(Collection<Record> records) {
		if (records != null && !records.isEmpty()) {
			records = records.stream().filter(rec -> {
				if (rec.getSourceTypes().stream().filter(source -> !source.equals(SourceType.BT)).findAny().orElse(null) == null) {
					return true;
				} else {
					return false;
				}
			}).collect(Collectors.toList());
		}
		return records;
	}

}
