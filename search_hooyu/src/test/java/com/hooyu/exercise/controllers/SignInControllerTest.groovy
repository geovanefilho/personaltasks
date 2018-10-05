package com.hooyu.exercise.controllers

import org.springframework.ui.Model
import org.springframework.web.servlet.ModelAndView

import com.hooyu.exercise.customers.domain.Customer
import com.hooyu.exercise.exception.CustomerNotFoundException
import com.hooyu.exercise.service.SignInService

import spock.lang.Specification;

class SignInControllerTest extends Specification {

	SignInController controller
	
	def setup() {
		controller = new SignInController()
	}
	
	def "should return the login view with empty model object"() {
		when:
			ModelAndView modelAndView = controller.signin(null, null);

		then:
			modelAndView.getViewName() == "signin"
		and:
			modelAndView.getModel().get("emailAddress") == null
	}
	
	def "should return model with error message"() {
		setup:
			String error = "Error";
			Model model = Stub(Model.class);
			Map<String, Object> custForm = new HashMap<>();
			custForm.put("customerForm", new Customer());
		when:
			model.asMap() >> custForm
		and:
			ModelAndView modelAndView = controller.signin(model, error);

		then:
			modelAndView.getViewName() == "signin"
		and:
			modelAndView.getModel().get("error") == error
	}
	
	def "should redirect to the search page"() {
		setup:
			SignInService signInService = Stub(SignInService.class);
			SignInController controller = new SignInController(signInService);
			
			String email = "pazine@gmail.com"
			Customer cust = new Customer();
			cust.setEmailAddress(email);
			
		when:
			signInService.signIn(email) >> {}
		and:
			ModelAndView modelAndView = controller.login(cust, null);
		then:
			modelAndView.getViewName().contains("redirect")
	}
	
	def "should return signin page with error message"() {
		setup:
			SignInService signInService = Stub(SignInService.class);
			SignInController controller = new SignInController(signInService);
			
			String errorMsg = "Error"
			String email = "pazine@gmail.com"
			Customer cust = new Customer();
			cust.setEmailAddress(email);
			
		when:
			signInService.signIn(email) >> {throw new CustomerNotFoundException(errorMsg)}
		and:
			ModelAndView modelAndView = controller.login(cust, null);
			
		then:
			modelAndView.getViewName() == "signin"
		and:
			modelAndView.getModel().get("error") == errorMsg
	}
	
	def "should return signin page with error message when email is empty"() {
		setup:
			String email = ""
			Customer cust = new Customer();
			cust.setEmailAddress(email);
			
		when:
			ModelAndView modelAndView = controller.login(cust, null);
			
		then:
			modelAndView.getViewName() == "signin"
		and:
			!((String) modelAndView.getModel().get("error")).isEmpty();
	}
	
	def "should return signin page with error message when email is null"() {
		setup:
			Customer cust = new Customer();
			cust.setEmailAddress(null);
			
		when:
			ModelAndView modelAndView = controller.login(cust, null);
			
		then:
			modelAndView.getViewName() == "signin"
		and:
			!((String) modelAndView.getModel().get("error")).isEmpty();
	}
	
	def "should return signin page with error message when customer is null"() {
		when:
			ModelAndView modelAndView = controller.login(null, null);
			
		then:
			modelAndView.getViewName() == "signin"
		and:
			!((String) modelAndView.getModel().get("error")).isEmpty();
	}
}
