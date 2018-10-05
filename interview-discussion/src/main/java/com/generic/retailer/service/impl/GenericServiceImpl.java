/**
 * 
 */
package com.generic.retailer.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import com.generic.retailer.service.GenericService;

/**
 * @author geovanefilho
 *
 *	Implementation of generic methods in dao
 */
public class GenericServiceImpl<T extends Serializable> implements GenericService<T> {
	
	protected Validator validator;
	
	protected GenericServiceImpl() {}

	public GenericServiceImpl(Validator validator) {
		this.validator = validator;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public T save(T model) {
		if (this.validator != null) {
			this.validarEntidade(model, this.validator);
		}
		//Should save in database
		return model;
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveAll(Collection<T> models) {
		if (models != null) {
			for (T model : models) {
				model = this.save(model);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public T merge(T model) {
		if (this.validator != null) {
			this.validarEntidade(model, this.validator);
		}
		//Should merge in database
		return model;
	}
	
	/**
	 * Validate the entity
	 *
	 * @param entity
	 */
	public void validarEntidade(T entity, Validator customValidator) {

		BindException errors = new BindException(entity, entity.getClass().getName());

		customValidator.validate(entity, errors);

		List<ObjectError> list = errors.getAllErrors();
		if (list != null && !list.isEmpty()) {

			String error = "";
			
			for (ObjectError objErr : list) {
				if (objErr instanceof FieldError) {
					error += ((FieldError) objErr).getField() + " ";
				}
				
				error += objErr.getDefaultMessage() + "; ";
			}

			throw new ValidationException(error);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> findAll() {
		//Should get all the entities in database
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public T find(Long id) {
		//should find an entity at the database
		return null;
	}

}
