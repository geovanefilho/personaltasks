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
 * Types of discounts available for a product
 * 
 * Percentage - The value of discount is a percentage applied at the value of the amount of a product related in the discount
 * Price - The value of discount is a value applied at the total value of the amount of a product related in the discount
 * 
 * @author geovanefilho
 *
 */
public enum DiscountType {

	PERCENTAGE(1),
	PRICE(2);
	
	private final int code;
    
    DiscountType(int code) {
        this.code = code;
    }
    
    public final static Map<String, DiscountType> TYPES = Stream.of(values())
            .collect(Collectors.toMap(DiscountType::code, dt -> dt));

    public String code() {
        return name();
    }

    public int numericCode() {
        return code;
    }

    /**
     * Get a discount by its code
     * 
     * @param code
     * @return
     */
    public static DiscountType getByCode(String code) {
    	DiscountType discountType = TYPES.get(code);
        if (discountType == null) {
        	final Logger logger = LogManager.getLogger(DiscountType.class);
        	//Message should be internationalized
        	String msg = "Discount type for code '" + code + "' unavailable!";
        	logger.log(Level.WARN, msg);
        }
        return discountType;
    }
	
}
