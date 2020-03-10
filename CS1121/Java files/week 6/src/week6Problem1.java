import java.util.Scanner;
/*
    Project Title: Week 6 Problem 1
    Name: Niklas Romero
    Date: 10/11/2019
    Class: CS 1121
    Project description: The program below takes two inputs form a user and uses
    pythagorean's theorem to create a solution.
     */
public class week6Problem1 {

    public static void main(String[] args) {
Scanner scan = new Scanner(System.in);
System.out.println("what is the length of the first side triangle ?");
int a = scan.nextInt();
System.out.println("what is the length of the second side triangle ?");
int b =  scan.nextInt();

        week6Problem1 tester = new week6Problem1();
        System.out.println(tester.Pythagorean(a,b));

    }
    /*
    Method header: The method takes two sides of a triangle and calculates the third side using pythagorean's theorem.
     */
    public double Pythagorean(int a, int b){

        double c = Math.sqrt((a * a)+(b * b));
        return c;

    }
}
