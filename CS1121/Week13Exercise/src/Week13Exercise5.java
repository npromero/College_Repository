import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Week13Exercise5 {
    public static void main(String[] args) {
        File theFile = new File("exercise.txt"); //creates file with the input file name with suffix .txt
        Scanner scan = null;

        try {
            scan = new Scanner(theFile);
            ArrayList<String> read = new ArrayList<>();  //creates the array ;list for storing values from the file
            while (scan.hasNext()) {              //loop to add elements to the array list
                read.add(scan.next());



                for(int i =read.size()-1; -1<i ;i--)
                {
                    System.out.printf(read.get(i));
                }

            }


        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {

            if (scan != null) {
                scan.close();
            }

        }
    }
}
