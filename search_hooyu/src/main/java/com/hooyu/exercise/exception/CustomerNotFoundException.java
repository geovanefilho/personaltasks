package com.hooyu.exercise.exception;

/**
 * A Excepction launched when the system does not find a customer with the information provided.
 * 
 * @author geovanefilho
 *
 */
public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3461057742559595977L;

	public CustomerNotFoundException(String message) {
		super(message);
	}
}
