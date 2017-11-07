import java.util.ArrayList;
import java.util.Scanner;
/* User Interface Boundary class
 Holds all of the methods that interact with the user, whether that be through getting input or displaying output.
 
 The next version is planned to be GUI based.
 */
public class UserInterfaceBoundary {
	private static Scanner scanner = new Scanner(System.in);
	
	/* Display Main Menu
	 Displays the main menu of the program.
	 
	 This method is called by 
	 	MealPlannerHelper's MainMenu
	 */
	
	public int displayMainMenu() {
		System.out.println("Welcome to Meal Planner. Please enter the number of what you would like to do next.");
		System.out.println("1 = Create new meal.");
		System.out.println("2 = Display list of all meals.");
		System.out.println("3 = Delete a meal.");
		System.out.println("4 = Edit a meal.");
		System.out.println("5 = Create a Meal Plan");
		System.out.println("0 = Exit");
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
		int input = scanner.nextInt();
				/*Notes on this scanner:
				1. It accepts input and changes it to an int.
				2. It JUST reads the number and not the end of the line. This can throw off upcoming inputs.
				3. The Int part can be substituted for Double or Float etc.
				*/
		scanner.nextLine(); //Used to read the remainder of the line after a scanner.nextInt(); 
		return input;
	}
	
	/* Generic Message
	 Used for display error messages or anything else that would pop up in a new box. Probably unnecessary for a command line interface, but
	 good practice to get used to for when the program switches to a GUI.
	 
	 This method is called by
	 	UserInterfaceBoundary's createMealName
	 	"" deleteMeal
	 	"" displayMealList
	 	"" editMeal
	 	"" selection
	 	MealPlannerHelper's mainMenu
	*/
	public void genericMessage (String message) {
		System.out.println(message);
	}
	
	
	/* Display Meal List
	 Displays the names of all the meals in myMealList. If there are none it informs the user.
	 
	 This method is called by
	 	MealPlannerHelper's mainMenu
	 	UserInterfaceBoundary's selection
	*/
	
	public void displayMealList (MealListHelper mlo) {
		if (mlo.getmyMealListSize() == 0) {
			genericMessage("There are currently no meals.");
		}
		else {
			ArrayList<String> names = mlo.getmyMealListNames();
			for (int i = 0; i <names.size(); i++) {
				System.out.println((i + 1) + ". " + names.get(i));
			}
		}
		System.out.println();
	}
	
	/* Create Meal
	 Creates a new meal.
	 
	 Currently each meal consists of only a name. There are plans to put in other fields like the meal's recipe and user tags later.
	 
	 Get the information to fill the fields for a new meal first then create the meal and set those fields.
	 
	 This method is called by
	 	MealPlannerHelper's mainMenu
	*/
	public void createMeal(MealListHelper mlho) {
		String name = createMealName(mlho);
		Meal m = new Meal();
		//Insert code to fill any other fields for a new meal here
		m.setName(name);
		//Insert code to set any other fields for m here
		mlho.createNewMeal(m);
	}
	
	/* Create Meal Name
	 Walks the user through creating a name for the meal. Will check for empty fields as well as call methods to check for duplicate names.
	 
	 This method is called by
	 	UserInterfaceBoundary's createMeal
	 	"" editMeal
	 */
	public String createMealName (MealListHelper mlho) {
		while (true) { //Java freaks out if you try to create a variable and set it to true and then make a loop. If you do it this way, so that the only way out of the while loop is a return statement, Java's cool.
			System.out.println("Please enter a name for the meal.");
			String name = scanner.nextLine();
			if (name.isEmpty()) {
				genericMessage("That is not a valid name.");
			}
			else {
				if (mlho.checkDuplicateName(name) == true) {
					genericMessage("That name is already taken.");
				}
				else {
					return name;
				}
			}
		}
	}
	
	
	/* Delete Meal
	 Walks the user through deleting a meal from myMealList.
	 
	 This method is called by
	 	MealPlannerHelper's mainMenu
	*/
	//Note: EditMeal and DeleteMeal are too similar and I should create and use common methods. Since I'm going to be switching to GUI soon I'm not going to worry about it now, but I should then.
	public void deleteMeal(MealListHelper mlho) {
		if (mlho.getmyMealListSize() == 0) {
			genericMessage("There are currently no meals.");
		}
		else {
			boolean leaveLoop = false;
			while (!leaveLoop) {
			int input = selection(mlho);
				if (input == 0) {
					leaveLoop = true;
				}
				else {
					input = input - 1; //This is to prevent the off by one error so the user can choose meal 1 and select it from element 0 in the array list.
					   				   //Doing this any earlier would prevent the user from choosing 0 to escape.
					while(true) {
						System.out.println("You are about to delete " + (mlho.getMealName(input)));
						System.out.println("Would you like to continue? Y/N");
						String yn = scanner.nextLine();
						yn =  yn.trim();
						yn = yn.toUpperCase();
						yn = yn.substring(0,1);
						if (yn.equals("N")) {
							break;
						}
						else if (yn.equals("Y")) {
							mlho.deleteMeal(input);
							System.out.println("Delete successful.");
							leaveLoop = true;
							break;
						}
						else {
							genericMessage(yn + " is not a valid choice.");
						}
					}
				}
			}
		}
	}
	
	/* selection
	 This method displays all the meals from myMealList and prompts the user to select one. It then hands the selected number back
	 to the method that called it.
	 
	 This method is called by
	 	UserInterfaceBoundary's deleteMeal
	 	""	editMeal
	 */
	
	public int selection(MealListHelper mlho) {
		while(true) {
			displayMealList(mlho);
			System.out.println("Please type the number next to the meal you wish to select, or type 0 to quit");
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
			int input = scanner.nextInt();
					/*Notes on this scanner:
					1. It accepts input and changes it to an int.
					2. It JUST reads the number and not the end of the line. This can throw off upcoming inpus.
					3. The Int part can be substituted for Double or Float etc.
					*/
			if (input > mlho.getmyMealListSize() || input < 0) {
				genericMessage ("That meal does not exist.");
				continue;
			}
			scanner.nextLine(); //Used to read the remainder of the line after a scanner.nextInt();
			return input;
		}
	}
	
	/* Edit Meal Method
	 This method walks the user through editing an existing meal. Currently the only field a meal has is it's name. This will change
	 in future versions and so will this method.
	 
	 This method is called by
	 	MealPlannerHelper's mainMenu
	*/
	
	//Note: EditMeal and DeleteMeal are too similar and I should create and use common methods. Since I'm going to be switching to GUI soon I'm not going to worry about it now, but I should then.
	public void editMeal(MealListHelper mlho) {
		if (mlho.getmyMealListSize() == 0) {
			genericMessage("There are currently no meals.");
		}
		else {
			Boolean leaveLoop = false;
			while(leaveLoop == false) {
				int input = selection(mlho);
				if(input == 0) {
					leaveLoop = true;
				}
				else {
					input = input - 1; //This is to prevent the off by one error so the user can choose meal 1 and select it from element 0 in the array list.
									   //Doing this any earlier would prevent the user from choosing 0 to escape.
					while(true) {
						System.out.println("You are about to edit " + (mlho.getMealName(input)));
						System.out.println("Would you like to continue? Y/N");
						String yn = scanner.nextLine().trim().substring(0, 1);
						yn = yn.toUpperCase();
						if (yn.equals("N")) {
							break;
						}
						else if (yn.equals("Y")) {
							String name = createMealName(mlho);
							mlho.setMealName(input, name);
							leaveLoop = true;
							break;
						}
						else {
							genericMessage("That is not a valid choice.");
						}
					}
				}
				
			}
		}
	}

	/* Meal Plan method
	 The method that actually plans out a random list of meals.
	 
	 The user has to input the number of meals they want to get. This is to allow a user who wants to plan more/less then a week. Or
	 to plan for multiple meals in a week.
	 
	 This method checks for empty input, appropriate input (ie: no negative number of meals), and makes sure there are enough meals
	 in myMealList to create a meal plan of the desired size.
	 
	 This method is called by:
	 	MealPlannerHelper's mainMenu
	 */
	
	public void mealPlan(MealListHelper mlho) {
		while(true) {
			System.out.println("Type how many meals you would like to plan for or type 0 to exit.");
			while(!scanner.hasNextInt()) { // Checks to make sure the input is an int.
				scanner.next();
				System.out.println("That is not a valid input. Please try again.");
			}
			int mealAmount = scanner.nextInt();
			scanner.nextLine(); //Used to read the remainder of the line after a scanner.nextInt(); . Feel like there should be a better variable name, but it works ok in this case.
			if (mealAmount == 0) {
				break;
			}
			else if (mealAmount < 0) {
				System.out.println("There are no such things as negative meals. Try again.");
			}
			else {
				if (mealAmount > mlho.getmyMealListSize()) {
					System.out.println("There are not enough meals to make a meal list of that size.");
				}
				else {
					ArrayList<String> mealPlan = mlho.CreateMealPlan(mealAmount);
					for (int i = 0; i <mealPlan.size(); i++) {
						System.out.println(i + 1 + ". " + mealPlan.get(i));
					}
					System.out.println();
					break;
				}
			}
		}
	}
	
	
	
}
