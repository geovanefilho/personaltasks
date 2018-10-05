/**
 * 
 */
package com.hooyu.exercise.helper;

/**
 * ResponseBodyHelper is a class created to help provide information for the requests.
 * It contains the status of the request and any message and data provided by the service.
 * 
 * @author geovanefilho
 *
 */
public class ResponseBodyHelper {

	private RequestStatus status;
	private String message;
	private Object data;
	
	/**
	 * Create a success response with the data provided.
	 * 
	 * @param data Information that should be delivered to the client
	 */
	public ResponseBodyHelper(Object data) {
		this.status = RequestStatus.SUCCESS;
		this.data = data;
	}
	
	/**
	 * Create a warning response, it was successfully executed
	 * but has messages that should be delivered to the client.
	 * 
	 * @param data Information that should be delivered to the client
	 */
	public ResponseBodyHelper(Object data, String message) {
		this.status = RequestStatus.WARNING;
		this.data = data;
	}
	
	/**
	 * Create an error response containing the message with information about the error
	 * and the data sent in the request.
	 * 
	 * @param data Information that should be delivered to the client
	 * @param exception Information about the error
	 */
	public ResponseBodyHelper(Object data, Exception exception) {
		this.status = RequestStatus.ERROR;
		this.message = exception.getMessage();
	}
	
	/**
	 * Create an error response containing the message with information about the error.
	 * 
	 * @param exception Information about the error
	 */
	public ResponseBodyHelper(Exception exception) {
		this.status = RequestStatus.ERROR;
		this.message = exception.getMessage();
	}
	
	public RequestStatus getStatus() {
		return status;
	}
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
