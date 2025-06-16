package service;

import dao.UserDAO;
import models.User;
import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User authenticateUser(String email, String password) {
        System.out.println("UserService.authenticateUser(): Attempting to authenticate user: " + email);

        // Panggil method getUser dari UserDAO
        User user = userDAO.getUser(email, password);

        if (user != null) {
            System.out.println("UserService.authenticateUser(): User authenticated successfully: " + email);

            // DEBUG: tampilkan data profil yang diambil
            System.out.println(" - fullName: " + user.getFullName());
            System.out.println(" - bio: " + user.getBio());
            System.out.println(" - description: " + user.getDescription());
            System.out.println(" - phone: " + user.getPhoneNumber());
            System.out.println(" - address: " + user.getAddress());

        } else {
            System.out.println("UserService.authenticateUser(): Authentication failed for user: " + email);
        }

        return user;
    }

    public void registerUser(String name, String email, String password, String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        userDAO.addUser(user);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
}
