import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterfaceBoundary implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Scanner scanner = new Scanner(System.in);
	
	/* Display Main Menu
	 new String input
	 Says: "Welcome to Meal Planner. Please enter the number of what you would like to do next."
	 	1. Create new meal.
	 	2. Display list of all meals.
	 	3. Delete a meal.
	 	4. Edit a meal.
	 	5. Create meal plan.
	 	0. Exit program.
	 
	 input = user input
	 trim input to first character
	 Return user input
	 */
	
	public int DisplayMainMenu() {
		System.out.println("Welcome to Meal Planner. Please enter the number of what you would like to do next.");
		System.out.println("1 = Create new meal.");
		System.out.println("2 = Display list of all meals.");
		System.out.println("3 = Delete a meal.");
		System.out.println("4 = Edit a meal.");
		System.out.println("5 = Create a Meal Plan");
		//System.out.println("5 = Create meal plan.");
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
				2. It JUST reads the number and not the end of the line. This can throw off upcoming inpus.
				3. The Int part can be substituted for Double or Float etc.
				*/
		String sentence = scanner.nextLine(); //Used to read the remainder of the line after a scanner.nextInt(); . Feel like there should be a better variable name, but it works ok in this case.
		return input;
	}
	
	/* Generic Message method (string message)
	 Display message
	 
	 Used for display error messages or anything else that would pop up in a new box.
	 */
	public void GenericMessage (String message) {
		System.out.println(message);
	}
	
	
	/*
	Display Meal List Method
	Get mealList size
		if mealList size < 0
			Display: There are no meals
		else
			get names from MealList
			cycle through names and display them
	*/
	
	public void DisplayMealList (MealList mlo) {
		if (mlo.GetmealListSize() == 0) {
			GenericMessage("There are currently no meals.");
		}
		else {
			ArrayList<String> names = mlo.GetMealListNames();
			for (int i = 0; i <names.size(); i++) {
				System.out.println(i + 1 + ". " + names.get(i));
			}
		}
		System.out.println();
	}
	
	//Creates a new meal. Get the information to fill the fields for a new meal first then create the meal and set those fields.
	public void CreateMeal(MealList mlo) {
		String name = CreateMealName(mlo);
		Meal m = new Meal();
		//Insert code to fill any other fields for a new meal here
		m.setName(name);
		//Insert code to set any other fields for m here
		mlo.CreateNewMeal(m);
	}
	
	public String CreateMealName (MealList mlo) {
		while (true) { //Java freaks out if you try to create a variable and set it to true and then make a loop. If you do it this way, so that the only way out of the while loop is a return statement, Java's cool.
			System.out.println("Please enter a name for the meal.");
			String name = scanner.nextLine();
			if (name.isEmpty()) {
				GenericMessage("That is not a valid name.");
			}
			else {
				if (mlo.CheckDuplicateName(name) == true) {
					GenericMessage("That name is already taken.");
				}
				else {
					return name;
				}
			}
		}
	}
	
	
	/* Delete Meal Method
	if (mlo.GetmealListSize() == 0) {
		GenericMessage("There are currently no meals.");
	}
	else {
		leaveLoop = false
		loop (while leaveLoop !true)
			input = call Selection method
			if input = 0
				leaveLoop = true
			else
				loop
					Display: You are about to delete (mlo.getMealName(passing input))
					Display: Would you like to continue?
					Get next line, Trim and force upper case
					if "N"
						break
					else if "Y"
						call mlo's DeleteMeal method passing input
						Display: Delete successful.
						leaveLoop = true
						break
					else
						GenericMessage("That is not a valid choice.)
	}
	
	*/
	//Note: EditMeal and DeleteMeal are too similar and I should create and use common methods. Since I'm going to be switching to GUI soon I'm not going to worry about it now, but I should then.
	public void DeleteMeal(MealList mlo) {
		if (mlo.GetmealListSize() == 0) {
			GenericMessage("There are currently no meals.");
		}
		else {
			boolean leaveLoop = false;
			while (leaveLoop == false) {
			int input = Selection(mlo);
				if (input == 0) {
					leaveLoop = true;
				}
				else {
					input = input - 1; //This is to prevent the off by one error so the user can choose meal 1 and select it from element 0 in the array list.
					   				   //Doing this any earlier would prevent the user from choosing 0 to escape.
					while(true) {
						System.out.println("You are about to delete " + (mlo.GetMealName(input)));
						System.out.println("Would you like to continue? Y/N");
						//String yn = scanner.nextLine().trim().substring(0, 1);
						String yn = scanner.nextLine();
						yn =  yn.trim();
						yn = yn.toUpperCase();
						yn = yn.substring(0,1);
						if (yn.equals("N")) {
							break;
						}
						else if (yn.equals("Y")) {
							mlo.DeleteMeal(input);
							System.out.println("Delete successful.");
							leaveLoop = true;
							break;
						}
						else {
							GenericMessage("That is not a valid choice.");
						}
					}
				}
			}
		}
	}
	
	/* Display Selection method
	 loop
	 	call DisplayMealList
	 	display "Please type the number next to the meal you wish to delete, or type 0 to quit."
	 	(I've got a way to get just numeric inputs. Still don't know how it is doing it.)
	 	try
	 		parse input into int
	 	catch
	 		GenericMessage (Please type a valid integer)
	 		continue
	 	if input > mlo.GetmealListSize()
	 		GenericMessage (That meal does not exist.)
	 		continue
	 	if input < 0
	 		GenericMessage (That meal does not exist.)
	 		continue
	 	return input
	 */
	
	public int Selection(MealList mlo) {
		while(true) {
			DisplayMealList(mlo);
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
			if (input > mlo.GetmealListSize() || input < 0) {
				GenericMessage ("That meal does not exist.");
				continue;
			}
			String sentence = scanner.nextLine(); //Used to read the remainder of the line after a scanner.nextInt();
			return input;
		}
	}
	
	/* Edit Meal Method
	if (mlo.GetmealListSize() == 0) {
		GenericMessage("There are currently no meals.");
	}
	else {
		leaveLoop = false
		loop (while leaveLoop !true)
			input = call Selection method
			if input = 0
				leaveLoop = true
			else
				loop
					Display: You are about to edit (mlo.getMealName(passing input))
					Display: Would you like to continue?
					Get next line, Trim and force upper case
					if "N"
						break
					else if "Y"
						name = call CreateMealName
						call mlo's SetMealName passing name and input
	
	*/
	
	//Note: EditMeal and DeleteMeal are too similar and I should create and use common methods. Since I'm going to be switching to GUI soon I'm not going to worry about it now, but I should then.
	public void EditMeal(MealList mlo) {
		if (mlo.GetmealListSize() == 0) {
			GenericMessage("There are currently no meals.");
		}
		else {
			Boolean leaveLoop = false;
			while(leaveLoop == false) {
				int input = Selection(mlo);
				if(input == 0) {
					leaveLoop = true;
				}
				else {
					input = input - 1; //This is to prevent the off by one error so the user can choose meal 1 and select it from element 0 in the array list.
									   //Doing this any earlier would prevent the user from choosing 0 to escape.
					while(true) {
						System.out.println("You are about to edit " + (mlo.GetMealName(input)));
						System.out.println("Would you like to continue? Y/N");
						String yn = scanner.nextLine().trim().substring(0, 1);
						yn = yn.toUpperCase();
						if (yn.equals("N")) {
							break;
						}
						else if (yn.equals("Y")) {
							String name = CreateMealName(mlo);
							mlo.SetMealName(input, name);
							leaveLoop = true;
							break;
						}
						else {
							GenericMessage("That is not a valid choice.");
						}
					}
				}
				
			}
		}
	}

	/* Meal Plan method
	 Display: How many meals would you like to plan for? Or type 0 to exit. (input = mealAmmount)
	 	If 0: Break
	 	If invalid input: Display: that is not a valid input. Please try again.
	 	If !0 and valid input:
	 		Check if mealAmmount > mealList
	 			if Y: 
	 				Display: There are not enough meals. Please choose a smaller number.
	 			if N:
	 				ArrayList<Meal> mealPlan = mlo's CreateMealPlan method passing mealAmmount
					for (int i = 0; i <mealPlan.size(); i++) {
						System.out.println(i + 1 + ". " + names.get(i));
					}
		System.out.println();
	 */
	
	public void MealPlan(MealList mlo) {
		while(true) {
			System.out.println("Type how many meals you would like to plan for or type 0 to exit.");
			while(!scanner.hasNextInt()) { // Checks to make sure the input is an int.
				scanner.next();
				System.out.println("That is not a valid input. Please try again.");
			}
			int mealAmmount = scanner.nextInt();
			String sentence = scanner.nextLine(); //Used to read the remainder of the line after a scanner.nextInt(); . Feel like there should be a better variable name, but it works ok in this case.
			if (mealAmmount == 0) {
				break;
			}
			else if (mealAmmount < 0) {
				System.out.println("There are no such things as negative meals. Try again.");
			}
			else {
				if (mealAmmount > mlo.GetmealListSize()) {
					System.out.println("There are not enough meals to make a meal list of that size.");
				}
				else {
					ArrayList<String> mealPlan = mlo.CreateMealPlan(mealAmmount);
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
