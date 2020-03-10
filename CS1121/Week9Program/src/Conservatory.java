/*
Title: Conservatory
Names: Niklas Romero
Date; 11/6/2019
Class: CS1121 L01
Project description: The program uses the Creature class to make an array of creature objects/ The program prints out
 each color first and then prints out the numbered creature with each of its properties.
 */
public class Conservatory {
    public static void main(String[] args) {
        /*
        Instance variables for creature 4
         */
        String name1 = "reggie";
        String color1 = "red";
        String texture1 = "spiky";
        /*
        Instance variables for creature 5
         */
        String name2 = "john";
        String color3 = "white";
        String texture4 = "fluffy";
        int teethNum = 50;
        String color2 = "green";
        String texture2 = "scaly";
        boolean antennae = true;
        boolean carnivorous = true;
        int height = 340;
        /*
        Creating the first 3 creatures using a loop
         */
        Creature  [] pens = new Creature[5];
        int i = 0;
        while(i < 3)
        {
            pens[i] = new Creature();
           i++;
        }
        /*
        Creating and differentiating the creatures from one another
         */
        pens[3] = new Creature(name1,color1,texture1);
        pens[4] = new Creature(teethNum,color2,texture2,antennae,carnivorous,height,name2);
        pens[0].setName("Draco");
        pens[0].setColor("red");
        pens[1].setName("Tempest");
        pens[2].setName("Wendy");
        pens[2].setColor("blue");
        pens[2].setCarnivorous(false);
        /*
        Comparing each creatures color
         */
        System.out.println("Observing Property: Color");
        int in = 0;
        while(in <= pens.length -1)
        {
            System.out.println((in+1) + ": " + pens[in].getColor());
            in++;
        }
        System.out.println(" ");
        /*
        Showcasing all of the creatures and their properties in the pens
         */
        System.out.println("Analyzing each creature: ");
        int index = 0;
        while(index <= pens.length -1)
        {
            System.out.println((index+1) + ": " + pens[index].toString());
            index++;
        }
    }
}
