import java.util.*;
class Person {
    private String name;
    private int id;

    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() { return name; }
    public int getId() { return id; }
}

// Student class inherits from Person
class Student extends Person {
    private ArrayList<Book> issuedBooks = new ArrayList<>();

    public Student(String name, int id) {
        super(name, id);
    }

    public void issueBook(Book book) {
        issuedBooks.add(book);
    }

    public void returnBook(Book book) {
        issuedBooks.remove(book);
    }

    public void showIssuedBooks() {
        if (issuedBooks.isEmpty()) {
            System.out.println("No books issued by " + getName());
        } else {
            System.out.println(getName() + " has issued:");
            for (Book b : issuedBooks) {
                System.out.println(" - " + b.getTitle());
            }
        }
    }
}

// Librarian class inherits from Person
class Librarian extends Person {
    public Librarian(String name, int id) {
        super(name, id);
    }

    public void addBook(Library lib, Book book) {
        lib.addBook(book);
        System.out.println("Book '" + book.getTitle() + "' added by Librarian " + getName());
    }

    public void removeBook(Library lib, int bookId) {
        lib.removeBook(bookId);
        System.out.println("Book with ID " + bookId + " removed by Librarian " + getName());
    }
}

// Book class
class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return isIssued; }

    public void issue() { isIssued = true; }
    public void returned() { isIssued = false; }

    public void displayBook() {
        System.out.println("[" + bookId + "] " + title + " by " + author + " - " + (isIssued ? "Issued" : "Available"));
    }
}

// Library class (manages books)
class Library {
    private ArrayList<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(int bookId) {
        books.removeIf(b -> b.getBookId() == bookId);
    }

    public void showAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library!");
        } else {
            System.out.println("\n--- Library Books ---");
            for (Book b : books) {
                b.displayBook();
            }
        }
    }

    public Book findBook(int bookId) {
        for (Book b : books) {
            if (b.getBookId() == bookId) {
                return b;
            }
        }
        return null;
    }
}

// Main class
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Library library = new Library();
        Librarian librarian = new Librarian("Mrs. Sharma", 1);
        Student student1 = new Student("Aarav", 101);
        Student student2 = new Student("Siya", 102);

        // Add sample books
        librarian.addBook(library, new Book(201, "Java Programming", "James Gosling"));
        librarian.addBook(library, new Book(202, "Python Essentials", "Guido van Rossum"));
        librarian.addBook(library, new Book(203, "Data Structures", "Robert Lafore"));

        int choice;
        do {
            System.out.println("\n=== LIBRARY MANAGEMENT MENU ===");
            System.out.println("1. Show All Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Show Student's Issued Books");
            System.out.println("5. Add Book (Librarian)");
            System.out.println("6. Remove Book (Librarian)");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    library.showAllBooks();
                    break;

                case 2:
                    System.out.print("Enter Student ID (101 for Aarav / 102 for Siya): ");
                    int sid = sc.nextInt();
                    Student s = (sid == 101) ? student1 : student2;

                    System.out.print("Enter Book ID to issue: ");
                    int bid = sc.nextInt();
                    Book book = library.findBook(bid);
                    if (book != null && !book.isIssued()) {
                        book.issue();
                        s.issueBook(book);
                        System.out.println("Book '" + book.getTitle() + "' issued to " + s.getName());
                    } else {
                        System.out.println("Book not found or already issued!");
                    }
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    sid = sc.nextInt();
                    s = (sid == 101) ? student1 : student2;

                    System.out.print("Enter Book ID to return: ");
                    bid = sc.nextInt();
                    book = library.findBook(bid);
                    if (book != null && book.isIssued()) {
                        book.returned();
                        s.returnBook(book);
                        System.out.println("Book '" + book.getTitle() + "' returned by " + s.getName());
                    } else {
                        System.out.println("Invalid return operation!");
                    }
                    break;

                case 4:
                    System.out.print("Enter Student ID: ");
                    sid = sc.nextInt();
                    s = (sid == 101) ? student1 : student2;
                    s.showIssuedBooks();
                    break;

                case 5:
                    System.out.print("Enter new Book ID: ");
                    int nbid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Book Title: ");
                    String ntitle = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String nauthor = sc.nextLine();
                    librarian.addBook(library, new Book(nbid, ntitle, nauthor));
                    break;

                case 6:
                    System.out.print("Enter Book ID to remove: ");
                    int rb = sc.nextInt();
                    librarian.removeBook(library, rb);
                    break;

                case 7:
                    System.out.println("Exiting Library System...");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 7);

        sc.close();
    }
}
