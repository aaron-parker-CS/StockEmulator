/*
 * Aaron Parker
 * 
 * Stock with a dividend, a dividend stock if you will.
 */

package application;

public class DividendStock extends Stock implements java.io.Serializable {
	
	private static final SecurityType divType = SecurityType.DIVIDENDSTOCK;
	private static final long serialVersionUID = 3L;
	private double dividendAmt;
	
	public SecurityType getType() {
		return divType;
	}
	
	public double getDividend() {
		return dividendAmt;
	}
	
	public void setDividend(double amt) throws InvalidPriceException {
		if(amt > 0 && amt < Double.MAX_VALUE) {
			dividendAmt = amt;
		} else {
			throw new InvalidPriceException();
		}
	}
	
	@Override
	public String toString() { // Ticker, Price per Share / Quarterly Dividend
		return abbreviatedName + " - $" + pricePerShare + " / $" + dividendAmt + " quarterly.";
	}
	
	@Override
	public void printInfo() {
		// print all relevant stock information into the console
		System.out.println("Type: " + getType());
		System.out.println("Name: " + getName());
		System.out.println("Ticker Symbol: " + getAbbreviatedName());
		System.out.println("Purchase Price: $" + getPurchasePrice());
		System.out.println("Price per Share: $" + getPricePerShare());
		System.out.println("Shares owned: " + getNumOwned());
		System.out.println("Dividend per Quarter: " + getDividend());
	}
	
	
	public DividendStock(String name, String aName, double purchase, double perShare, double dividend, int numOwned) throws InvalidNameException, InvalidPriceException{
		
		super( name,  aName,  purchase,  perShare,  numOwned);
		setDividend(dividend);
	
	}
	
	// The following constructors are only used to generate the example file.

	public DividendStock(String name, String aName, double perShare, double dividend) {
		super(name, aName, perShare);
		try {
			setDividend(dividend);
		} catch(InvalidPriceException e) {
			System.out.println("You have entered an invalid dividend amount.");
			e.printStackTrace();
		}
	}
	
	public DividendStock(String name, String aName, double purchase, double perShare, double dividend) {
		super(name, aName, purchase, perShare);
		try {
			setDividend(dividend);
		} catch(InvalidPriceException e) {
			System.out.println("You have entered an invalid dividend amount.");
			e.printStackTrace();
		}
	}

}
