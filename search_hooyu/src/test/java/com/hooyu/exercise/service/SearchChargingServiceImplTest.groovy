package com.hooyu.exercise.service

import com.hooyu.exercise.customers.domain.Customer
import com.hooyu.exercise.customers.domain.CustomerType
import com.hooyu.exercise.service.impl.SearchChargingServiceImpl

import net.icdpublishing.exercise2.searchengine.domain.Record
import net.icdpublishing.exercise2.searchengine.domain.SourceType
import net.icdpublishing.exercise2.searchengine.loader.DataLoader
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery
import spock.lang.Specification;

class SearchChargingServiceImplTest extends Specification {

	SearchChargingService service
	
	def setup() {
		service = new SearchChargingServiceImpl()
	}
	
	def "should charge for this result"() {
		setup:
			String email = "john.doe@192.com"
			Customer custPremium = createDummyCustomer(email, "John", "Doe", CustomerType.PREMIUM)
			SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery("Pasini", "E1 4HQ")
			
			DataLoader data = new DataLoader();
			
		when:
			int credit = service.creditNeeded(data.loadAllDatasets(), custPremium);
			
		then:
			credit == 3;
	}
	
	def "should not charge for results just from BT"() {
		setup:
			String email = "john.doe@192.com"
			Customer custPremium = createDummyCustomer(email, "John", "Doe", CustomerType.PREMIUM)
			SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery("Pasini", "E1 4HQ")
			
			DataLoader data = new DataLoader();
			Collection<Record> records = new ArrayList();
			for (Record record : data.loadAllDatasets()) {
				boolean add = true;
				for (SourceType type : record.getSourceTypes()) {
					if (type != SourceType.BT) {
						add = false;
					}
				}
				if (add) records.add(record);
			}
			
		when:
			int credit = service.creditNeeded(records, custPremium);
			
		then:
			credit == 0;
	}
	
	def "should need 0 credits for non paying"() {
		setup:
			String email = "harry.lang@192.com"
			Customer cust = createDummyCustomer(email, "Harry", "Lang", CustomerType.NON_PAYING)
			SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery("Pasini", "E1 4HQ")
			
			DataLoader data = new DataLoader();
			
		when:
			int credit = service.creditNeeded(data.loadAllDatasets(), cust);
			
		then:
			credit == 0;
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
