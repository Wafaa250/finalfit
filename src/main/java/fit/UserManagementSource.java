package fit;

import java.util.HashMap;
import java.util.Map;

import java.util.logging.Logger;

public class UserManagementSource {

    private static final String USER_LABEL = "User "; // Constant for "User "
    private Map<String, User> users = new HashMap<>(); // HashMap to store users with email as the key
    private static final Logger LOGGER = Logger.getLogger(UserManagementSource.class.getName());

    // Method to add a new user
    public String addUser(String username, String city, String email, String password, String role) {
        if (users.containsKey(email.toLowerCase())) {
            return USER_LABEL + "already exists.";
        }
        User newUser = new User(username, city, email.toLowerCase(), password, role);
        users.put(email.toLowerCase(), newUser);
        return USER_LABEL + username + " added successfully.";
    }

    // Method to update a user's role
    public String updateUserRole(String email, String newRole) {
        User user = users.get(email.toLowerCase());
        if (user == null) {
            return USER_LABEL + "does not exist.";
        }
        user.setRole(newRole);
        return USER_LABEL + user.getUsername() + "'s role updated to " + newRole + ".";
    }

    // Method to disable a user's account
    public String disableUser(String email) {
        User user = users.get(email.toLowerCase());
        if (user == null) {
            return USER_LABEL + "does not exist.";
        }
        user.setActive(false);
        return USER_LABEL + user.getUsername() + "'s account has been disabled.";
    }

    // Method to approve an instructor's registration
    public String approveInstructorRegistration(String email) {
        User user = users.get(email.toLowerCase());
        if (user == null) {
            return USER_LABEL + "does not exist.";
        }
        if (!"Instructor".equalsIgnoreCase(user.getRole())) {
            return USER_LABEL + "is not an Instructor.";
        }
        user.setActive(true);
        return "Instructor " + user.getUsername() + " has been approved.";
    }

   /* // Method to check if a user exists
    public boolean doesUserExist(String email) {
        return users.containsKey(email.toLowerCase());
    }
*/
    

    // Inner class for User
    public static class User {
        private String username;
        private String city;
        private String email;
        private String password;
        private String role;
        private boolean active;

        // Constructor with required parameters
        public User(String username, String city, String email, String password, String role) {
            this.username = username;
            this.city = city;
            this.email = email;
            this.password = password;
            this.role = role;
            this.active = false; // By default, users are inactive
        }

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public String getCity() {
            return city;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        // Override toString to print user details easily
        @Override
        public String toString() {
            return USER_LABEL + "Name: " + getUsername() + ", Email: " + getEmail() + ", Role: " + getRole();
        }
    }
}
