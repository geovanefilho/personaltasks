package com.hooyu.exercise.controllers

import javax.servlet.http.HttpSession

import org.springframework.ui.Model
import org.springframework.web.servlet.ModelAndView

import com.hooyu.exercise.customers.domain.Customer
import com.hooyu.exercise.exception.CustomerNotFoundException
import com.hooyu.exercise.helper.ResponseBodyHelper
import com.hooyu.exercise.helper.SearchRequest
import com.hooyu.exercise.helper.SessionHelper
import com.hooyu.exercise.service.SearchService

import net.icdpublishing.exercise2.searchengine.domain.Record
import net.icdpublishing.exercise2.searchengine.loader.DataLoader
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery
import spock.lang.Specification;

class SearchControllerTest extends Specification {

	SearchController controller
	
	def setup() {
		controller = new SearchController()
	}
	
	def "should return the login view with empty model object"() {
		when:
			ModelAndView modelAndView = controller.searchHome(null);

		then:
			modelAndView.getViewName() == "search"
		and:
			modelAndView.getModel().get("surname") == null
		and:
			modelAndView.getModel().get("postcode") == null	
	}
	
	def "should return the login view with model object already created"() {
		setup:
			Model model = Stub(Model.class);
			ModelAndView modelAndView = controller.searchHome(model);
			modelAndView.getModel().putAt("query", new SimpleSurnameAndPostcodeQuery("Pasini", "E1 4HQ"));
			
		when:
			model.asMap() >> modelAndView.getProperties();
		and:
			ModelAndView modelValidation = controller.searchHome(model);

		then:
			modelValidation.getViewName() == modelAndView.getViewName()
		and:
			modelValidation.getModel().get("surname") == modelAndView.getModel().get("surname")
		and:
			modelValidation.getModel().get("postcode") == modelAndView.getModel().get("postcode")
	}
	
	def "should return the values for the request"() {
		setup:
			SearchService searchService = Stub(SearchService.class);
			SearchController controllerThis = new SearchController(searchService);
			SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery("Pasini", "E1 4HQ")
			HttpSession session = Stub(HttpSession.class);
			String email = "pazine@gmail.com"
			
			DataLoader data = new DataLoader();
			
		when:
			session.getAttribute(SessionHelper.SESSION_EMAIL) >> email
		and:
			searchService.search(query, email) >> data.loadAllDatasets()
		and:
			ModelAndView modelAndView = controllerThis.searchFor(query, session);
			
		then:
			((Collection) ((ResponseBodyHelper) modelAndView.getModel().get("result")).getData()).size() == 4
	}
	
	def "should return redirect view due false consumer"() {
		setup:
			SearchService searchService = Stub(SearchService.class);
			SearchController controllerThis = new SearchController(searchService);
			SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery("Pasini", "E1 4HQ")
			HttpSession session = Stub(HttpSession.class);
			String email = "pazine@gmail.com"
			
			DataLoader data = new DataLoader();
			
		when:
			session.getAttribute(SessionHelper.SESSION_EMAIL) >> email
		and:
			searchService.search(query, email) >> { throw new CustomerNotFoundException() }
		and:
			ModelAndView modelAndView = controllerThis.searchFor(query, session);
			
		then:
			modelAndView.getViewName().contains("redirect")
	}
	
	def "should return ResponseBodyHelper with values for the request"() {
		setup:
			SearchService searchService = Stub(SearchService.class);
			SearchController controllerThis = new SearchController(searchService);
			SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery("Pasini", "E1 4HQ")
			HttpSession session = Stub(HttpSession.class);
			String email = "pazine@gmail.com"
			
			DataLoader data = new DataLoader();
			
		when:
			session.getAttribute(SessionHelper.SESSION_EMAIL) >> email
		and:
			searchService.search(query, email) >> data.loadAllDatasets()
		and:
			ResponseBodyHelper response = controllerThis.searchJson(query, session);
			
		then:
			((Collection) response.getData()).size() == data.loadAllDatasets().size()
	}
	
	def "should return collection of records"() {
		setup:
			SearchService searchService = Stub(SearchService.class);
			SearchController controllerThis = new SearchController(searchService);
			SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery("Pasini", "E1 4HQ")
			String email = "pazine@gmail.com"
			Customer cust = new Customer();
			cust.setEmailAddress(email);
			SearchRequest request = new SearchRequest(query, cust);
			
			DataLoader data = new DataLoader();
			
		when:
			searchService.search(query, email) >> data.loadAllDatasets()
		and:
			Collection<Record> response = controllerThis.handleRequest(request);
			
		then:
			response.size() == data.loadAllDatasets().size()
	}
	
	def "should return empty collection for null request"() {
		when:
			Collection<Record> response = controller.handleRequest(null);
			
		then:
			response.isEmpty()
	}
	
	def "should return empty collection for null customer"() {
		setup:
			SimpleSurnameAndPostcodeQuery query = new SimpleSurnameAndPostcodeQuery("Pasini", "E1 4HQ")
			SearchRequest request = new SearchRequest(query, null);
		when:
			Collection<Record> response = controller.handleRequest(request);
			
		then:
			response.isEmpty()
	}
	
	def "should return empty collection for null query"() {
		setup:
			String email = "pazine@gmail.com"
			Customer cust = new Customer();
			cust.setEmailAddress(email);
			SearchRequest request = new SearchRequest(null, cust);
		when:
			Collection<Record> response = controller.handleRequest(request);
			
		then:
			response.isEmpty()
	}

}
