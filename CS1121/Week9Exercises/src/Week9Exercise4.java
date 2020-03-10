/*
Title: Week9Exercise4
Names: Niklas Romero
Date; 11/03/2019
Class: CS1121 L01
Project description: The program changes every other value of a 10 long int array by multiplying the value by 10
 */

public class Week9Exercise4 {
public void alternatesTimesTen(int[] num)
{
    num[0] = num[0] *10;
    num[2] = num[2] *10;
    num[4] = num[4] *10;
    num[6] = num[6] *10;
    num[8] = num[8] *10;



    // cant use loops yet so cant use this code.
   /* int count = 0;
while (count <= num.length-1 )
{
    if (count %2 == 0)
    {
      num[count] = num[count] *10;
    }
    count = count + 1;
}

    */
}
    public static void main(String[] args) {
        int[] num = {1,2,3,4,5,6,7,8,9,10};
        Week9Exercise4 tester =new Week9Exercise4();
        tester.alternatesTimesTen(num);
        System.out.println("Values in Array: " + num[0] + ", " + num[1] + ", " + num[2] + ", " +num[3] + ", " + num[4] +
                ", " + num[5] + ", " + num[6] + ", "  + num[7] + ", " + num[8] + ", " + num[9]);

    }
}
