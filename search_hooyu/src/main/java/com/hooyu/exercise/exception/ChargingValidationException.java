package com.hooyu.exercise.exception;

/**
 * A Excepction launched when the system could not validate the needed to charge a
 * search result.
 * 
 * @author geovanefilho
 *
 */
public class ChargingValidationException extends RuntimeException {

	private static final long serialVersionUID = 4685705877408023195L;

	public ChargingValidationException(String message) {
		super(message);
	}
}
