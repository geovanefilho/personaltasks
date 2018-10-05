/**
 * 
 */
package com.generic.retailer.model.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * Availabilities of a discount for the product
 * 
 * BEFORE_TIME - The availableAt is the hour of the day that the discount will not be available anymore (1 to 24)
 * 												1 AM is the lowest time to finish and midnight is the highest
 * AFTER_TIME - The availableAt is the hour of the day that the discount will start (0 to 23)
 * 												Midnight is the lowest time to start and 11 PM the highest
 * DAY_OF_MONTH - The availableAt is the day of the month that the discount is available (1 to 31)
 * DAY_OF_WEEK - The availableAt is the day of the week that the discount is available (1 to 7)
 * 												1 is Sunday and 7 is Saturday
 * MONTH_OF_YEAR - The availableAt is the month of the year that the discount is available (0 to 11)
 * 												0 is January and 11 is December
 * We could implement a "between" if it would be necessary for the system
 * 
 * @author geovanefilho
 *
 */
public enum DiscountAvailability {

	BEFORE_TIME(1),
	AFTER_TIME(2),
	DAY_OF_MONTH(3),
	DAY_OF_WEEK(4),
	MONTH_OF_YEAR(5);
	
	private final int code;
    
	DiscountAvailability(int code) {
        this.code = code;
    }
    
    public final static Map<String, DiscountAvailability> AVAILABILITY = Stream.of(values())
            .collect(Collectors.toMap(DiscountAvailability::code, dt -> dt));

    public String code() {
        return name();
    }

    public int numericCode() {
        return code;
    }

    /**
     * Get a availability by its code
     * 
     * @param code
     * @return
     */
    public static DiscountAvailability getByCode(String code) {
    	DiscountAvailability availability = AVAILABILITY.get(code);
        if (availability == null) {
        	final Logger logger = LogManager.getLogger(DiscountAvailability.class);
        	//Message should be internationalized
        	String msg = "Discount availability for code '" + code + "' unavailable!";
        	logger.log(Level.WARN, msg);
        }
        return availability;
    }
}
