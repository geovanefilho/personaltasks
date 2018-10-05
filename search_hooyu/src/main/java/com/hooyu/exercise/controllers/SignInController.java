package com.hooyu.exercise.controllers;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.hooyu.exercise.customers.domain.Customer;
import com.hooyu.exercise.exception.CustomerNotFoundException;
import com.hooyu.exercise.service.SignInService;

/**
 * The SignInController is the controller who handles all the inputs for the login service.
 * 
 * @author geovanefilho
 *
 */
@Controller
@RequestMapping("/")
@ControllerAdvice
public class SignInController {
    
	@Autowired
	private SignInService signInService;
	
	public SignInController(SignInService signInService) {
		this.signInService = signInService;
	}
	
	/**
	 * The signin method is the index method for the system
	 * It will redirect to the login page.
	 * 
	 * @param model Model with information from the request
	 * @param error Error message to show in the login page if needed 
	 * @return The view where the system will redirect into it.
	 */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView signin(Model model, String error) {
    	Map<String, Object> infos = new HashMap<>();
    	if (model != null) {
    		infos = model.asMap();
    	}
    	
        if (error != null) {
        	infos.put("error", error);
        }
        
        if (infos.get("customerForm") == null) {
        	infos.put("customerForm", new Customer());
        }
        
        return new ModelAndView("signin", infos);
    }
    
    /**
	 * The login method is the input who will call the service to validate the login of the customer
	 * 
	 * @param custForm The form with the information of the customer that will sign in.
	 * @param model the model with the information from the request
	 * @return The view after signed in or the sign in view with the error message.
	 */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("customerForm") Customer custForm, Model model) {
    	
        if (custForm == null || custForm.getEmailAddress() == null || custForm.getEmailAddress().isEmpty()) {
        	return signin(model, "You need to provide a valid customer email!"); //message should be internationalized
        } else {
        	try {
        		this.signInService.signIn(custForm.getEmailAddress());
        	} catch (CustomerNotFoundException cnfe) {
        		final Logger logger = LogManager.getLogger(SignInController.class);
            	logger.log(Level.ERROR, cnfe);
        		return signin(model, cnfe.getMessage());
        	}
        	return new ModelAndView("redirect:/search/");
        }
    }
    
    
}
