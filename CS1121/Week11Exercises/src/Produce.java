/**
 * Produce: Provided Class for CS1121 Multiple Object Interactions Lab
 * 
 * Used to Create Produce that populate the Garden ecosystem and are eaten by Bunnies
 * 
 * @author YOURNAMEHERE
 *
 */
public class Produce {
	//The name of a specific produce item
	private String produceName;
	//True or False value for if the Produce object has been pollinated or not
	private boolean isPollinated;
	
	/**
	 * Constructor to create a new Produce Object
	 * 
	 * @param produceType an integer which is used to determine the name of the produce created
	 */
	public Produce(int produceType){
		isPollinated = false;
		switch(produceType){
		case 0: this.produceName = "Rose"; break;
		case 1: this.produceName = "Carnation"; break;
		case 2: this.produceName = "Iris"; break;
		case 3: this.produceName = "Hydrangea"; break;
		case 4: this.produceName = "Tulip"; break;
		default: this.produceName = "Chrysanthemum"; break;
		}
	}
	
	/**
	 * Get the type of produce this Produce object is 
	 * 
	 * @return the name of the type of produce
	 */
	public String getProduceName(){
		return this.produceName;
	}
	
	/**
	 * Get whether or not this Produce has been eaten
	 * 
	 * @return a boolean value representing if the produce item was eaten
	 */
	public boolean getIsPollinated(){
		return this.isPollinated;
	}
	
	/**
	 * Change whether or not this produce object has been eaten
	 * When the object is eaten, also changes its name to EATEN for easy printing
	 * 
	 * @param isPollinated Should be true if the produce was eaten.
	 */
	public void setIsPollinated(boolean isPollinated){
		this.isPollinated = isPollinated;
		if (isPollinated)
		{
			this.produceName = "Pollinated";
		}
	}
	
	/**
	 * @Override for toString
	 * 
	 * @return String with the name of the type of Produce
	 */
	public String toString(){
			return this.produceName;
	}

}
