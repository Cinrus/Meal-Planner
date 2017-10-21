import java.io.*;
import java.util.*;

public class PersistenceBoundary implements Serializable {

	private static final long serialVersionUID = 1L;

	
	
	/* Load Method
	 Searches for an existing array list of meals
	 	If found: loads that array list as an object (loadTemp)
	 	Casts it as an array list of meals
	 	Hands that over to mlo
	 	mlo sets loadTemp to existing meals array list variable
	 */
	public void Load(MealList mlo) {
		File savedMeals = new File("C:\\Java\\Eclipse Workspace\\Meal Planner\\MealSaves.ser"); // This is to find the file and checks if it exists.
		if(savedMeals.exists() && !savedMeals.isDirectory()) {
			try {
				FileInputStream fileStream = new FileInputStream("C:\\Java\\Eclipse Workspace\\Meal Planner\\MealSaves.ser"); //This line actually gets the data out of the file. Hence why it needs a path.
				ObjectInputStream nos = new ObjectInputStream(fileStream);
				Object one = nos.readObject(); // Holds the read object
				@SuppressWarnings("unchecked")
				/*Above line suppresses the warning for the line below. The warning exists because the compiler can't ever be sure that the object being cast is an ArrayList<Meal>. 
				As the object should ALWAYS be a ArrayList<Meal> and research has shown is no better way to write the code(and the ide suggested this option). As such a suppressed warning is called for.
				*/
				ArrayList<Meal> loadTemp = (ArrayList<Meal>) one; //Creates a ArrayList<Meal> variable to hold the loaded object and casts it as an ArrayList<Meal>
				mlo.LoadMealList(loadTemp);
				nos.close(); // Closing the file input stream
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	
	/* Save Method
	 get mealList
	 saveMeals Method (passed array list mealList
	 Saves meals to a txt doc
	 */
	
	public void Save(MealList mlo) {
		ArrayList<Meal> mealList = mlo.GetMealList();
		try {
			FileOutputStream fs = new FileOutputStream("MealSaves.ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(mealList);
			os.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
