import java.io.Serializable;

public class Meal implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	
	public String getName() {
		return name;
	}
		
	public void setName(String n) {
		name = n;
	}

}
