package com.hooyu.exercise.controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hooyu.exercise.customers.domain.Customer;
import com.hooyu.exercise.exception.CustomerNotFoundException;
import com.hooyu.exercise.helper.ResponseBodyHelper;
import com.hooyu.exercise.helper.SearchRequest;
import com.hooyu.exercise.helper.SessionHelper;
import com.hooyu.exercise.service.SearchService;

import net.icdpublishing.exercise2.searchengine.domain.Record;
import net.icdpublishing.exercise2.searchengine.requests.SimpleSurnameAndPostcodeQuery;

/**
 * The SearchController contains all the request inputs for the search services.
 * This controller uses a DummyRetrievalServiceImpl class that simulate an engine search.
 * 
 * @author geovanefilho
 *
 */
@Controller
@RequestMapping("/search")
@ControllerAdvice
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}
	
	/**
	 * The searchHome method is the index method for the URI /search/ 
	 * 
	 * @param model Model information provided in the request
	 * @return The view where the system should redirect into it.
	 */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView searchHome(Model model) {
        if (model == null || !model.containsAttribute("query")) {
        	return new ModelAndView("search", "query", new SimpleSurnameAndPostcodeQuery(null, null));
        } else {
        	return new ModelAndView("search", model.asMap());
        }   
    }
    
    /**
     * The searchFor method executes a search in the engine with the provided keys.
     * It needs to contain a valid customer signed in to execute the search.
     * This method returns a view where will show the result in a table.
     * 
     * @param query Information to find the records that matches it.
     * @param session The session with information of the signed in customer.
     * @return The view where the system should redirect into it with the result of the search.
     */
    @RequestMapping(value = "/for", method = RequestMethod.POST)
    public ModelAndView searchFor(@ModelAttribute("query") SimpleSurnameAndPostcodeQuery query, HttpSession session) {
    	String email = (String) session.getAttribute(SessionHelper.SESSION_EMAIL);
    	Customer cust = new Customer();
    	cust.setEmailAddress(email);
    	
    	Collection<Record> result = null;
    	try {
    		result = handleRequest(new SearchRequest(query, cust));
    	} catch(CustomerNotFoundException cnfe) {
    		final Logger logger = LogManager.getLogger(SearchController.class);
        	logger.log(Level.ERROR, cnfe);
    		return new ModelAndView("redirect:/", "error", cnfe.getMessage());
    	}
    	
    	return new ModelAndView("result", "result", new ResponseBodyHelper(result));
    }
    
    /**
     * The searchJson method executes a search in the engine with the provided keys.
     * It needs to contain a valid customer signed in to execute the search.
     * This method returns the information in a JSON format.
     * 
     * @param query Information to find the records that matches it.
     * @param session The session with information of the signed in customer.
     * @return The ResponseBodyHelper with the information of the search result
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBodyHelper searchJson(@ModelAttribute("query") SimpleSurnameAndPostcodeQuery query, HttpSession session) {
    	String email = (String) session.getAttribute(SessionHelper.SESSION_EMAIL);
    	Customer cust = new Customer();
    	cust.setEmailAddress(email);
    	
    	ResponseBodyHelper responseBodyHelper;
    	
    	Collection<Record> result = null;
    	try {
    		result = handleRequest(new SearchRequest(query, cust));
    	} catch(CustomerNotFoundException cnfe) {
    		final Logger logger = LogManager.getLogger(SearchController.class);
        	logger.log(Level.ERROR, cnfe);
    		responseBodyHelper = new ResponseBodyHelper(query, cnfe);
    		return responseBodyHelper;
    	}
    	
    	responseBodyHelper = new ResponseBodyHelper(result);
    	return responseBodyHelper;
    }
	
    /**
     * Call the service to search the information and return it if finds something.
     * 
     * @param request The request containing the query to execute and the customer email address
     * @return Collection<Record> with the records found on the search.
     * @throws CustomerNotFoundException Throw if the customer with the email address provided does not exists
     */
	public Collection<Record> handleRequest(SearchRequest request) throws CustomerNotFoundException {
		Collection<Record> result = new ArrayList<>();
		if (request != null && request.getQuery() != null && request.getCustomer() != null) {
			result = this.searchService.search(request.getQuery(), request.getCustomer().getEmailAddress());
		}
		return result;
	}
}