/**
 * 
 */
package com.bskyb.internettv.parental_control_util;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bskyb.internettv.parental_control_exception.ParentalControlLevelException;

/**
 * Levels of parental control available on the system.
 * 
 * LEVEL_U - U is the least restrictive level from the system.
 * 		If a movie is set with this level everyone could see it.
 * 		If a customer set this parental control level in your profile, only movies with this level can be available to watch.
 * LEVEL_PG - PG is the level that provides guidance to children under 8 years old.
 * 		If a movie is set with this level everyone above 8 years older could see it.
 * 		If a customer set this parental control level in your profile, only movies with this level and Level U can be available to watch.
 * LEVEL_12 - 12 is the level that provides guidance to children under 12 years old.
 * 		If a movie is set with this level everyone above 12 years older could see it.
 * 		If a customer set this parental control level in your profile, only movies with this and levels PG and U can be available to watch.
 * LEVEL_15 - 15 is the level that provides guidance to persons under 15 years old.
 * 		If a movie is set with this level everyone above 15 years older could see it.
 * 		If a customer set this parental control level in your profile, only movies with this and levels 12, PG and U can be available to watch.
 * LEVEL_18 - 18 is the most restrictive level in the system.
 * 		If a movie is set with this level just persons above 18 years older could see it.
 * 		If a customer set this parental control level in your profile, he could see any movie available in the system.
 * 
 * @author geovanefilho
 *
 */
public enum ParentalControlLevel {
	LEVEL_U(0, "U"),
	LEVEL_PG(1, "PG"),
	LEVEL_12(2, "12"),
	LEVEL_15(3, "15"),
	LEVEL_18(4, "18");
	
	private final int code;
	private final String description;
    
	ParentalControlLevel(int code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public final static Map<String, ParentalControlLevel> LEVELS = Stream.of(values())
            .collect(Collectors.toMap(ParentalControlLevel::description, level -> level));

    public String code() {
        return name();
    }

    public int numericCode() {
        return code;
    }
    
    public String description() {
    	return description;
    }

    /**
     * Get a parental control level by its name
     * 
     * @param name The name of the parental control level
     * @return A <code>ParentalControlLevel</code>
     * @throws ParentalControlLevelException Launched if the level with the name provided does not exists.
     */
    public static ParentalControlLevel getByName(String name) throws ParentalControlLevelException {
    	ParentalControlLevel level = LEVELS.get(name);
        if (level == null) {
        	final Logger logger = LogManager.getLogger(ParentalControlLevel.class);
        	//Message should be internationalized
        	String msg = "Level with name: '" + name + "' unavailable!";
        	logger.log(Level.ERROR, msg);
        	throw new ParentalControlLevelException(msg);
        }
        return level;
    }

}
