/**
 * Garden: Provided Class for CS1121 Multiple Object Interactions Lab
 * 
 * Used to Simulate a Garden Ecosystem with Bunnies and Produce
 * 
 * @author YOURNAMEHERE
 *
 */
public class Garden {
	
	//Every garden has an array of Produce, which are the crops in the garden
	private Produce[] crops; 
	
	public static void main(String[] args){
		Garden g1 = new Garden(8);
		BumbleBee barry = new BumbleBee("Rose");
		System.out.println(g1);
		int i = 0;
		while(i < g1.getCrops().length)
		{
			barry.pollinate(g1.getCrop(i));
			i++;
		}
		System.out.println(g1);
		System.out.println(barry);
	}

	/**
	 * Constructor for a Garden object
	 * 
	 * @param numberOfCrops how many crops should the garden contain?
	 */
	public Garden(int numberOfCrops){
		//Set the size of the garden, now that we know it!
		crops = new Produce[numberOfCrops];
		//Generate all the Produce objects for the crops
		for (int i = 0; i < this.getCrops().length; i++){
			//What interesting thing does the line below do?
			crops[i] = new Produce((int)(Math.random() * 5));
		}
	}
	
	/**
	 * Get the array containing all of the Garden crops
	 * 
	 * @return the array of Produce objects representing the garden
	 */
	public Produce[] getCrops(){
		return this.crops;
	}
	
	/**
	 * Get a specific crop from the garden based on its position in the crop array
	 * 
	 * @param index the index of the crop array to obtain the element from
	 * @return the Produce object at the index in the array
	 */
	public Produce getCrop(int index){
		return this.crops[index];
	}
	
	/**
	 * Determine how many crops in the garden have been eaten
	 * 
	 * @return an integer representing how many crops were eaten
	 */
	public int getPollinatedCrops(){
		int pollinatedCounter = 0;
		for (int i = 0; i < this.getCrops().length; i++){
			if (this.getCrop(i).getProduceName().compareTo("Pollinated") == 0){
				pollinatedCounter = pollinatedCounter + 1;
			}
		}
		return pollinatedCounter;
	}
	
	/**
	 * @Override for toString 
	 * 
	 * @return a listing of all the crops that are in the garden object
	 */
	public String toString(){
		String cropList = ""; 
		for (int i = 0; i < this.getCrops().length; i++){
			if (i == 0){
				cropList = this.getCrop(i).getProduceName();
			}
			else {
			cropList = cropList + ", " + this.getCrop(i) ;
			}
		}
				
		return "The garden contains: \n " + cropList;
	}

}
