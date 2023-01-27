/*
 * Aaron Parker
 * 
 * From what I understand, CDs are certificates of deposit, and they have a requirement of only having a purchasePrice of 1 per project guidelines
 * 
 */

package application;

public class CD extends SecurityTypes implements java.io.Serializable{

	private static final SecurityType type = SecurityType.CD;
	private static final long serialVersionUID = 5L;
	private int purchasePrice = 1;
	private int amtOwned;
	
	public SecurityType getType() {
		return type;
	}
	
	public double getNumOwned() {
		return amtOwned;
	}
	
	public void setNumOwned(int amt) throws InvalidPriceException {
		if(amt >= 0 && amt < Integer.MAX_VALUE) {
			amtOwned += amt;
		} else {
			throw new InvalidPriceException();
		}
	}

	public void setPurchasePrice(double amt) {
		purchasePrice = 1;
	}
	
	public double getPurchasePrice() {
		return (double)purchasePrice;
	}
	
	@Override
	public String toString() {
		return abbreviatedName + " - $" + pricePerShare + " CD ";
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
	
	public CD(String name, String aName, double purchase, double perShare) {
		super();
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
	
	public CD(String name, String aName, double purchase, double perShare, int numOwned) throws InvalidNameException, InvalidPriceException {

			setName(name);
			setAbbreviatedName(aName);
			setPurchasePrice(purchase);
			setPricePerShare(perShare);
			setNumOwned(numOwned);
	}

}
