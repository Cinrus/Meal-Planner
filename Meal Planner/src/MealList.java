import java.io.Serializable;
import java.util.ArrayList;

public class MealList implements Serializable {

	private static final long serialVersionUID = 1L;
	// Create Meal List array list: mealList
	private ArrayList<Meal> mealList = new ArrayList<Meal>();
	
	/* Create New Meal
	 New String name
	 closed loop
	 	name = uibo's Request Meal Name method
	 	if name is blank
	 		call uibo's Generic Message method passing (That is an invalid name. Please try again.)
	 	else
	 		call Check Duplicate Name method passing name
	 		if duplicate is false:
	 			break loop
	 new Meal m
	 call m's setName method passing name
	 add m to mealList
	 call pbo's save method (skipping for now)
	 */
	
	public void CreateNewMeal(Meal m) {
		mealList.add(m);
		//call pbo save method
	}
	
	/* Check Duplicate Name (string name)
	 new Boolean duplicate = false
	 loop for (i = 0; i < mealList.size(); i++)
	 	if mealList.get(i).getName().equals(name)
	 		call uibo's Generic Message method passing (That name is already taken. Please try again.)
	 		duplicate = true
	 return duplicate
	 */
	
	public Boolean CheckDuplicateName(String name) {
		Boolean duplicate = false;
		for (int i = 0; i < mealList.size(); i++) {
			if (mealList.get(i).getName().equals(name)) {
				duplicate = true;
			}
		}
		return duplicate;
	}
	
	/* Read Meal List
	 if mealList.size == 0
	 	call uibo's Generic Message method passing (There are no meals currently.)
	 else
	 	call uibo's Display Meal List method passing mealList
	 */

	public int GetmealListSize() {
		// TODO Auto-generated method stub
		return mealList.size();
	}

	public ArrayList<String> GetMealListNames() {
		// TODO Auto-generated method stub
		ArrayList<String> names = new ArrayList<String>();
		for (int i = 0; i <mealList.size(); i++) {
			names.add(mealList.get(i).getName());
		}
		return names;
	}
	
	/* Delete Meal method (integer index)
	 mealList.remove(index - 1);
	*/
	
	public void DeleteMeal(int index) {
		mealList.remove(index); 
	}
	
	/* Set Meal Name (integer index, string name)
	mealList.get(index).setName(name);
	 */
	
	public void SetMealName (int index, String name) {
		mealList.get(index).setName(name);
	}
	
	public String GetMealName(int index) {
		return mealList.get(index).getName();
	}
	
	public ArrayList <Meal> GetMealList() {
		return mealList;
	}
	
	public void LoadMealList(ArrayList<Meal> loadTemp) {
		mealList = loadTemp;
	}

	//Creates an array list of meal names drawn from mealList. It uses mealAmmount to determine how big this array list should be.
	//It then passes the array list back.
	public ArrayList<String> CreateMealPlan(int mealAmmount) {
		boolean invalidMeal = true;
		ArrayList<String> meals = new ArrayList<String>();
		
		for (int i = 0; i < mealAmmount; i++) {
			invalidMeal = true;
			while (invalidMeal) {
				int index = (int)(Math.random() * mealList.size());
				Meal m = mealList.get(index);
				String name = m.getName();
				if (!meals.contains(name)) {
					invalidMeal = false;
					meals.add(name);
				}
			}
		}
		return meals;
	}
}
