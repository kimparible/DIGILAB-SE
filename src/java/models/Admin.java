package models;

public class Admin extends User {

    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password, "Admin"); // Memanggil constructor User dengan 5 parameter
    }

    public void displayUserInfo() {
        System.out.println("Admin Info:");
        System.out.println("ID: " + getId());
        System.out.println("Username: " + getUsername());
        // ... other info
    }

    // Admin specific methods (e.g., manage users, generate reports)
    public void manageUsers() {
        // Implement logic to manage users
        System.out.println("Managing users...");
    }
}