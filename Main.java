/*
 * 
 * Aaron Parker
 * 
 * CPS 255 Final project, part 3
 * 
 * The purpose of this final part of the project is to make a fully functional GUI program to display somewhat
 * of an emulated stock portfolio. Now what we give the user rights to do would make the SEC's job a living
 * nightmare, but the user will be able to Edit, Save, Add, and scroll through stocks in their portfolio. 
 * Their portfolio will be read from a filename, "securities.ser" in which we will keep all relevant info
 * to make the project run. The first entry will be how many stocks are in the portfolio, followed by the 
 * securitytype objects stored within. The application will save when exiting, and automatically sort the stocks.
 * 
 */

package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Main extends Application {
	
	// We declare this first so that we can show all errors to the user
	Alert errorAlert = new Alert(AlertType.ERROR);
	
	public static void sortArray(ArrayList<SecurityTypes> arr, int n){
		//Recursive insertion sort based on lexographical order
		if(n <= 1)
			return;
		
		sortArray(arr, n-1);
		
		SecurityTypes last = arr.get(n - 1);
		int j = n - 2;
		
		while(j >= 0 && arr.get(j).compareTo(last) > 0) {
			arr.set(j+1, arr.get(j));
			j--;
		}
		arr.set(j+1, last);
		
	}
	
	public static void writeObjects(ArrayList<SecurityTypes> arr, String fileName) {
		try {
			// Create the new .ser file and open an output stream
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			// the first # in the file says how many objects are contained in it
			out.write(arr.size());
			for(int i = 0; i < arr.size(); i++) {
				// write each object into the file
				out.writeObject(arr.get(i));
			}
//			System.out.println("Array saved to securities.ser");
			// close the streams
			out.close();
			fileOut.close();
		} catch (IOException error) {
			Alert fileAlert = new Alert(AlertType.ERROR);
			fileAlert.setContentText("ERROR: ERROR WRITING FILE!");
			fileAlert.showAndWait();
			error.printStackTrace();
		}
	}
	
	public static ArrayList<SecurityTypes> createFile(String fileName) {
		ArrayList<SecurityTypes> defaultList = new ArrayList<SecurityTypes>();
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.write(1);
			Stock defaultStock = new Stock();
			out.writeObject(defaultStock);
			defaultList.add(defaultStock);
			out.close();
			fileOut.close();
		} catch (IOException error) {
			Alert fileAlert = new Alert(AlertType.ERROR);
			fileAlert.setContentText("ERROR: ERROR WRITING FILE!");
			fileAlert.showAndWait();
			error.printStackTrace();
		}
		return defaultList;
	}
	
	public static void createDefaultFile() {
		// hardcoded stocks, we can make a method to have the user choose or input their own data later per guidelines
		DividendStock myStock1 = new DividendStock("Apple Corporation", "AAPL", 219, 300, 24);
		Stock myStock2 = new Stock("Testla Corporation", "TEST", 25);
		MutualFund myStock3 = new MutualFund("Vanguard SPY MutualFund", "QQQ", 300, 400);
		CD myStock4 = new CD("Bourgeois Incorporated", "BANK", 1, 250);
		Stock myStock5 = new Stock("NetFlex Exercise Corporation", "NF", 50.4);
		Stock myStock6 = new Stock("Bungo Corporation", "BUNG", 67.5);
		MutualFund myStock7 = new MutualFund("InvesCo S&P 500 Index", "ISPF", 1000.4);
		DividendStock myStock8 = new DividendStock("Whetzel Pretzel", "WP", 28.4, 2.50);
					
		ArrayList<SecurityTypes> myStocks = new ArrayList<SecurityTypes>();
		myStocks.add(myStock1);
		myStocks.add(myStock2);
		myStocks.add(myStock3);
		myStocks.add(myStock4);
		myStocks.add(myStock5);
		myStocks.add(myStock6);
		myStocks.add(myStock7);
		myStocks.add(myStock8);
					
		sortArray(myStocks, myStocks.size()); // sort the array based on alphabetical ticker symbol
					
		writeObjects(myStocks, "securities.ser"); // write the stocks into the file
	}
	
	public static ArrayList<SecurityTypes> readFile(String fileName){
		ArrayList<SecurityTypes> stockList = new ArrayList<SecurityTypes>();
		//createFile(); // method to create the file needed for the following code, unneeded if the file already exists.
		try {
			// open the existing file to read serialized data, if this isn't found, we make a new one.
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			// the first entry in the .ser is the range of the objects
			int range = in.read();
			for(int i = 0; i < range; i++) {
				stockList.add((SecurityTypes)in.readObject());
			}
			in.close();
			fileIn.close();
		} catch (FileNotFoundException err) {
			Alert fileAlert = new Alert(AlertType.ERROR);
			fileAlert.setContentText("ERROR: File not found! Creating a blank file.");
			fileAlert.showAndWait();
			stockList = createFile("securities.ser");
			err.printStackTrace();
		} catch (IOException err) {
			Alert fileAlert = new Alert(AlertType.ERROR);
			fileAlert.setContentText("ERROR: ERROR READING FILE!");
			fileAlert.showAndWait();
			err.printStackTrace();
		} catch (ClassNotFoundException err) {
			Alert fileAlert = new Alert(AlertType.ERROR);
			fileAlert.setContentText("ERROR: Class error!");
			fileAlert.showAndWait();
			err.printStackTrace();
		}
		
		return stockList;
	}
	
	public static int readRange(String fileName){
		//createFile(); // method to create the file needed for the following code, unneeded if the file already exists.
		try {
			// open the existing file to read serialized data, if this isn't found, we make a new one.
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			// the first entry in the .ser is the range of the objects
			int range = in.read();
			in.close();
			fileIn.close();
			return range;
		} catch (FileNotFoundException err) {
			Alert fileAlert = new Alert(AlertType.ERROR);
			fileAlert.setContentText("ERROR: File not found!");
			fileAlert.showAndWait();
			err.printStackTrace();
		} catch (IOException err) {
			Alert fileAlert = new Alert(AlertType.ERROR);
			fileAlert.setContentText("ERROR: ERROR READING FILE!");
			fileAlert.showAndWait();
			err.printStackTrace();
		}
		
		return -1;
	}
	
	public static void disableFields(LinkedList<TextField> list) {
		for(TextField item : list) {
			item.setEditable(false);
		}
	}
	
	public static void enableFields(LinkedList<TextField> list) {
		for(TextField item : list) {
			item.setEditable(true);
		}
	}
	/*
	 * CREATE GLOBALS:
	 */
	
	ArrayList<SecurityTypes> stockList = readFile("securities.ser");
	int stockRange = readRange("securities.ser");
	int currentPos = 0;
	/*
	 * These booleans are used to control the states of the program, whether or not 
	 * we are making a new entry to the list, or are editing.
	 */
	Boolean newEntry = false;
	Boolean isEditing = false;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//createFile();
			System.out.println(stockList.toString());
			// create all the needed buttons
			Button leftArrow = new Button("<<");
			Button rightArrow = new Button(">>");
			Button saveBtn = new Button("Save");
			Button addBtn = new Button("Add");
			Button rmBtn = new Button("Remove");
			Button editBtn = new Button("Edit");
			
			// create all the needed labels
			Label nameLabel = new Label("Name:");
			Label typeLabel = new Label("Type:");
			Label tickerLabel = new Label("Ticker Symbol:");
			Label priceLabel = new Label("Current Price/Share:");
			Label purchaseLabel = new Label("Purchase price:");
			Label ownedLabel = new Label("Shares owned:");
			Label divLabel = new Label("Dividend amount:");
			
			// create the combobox for the security type, i found making an enumerator for the class types made it convenient.
			final ComboBox<SecurityType> typeBox = new ComboBox<SecurityType>();
			typeBox.getItems().addAll(
					SecurityType.STOCK,
					SecurityType.DIVIDENDSTOCK,
					SecurityType.MUTUALFUND,
					SecurityType.CD
					);
			typeBox.setDisable(true);
			typeBox.setValue(stockList.get(currentPos).getType());
			
			// create the text fields and set the default, which is position 0 of the arraylist.
			// by default, the first (Alphabetical by name of company) stock will be displayed in the fields.
			// we're using text fields because we anticipate that the user will be able to edit these values.
			TextField nameField = new TextField();
			nameField.setEditable(false);
			nameField.setText(stockList.get(currentPos).getName());
			
			TextField tickerField = new TextField();
			tickerField.setEditable(false);
			tickerField.setText(stockList.get(currentPos).getAbbreviatedName());
			
			TextField priceField = new TextField();
			priceField.setEditable(false);
			priceField.setText(Double.toString(stockList.get(currentPos).getPricePerShare()));
			
			TextField purchaseField = new TextField();
			purchaseField.setEditable(false);
			String purchaseText = Double.toString(stockList.get(currentPos).getPurchasePrice());
			if(purchaseText != null)
				purchaseField.setText(purchaseText);
			
			TextField ownedField = new TextField();
			ownedField.setEditable(false);
			String ownedText = Double.toString(stockList.get(currentPos).getNumOwned());
			if(ownedText != null)
				ownedField.setText(ownedText);
			
			TextField divField = new TextField();
			divField.setEditable(false);
			
			// we make a list of the fields to make bulk disabling/enabling editing easier via refactoring
			LinkedList<TextField> fieldList = new LinkedList<TextField>();
			fieldList.add(nameField);
			fieldList.add(tickerField);
			fieldList.add(priceField);
			fieldList.add(purchaseField);
			fieldList.add(ownedField);
			fieldList.add(divField);
			
			// the only way i found to get the specific dividend
			try {
				// cast the object to a dividend stock, this will throw a ClassCastException if the security isn't a dividendstock,
				// this lets us set the divField to be disabled and read "N/A".
				DividendStock defaultDiv = (DividendStock) stockList.get(currentPos);
				String divText = Double.toString(defaultDiv.getDividend());
				divField.setText(divText);
			} catch (ClassCastException err) {
				divField.setDisable(true);
				divField.setText("N/A");
			}
			
			// the center box to hold all of the information labels and fields.
			VBox stockInfoBox = new VBox(10, nameLabel, nameField, typeLabel, typeBox, tickerLabel, tickerField,
					priceLabel, priceField, purchaseLabel, purchaseField, ownedLabel, ownedField,
					divLabel, divField);
			Insets padding = new Insets(5,5,5,5);
			stockInfoBox.setPadding(padding);
			
			// the button bar to go on the bottom or top or however we decide to orient
			HBox buttonBar = new HBox(10, leftArrow, saveBtn, addBtn, editBtn, rmBtn, rightArrow);
			buttonBar.setPadding(padding);
			buttonBar.setAlignment(Pos.CENTER);
			
			// button methods:
			
			leftArrow.setOnAction(e -> {
				// decrement, and then wrap around to the end of the list if the current value is 0
				currentPos--;
				if(currentPos < 0) {
					// -1 to make sure we get a valid index
					currentPos = stockRange - 1;
				} 
				nameField.setText(stockList.get(currentPos).getName());
				typeBox.setValue(stockList.get(currentPos).getType());
				tickerField.setText(stockList.get(currentPos).getAbbreviatedName());
				priceField.setText(Double.toString(stockList.get(currentPos).getPricePerShare()));
				String purchase = Double.toString(stockList.get(currentPos).getPurchasePrice());
				if(purchase != null)
					purchaseField.setText(purchase);
				String owned = Double.toString(stockList.get(currentPos).getNumOwned());
				if(owned != null)
					ownedField.setText(owned);
				try{
					DividendStock updateDiv = (DividendStock) stockList.get(currentPos);
					String div = Double.toString(updateDiv.getDividend());
					divField.setText(div);
					divField.setDisable(false);
				} catch (ClassCastException err) {
					divField.setDisable(true);
					divField.setText("N/A");
				}
			});
			
			rightArrow.setOnAction(e -> {
				currentPos++;
				if(currentPos > stockRange - 1) {
					currentPos = 0;
				} 
				// just updating the fields
				nameField.setText(stockList.get(currentPos).getName());
				typeBox.setValue(stockList.get(currentPos).getType());
				tickerField.setText(stockList.get(currentPos).getAbbreviatedName());
				priceField.setText(Double.toString(stockList.get(currentPos).getPricePerShare()));
				String purchase = Double.toString(stockList.get(currentPos).getPurchasePrice());
				if(purchase != null)
					purchaseField.setText(purchase);
				String owned = Double.toString(stockList.get(currentPos).getNumOwned());
				if(owned != null)
					ownedField.setText(owned);
				try{
					DividendStock updateDiv = (DividendStock) stockList.get(currentPos);
					String div = Double.toString(updateDiv.getDividend());
					// if we have valid values, we can enable and display the appropriate vales
					divField.setDisable(false);
					divField.setText(div);
				} catch (ClassCastException err) {
					divField.setDisable(true);
					divField.setText("N/A");
				}
			});
			
			saveBtn.setOnAction(e -> { // yes i know this is a very long lambda, i couldn't figure out how to refactor it better without making everything global
				if(newEntry == true) {
					SecurityType currentValue = typeBox.getValue();
					try {
						// get the values from the fields to construct the object
						String name = nameField.getText();
						String ticker = tickerField.getText();
						double price = Double.parseDouble(purchaseField.getText());
						double perShare = Double.parseDouble(priceField.getText());
						int owned = Integer.parseInt(ownedField.getText());
						switch(currentValue) { // use the enum to select the appropriate object to create and add it to the stock list
							case STOCK:
								Stock newStock = new Stock(name, ticker, price, perShare, owned);
								stockList.add(newStock);
								break;
							case DIVIDENDSTOCK:
								double div= Double.parseDouble(divField.getText());
								DividendStock newDividend = new DividendStock(name, ticker, price, perShare, div, owned);
								stockList.add(newDividend);
								break;
							case MUTUALFUND:
								double mutualOwned = Double.parseDouble(ownedField.getText());
								MutualFund newMutual = new MutualFund(name, ticker, price, perShare, mutualOwned);
								stockList.add(newMutual);
								break;
							case CD:
								CD newCD = new CD(name, ticker, 1, 1, owned);
								break;
						}
					} catch(NullPointerException err) {
						errorAlert.setContentText("You have entered a null value, ensure you enter valid values for all fields.");
						errorAlert.showAndWait();
						return;
					} catch (NumberFormatException err) {
						errorAlert.setContentText("You have entered an invalid number, ensure you enter valid values for all fields.");
						errorAlert.showAndWait();
						return;
					} catch (InvalidNameException err) {
						errorAlert.setContentText("You have entered an invalid name, ensure you enter valid values for all fields."
								+ " Valid names contain characters A-Z and are at max 4 characters.");
						errorAlert.showAndWait();
						return;
					} catch (InvalidPriceException err) {
						errorAlert.setContentText("You have entered an invalid price, ensure you enter valid values for all fields.");
						errorAlert.showAndWait();
						return;
					}
					// and reset the state of the program
					newEntry = false;
				}
				
				if(isEditing) {
						SecurityType currentValue = typeBox.getValue();
						try {
							stockList.get(currentPos).setName(nameField.getText());
							stockList.get(currentPos).setAbbreviatedName(tickerField.getText());
							double price, purchase, mutualOwned;
							int owned;
							price = Double.parseDouble(priceField.getText());
							purchase = Double.parseDouble(purchaseField.getText());
							stockList.get(currentPos).setPricePerShare(price);
							stockList.get(currentPos).setPurchasePrice(purchase);
							if(currentValue == SecurityType.MUTUALFUND) {
								mutualOwned = Double.parseDouble(ownedField.getText());
								MutualFund currentFund = (MutualFund) stockList.get(currentPos); // cast to call .setNumOwned
								currentFund.setNumOwned(mutualOwned);
							} else {
								owned = (int) Double.parseDouble(ownedField.getText());
//								System.out.println(owned);
								stockList.get(currentPos).setNumOwned(owned);
							}
							
							if(currentValue == SecurityType.DIVIDENDSTOCK) {
								double dividend = Double.parseDouble(divField.getText());
								DividendStock currDiv = (DividendStock) stockList.get(currentPos);
								currDiv.setDividend(dividend);
							}
						} catch (InvalidNameException error) {
							errorAlert.setContentText("ERROR: You have entered an invalid name!");
							errorAlert.showAndWait();
							error.printStackTrace();
							return;
						} catch (InvalidPriceException error) {
							errorAlert.setContentText("ERROR: You have entered an invalid price! Ensure you are entering values"
									+ "that are greater than zero and less than " + Double.MAX_VALUE);
							errorAlert.showAndWait();
							error.printStackTrace();
							return;
						} catch (Exception err) { 
							errorAlert.setContentText("Ensure you are entering only valid numbers in the price fields.");
							errorAlert.showAndWait();
							err.printStackTrace();
							return;
						} 
						isEditing = false;
					}
				
				// save, disable editing, and re-enable buttons
				disableFields(fieldList);
				sortArray(stockList, stockList.size());
				writeObjects(stockList, "securities.ser");
				stockList = readFile("securities.ser");
				stockRange = readRange("securities.ser");
				leftArrow.setDisable(false);
				rightArrow.setDisable(false);
				typeBox.setDisable(true);
				editBtn.setDisable(false);
				rmBtn.setDisable(false);
				addBtn.setDisable(false);
			});
			
			rmBtn.setOnAction(e -> {
//				System.out.println("Removing current entry...");
				if(stockRange == 1) {
					errorAlert.setContentText("ERROR: You cannot delete the last security.");
					errorAlert.showAndWait();
					return;
				}
				stockList.remove(currentPos);
				writeObjects(stockList, "securities.ser");
				stockRange = readRange("securities.ser");
				rightArrow.fire();
			});
			
			addBtn.setOnAction(e -> {
				newEntry = true;
				leftArrow.setDisable(true);
				rightArrow.setDisable(true);
				rmBtn.setDisable(true);
				addBtn.setDisable(true);
				editBtn.setDisable(true);
				typeBox.setValue(SecurityType.STOCK);
				typeBox.setDisable(false);
				divField.setText("");
				nameField.setText("");
				tickerField.setText("");
				priceField.setText("");
				purchaseField.setText("");
				ownedField.setText("");
				addBtn.setDisable(true);
				enableFields(fieldList);
			});
			
			typeBox.setOnAction(e -> {
				// Disable the dividend field if any other type is selected, or enable if dividend is selected.
				if(typeBox.getValue() == SecurityType.DIVIDENDSTOCK) {
					divField.setDisable(false);
					divField.setEditable(true);
				} else {
					divField.setDisable(true);
					divField.setEditable(false);
				}
				if(typeBox.getValue() == SecurityType.CD) {
					purchaseField.setText("1");
					priceField.setText("1");
				}
			});
			
			editBtn.setOnAction(e -> {
				leftArrow.setDisable(true);
				rightArrow.setDisable(true);
				rmBtn.setDisable(true);
				addBtn.setDisable(true);
				editBtn.setDisable(true);
				enableFields(fieldList);
				isEditing = true;
			});
			
			// Debug Print
//			for(int i = 0; i < stockList.size(); i++) {
//				stockList.get(i).printInfo();
//			}
//			
			BorderPane root = new BorderPane();
			root.setCenter(stockInfoBox);
			root.setBottom(buttonBar);
			Scene scene = new Scene(root,350,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Stock emulator - CPS255 Final by Aaron Parker");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
//		System.out.println("Closing application, saving file...");
		sortArray(stockList, stockList.size());
		writeObjects(stockList, "securities.ser");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
