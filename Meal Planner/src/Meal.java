import java.io.Serializable;

/* Meal
 This is the meal class object. Currently meals consist of just a name. 
 There are plans to further information attached to each meal such as:
 	+ User created tags to describe the meal.
 	+ Recipe instructions for making the meal.
 	+ List of ingredients for the meal. This would later be used in a grocery list function that would automatically compile a list of all the ingredients to make the list of meals.
 */

public class Meal implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	
	/* getName
	 Returns the name of the meal.
	 
	 This method is called by:
	 		MealListHelper's checkDuplicateName
	 		MealListHelper's createMealPlan
	 		MealListHelper's createNewMeal
	 		MealListHelper's getMealListNames
	 		MealListHelper's getMealName
	 */
	public String getName() {
		return name;
	}
	
	/* setName
	 Sets the name of the meal.
	 
	 This method is called by:
	 		UserInterfaceBoundary's createMeal
	 		MealListHelper's setMealName
	 */
	public void setName(String n) {
		name = n;
	}

}
