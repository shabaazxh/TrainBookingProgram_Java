package TrainBookingProgram;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TrainBooking {
	
	static final int SEATING_CAPACITY = 8;//Max number of seats in the train

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		String [] Train = new String[SEATING_CAPACITY];//Create a new arraylist with the capacity of 8
		runBookingInstance(Train); //initialise the array to be empty
		
		//Introducing + Main menu inputs
		mainMenu(); //runs the mainMenu method showing all possible menu options the user can do
		Scanner selectMenu = new Scanner(System.in); //Allow user to select which option they would like to use
		String chosenFromMenu; //Store the users input to perform checks with later

		
		
		do { //run the following code - do loop used to allow user to keep selecting through the menu when using the program to check updated array
			
			chosenFromMenu = selectMenu.nextLine().toUpperCase(); //Storing the userinput inside variable names chosenFromMenu
			
			switch(chosenFromMenu) { //Switch case used to handle users input for each feature, switch based on their input
			case "E": //displays empty seats only
				emptySeats(Train);
				break;
			case "V": //case V lets you view all  seats
				ViewAllSeats(Train);//Calls the ViewAllSeats method
				break;
			case "A": //case A lets you see all available seats and book them
				try {
					emptySeats(Train);//calls empty seats method
					addCustomerToSeat(Train);//Calls the addcustomer method
				}
				
				catch(InputMismatchException e){
					System.out.println("You did not enter a seat number, please try again.");
					
				}
				break;
			case "F":
				findCustomerSeat(Train);//Calls the findCustomerSeat method
				break;
			case "O":
				bubbleSortAlpha(Train);// Calls the bubblesort alphabetical method to sort names alphabetically 
				break;
			case "D":
				deleteCustomerSeat(Train);//Calls the deleteCustomerSeat method
				break;
			case "Q":
				System.out.println("Thanks for using Train Booking, your session has been terminated.");//This terminates the program
				System.exit(0);//exits the program
				break;
			case "S"://Calls the writeToFile function allowing user to write to a file
				try {
					writeToFile(Train);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "L"://Calls the readFromFile function which allows user to read a file
				try {
					readFromFile(Train);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Could not find the file");
				}
				break;
			default:
				System.out.println("This is not a valid input"); //Default used just in case the user types something in wrong
				break;
			} 
		}while(chosenFromMenu != "Q"); //continue the do loop while "Q" for quit is not pressed
		}
		

					
		
		
	
	
	
	private static void mainMenu() { //Method created to hold all the intro print out lines to prevent having code pile up at the top
		System.out.println("Welcome to the Train Booking program. Please select one of the following options to begin.");
		System.out.println("'V' - to View all seats.");
		System.out.println("'A' - to book a customer.");
		System.out.println("'E' - to display empty seats.");
		System.out.println("'D' - to delete a customers seat.");
		System.out.println("'F' - to Find a customers seat.");
		System.out.println("'S' - to store Train booking data.");
		System.out.println("'L' - to Load Train Booking from file.");
		System.out.println("'O' - to View customers seats in alphabetical order.");
		System.out.println("'Q' - to Quit the program.");
		
	}
	
	private static void bubbleSortAlpha(String [] TrainData) { //Method to sort the names inside the array into alphabetical order
		String temp; //creating a temp variable of type String to hold the name during swap 
		System.out.println("Names sorted in order: ");//print names sorted in order to the console
		
		
		for (int i =0; i<TrainData.length; i++) {  //for loop to run through the array from beginning starting at 0
			for(int j = i + 1; j<TrainData.length; j++) { //another for loop to run from + 1 of the first loop in order to compare values
				if(TrainData[i].compareTo(TrainData[j]) > 0) { //checking values, ".compareTo" compares the 2 strings and if a positive integer is returned, the names are no in order thus a swap is initiated
					temp = TrainData[i]; //take the first name held inside [i] and store it inside temp variable
					TrainData[i] = TrainData[j];// [i] name is now = to the name held inside [j]
					TrainData[j] = temp; // and now the value that was inside temp is not = [j] thus swapping the values
				}
			}
			System.out.println(TrainData[i]); //display sorted names to the console
		}
		
	}

	private static void readFromFile(String [] TrainArray) throws FileNotFoundException { //Read from a file, line by line
		System.out.println("Where is the file located? - For example (/Users/shabaaz/eclipse-workspace/Program/output.txt)");
		
		Scanner fileLocation = new Scanner(System.in); //allow user to enter the location of the file
		String typedFileLocation = fileLocation.nextLine();//store the users input/filePath into a variable named typedFileLocation
		
		File file = new File(typedFileLocation); //creating a new file object which takes in the users input/filePath
		try { //try block used to catch any exceptions such as file not found
			Scanner readFile = new Scanner(file); //Create a new scanner object in order to read the file
			
			while(readFile.hasNextLine()) { //while loop used to read through the lines, while there is another line...
				for(int i = 0; i<TrainArray.length; i++) { //run for loop if above condition is met
					TrainArray[i] = readFile.nextLine();//the index of the array the loop is current on is = to the line in the file
				}
				
			}
			
			
		} catch (FileNotFoundException e) { //handle if the file cannot be found
			System.out.println("Sorry, the file could not be found, please try again.");//display error message to user
		}
		
		catch(NoSuchElementException e) { //handle if elements in file is not the same size as the array
			System.out.println("The file you opened is does not fill the elements of the bookings, please fix the file or try another.");//display error message to user
		}
		
		
		
	}
	
	private static void writeToFile (String [] TrainContents) throws FileNotFoundException { //Write what the user types into a file
		System.out.println("Written to file.");//Message to display that the function is being run
		int seatingno = -1;//printing the seat no along with the name (start from -1 so that the first seat is 0)
		
		try { //try block used to allow the program to try and make the file and read it
			FileWriter fr = new FileWriter("storing.txt");//Creates a new  file called "storing.txt" to store array data
			BufferedWriter br = new BufferedWriter(fr);//New buffered writer created to allow us to write into the storing.txt file
			PrintWriter out = new PrintWriter(br);//print writer used to write output to the file 
			for(int i = 0; i<TrainContents.length; i++) { //for loop created to loop through the array and find the elements
					seatingno++;//increment the counter by 1 each time loop is run to show seat no
					out.write(TrainContents[i]);//write the seat number and the person whom it belongs to to the file
					out.write("\n");//create a new line
				}
				out.close();//close the file once done looping
		}
		catch(IOException e) {//Error handling exception
			System.out.println("Error!!" + e);
		}
}
	
	//Deleting a customer from the array (Train)//
	private static void deleteCustomerSeat(String deleteCustomers[]) {
		System.out.println("Enter customer first and second name to delete: "); //Display user to allow them to understand how to delete customers
		Scanner deleteCustomer = new Scanner (System.in);// Allow user input to let them type the name of the customer they wish to delete
		String deleteCustomerSeating = deleteCustomer.nextLine();//Store the name input provided 
		
		for (int d = 0; d<deleteCustomers.length; d++) { //loop through the Train Array 
			if(deleteCustomerSeating.equals(deleteCustomers[d])) {//Check to see if the name typed in is present within the Train Array already
				deleteCustomers[d] = "e";//if it is present within the array, replace their name with "e" to make it a empty seat
				System.out.println("Customer has been deleted from seat " + d);//Confirm to the user that the customer has been deleted and from which seat
				//System.out.println(Arrays.toString(deleteCustomers));//print out the new Train array once customer is deleted from the Train Array
				
			}
		}
		
	}
	
	//Book customer a seat, customer can book the seat and enter name, this will add their name to the index of the array
	private static void addCustomerToSeat(String addToTrainSeats[]) {
		
		int chosenSeat; //the selected seat by the user. Declared at top to allow use throughout
		
			System.out.println("Enter which seat to take: ");//Print this out to allow user to choose seating
			Scanner seatSelect = new Scanner(System.in);//create a new input to allow the user to type and select seating
			chosenSeat = seatSelect.nextInt();//store their input
			
			
			try { //try to do the following code - happens if user input is valid
				for (int z = 0; z<addToTrainSeats.length; z++) { //loop through the train booking array
					if(!addToTrainSeats[chosenSeat].equals("e")) {//if the chosen seat is not equal to "e", meaning its empty
						System.out.println("Sorry this seat is not avaialble at the moment, please try again.");//display the seat is not empty
						return;//end the program
						
					}
					
					else {
						System.out.println("Booking seat: " + chosenSeat);//display the seat they chose to book and mark as booked
						System.out.println("Name: ");//Ask user to enter their name
						Scanner FirstName = new Scanner(System.in);//Allow user to enter their name to add their name into the index in the array
						String customerName = FirstName.nextLine();//Store their name that they typed
						System.out.println("Thank you " + customerName + "," + " seat " + chosenSeat + " has been booked for you.");//Print out Thank you message and what seat number
						customerName.toLowerCase();//make their name lowercase - makes it easier when doing checks later or deleting customers
						addToTrainSeats[chosenSeat]= customerName;//Finally add their name into the Array of seats in the Train
						//System.out.println(Arrays.toString(Train));
						return;
					}
					
				} 
				
				
				
			} 
			catch (ArrayIndexOutOfBoundsException exception) { //if user enters seat number greater than the array length, error is caught and handled 
				System.out.println("This is not a seat we offer, try again.");//display this error message to the user
			}

		return;
	
			
	}
					
	
		
				
		
	
	//Find the customers seat based on Name
	private static void findCustomerSeat(String findCustomers[]) {
		System.out.println("Customer Name (Please enter first and second name respectively: ");
		Scanner findCustSeat = new Scanner(System.in);
		String storeFindSearch = findCustSeat.nextLine();
		
		for (int f = 0; f<findCustomers.length; f++) {
			if(storeFindSearch.equals(findCustomers[f])) {//check to see if the customer is inside the Array
				System.out.println("Customer " + storeFindSearch + " is in seat no: " + f);//if customer is inside the array then display their seat number
				return;//stop the program from printing anything further if condition is met
			}
			
		}
		
		System.out.println("Could not find this customer"); //If the customer cannot be find within the array, this message is printed to the screen
		return;//stop the program from continuing 
		
	}
	//Empty all seats and add Mary Drew in the first index of the Array
	private static void runBookingInstance(String TrainSeats[]) {
		for (int x = 0; x < TrainSeats.length; x++) { //loop through the array, and replace all elements with "e" test for empty seats later
			TrainSeats[x] = "e";//replace each element with "e"
		}
		
		//TrainSeats[1] = "Mary Drew";//Add the name Mary Drew to index 1 in the array
		//TrainSeats[2] = "Boe Drew";
		//TrainSeats[3] = "Alpha Drew";
		//TrainSeats[4] = "Charlie Drew";
		//TrainSeats[5] = "Darren Drew";
		
		
	}
	
	
	private static void emptySeats(String emptySeats[]) { //Create a method to display the empty seats in the train
		System.out.println("The following seats are currently available: "); //print out a message to the user letting them know these are the available seats
		for(int i = 0; i<emptySeats.length; i++) { //create a for loop to run through the array
			if(emptySeats[i].equals("e")) { //while looping find if the current index is "e" or not
				System.out.println("Seat " + i + " is currently available."); //if it is "e" then display that index as an empty seat
			}
		
		}
		
	}
	
	//Display all available seats and which ones are occupied 
	private static void ViewAllSeats(String TrainSeatsAvailable[]) {
		for (int j = 0; j<TrainSeatsAvailable.length; j++) { //loops through the array
			if(TrainSeatsAvailable[j].equals("e")) {//when looping, if an element within the array = "e", it will be displayed as an empty seat
				System.out.println("Seat " + j + " is available"); //print out the available seats
			}
			
			else {
				if(TrainSeatsAvailable[j] != "e") { //if when looping it comes across an index where the element is not an "e", this means the seat is taken
					System.out.println("Seat " + j + " is occupied by " + TrainSeatsAvailable[j]); //We print out the seat number and display who the seat is taken by
				}
			}
		}
	}
		
}

