/*
Title: Week10Exercise1
Names: Niklas Romero
Date; 11/10/2019
Class: CS1121 L01
Project description: The program prints out arrays of only odd numbers of specified length
 */
public class Week10Exercise4 {
    /*
    Method header: The method takes in an array and creates a new array with only the odd values starting from 0 and to the length going up by odds)
     */
    public int[] arrayOfOdds(int num)
    {
        int i = 0;
        int k = 0;
        int [] res = new int[num+1];
        int [] shuffled = new int[num+1];

        for(i=0;i <= res.length-1; i++ )
        {
            res[i] = i;
            if(i % 2 != 0)
            {
             shuffled[k] = res[i];
             k++;
            }


        }
        int [] result = new int[k];
        for( int counter = 0; counter < k;counter++)
        {
            result[counter]  = shuffled[counter];
        }
        return result;
    }
    public static void main(String[] args) {
        int i = 0;
        Week10Exercise4 test = new Week10Exercise4();
        int[] array1 = test.arrayOfOdds(30);
        int[] array2 = test.arrayOfOdds(25);
        for(i = 0; i < array1.length;i++)
        {
           System.out.print("(" +array1[i] + ")");
        }
        System.out.println();
        for(i = 0; i < array2.length;i++)
        {
            System.out.print("(" +array2[i] + ")");
        }

    }
}
