import java.io.Serializable;

public class Main implements Serializable {


	private static final long serialVersionUID = 1L;

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		
		MealHelper mho = new MealHelper();
		
		mho.StartUp();
	}

}


/* Things to look into:
	X1. When selecting "Edit a meal" from the main menu it displays "That is an invalid choice. Try again." instead of "There are currently no meals." like other options.
		1.2 Actually choosing Edit is not registering as a valid option.
		1.3 Edit was displayed as an option, but was not an actual option in MealHelper. This has been fixed.
		
	X2. Choosing to Delete a meal when meals are an option is going back to the main menu.
		2.1 This seems to have been resolved
		
	?3. When choosing a meal to delete user is getting an off by +1 error. ie: Choose meal 1, and the program selects meal 2.
	
	4. When confirming if you want to delete, user's Y/N confirmation is not being trimmed or sent to upper case.
	
	5. Need to make it so that the uibo's Display Main Menu method won't try to take an empty from user.
	
	6.
*/