/*
 * Aaron Parker
 * 
 * Stock is used to store security types, we can specify an amount owned or not.
 * 
 */

package application;

public class Stock extends SecurityTypes implements java.io.Serializable {
	
	private static final SecurityType type = SecurityType.STOCK;
	private static final long serialVersionUID = 2L;
	protected int numSharesOwned;
	
	public SecurityType getType() {
		return type;
	}

	public double getNumOwned() {
		return numSharesOwned;
	}
	@Override
	public void setNumOwned(int amt) throws InvalidPriceException {
		if(amt >= 0 && amt < Integer.MAX_VALUE) {
			numSharesOwned += amt;
		} else {
			throw new InvalidPriceException();
		}
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
	}

	@Override
	public String toString() {
		return abbreviatedName + " - $" + pricePerShare; // ticker symbol plus price
	}
	
	public Stock(String name, String aName, double perShare) {
		try {
			setName(name);
			setAbbreviatedName(aName);
			setPurchasePrice(perShare);
			setPricePerShare(perShare);
		} catch (InvalidNameException e) {
			System.out.println("You have entered an invalid name.");
			e.printStackTrace();
		} catch (InvalidPriceException e) {
			System.out.println("You have entered an invalid share price or amount.");
			e.printStackTrace();
		}
	}
	
	public Stock(String name, String aName, double purchase, double perShare) {
		// constructor not specifying the amount of a stock owned
		assert name != null;
		numSharesOwned = 0;
		try {
			setName(name);
			setAbbreviatedName(aName);
			setPurchasePrice(purchase);
			setPricePerShare(perShare);
		} catch (InvalidNameException e) {
			System.out.println("You have entered an invalid name.");
			e.printStackTrace();
		} catch (InvalidPriceException e) {
			System.out.println("You have entered an invalid share price or amount.");
			e.printStackTrace();
		}
	}
	
	public Stock() {
		try{
			setName("NONE");
			setAbbreviatedName("NONE");
			setPurchasePrice(0);
			setPricePerShare(0);
			setNumOwned(0);
		} catch (InvalidNameException err) {
			err.printStackTrace();
		} catch (InvalidPriceException err) {
			err.printStackTrace();
		}
	}
	
	public Stock(String name, String aName, double purchase, double perShare, int numOwned) throws InvalidNameException, InvalidPriceException {
		// constructor that specifies the amount of an owned stock.
//		assert (name != null);
//		try {
			setName(name);
			setAbbreviatedName(aName);
			setPurchasePrice(purchase);
			setPricePerShare(perShare);
			setNumOwned(numOwned);
//		} catch (InvalidNameException e) {
//			System.out.println("You have entered an invalid name.");
//			e.printStackTrace();
//		} catch (InvalidPriceException e) {
//			System.out.println("You have entered an invalid share price or amount.");
//			e.printStackTrace();
//		} catch (Exception e) {
//			System.out.println("An error occurred.");
//		}
	}
}
