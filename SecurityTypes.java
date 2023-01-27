/*
 * Aaron Parker
 * 
 * The SecurityTypes class serves as a base class for different stock types, and therefore,
 * will set the methods and attributes for them. 
 * 
 * There will be no object instantiated of "SecurityTypes," the only purpose is to serve as reusable code for
 * all children classes.  Every child class will have a name, abbreviated name (ticker symbol), purchase price,
 * and a price per share.
 */

package application;


public abstract class SecurityTypes implements java.io.Serializable {
	
	// abstract class to serve as a base class for all securities

	protected String name;
	protected String abbreviatedName; // 4 characters, no special characters or digits allowed, must be capitalized
	protected double purchasePrice;
	protected double pricePerShare;
	
	private static final long serialVersionUID = 1L;
	
	public void setName(String newName) throws InvalidNameException { // we want to throw spe
		assert newName != null: "Null String input.";
		if(newName.isEmpty() || newName.isBlank()) { 
			throw new InvalidNameException();
		}
		name = newName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setAbbreviatedName(String newName) throws InvalidNameException {
		assert newName != null: "Null string input for Abbreviation.";
		if(newName.isBlank() || newName.isEmpty())
			throw new InvalidNameException();
		
		newName = newName.toUpperCase();
		if(newName.length() > 4) { // abbreviated name character limit
			throw new InvalidNameException();
		}
		
		for(int i = 0; i < newName.length(); i++) {
			char temp = newName.charAt(i); // grab the string at the index
			//System.out.printf("Char at {%d}: %c\n",i, temp); // debug print
			if(!Character.isAlphabetic(temp)) {
				throw new InvalidNameException();
			}
		}
		//If we reach this point, it should be a valid abbreviated name.
		abbreviatedName = newName;
		
	}
	
	public String getAbbreviatedName() {
		return abbreviatedName;
	}
	
	public void setPurchasePrice(double price) throws InvalidPriceException {
		if(price < 0.00 || price > Double.MAX_VALUE) 
			throw new InvalidPriceException();
		
		purchasePrice = price;
		
	}
	
	public double getPurchasePrice() {
		return purchasePrice;
	}
	
	public void setPricePerShare(double price) throws InvalidPriceException {
		if(price < 0.00 || price > Double.MAX_VALUE)
			throw new InvalidPriceException();
		
		pricePerShare = price;
	}
	
	public double getPricePerShare() {
		return pricePerShare;
	}
	
	public abstract String toString();
	public abstract void printInfo();
	public abstract double getNumOwned();
	public abstract void setNumOwned(int amt) throws InvalidPriceException;
	public abstract SecurityType getType();
	
	public int compareTo(SecurityTypes other) {
		//Used to sort in alphabetical order by ticker symbol
		String thisName = this.getAbbreviatedName();
		String otherName = other.getAbbreviatedName();
		if(thisName.compareTo(otherName) > 0) {
			return 1;
		} else if(thisName.compareTo(otherName) < 0) {
			return -1;
		} else {
			return 0;
		}
	}

}
