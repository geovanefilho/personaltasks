package com.generic.retailer.service.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.generic.retailer.model.Discount;

/**
 * Class responsible for validate a discount
 * 
 * @author geovanefilho
 *
 */
@Component("discountValidator")
public class DiscountValidator implements Validator {
	
	private int MAX_NAME_LENGTH = 1500; //it should use internationalization instead of plain text
	private String NAME_LENGTH_VALIDATION = "must have up to " + MAX_NAME_LENGTH + " caracters.";
	private static final int MINIMUM_VALUE_FOR_AMOUNT_RELATED = 1;
	private String AMOUNT_RELATED_VALIDATION = "must be higger or equals to " + MINIMUM_VALUE_FOR_AMOUNT_RELATED;
	
    @Override
	public boolean supports(Class<?> clazz) {
		return Discount.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
        
        ValidationUtils.rejectIfEmpty(errors, "discountName", UtilValidator.REQUIRED_FIELD_VALIDATION_CODE, UtilValidator.REQUIRED_FIELD); 
        ValidationUtils.rejectIfEmpty(errors, "amountRelated", UtilValidator.REQUIRED_FIELD_VALIDATION_CODE, UtilValidator.REQUIRED_FIELD);
        ValidationUtils.rejectIfEmpty(errors, "value", UtilValidator.REQUIRED_FIELD_VALIDATION_CODE, UtilValidator.REQUIRED_FIELD);
                   
        Discount discount = (Discount) target;
        
        if (discount.getDiscountName() != null && discount.getDiscountName().length() > MAX_NAME_LENGTH) {
        	errors.rejectValue("discountName", UtilValidator.FIELD_VALIDATION_CODE, NAME_LENGTH_VALIDATION);
        }
        
        if (discount.getAmountRelated() != null && discount.getAmountRelated() < MINIMUM_VALUE_FOR_AMOUNT_RELATED) {
        	errors.rejectValue("amountRelated", UtilValidator.FIELD_VALIDATION_CODE, AMOUNT_RELATED_VALIDATION);
        }
	}
}

