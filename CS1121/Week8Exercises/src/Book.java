import  java.util.Scanner;
/*
Title: Book
Names: Niklas Romero
Date; 10/27/2019
Class: CS1121 L01
Project description: The program lets you modify titles, Author, published year & Genre
 of current books in the program utilizing classes and objects
*/
public class Book {   // I am creating the class to use the book object later in the code
    private String title = "Moby Dick";  // I am creating instance variables to establish properties of books
    private int yearPublished = 1851;
    private String author = "Herman Melville";
    private String genre = "Adventure Fiction";
    public Book() // The default constructor is being used showing the default for every book
    {
        getTitle();
        getYearPublished();
        getAuthor();
        getGenre();
    }

    public Book(String title, int yearPublished, String author,String genre) // constructor for creating your own book
    {
        setTitle(title); // setting all of the properties for 1 book
        setYearPublished(yearPublished);
        setAuthor(author);
        setGenre(genre);
    }

    public String toString() // print statement instead of just printing the location in memory
    {

        String text = ("The book " + title + " was published in " + yearPublished +
                " and was written by " + author + " as an  " + genre );
        return text;
    }

    public  int getYearPublished()  // allows constructor to get the value from the class
    {
        return yearPublished; // value putting into the book for this instance variable
    }
    public String getTitle()
    {
        return title;
    }
    public String getAuthor()
    {
        return author;
    }
    public String getGenre()
    {
        return genre;
    }
    public void setTitle(String title) // Allows the user to set the title of a book
    {
        this.title = title;
    }
    public void setAuthor(String author)
    {
        this.author = author;
    }
    public void setGenre(String genre)
    {
        this.genre = genre;
    }
    public  void setYearPublished(int yearPublished)
    {
        this.yearPublished = yearPublished;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Book book1 = new Book(); // creating the first book with the default constructor
        System.out.println("What is the title fo your book?");
        String title = scan.nextLine();
        System.out.println("Who wrote the book?");
        String author = scan.nextLine();
        System.out.println("What is the genre of the book?");
        String genre = scan.nextLine();
        System.out.println("When was the book published(input the numbers not the words)?");
        int yearPublished = scan.nextInt();
        Book book2 = new Book(title,yearPublished,author,genre); // creating another book with the second constructor
        System.out.println(book1);
        System.out.println(book2);
        book1.setTitle(" The walking Dead"); // overriding the first books values
        book1.setYearPublished(2003);
        book1.setAuthor("robert Kirkman");
        book1.setGenre("post apocalyptic");
        System.out.println(book1);

    }

}


