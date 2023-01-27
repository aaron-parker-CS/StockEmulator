/*
 * Aaron Parker
 * 
 * Exception sublcass to handle invalid names. This exception class is used for custom messages to the end user.
 */

package application;

public class InvalidNameException extends Exception {
	private static final long serialVersionUID = 1000L;
	public InvalidNameException() {
		super();
	}
}
