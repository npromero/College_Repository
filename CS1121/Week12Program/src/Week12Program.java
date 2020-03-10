/*
Title: Lab10Problem1
Names: Niklas Romero
Date; 11/20/2019
Class: CS1121 L01
Project description: The program edits text given by the user based off of thanks giving sentences
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Week12Program {
    public static void main(String[] args) {
        //Initializing counters
        int gobbleCount = 0;
        int thanksgivingCount = 0;
        int fallCount = 0;
        int studyCount = 0;

        Scanner scan = new Scanner(System.in);
        ArrayList<String> test = new ArrayList<String>();
String testVariable = "";
//Loop that creates arraylist
 while(!test.contains("STOP"))
        {
          System.out.println("Put in an input, STOP will end the program");
          test.add(scan.nextLine());
        }
        test.remove("STOP");
       System.out.println(test);
       // loop modifies arraylist
        for(int i = 0;i < test.size();i++)
        {
            if (test.get(i).contains("gobble"))
            {
                testVariable = test.get(i);
                testVariable = testVariable.toUpperCase();
                test.set(i, testVariable);
                gobbleCount = gobbleCount + 1;
            }
            if (test.get(i).contains("Thanksgiving"))
            {
                testVariable = test.get(i);
                // finding the start of the thanks giving and replacing every value before
                testVariable = testVariable.replace(testVariable.substring(0,testVariable.indexOf("Thanksgiving")),"");
                // finding the start of the thanks giving and replacing every value after the end of the word
                testVariable = testVariable.replace(testVariable.substring(testVariable.indexOf("Thanksgiving")+12),"");

                test.set(i, testVariable);
                thanksgivingCount = thanksgivingCount +1;
            }
                testVariable =  test.get(i);
                String marker = testVariable.toLowerCase();
                if(marker.contains(("fall")))
                {
                    testVariable = testVariable.toLowerCase();
                    test.set(i, testVariable);
                    fallCount = fallCount +1;
                }

                if(test.get(i).contains("study"))
                {
                    testVariable = test.get(i);
                    System.out.println("What are you studying");
                    testVariable =scan.nextLine();
                    test.set(i, testVariable);
                   studyCount = studyCount + 1;
                }
            int modMade = gobbleCount + thanksgivingCount + fallCount + studyCount;
                //outputs
                System.out.println(test);
                System.out.println("Total modifications made: " + modMade);
                System.out.println("Gobble counter: " + gobbleCount);
                System.out.println("Thanksgiving counter: " + thanksgivingCount);
                System.out.println("fall counter: " + fallCount);
                System.out.println("study counter: " + studyCount);

        }
    }
}
