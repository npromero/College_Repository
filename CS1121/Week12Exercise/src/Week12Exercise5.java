

import java.util.ArrayList;

public class Week12Exercise5 {
    public static void main(String[] args) {
        ArrayList<String> editableTest = new ArrayList<>();
        java.lang.String testVariable = "Error";
        editableTest.add("text1");
        editableTest.add("text2");
        editableTest.add("text3");
        editableTest.add("text4");
        editableTest.add("text5");
        for(int i = 0; i<editableTest.size();i++)
        {
            testVariable = editableTest.get(i);
            testVariable = java.lang.String.format("%20s",testVariable);
            editableTest.set(i,testVariable);
        }

        for(int i = 0; i<editableTest.size();i++)
        {
            System.out.printf("Index%-5d:  %s %n",i+1,editableTest.get(i));
        }
    }


}
