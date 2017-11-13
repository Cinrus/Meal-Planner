// Meal Planner 2.2
// Old out of date copy
import java.util.*;
import java.io.*; //Imports the serializable package so that objects can be saved.
import java.lang.*; //Imports the .isEmpty among other things.

//Meal Class
class Meal implements Serializable {
	private String name;
	
	public String getName() {
		return name;
	}//Close Method
		
	public void setName(String n) {
		name = n;
	}//Close method
}

//Help class that actually does everything.
class MealHelper implements Serializable { //Lets the objects in this class be serialized
	
	private ArrayList<Meal> MealsList = new ArrayList<Meal>();
	private static Scanner scanner = new Scanner(System.in);
	
	//Method searches for and automatically loads an existing MealsList array list.
	@SuppressWarnings("unchecked") 
	/*Suppresses the warning for the line "MealsList = (ArrayList<Meal>) one;" The warning exists because the compiler can't ever be sure that the object being cast is an Meal type arraylist. 
	As the object should ALWAYS be a Meal type arraylist and research has shown is no better way to write the code. As such a suppressed warning is called for. Testing at this stage 
	(loading and saving method implamentation) has show that the code works exactly as desired despite the warning.*/
	public void StartUp() {
		File savedMeals = new File("C:\\Java\\Meal Planner\\MealSaves.ser");
		if(savedMeals.exists() && !savedMeals.isDirectory()) { 
			try {
				FileInputStream fileStream = new FileInputStream("MealSaves.ser");
				ObjectInputStream nos = new ObjectInputStream(fileStream);
				Object one = nos.readObject(); // Holds the read obect
				MealsList = (ArrayList<Meal>) one; //Casts the object as an Array List of Meals and puts it in the MealsList variable.
				nos.close(); // Closing the file input stream
			}// End try
			catch (Exception ex) {
					ex.printStackTrace();
			}// End catch
		} //End if
		MainMenu();
	} // End method

	
	
	//Doing double spacing between methods to increase whitespace and to help readability.
	//Main Menu
	public void MainMenu() {
		boolean exitMain = true; // Previously named "exitLoop", but there are going to be multiple loops.
		/* Note the difference between =, ==, and string1.equals(string2).
		= sets variables.
		== checks Boolean equations
		string1.equals(string2) checks strings character by character.
		
		Side note: true is a state. True is not.
		*/
		while (exitMain) { // The while loop checks the condition first, then executes. Do While loops execute then check the condition to see if they should continue.
		// Note: while (exitMain == true) is the same as while (exitMain) or at least should be
		/* 	Note: Ben noted that all Boolean values in Java default to true. As a result it would be easier to just put while (true) and put in 'break' when I want to exit.
			I'm not sure if while (Boolean true) would create a boolean variable and set it to true by default. Would be worth testing. Otherwise the variable still has to be declared earlier
			even if it gets set to true by default.
		*/
			System.out.println("Welcome to Meal Planner. Please enter the number of what you would like to do next.");
			System.out.println("1 = Create new meal.");
			System.out.println("2 = Display list of all meals.");
			System.out.println("3 = Delete a meal.");
			System.out.println("4 = Edit a meal.");
			System.out.println("5 = Create meal plan.");
			System.out.println("6 = Exit");
			while(!scanner.hasNextInt()) { // Checks to make sure the input is an int.
			/* 
				.hasNext() gives a input for the user, but doesn't actually scan it. It's just checking true or false. ***Note that this is an input request INSIDE the while loop condition.
				.hasNextInt() will give the user an input and check to see if it is an int.
				The ! means opposite so the while loop keeps going while .hasNextInt is anything BUT an int.
				the .next (as seen below) scans through the input, but ultimately ignores it. In short it's like saying "Yes I recognize you gave me an input, but I don't care what it is I'm just using it to get to the next input."	
			*/
				scanner.next();
				System.out.println("That is not a valid input. Please try again.");
			}
			int userInput = scanner.nextInt();
				/*Notes on this scanner:
				1. It accepts input and changes it to an int.
				2. It JUST reads the number and not the end of the line. This can throw off upcoming inpus.
				3. The Int part can be substituted for Double or Float etc.
				*/
			String sentence = scanner.nextLine(); //Used to read the remainder of the line after a scanner.nextInt(); . Feel like there should be a better variable name, but it works ok in this case.
			if (userInput == 1) {
				Create();
			}
			else if (userInput == 2) {
				Read();
			}
			else if (userInput == 3) {
				Delete();
			}
			else if (userInput == 4) {
				Edit();
			}
			else if (userInput == 5) {
				Random();
			}
			else if (userInput == 6) {
				exitMain = false;
			}
			else {
				System.out.println("That is not a valid input. Please try again.");
			} // Close if
		}// Close while
	} // Close Method
	
	
	
	//Adds new meals to MealList. Currently for testing, meals are just Strings
	//Also needs to check for redundant names in the future.
	public void Create() {
		String name = this.CheckName();
		Meal newMeal = new Meal();
		newMeal.setName(name);
		MealsList.add(newMeal);
		Save();
	}// Close method
	
	
	
	//Displays Meals in MealList
	public void Read() {
		if (MealsList.size() == 0) {
			System.out.println("There are currently no meals.");
		} // End if
		else {
			System.out.println();
			Meal m;
			String name;
			for (int i = 0; i < MealsList.size(); i++) {
				m = MealsList.get(i);
				name = m.getName(); //Variable was previously Name, but variables should always start as lowercase. Static variables should be all uppercase.
				System.out.println(i+1 + ". " + name);
			} // Close loop
			System.out.println();
		} // Close else
	} // Close method
	
	
	
	//Removes a meal in MealList
	public void Delete() {
		boolean empty = MealsList.isEmpty(); //Will return true if empy and false if it has something
		if (empty) {
			System.out.println("There are currently no meals to delete.");
		} //End if
		else {
			boolean deleteLoop = true;
			while (deleteLoop) {
				Read();
				System.out.println("Please type the number next to the meal you wish to delete, or type Exit to quit.");
				String input = scanner.nextLine();
				if (input.equals("Exit") || input.equals("exit")) {
					break;
				}//End if
				else {
					int i; // Originally part of the try block, this moved it out of scope for the rest of the program. Java is apperently very picky about scope.
					try {
					i = Integer.parseInt(input); //wraps the input into an int
					}//End try
					catch (NumberFormatException exception){
						System.out.println("Please type a valid integer or Exit.");
						continue;
						// The idea is that it tries to convert input into an int (i). If it fails it brings up a message and skips to the next iteration of the loop.
					} //End catch
					if (i > MealsList.size() || i < 1) {
						System.out.println("That was not a valid input.");
						continue;
					} //End if
					else {
						System.out.println("You are about to delete " + MealsList.get(i - 1).getName()); //This should use the getName method from the object located at in
						System.out.println("Would you like to continue? Y/N");
						input = scanner.nextLine();
						boolean ynLoop = true;
						while (ynLoop) {
							if (input.equals("N") || input.equals("n") || input.equals("No") || input.equals("no")) {
								break;
							} //End if
							else if (input.equals("Y") || input.equals("y") || input.equals("Yes") || input.equals("yes")) {
								MealsList.remove(i - 1); //Remember: i is based on the absolute size of the array list. Since the array list index starts at 0 the index you want to pull from is actually one less than i.
								Save();
								Read();
								break;
							} // End else if
							else {
								System.out.println("That is not a valid input.");
							} //End else
						} // End loop
					} //End else
				} //End else
			} // End loop
		} // End else
	}// End method

	

	//Updates a meal in the MealList
	//Note. In the future when meals have multiple variables, the Meal Object should have methods to set these and the Update method just calls those methods.
	public void Edit() {
		boolean empty = MealsList.isEmpty(); //Will return true if empy and false if it has something
		if (empty) {
			System.out.println("There are currently no meals to edit.");
		} //End if
		else {
			boolean editLoop = true;
			Read();
			while (editLoop) {
				System.out.println("Please type the number next to the meal you wish to edit, or type Exit to quit.");
				String input = scanner.nextLine();
				if (input.equals("Exit") || input.equals("exit")) {
					break;
				}//End if
				else {
					int i;
					try {
					i = Integer.parseInt(input); //wraps the input into an int
					}//End try
					catch (NumberFormatException exception){
						System.out.println("Please type a valid integer or Exit.");
						continue;
						// The idea is that it tries to convert input into an int (i). If it fails it brings up a message and skips to the next iteration of the loop.
					} //End catch
					if (i > MealsList.size() || i < 1) {
						System.out.println("That was not a valid input.");
						continue;
					} //End if
					else {
						System.out.println("You are about to edit " + MealsList.get(i - 1).getName()); //This should use the getName method from the object located at in
						System.out.println("Would you like to continue? Y/N");
						input = scanner.nextLine();
						boolean ynLoop = true;
						while (ynLoop) {
							if (input.equals("N") || input.equals("n") || input.equals("No") || input.equals("no")) {
								break;
							} //End if
							else if (input.equals("Y") || input.equals("y") || input.equals("Yes") || input.equals("yes")) {
								String name = this.CheckName();
								MealsList.get(i - 1).setName(name); //Sets the name of the object in the array list. This is important to remember.
								Save();
								Read();
								break;
							} // End else if
							else {
								System.out.println("That is not a valid input.");
							} //End else
						} // End loop
					} //End else
				} //End else
			} // End loop
		} // End else
	}// End method

	//creates a randomized list of 7 meals with no duplicates
	public void Random() {
		int range = MealsList.size();
		ArrayList<String> meals = new ArrayList<String>();
		Meal m;
		String name;
		boolean invalidMeal = true;
		boolean isIn;
		
		if (range < 7) {
			System.out.println("There needs to be at least seven meals to choose from.");
		}
		else {
			for (int i = 0; i < 7; i++) {
				invalidMeal = true;
				while (invalidMeal) {
					int index = (int)(Math.random() * range);
					m = MealsList.get(index);
					name = m.getName();
					if (isIn = meals.contains(name)) {
						invalidMeal = true;
					}
					else {
						invalidMeal = false;
						meals.add(name);
					} //End else
				}//End while loop
			}//End for loop
			for (int i = 0; i < meals.size(); i++) {
				System.out.println(meals.get(i));
			} // Close loop
		}// End else
	}// End method


	//Save method
	public void Save() {
		try {
			FileOutputStream fs = new FileOutputStream("MealSaves.ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(MealsList);
			os.close();
		} //End try
		catch (Exception ex) {
			ex.printStackTrace();
		}//End catch
	}//End method

	
	//Checks names for valid inputs and duplicates
	public String/*Returns String*/ CheckName() {
		boolean invalidName = true;
		Meal m;
		String input = "";
		while (invalidName) {
			invalidName = false;
			System.out.println("Please type the name you want the meal to have.");
			input = scanner.nextLine();
			if (input.length() == 0) { //because .isEmpty isn't working
				System.out.println("That is not a valid name.");
				invalidName = true;
				continue;
			} //end if
			for (int i = 0; i < MealsList.size(); i++) {
				m = MealsList.get(i); //Note: MealsList.get(i).getName() should work
				if (m.getName().equals(input)) { //When compairing strings you want to use XXX.equals(???)
					System.out.println("That name is already taken.");
					invalidName = true;
				} // Close if
			} //Close loop
		} //Close loop
		return input;
	} //Close method
	
	
} //Close class
	
//Main Method.
class MealPlanner implements Serializable {
	public static void main (String[] args) {

		MealHelper helper = new MealHelper();
		
		helper.StartUp();
	}
}

