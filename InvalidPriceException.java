/*
 * Aaron Parker
 * 
 * Exception subclass to handle invalid prices. This class is utilized to display custom error messages related to price.
 */

package application;

public class InvalidPriceException extends Exception {
	private static final long serialVersionUID = 2000L;
	public InvalidPriceException() {
		super();
	}
}
