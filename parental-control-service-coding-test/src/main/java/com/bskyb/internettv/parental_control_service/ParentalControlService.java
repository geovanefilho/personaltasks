package com.bskyb.internettv.parental_control_service;

import com.bskyb.internettv.thirdparty.TechnicalFailureException;
import com.bskyb.internettv.thirdparty.TitleNotFoundException;

/**
 * The {@code ParentalControlService} interface is the service
 * available for Parental Control.
 * This class only provide services to assert and provide informations
 * about the parental control level.
 * 
 * @author geovanefilho
 *
 */
public interface ParentalControlService {
	
	/**
	 * The {@code canWatchMovie} method return if a customer can or
	 * can not see a specific movie with the parental control level
	 * defined in your profile.
	 * 
	 * @param customerParentalControlLevel The Parental Control Level defined in the customer profile.
	 * @param movieId The ID from the movie that the customer wish to see
	 * 
	 * @return <code>true</code> if the customer can watch the movie or <code>false</code>
	 * otherwise.
	 * 
	 * @throws TitleNotFoundException Launched if the movie could not be found with the specified ID.
	 * @throws TechnicalFailureException Launched if occurred some technical failure.
	 */
    boolean canWatchMovie(String customerParentalControlLevel, String movieId) throws TitleNotFoundException, TechnicalFailureException;
    
}
