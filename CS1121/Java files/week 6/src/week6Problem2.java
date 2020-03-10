import java.util.Scanner;
/*
    Project Title: Week 6 Problem 2
    Name: Niklas Romero
    Date: 10/11/2019
    Class: CS 1121
    Project description: The program below takes three inputs form a user cubes all three numbers before outputting
    them cubed back to the user.
     */
public class week6Problem2 {
    public static void main(String[] args) {
        week6Problem2 tester = new week6Problem2();
        Scanner scan = new Scanner(System.in);
        System.out.println("Provide 3 numbers please (space or new line separated): ");
        double x = scan.nextDouble();
        double y = scan.nextDouble();
        double z = scan.nextDouble();
        x = tester.cubed(x);
        y = tester.cubed(y);
        z =  tester.cubed(z);
        System.out.println("Your numbers cubed are: " + x + "," + y + ","+ z + ".");
    }
    /*
    Method header: Takes a double from the user and cubes it
     */
    public double cubed(double num) {
        num = num * num * num;
       return num;
    }
}
