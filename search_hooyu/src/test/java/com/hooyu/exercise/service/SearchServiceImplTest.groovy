package com.hooyu.exercise.service

import com.hooyu.exercise.customers.dao.CustomerDao
import com.hooyu.exercise.customers.domain.Customer
import com.hooyu.exercise.customers.domain.CustomerType
import com.hooyu.exercise.service.impl.SearchServiceImpl

import net.icdpublishing.exercise2.searchengine.domain.Record
import net.icdpublishing.exercise2.searchengine.loader.DataLoader
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery
import net.icdpublishing.exercise2.searchengine.services.SearchEngineRetrievalService
import spock.lang.Specification;

class SearchServiceImplTest extends Specification {

	SearchEngineRetrievalService retrievalService
	CustomerDao dao
	SearchChargingService searchChargingService
	SearchService service
	
	def setup() {
		retrievalService = Stub(SearchEngineRetrievalService.class);
		dao = Stub(CustomerDao.class);
		searchChargingService = Stub(SearchChargingService.class);
		
		service = new SearchServiceImpl(retrievalService, dao, searchChargingService)
	}
	
	def "should return records found"() {
		setup:
			String email = "john.doe@192.com"
			Customer custPremium = createDummyCustomer(email, "John", "Doe", CustomerType.PREMIUM)
			SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery("Pasini", "E1 4HQ")
			
			DataLoader data = new DataLoader();
			
		when:
			retrievalService.search(query) >> data.loadAllDatasets()
		and:
			dao.findCustomerByEmailAddress(email) >> custPremium
		and:
			Collection<Record> result = service.search(query, email)
			
		then:
			result.size() == data.loadAllDatasets().size();
	}
	
	def "should return filtered records"() {
		setup:
			String email = "harry.lang@192.com"
			Customer cust = createDummyCustomer(email, "Harry", "Lang", CustomerType.NON_PAYING)
			SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery("Pasini", "E1 4HQ")
			
			DataLoader data = new DataLoader();
			
		when:
			retrievalService.search(query) >> data.loadAllDatasets()
		and:
			dao.findCustomerByEmailAddress(email) >> cust
		and:
			Collection<Record> result = service.search(query, email)
			
		then:
			result.size() == 1;
	}
	
	private Customer createDummyCustomer(String email, String forename, String surname, CustomerType type) {
		Customer c = new Customer();
		c.setEmailAddress(email);
		c.setForename(forename);
		c.setSurname(surname);
		c.setCustomType(type);
		return c;
	}

}
