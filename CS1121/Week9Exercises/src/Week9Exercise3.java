/*
Title: Week9Exercise3
Names: Niklas Romero
Date; 11/03/2019
Class: CS1121 L01
Project description: The program displays information in the given anArray array
 */

public class Week9Exercise3 {
    public static void main(String[] args) {
        String [] anArray ={"Sphinx" , "of","black","quartz", "judge","my","vow"};
        System.out.println(anArray[0]);
        System.out.println(anArray[anArray.length -1 ]);
        System.out.println("The length of anArray is " + anArray.length);
        char [] someText = anArray[3].toCharArray();
        System.out.println(someText[0]);
    }
}
