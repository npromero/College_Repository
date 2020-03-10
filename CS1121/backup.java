/*
        Names: Noah Riske & Niklas Romero
        Date: 12/11/19
        Class: CS1121 L01
        Description: The program takes a file with blanks in the text and alows the user to fill in the blanks. Each
        step of the process is its own method. The program creates a new file with the modified text and prints out the
        text to the console in a readable format'
        */
// imports
import java.util.ArrayList;
import  java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class TextPredictor {
    public static void main(String[] args) {

        TextPredictor run = new TextPredictor();
        Scanner scantxt = new Scanner(System.in);
        System.out.println("Please input the name of your file, no need for suffix like .txt , .pdf ...");
        String filename = scantxt.nextLine();
        run.playPredictText(filename);
    }
/*
Method 1 Header: The method loadMessage creates the array list textTemplate by importing the text on the document
specified by user input in the main.
 */

    public ArrayList<String> loadMessage(File theFile)
    {


        Scanner scan = null;
        try {

            scan = new Scanner(theFile);
            ArrayList<String> textTemplate= new ArrayList<>();
            while (scan.hasNext())
            {
                textTemplate.add(scan.next()); // adding each word to the array list

            }
            return textTemplate;

        }
        catch(FileNotFoundException e) // catches if the file isn't found
        {
            e.printStackTrace();
        }
        finally
        {

            if (scan != null)
            {
                scan.close(); // closes the file
            }


        }
        return null;
    }
    /*
    Method 2 Header: The method finds the specified values to be replaced and gives a prompt based on the type of
    replacement needed for the file.
     */

    public  ArrayList<String> predictionFill(File theFile)
    {
        TextPredictor  run = new TextPredictor();
        Scanner scantxt = new Scanner(System.in);
        ArrayList<String> file = run.loadMessage(theFile);
        for(int i = 0;i<file.size();i++)  // runs through the array list to find the prompts
        {
            if(file.get(i).contains("[NOUN]"))
            {
                System.out.println("Please input a noun");
                String txt = scantxt.next();
                file.set(i,txt);
            }
            else if(file.get(i).contains("[PROPER-NOUN]")) // checking for the specified prompt
            {
                System.out.println("Please input a proper noun");
                String txt = scantxt.next();
                file.set(i,txt);
            }
            else if(file.get(i).contains("[ADJECTIVE]"))
            {
                System.out.println("Please input an adjective");
                String txt = scantxt.next();
                file.set(i,txt);
            }
            else if(file.get(i).contains("[VERB]"))
            {
                System.out.println("Please input a verb");
                String txt = scantxt.next();
                file.set(i,txt);
            }
            else if(file.get(i).contains("[SPORT]"))
            {
                System.out.println("Please input a sport");
                String txt = scantxt.next();
                file.set(i,txt);
            }
            else if(file.get(i).contains("[FOOD]"))
            {
                System.out.println("Please input a food");
                String txt = scantxt.next();
                file.set(i,txt);
            }

        }
        return file;
    }
    /*
    Method 3 Header: the method prints out the modified text neatly, 40 to 45 (if available) characters per line to a
    new file and the command prompt
     */
    public  void shareResult( File theFile, String filename)
    {
        TextPredictor  run = new TextPredictor();
        ArrayList<String> file = run.predictionFill(theFile);
        PrintWriter thePrinter = null;
        try {
            thePrinter = new PrintWriter(filename + ".out"); // creating new file with suffix .out
            String text = "";
            for (int i = 0; i < file.size() ; i++)
            {
                String txt = file.get(i);
                text = text +" " + txt; // making a new string to find number of characters
                if( text.length() > 40 && text.length() < 50) // checking if there are enough characters to print
                    // the line without making the line too long
                {
                    thePrinter.printf("%s \n",text);
                    System.out.printf("%s \n",text);
                    text = ""; // resetting counter for characters
                }
                else if (text.length() < 40 && i == file.size()-1) // Printing out if the last line is too short to
                    // print under normal circumstances
                {
                    thePrinter.printf("%s \n",text);
                    System.out.printf("%s \n",text);
                }
            }

        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {

            if (thePrinter != null)
            {
                thePrinter.close();
            }

        }
    }
    /*
    Method 4 Header: Runs the other methods for the user
     */
    public void playPredictText(String filename)
    {
        TextPredictor run = new TextPredictor();
        File theFile = new File(filename + ".dat"); //creates file with the input file name with suffix .dat
        run.shareResult(theFile,filename);
    }

}




