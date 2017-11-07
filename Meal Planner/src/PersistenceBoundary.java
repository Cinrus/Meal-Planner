import java.io.*;
import java.util.*;

/*
 This is the persistence boundary class. It's purpose is to act as an interface for saving and loading meal data.
 Right now meal data is being saved as a document in a static location. In the future it could be saved to a location that
 the user desires, or possibly even a database.
 */
public class PersistenceBoundary implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MEALARRAYLOCATION = "C:\\Java\\Eclipse Workspace\\Meal Planner\\MealSaves.ser"; //Location saved as a static for easier management.
	
	
	/* Load Method
	First, this method checks to see if anything exists at "C:\\Java\\Eclipse Workspace\\Meal Planner\\MealSaves.ser".
	If something does this method loads statically from that location and casts the object as an ArrayList<Meal>.
	Finally it calls the loadMealList in MealListHelper to transfer the array list.
	
	This method
		returns: void
		has as parameters: a MealListHelper
		is called by: 
			MealPlannerHelper's startUp method
	 */
	public void Load(MealListHelper mlho) {
		File savedMeals = new File(MEALARRAYLOCATION); // This is to find the file and checks if it exists.
		if(savedMeals.exists() && !savedMeals.isDirectory()) {
			try {
				FileInputStream fileStream = new FileInputStream(MEALARRAYLOCATION); //This line actually gets the data out of the file. Hence why it needs a path.
				ObjectInputStream nos = new ObjectInputStream(fileStream);
				Object one = nos.readObject(); // Holds the read object
				@SuppressWarnings("unchecked")
				/*Above line suppresses the warning for the line below. The warning exists because the compiler can't ever be sure that the object being cast is an ArrayList<Meal>. 
				As the object should ALWAYS be a ArrayList<Meal> and research has shown there is no better way to write the code(and Eclipse suggested this option). As such a suppressed warning is called for.
				*/
				ArrayList<Meal> loadTemp = (ArrayList<Meal>) one; //Creates a ArrayList<Meal> variable to hold the loaded object and casts it as an ArrayList<Meal>
				mlho.loadMealList(loadTemp);
				nos.close(); // Closing the Object Input Stream
				fileStream.close(); //Closing the File Input Stream
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	
	/* Save Method
	 This method saves the current meal list. The current plan is that whenever a change is made to MealListHelper's mealList this
	 method is called automatically to persist the change.
	 
	 This method
	 	returns: void
	 	has parameters: MealListHelper
	 	is called by:
	 		MealPlannerHelper's mainMenu();
	 */
	
	public void Save(MealListHelper mlho) {
		ArrayList<Meal> mealList = mlho.getMealList();
		try {
			FileOutputStream fs = new FileOutputStream(MEALARRAYLOCATION);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(mealList);
			os.close();
			fs.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
