import java.util.Scanner;
/*
Title: Week7Program
Names: Niklas Romero
Date; 10/18/2019
Class: CS1121 L01
Project description: The program runs 8 different methods to do different tasks based on user input or no input.
The first method checks to see if a number is even or not. The second method prints out a letter grade.
The third method calculates your pay check. The 4th & 5th methods determine the users birth stone.
The 6th method determines the user's lab instructor. The 7th method prints out a random ASCII character.
The final method tests a non return type method to see how to print out a statement outside of the main method.
 */
/*
Method 1 description: The method uses an integer that the user inputs to check whether the number is even or not. The
method will return a boolean (True or False)
 */
public class Week7Program {

public boolean isEven(int num)
{
    if (num %2 == 0 ) // % number checks if dividing by that number causes a remainder
    {
        return true;
    }
    else
        {
            return false;
        }

}
/*
Method 2: The program takes the grade percentage from the user and determines the letter grade the user got in
the class. The method returns a letter grade
 */
public String letterGrade(double percent)
{
    String grade = null; // null allows the string to be assigned actual text later in the program
    if (percent > 100)
    {
     System.out.println(" Percentage to big, try again");
    }
    else if (percent >= 90)
    {
        grade ="A";
    }
    else if (percent >= 80)
    {
        grade ="B";
    }
    else if (percent >= 70)
    {
        grade ="C";
    }
    else if (percent >= 60)
    {
        grade ="D";
    }
    else
    {
        grade ="F";
    }
        return grade;
}
/*
Method 3 description: The method takes the users pay rate , hours and tax rate to calculate their weekly pay check.
The method will return the user's paycheck as a double.
 */
public  double weeklyPayCheck(double payRate , double hours, double taxRate)
{
double payCheck = (payRate*hours) - ((payRate*hours) * (taxRate/100));
return payCheck;
}
/*
Method 4 description: The method takes the text version oth the user's birth month and determines the stones
associated with that month. The method returns the birth stones at text.
 */
public String birthStone(String month)
    {
        String stone = null;
        switch(month) // special if statement
        {
            case "January": stone = "garnet"; // parameter : what program does based on parameter
                break; // stops form going into the other cases
            case "February": stone = "amethyst";
                break;
            case "March": stone = "bloodstone, aquamarine";
                break;
            case "April": stone = "diamond";
                break;
            case "May": stone = "emerald";
                break;
            case "June": stone = "pearl, moonstone, alexandrite";
                break;
            case "July": stone = "ruby";
                break;
            case "August": stone = "spinel, peridot";
                break;
            case "September": stone = "sapphire";
                break;
            case "October": stone = "opal, tourmaline";
                break;
            case "November": stone = "topaz, citrine";
                break;
            case "December": stone = "turquoise, zircon, tanzanite";
                break;
            default: stone = "No Birthstone"; // if none of the conditions are met, run this
                break;
        }
        return stone;
    }
    /*
Method 5 description: The method takes the number version oth the user's birth month and determines the stones
associated with that month. The method returns the birth stones at text.
 */
    public String birthStone(int month)
    {
        String stone = null;
        switch(month)
        {
            case 1: stone = "garnet";
                break;
            case 2: stone = "amethyst";
                break;
            case 3: stone = "bloodstone, aquamarine";
                break;
            case 4: stone = "diamond";
                break;
            case 5: stone = "emerald";
                break;
            case 6: stone = "pearl, moonstone, alexandrite";
                break;
            case 7: stone = "ruby";
                break;
            case 8: stone = "spinel, peridot";
                break;
            case 9: stone = "sapphire";
                break;
            case 10: stone = "opal, tourmaline";
                break;
            case 11: stone = "topaz, citrine";
                break;
            case 12: stone = "turquoise, zircon, tanzanite";
                break;
            default: stone = "No Birthstone";
                break;
        }
        return stone;
    }
/*
Method 6 description: The method takes the numerical section of CS1121 from the user and determines the lab instructor
associated with that lab section, the method returns the lab instructor's name as text.
 */
    public String labInstructor(int section)
    {
        String instructor = null;
        if (section == 2 || section == 3)
        {
            instructor = "Sadia Nowrin";
        }
        else if (section == 5 || section == 6)
        {
            instructor = "Soheil Sepahyar";
        }
        else if (section == 1 || section == 4)
        {
            instructor = "Maxwell D’Souza";
        }
        else
        {
            instructor = "Not a Valid Section";
        }
        return instructor;
    }
    /*
    Method 7 description: The method picks a random ASCII character and returns it to the user.
     */
    public char getRandomASCII()
    {
        int randomNum = ((int)(Math.random()*127));
        char ascii = (char)randomNum;
        return ascii;
    }
    /*
    Method 8 description:  The method takes a number from the user and returns it back the user by testing a void type
    method. Void type methods typically dont have return values so the method prints out the value to the user.
     */
    public  void  testerPrintout(int numb)
    {
        System.out.println("For Testing: Variable passed in currently has value of: " + numb);
    }


    public static void main(String[] args) {
    Week7Program run = new Week7Program();
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter a number ");
    int num = scan.nextInt();
    System.out.println("Enter grade percentage ");
    double percent = scan.nextDouble();
        System.out.println("Enter pay rate ");
        double payrate = scan.nextDouble();
        System.out.println("Enter hours ");
        double hours = scan.nextDouble();
        System.out.println("Enter tax rate  as a percent (example 7% is 7 so input 7)");
        double taxRate = scan.nextDouble();
        System.out.println("Enter your month of birth as a number (Example: January is 1 so enter 1 ");
        int monthNum = scan.nextInt();
        System.out.println("Enter your month with the first letter capitalized ");
        String month = scan.next();
        System.out.println("Enter your lab section(Example: LO1 is  lab 1 so enter 1 ");
        int section = scan.nextInt();
        System.out.println("Enter a random number ");
        int numb = scan.nextInt();

System.out.println("Method: Is Even - " + "Arguments sent: " + "(" + num + ")" +  " " + "Result: " + " " + run.isEven(num));
System.out.println("Method: Letter Grade - " + "Arguments sent: " + "(" + percent + ")" +  " " + "Result: " +  " " +run.letterGrade(percent));
System.out.println("Method: Weekly Paycheck - " +"Arguments sent: " +  "(" + payrate + "," + hours + "," + taxRate + ")" +  " " + "Result: " +  " " + run.weeklyPayCheck(payrate,hours,taxRate));
System.out.println("Method: Birthstone (number) - " + "Arguments sent: " + "(" + monthNum + ")" +  " " + "Result: " +  " " + run.birthStone(monthNum));
System.out.println("Method: Birthstone (String) - " + "Arguments sent: " + "(" + month + ")" +  " " + "Result: " +  " " + run.birthStone(month));
System.out.println("Method: Lab Instructor - " + "Arguments sent: " + "(" + section + ")" +  " " + "Result: " +  " " + run.labInstructor(section));
System.out.println("Method: Lab Instructor - " + "Arguments sent: " + "(" + "No arguments" + ")" +  " " + "Result: " +  " " + run.getRandomASCII());
run.testerPrintout(numb);
    }
}
