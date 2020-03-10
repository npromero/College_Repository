/*
Title: FileStats
Names: Niklas Romero
Date; 1/25/2020
Class: CS1122 L02
Project description: The program reads a file and reports the amount of characters, words & lines used in the file
along side with the length of the file
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
public class FileStats {
    public static void main(String[] args) {
        Scanner scantxt = new Scanner(System.in);
        System.out.println("Enter a filename");
        String fileName = scantxt.nextLine();
        ArrayList<String> getChars = new ArrayList<>();
        ArrayList<String> getWords = new ArrayList<>();
        Scanner scan = null;
        File file = null;
        int chars = 0;
        int words =0;
        int lines = 0;

        try {
            file = new File(fileName);

            // Making sure the file exists & re-executing if the file cannot be found
            while(!(file.exists()))
            {
                System.out.println("File not found. Try again.");
                System.out.println("Enter a filename");
                fileName = scantxt.nextLine();
                file = new File(fileName);
            }
            // Starts calculating & accessing the file
             scan = new Scanner(file);
            while (scan.hasNextLine())
            {
                    getWords.add(scan.nextLine());
                    lines++;
            }
            // converting the matrix of lines int a matrix of words
            for(int i =0 ; i < getWords.size(); i++)
            {
                String s = getWords.get(i);
                String[] s2 = s.split(" ");
                for (int j = 0; j < s2.length ; j++)
                {
                    getChars.add(s2[j]);
                }
            }
            // using the word array to get the characters used
            for(int i =0 ; i < getChars.size(); i++)
            {
                String text = getChars.get(i);
                int add = text.length() +1; // Accounting for all the spaces not added
                chars = chars + add;
                words++;
            }

                chars = chars-1; // getting rid of the extra space at the end of the character finding loop
            // outputs
            System.out.println(chars);
            System.out.println(words);
            System.out.println(lines);
            System.out.println(file.length());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            scan.close();
        }

    }

}



