import java.util.Scanner;
/*
Title: Week7Exercise5
Names: Niklas Romero
Date; 10/18/2019
Class: CS1121 L01
Project description: The program does square roots and squares of floating point values to test for round off errors.
 */
public class Week7Exercise5 {

    public static void main(String[] args) {
        Scanner scan =  new Scanner(System.in);
        System.out.println("Enter an integer");
        float x = scan.nextFloat();
        if (x >= 1 || x <= 100)
        {
            x = (float)Math.sqrt(x);
            System.out.println("Square root: " + x);
            x = x * x;
            System.out.println("Square root Squared: " + x);
        }
        else
            {
                x = x* x;
                System.out.println("Squared: " + x);
            }
    }
}
