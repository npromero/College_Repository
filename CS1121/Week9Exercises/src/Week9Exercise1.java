/*
Title: Week9Exercise1
Names: Niklas Romero
Date; 11/03/2019
Class: CS1121 L01
Project description: The program changes the values of the given array
 */

public class Week9Exercise1 {
    public static void main(String[] args)
    {
        double [] changeMe= {3.14,0.999,42.24,-14.7}; // array being created, index goes from 0 to length -1
        changeMe[2] = changeMe[2] - 3;
        changeMe[1] = changeMe[1] *(Math.pow(6,changeMe[0]));
        changeMe[0] = changeMe[0] / Math.sqrt(2);
        changeMe[3] = Math.abs(changeMe[3]);
        System.out.println("Array changeMe:  { " + changeMe[0] + " , " + changeMe[1] + " , "+ changeMe[2] + " , " + changeMe[3] + " }");
    }
}
