/**
 * 
 */
package com.hooyu.exercise.helper;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The types of status for request results.
 * 
 * SUCCESS - A success request result
 * WARNING - A success request result with some information to sent to the client
 * ERROR - An unsuccessful request result with information for the error 
 * 
 * @author geovanefilho
 *
 */
public enum RequestStatus {

	SUCCESS("Success"),
	WARNING("Warning"),
	ERROR("Error");
	
	private final String description;
    
	RequestStatus(String description) {
        this.description = description;
    }
    
    public final static Map<String, RequestStatus> STATUS = Stream.of(values())
            .collect(Collectors.toMap(RequestStatus::description, level -> level));

    public String code() {
        return name();
    }
    
    public String description() {
    	return description;
    }

    /**
     * Get a request status by its name
     * 
     * @param name The name of the request status
     * @return A <code>RequestStatus</code>
     */
    public static RequestStatus getByName(String name) {
        return STATUS.get(name);
    }
}
