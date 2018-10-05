/**
 * 
 */
package com.hooyu.exercise.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hooyu.exercise.customers.dao.CustomerDao;
import com.hooyu.exercise.customers.domain.Customer;
import com.hooyu.exercise.exception.CustomerNotFoundException;
import com.hooyu.exercise.helper.SessionHelper;
import com.hooyu.exercise.service.SignInService;

/**
 * The implementation for the SignIn services that uses the CustomerDao implementation
 * to resolve the services necessary to handle login services.
 * 
 * @author geovanefilho
 *
 */
@Service
public class SignInServiceImpl implements SignInService {
	
	@Autowired
	private CustomerDao customerDao;
	
	public SignInServiceImpl() {}
	
	public SignInServiceImpl(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void signIn(String emailAddress) throws CustomerNotFoundException {
		Customer cust = this.customerDao.findCustomerByEmailAddress(emailAddress);
		if (cust != null) {
			session().setAttribute(SessionHelper.SESSION_EMAIL, emailAddress);
		}
	}
	
	/**
	 * Get the session with the information of the requests and users.
	 * 
	 * @return A HttpSession
	 */
	private HttpSession session() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest().getSession(true);
	}

}
