import java.io.*;

public class MealHelper implements Serializable {


	private static final long serialVersionUID = 1L;
	PersistenceBoundary pbo;
	UserInterfaceBoundary uibo;
	MealList mlo;
	/* startUp method
	 
	 creates PersistanceBoundary object pbo
	 creates UserInterfaceBoundary object uibo
	 creates MealList object mlo
	
	(skipping: calls pbo load stuffs)
	 
	calls Main Menu method
	*/
	
	public void StartUp() {
		pbo = new PersistenceBoundary();
		uibo = new UserInterfaceBoundary();
		mlo = new MealList();
		
		pbo.Load(mlo);
		
		MainMenu();
	}
	
	 
	 /* Main Menu
	  closed loop
	  	calls uibo's Display Main Menu method
	  	If return is:
	  		1. Create new meal
	  			
	  		2. Read Meal List
	  			
	  		3. Delete meal
	  			
	  		0. Exit
	  			closes the program
	  		Anything else
	  			Display: That is an invalid choice try again
	 */
	 
	public void MainMenu() {
		boolean loop = true;
		while (loop) {
			int input = uibo.DisplayMainMenu();
			switch (input) {
			case 1: uibo.CreateMeal(mlo);
					pbo.Save(mlo);
					break;
			case 2: uibo.DisplayMealList(mlo);
					break;
			case 3: uibo.DeleteMeal(mlo);
					pbo.Save(mlo);
					break;
			case 4: uibo.EditMeal(mlo);
					pbo.Save(mlo);
					break;
			case 5: uibo.MealPlan(mlo);
					break;
			case 0: loop = false;
					break;
			default: uibo.GenericMessage("That is an invalid choice. Try again.");
					 break;
			}
		}
	}
	

	
}
