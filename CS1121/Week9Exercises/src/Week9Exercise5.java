public class Week9Exercise5 {
    public static void main(String[] args) {
        int [] array1 = {1,2,3,4,5};
        int [] array2 = {2,4,6,8,10};
        System.out.println("Array1: " + array1 + "Array2: "+ array2);
array1 = array2;
        System.out.println("Array1: " + array1 + "Array2: "+ array2);
        array1[3] = 10;
        System.out.println("Vaue at index 3 in Array1: " + array1[1] + "Array2: "+ array2[1]);

    }
}
