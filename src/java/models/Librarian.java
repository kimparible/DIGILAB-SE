package models;

public class Librarian extends User {

    public Librarian(int id, String name, String email, String password) {
    super(id, name, email, password, "Librarian");
}

    public void displayUserInfo() {
        System.out.println("Librarian Info:");
        System.out.println("ID: " + getId());
        System.out.println("Username: " + getUsername());
        // ... other info
    }

    // Librarian specific methods (e.g., manage books, approve requests)
    public void manageBooks() {
        // Implement logic to manage books
        System.out.println("Managing books...");
    }
}