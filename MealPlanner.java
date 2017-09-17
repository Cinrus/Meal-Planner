// Meal Planner 1.0

import java.util.*;
import java.io.*; //Imports the serializable package so that objects can be saved.
import java.lang.*; //Imports the .isEmpty among other things.

//Meal Class
class Meal implements Serializable {
	Scanner scanner = new Scanner(System.in);
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
	
		//Checks names for valid inputs and duplicates
	public String/*Returns String*/ CheckName() {
		boolean exitCheckName = true;
		Meal m;
		String input = "";
		while (exitCheckName) {
			System.out.println("Please type the name you want the meal to have.");
			input = scanner.nextLine();
			if (input.length() == 0) { //because .isEmpty isn't working
				System.out.println("That is not a valid name.");
				continue;
			} //end if
			for (int i = 0; i < MealsList.size(); i++) {
				m = MealsList.get(i); //Note: MealsList.get(i).getName() should work
				if (m.getName().equals(input)) { //When compairing strings you want to use XXX.equals(???)
					System.out.println("That name is already taken.");
					continue;
				} //Close if
			} //Close loop
			break;
		} //Close loop
		return input;
	} //Close method
	
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
			System.out.println("1 = Create new meal(s).");
			System.out.println("2 = Display list of all meals.");
			System.out.println("3 = Delete a meal.");
			System.out.println("4 = Edit a meal.");
			System.out.println("5 = Exit");
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
			}/*
			else if (userInput == 3) {
				Delete();
			}
			else if (userInput == 4) {
				Edit();
			}*/
			else if (userInput == 5) {
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
		//Save();
	}// Close method
	
	
	//Displays Meals in MealList
	public void Read() {
		System.out.println();
		Meal m;
		String name;
		for (int i = 0; i < MealsList.size(); i++) {
			m = MealsList.get(i);
			name = m.getName(); //Variable was previously Name, but variables should always start as lowercase. Static variables should be all uppercase.
			System.out.println(i+1 + ". " + name);
		} // Close loop
		System.out.println();
	} // Close method
	
	
	//Removes a meal in MealList
	public void Delete() {
		boolean empty = MealsList.isEmpty(); //Will return true if empy and false if it has something
		if (empty) {
			System.out.println("There are currently no meals to delete.");
		} //End if
		else {
			Read();
			boolean deleteLoop = true;
			while (deleteLoop) {
				System.out.println("Please type the number next to the meal you wish to delete, or type Exit to quit.");
				String input = scanner.nextLine();
				if (input.equals("Exit") || input.equals("exit")) {
					break;
				}//End if
				else {
					try {
					int i = Integer.parseInt(input); //wraps 
					}//End try
					catch (NumberFormatException exception){ //(InputMismatchException exception){
						System.out.println("Please type a valid integer or Exit.");
						continue;
						// The idea is that it tries to convert input into an int (i). If it fails it brings up a message and skips to the next iteration of the loop.
					} //End catch
					if (i > MealsList.size + 1 || i < MealsList.size + 1) {
						System.out.println("That was not a valid input.");
						continue;
					} //End if
					else {
						System.out.println("You are about to delete " + MealsList.get(i).getName()); //This should use the getName method from the object located at in
						System.out.println("Would you like to continue? Y/N");
						input = scanner.nextLine();
						boolean ynLoop = true;
						while (ynLoop) {
							if (input.equals("N") || input.equals("n") || input.equals("No") || input.equals("no")) {
								break;
							} //End if
							else if (input.equals("Y") || input.equals("y") || input.equals("Yes") || input.equals("yes")) {
								MealsList.remove(i);
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
				
				
		/*
		boolean empty = MealsList.isEmpty(); //Will return true if empy and false if it has something
		if (empty) {
			System.out.println("There are currently no meals to delete.");
		}
		else {
			boolean exitDelete = true;
			
			while (exitDelete) { //Won't run without exitDelete being specifically initialized.
				Read();
				System.out.println("Please type the name of the meal you wish to delete. Alternatively type Exit to quit. Case matters.");
				String name = scanner.nextLine();
				if (name.equals("Exit")) {
					exitDelete = false;
				}
				else {
					boolean isIn = MealsList.contains(name); // Searches the array list for Name
					if (isIn) {
						MealsList.remove(name); // Removes Name from MealsList
						System.out.println(name + " has been removed.");
					}
					else {
						System.out.println(name + " could not be found. Please try again.");
					} // Close if
				}// Close if
			}// Close loop
		} // Close if
	}// Close Method
	*/

	
	/*
	//Updates a meal in the MealList
	//Note. In the future when meals have multiple variables, the Meal Object should have methods to set these and the Update method just calls those methods.
	public void Edit() {
		System.out.println();
		boolean empty = MealsList.isEmpty(); //Will return true if empy and false if it has something
		if (empty) {
			System.out.println("There are currently no meals to edit.");
		}
		else {
			boolean exitEdit = true;
			
			while (exitEdit) {
				Read();
				System.out.println("Please type the name of the meal you wish to edit. Alternatively type Exit to quit. Case matters.");
				String name = scanner.nextLine();
				if (name.equals("Exit")) {
					exitEdit = false;
				}
				else {
					boolean isIn = MealsList.contains(name); // Searches the array list for Name
					if (isIn == true) {
						int idx = MealsList.indexOf(name); // Finds the index of Name
						System.out.println("Please type what you would like the name to be.");
						name = scanner.nextLine();
						MealsList.set(idx, name);
					}
					else {
						System.out.println(name + " could not be found. Please try again.");
					} // Close if
				}// Close if
			}// Close loop
		} // Close if
	}// Close Method	
*/	


} //Close class
	
//Main Method.
class MealPlanner {
	public static void main (String[] args) {

		MealHelper helper = new MealHelper();
		
		helper.MainMenu();
	}
}

