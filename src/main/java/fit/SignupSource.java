package fit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignupSource {

    private final List<User> users = new ArrayList<>();

    public String validateSignUp(String username, String email, String role, String password, String confirmPassword) {
        if (!validateUsername(username)) {
            return "Invalid username. It must be alphanumeric and not exceed 15 characters";
        }
        if (!validateEmail(email)) {
            return "Email must end with @gmail.com";
        }
        if (isEmailUsed(email)) {
            return "Email address is already in use. Please change it";
        }
        if (!validatePassword(password)) {
            return "Password must be at least 6 characters long and contain at least one letter";
        }
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match. Please make sure to confirm correctly";
        }
        if (!validateRole(role)) {
            return "Invalid role. Available roles are: Admin, Instructor, Client, Store Owner";
        }

        User newUser = new User(username, email, password, role);
        users.add(newUser);

        // حفظ البيانات في الملف بعد التحقق من صحة البيانات
        saveUserToFile(newUser);

        return "Account created successfully";
    }

    private boolean validateUsername(String username) {
        return username.matches("[A-Za-z0-9]{1,15}");
    }

    private boolean validateEmail(String email) {
        return email.endsWith("@gmail.com") && email.length() > 10;
    }

    private boolean validatePassword(String password) {
        return password.length() >= 6 && password.chars().anyMatch(Character::isLetter);
    }

    private boolean validateRole(String role) {
        String[] validRoles = {"Admin", "Instructor", "Client", "Store Owner"};
        return List.of(validRoles).contains(role);
    }

    private boolean isEmailUsed(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length > 2 && details[2].trim().equalsIgnoreCase(email.trim())) {
                    return true;  // البريد الإلكتروني موجود بالفعل
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;  // البريد الإلكتروني غير موجود
    }



    // دالة لحفظ المستخدم في ملف
    private void saveUserToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/Accounts.txt", true))) {
            writer.write(user.getUsername() + "," + user.getLocation() + "," + user.getEmail() + "," + user.getPassword() + "," + user.getRole());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class User {
        private String username;
        private String email;
        private String password;
        private String role;
        private String location = "Nablus"; // إضافة موقع ثابت كمثال

        public User(String username, String email, String password, String role) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.role = role;
        }

        public String getUsername() {
            return username;
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

        public String getLocation() {
            return location;
        }
    }
 

}
