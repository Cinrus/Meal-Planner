import java.io.*;

public class MealPlannerHelper implements Serializable {


	private static final long serialVersionUID = 1L;
	PersistenceBoundary pbo;
	UserInterfaceBoundary uibo;
	MealListHelper mlho;
	/* startUp method
	 
	 creates PersistanceBoundary object pbo
	 creates UserInterfaceBoundary object uibo
	 creates MealListHelper object mlho
	 calls Main Menu method
	 
	 This method is called by the main method.
	*/
	
	public void startUp() {
		pbo = new PersistenceBoundary();
		uibo = new UserInterfaceBoundary();
		mlho = new MealListHelper();
		
		pbo.Load(mlho);
		
		mainMenu();
	}
	
	 
	 /* Main Menu
		Acts as the core operations loop. Also acts as handler for unexpected errors. An error logging method will be added in the
		next version.
		
		This method is called by MealPlannerHelper's startUp method only. Once a function, like creating a meal or a meal plan, is done
		the user should return to this loop on its own.
	 */
	 
	public void mainMenu() {
		boolean loop = true;
		while (loop) {
			int input = uibo.displayMainMenu();
			switch (input) {
			case 1: uibo.createMeal(mlho);
					pbo.Save(mlho);
					break;
			case 2: uibo.displayMealList(mlho);
					break;
			case 3: uibo.deleteMeal(mlho);
					pbo.Save(mlho);
					break;
			case 4: uibo.editMeal(mlho);
					pbo.Save(mlho);
					break;
			case 5: uibo.mealPlan(mlho);
					break;
			case 0: loop = false;
					break;
			default: uibo.genericMessage(input + " is an invalid choice. Try again.");
					 break;
			}
		}
	}

	
}
