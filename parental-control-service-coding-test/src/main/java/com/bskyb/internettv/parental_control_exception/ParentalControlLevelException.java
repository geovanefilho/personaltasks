/**
 * 
 */
package com.bskyb.internettv.parental_control_exception;

/**
 * The {@code ParentalControlLevelException} class is the exception launched
 * when the parental control level defined does not exists or it had some error
 * when tried to get the parental control level of a movie.
 * 
 * @author geovanefilho
 *
 */
public class ParentalControlLevelException extends Exception {

	private static final long serialVersionUID = -2129007937900417880L;

	/**
     * Constructs an {@code ParentalControlLevelException} with the specified detail message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method) 
     */
    public ParentalControlLevelException(String message) {
        super(message);
    }
    
	/**
     * Constructs an {@code ParentalControlLevelException} with the specified cause.
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method). (A null value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     */
    public ParentalControlLevelException(Throwable cause) {
        super(cause);
    }
    
    /**
     * Constructs an {@code ParentalControlLevelException} with the specified detail message
     * and cause.
     *
     * <p> Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated into this exception's detail
     * message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     *        
     */
    public ParentalControlLevelException(String message, Throwable cause) {
        super(message, cause);
    }

}
