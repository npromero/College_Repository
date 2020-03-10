/*
Title: Week11Exercise4.Bumblebee
Names: Niklas Romero
Date; 11/17/2019
Class: CS1121 L01
Project description: The class creates a bee object with a favorite flower that pollinates flowers with
different pollen power based on whether or not the flower is the bee's favorite flower.
 */

public class BumbleBee {
    private String favoriteFlower;
    private int totalPollenPower;
// getters
    public String getFavoriteFlower(){
        return this.favoriteFlower;
    }
    public int getTotalPollenPower(){
        return this.totalPollenPower;
    }
    //setter
    public void setFavoriteFlower(String favoriteFlower){ this.favoriteFlower = favoriteFlower;}
// Constructor based on favorite flower
    public BumbleBee(String favoriteFlower)
    {
       setFavoriteFlower(favoriteFlower);
       this.totalPollenPower = 0 ;
    }

    /*
    Method header: The pollinate method checks if the flower is pollinated or not to pollinate the flower. If the
    flower is the bee's favorite flower then the total pollination power is 10 times stronger than the base 10 power
    given from pollinating the flower.
     */
    public void pollinate(Produce p1 )
    {
        if (!p1.getIsPollinated() && p1.getProduceName().compareTo(this.favoriteFlower) == 0)
        {
            this.totalPollenPower = this.totalPollenPower + 100;
            p1.setIsPollinated(true);
        }
        else if(!p1.getIsPollinated())
        {
            this.totalPollenPower = this.totalPollenPower + 10;
            p1.setIsPollinated(true);
        }

    }
// overriding the toString to make useful printout data
    public String toString()
    {
        String text = "Your BumbleBee's favorite flower is " + getFavoriteFlower() + " and their total pollen power is  " + getTotalPollenPower() ;
        return text;

    }



}
