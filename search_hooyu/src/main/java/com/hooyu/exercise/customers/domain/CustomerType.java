package com.hooyu.exercise.customers.domain;

/**
 * Types of customer that the system accepts. 
 * 
 * PREMIUM - Premium customer can receive information from all the sources that the system access on.
 * NON_PAYING - Non paying customers must have informations exclusive from BT source only.
 * 
 * @author geovanefilho
 *
 */
public enum CustomerType {
	PREMIUM,
	NON_PAYING
}