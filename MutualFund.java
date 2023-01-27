/*
 * Aaron Parker
 * 
 * Mutual funds are basically just stocks, but they go off a specific index instead of a corporation
 * 
 */

package application;

public class MutualFund extends SecurityTypes implements java.io.Serializable {

	private static final SecurityType type = SecurityType.MUTUALFUND;
	private static final long serialVersionUID = 4L;
	private double amtOwned;
	
	public SecurityType getType() {
		return type;
	}
	
	public double getNumOwned() {
		return amtOwned;
	}
	
	@Override
	public void setNumOwned(int amt) throws InvalidPriceException {
		if(amt <= 0 || amt > Integer.MAX_VALUE)
			throw new InvalidPriceException();
		
		amtOwned = amt;
	}
	
	public void setNumOwned(double amt) throws InvalidPriceException {
		if(amt > 0 && amt < Double.MAX_VALUE) {
			amtOwned = amt;
		} else {
			throw new InvalidPriceException();
		}
	}
	
	@Override
	public String toString() {
		return "Mutual Fund: " + abbreviatedName + " - $" + pricePerShare;
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
	
	public MutualFund(String name, String aName, double perShare) {
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
	
	public MutualFund(String name, String aName, double purchase, double perShare) {
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
	
	// The methods above are used to generate the example file, this is used in the program.
	public MutualFund(String name, String aName, double purchase, double perShare, double numOwned) throws InvalidNameException, InvalidPriceException {

		setName(name);
		setAbbreviatedName(aName);
		setPurchasePrice(purchase);
		setPricePerShare(perShare);
		setNumOwned(numOwned);
		
	}
}
