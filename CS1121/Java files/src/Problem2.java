import java.util.Scanner;
public class Problem2 {
    /*
    Project Title: Problem 2
    Name: Niklas Romero
    Date: 10/4/2019
    Class: CS 1121
    Project description: The program below adds four numbers together to find the sum. The numbers are found via user
    input and output within the command prompt
     */
    public static void main(String [] args) {
       Scanner input = new Scanner(System.in); // creating a scanner to gert user input
       System.out.println("What is your first number: "); // Asking the user for inputs
       int num1 = input.nextInt(); // getting input form user
        System.out.println("What is your second number: ");
        int num2 = input.nextInt();
        System.out.println("What is your third number: ");
        int num3 = input.nextInt();
        System.out.println("What is your fourth number: ");
        int num4 = input.nextInt();
        double avg = (num1 + num2 + num3 + num4) / 4.0 ;  // adding the numbers together
        System.out.println("Your sum is: " + avg); // prints the sum to the user
    }
}
