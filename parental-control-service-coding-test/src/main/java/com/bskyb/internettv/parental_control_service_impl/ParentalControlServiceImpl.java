/**
 * 
 */
package com.bskyb.internettv.parental_control_service_impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bskyb.internettv.parental_control_exception.ParentalControlLevelException;
import com.bskyb.internettv.parental_control_service.ParentalControlService;
import com.bskyb.internettv.parental_control_util.ParentalControlLevel;
import com.bskyb.internettv.thirdparty.MovieService;
import com.bskyb.internettv.thirdparty.TechnicalFailureException;
import com.bskyb.internettv.thirdparty.TitleNotFoundException;

/**
 * This class is an implementation for {@link ParentalControlService}.
 * It provides the solutions to assert and get the informations about the parental control.
 * 
 * This class uses the service from {@link MovieService} to get informations about the movies.
 * 
 * @author geovanefilho
 *
 */
public class ParentalControlServiceImpl implements ParentalControlService {

	private MovieService movieService;
	
	public ParentalControlServiceImpl(MovieService movieService) {
		this.movieService = movieService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canWatchMovie(String customerParentalControlLevel, String movieId) throws TitleNotFoundException, TechnicalFailureException {
		
		ParentalControlLevel customerLevel;
		ParentalControlLevel movieLevel;
		
		try {
			customerLevel = ParentalControlLevel.getByName(customerParentalControlLevel);
			movieLevel = ParentalControlLevel.getByName(movieService.getParentalControlLevel(movieId));
		} catch (ParentalControlLevelException e) {
			final Logger logger = LogManager.getLogger(ParentalControlServiceImpl.class);
        	logger.log(Level.ERROR, e);
			throw new TechnicalFailureException(e);
		}
		
		return movieLevel.numericCode() <= customerLevel.numericCode();
	}

}
