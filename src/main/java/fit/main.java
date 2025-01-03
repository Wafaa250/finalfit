package fit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to Fitness Management System");

        while (running) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleSignUp(scanner);
                    break;
                case 2:
                    handleLogin(scanner);
                    break;
                case 3:
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void handleSignUp(Scanner scanner) {
        SignupSource signupSource = new SignupSource();

        System.out.println("\nSign Up:");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter city: ");
        String city = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter role (Admin, Instructor, Client): ");
        String role = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();

       
        if (role.equalsIgnoreCase("Client")) {
            System.out.print("Enter your age: ");
            String age = scanner.nextLine();
            System.out.print("Enter your fitness goals: ");
            String fitnessGoals = scanner.nextLine();
            System.out.print("Enter your dietary preference: ");
            String dietaryPreference = scanner.nextLine();

            
            saveClientToFile(username, city, email, password, age, fitnessGoals, dietaryPreference);
        }

        String result = signupSource.validateSignUp(username, email, role, password, confirmPassword);
        System.out.println(result);
    }

    private static void saveClientToFile(String username, String city, String email, String password, String age, String fitnessGoals, String dietaryPreference) {
        String filePath = "C:\\Users\\HP\\OneDrive\\Desktop\\software\\fit\\src\\main\\resources\\Clientaccounts.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(username + "," + city + "," + email + "," + password + "," + age + "," + fitnessGoals + "," + dietaryPreference);
            writer.newLine();
         //   System.out.println("Client details saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving client details: " + e.getMessage());
        }
    }
    private static void handleLogin(Scanner scanner) {
        SiginSource signinSource = new SiginSource();

        System.out.println("\nLogin:");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        signinSource.checkLoginValidInFile(username, password);
        if (signinSource.getCheckValid() == 1) {
            System.out.println("Login successful. Welcome " + username + "!");
            String role = signinSource.getWorkRole();
            System.out.println("Your role is: " + role);

            switch (role.toLowerCase()) {
                case "admin":
                    adminMenu(scanner);
                    break;
                case "instructor":
                    instructorMenu(scanner, username);
                    break;
                case "client":
                    clientMenu(scanner, username);
                    break;
                default:
                    System.out.println("No specific actions for this role.");
            }
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }
    private static void adminMenu(Scanner scanner) {
        boolean adminRunning = true;
        while (adminRunning) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Subscription Plans");
            System.out.println("3. Monitor Programs");
            System.out.println("4. Manage Accounts");
            System.out.println("5. Manage Subscriptions");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleUserManagement(scanner);
                    break;
                case 2:
                    handleSubscriptionManagement(scanner);
                    break;
                case 3:
                    handleProgramMonitoring(scanner);
                    break;
                case 4:
                    handleAccountManagement(scanner);
                    break;
                case 5:
                    handleSubscriptions(scanner);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    adminRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleSubscriptions(Scanner scanner) {
        SubscriptionManagementSource subscriptionManager = new SubscriptionManagementSource();
        System.out.println("\nSubscription Management:");
        System.out.println("1. Add Subscription");
        System.out.println("2. Modify Subscription");
        System.out.println("3. Delete Subscription");
        System.out.print("Enter your choice: ");

        int subChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (subChoice) {
            case 1:
                System.out.print("Enter client email: ");
                String email = scanner.nextLine();
                System.out.print("Enter subscription plan name: ");
                String planName = scanner.nextLine();
                if (subscriptionManager.addSubscription(email, planName)) {
                    System.out.println("Subscription added successfully.");
                }
                break;
            case 2:
                System.out.print("Enter client email: ");
                email = scanner.nextLine();
                System.out.print("Enter new subscription plan name: ");
                planName = scanner.nextLine();
                if (subscriptionManager.modifySubscriptionPlan(email, planName, 0)) { // Assumes method supports changing plan by email
                    System.out.println("Subscription modified successfully.");
                }
                break;
            case 3:
                System.out.print("Enter client email to delete subscription: ");
                email = scanner.nextLine();
                if (subscriptionManager.deleteSubscriptionPlan(email)) {
                    System.out.println("Subscription deleted successfully.");
                }
                break;
        }
    }

    private static void handleProgramMonitoring(Scanner scanner) {
        ProgramMonitoring programMonitoring = new ProgramMonitoring();

        System.out.println("\nProgram Monitoring:");
        System.out.println("1. View Popular Programs");
        System.out.println("2. Generate Revenue Report");
        System.out.println("3. Monitor Attendance");
        System.out.println("4. Generate Client Progress Report");
        System.out.println("5. Track Active and Completed Programs");
        System.out.println("6. Back to Admin Menu");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println("Popular Programs:");
                List<ProgramMonitoring.FitnessProgram> programs = programMonitoring.getPopularPrograms();
                for (ProgramMonitoring.FitnessProgram program : programs) {
                    System.out.println("Program: " + program.getName() + ", Enrollment: " + program.getEnrollment());
                }
                // No file update needed for viewing
                break;
            case 2:
                System.out.println("Revenue Report:");
                programMonitoring.generateRevenueReport().forEach((name, revenue) -> {
                    System.out.println("Program: " + name + ", Revenue: $" + revenue);
                });
                // No file update needed for viewing
                break;
            case 3:
                System.out.println("Attendance Statistics:");
                programMonitoring.getAttendanceStatistics().forEach((name, attendance) -> {
                    System.out.println("Program: " + name + ", Attendance: " + attendance);
                });
                // No file update needed for viewing
                break;
            case 4:
                System.out.println("Client Progress Report:");
                programMonitoring.generateClientProgressReport().forEach((client, progress) -> {
                    System.out.println("Client: " + client + ", Progress: " + progress);
                });
                // No file update needed for viewing
                break;
            case 5:
                System.out.println("Active and Completed Programs:");
                Map<String, List<ProgramMonitoring.FitnessProgram>> statusMap = programMonitoring.getActiveAndCompletedPrograms();
                statusMap.forEach((status, programList) -> {
                    System.out.println(status + " Programs:");
                    for (ProgramMonitoring.FitnessProgram program : programList) {
                        System.out.println("- " + program.getName());
                    }
                });
                break;
            case 6:
                System.out.println("Returning to Admin Menu...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void handleAccountManagement(Scanner scanner) {
        boolean accountRunning = true;
        while (accountRunning) {
            System.out.println("\nAccount Management:");
            System.out.println("1. Update User Email");
            System.out.println("2. Delete User Account");
            System.out.println("3. Back to Admin Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username to update email: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    String emailUpdateResult = AccountManagement.updateUserEmail(username, newEmail);
                    System.out.println(emailUpdateResult);
                    updateAccountFile(username, newEmail, "C:\\Users\\HP\\OneDrive\\Desktop\\software\\fit\\src\\main\\resources\\Accounts.txt");
                    break;
                case 2:
                    System.out.print("Enter username to delete account: ");
                    username = scanner.nextLine();
                    String deleteResult = AccountManagement.deleteUser(username);
                    System.out.println(deleteResult);
                    deleteAccountFromFile(username, "C:\\Users\\HP\\OneDrive\\Desktop\\software\\fit\\src\\main\\resources\\Accounts.txt");
                    break;
                case 3:
                    accountRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

        
    private static void instructorMenu(Scanner scanner, String username) {
        boolean instructorRunning = true;
        while (instructorRunning) {
            System.out.println("\nInstructor Menu:");
            System.out.println("1. Manage Programs");
            System.out.println("2. Interact with Clients");
            System.out.println("3. View Notifications");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleProgramManagement(scanner);
                    break;
                case 2:
                    handleClientInteraction(scanner);
                    break;
                case 3:
                    viewNotifications(scanner, username);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    instructorRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void clientMenu(Scanner scanner, String username) {
        boolean clientRunning = true;
        while (clientRunning) {
            System.out.println("\nClient Menu:");
            System.out.println("1. Manage Account");
            System.out.println("2. View Notifications");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleClientAccountManagement(scanner, username);
                    break;
                case 2:
                    viewNotifications(scanner, username);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    clientRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleUserManagement(Scanner scanner) {
        UserManagementSource userManager = new UserManagementSource();

        System.out.println("\nUser Management:");
        System.out.println("1. Add User");
        System.out.println("2. Update User Role");
        System.out.println("3. Disable User");
        System.out.println("4. Approve Instructor");
        System.out.println("5. Update User Email");
        System.out.println("6. Delete User");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter city: ");
                String city = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                System.out.print("Enter role: ");
                String role = scanner.nextLine();
                System.out.println(userManager.addUser(username, city, email, password, role));
                break;
            case 2:
                System.out.print("Enter email: ");
                email = scanner.nextLine();
                System.out.print("Enter new role: ");
                String newRole = scanner.nextLine();
                System.out.println(userManager.updateUserRole(email, newRole));
                break;
            case 3:
                System.out.print("Enter email: ");
                email = scanner.nextLine();
                System.out.println(userManager.disableUser(email));
                break;
            case 4:
                System.out.print("Enter email: ");
                email = scanner.nextLine();
                System.out.println(userManager.approveInstructorRegistration(email));
                break;
            case 5:
                System.out.print("Enter username to update email: ");
                username = scanner.nextLine();
                System.out.print("Enter new email: ");
                String newEmail = scanner.nextLine();
                System.out.println(AccountManagement.updateUserEmail(username, newEmail));
                break;
            case 6:
                System.out.print("Enter username to delete: ");
                username = scanner.nextLine();
                System.out.println(AccountManagement.deleteUser(username));
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    private static void updateAccountFile(String username, String newEmail, String filePath) {
        try {
            List<String> fileContent = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(filePath));
            for (int i = 0; i < fileContent.size(); i++) {
                String[] parts = fileContent.get(i).split(",");
                if (parts[0].equals(username)) { // Assuming username is the first field
                    parts[2] = newEmail; // Update email (assuming email is the third field)
                    fileContent.set(i, String.join(",", parts));
                    break;
                }
            }
            java.nio.file.Files.write(java.nio.file.Paths.get(filePath), fileContent);
         //   System.out.println("Account file updated successfully.");
        } catch (IOException e) {
            System.out.println("Error updating account file: " + e.getMessage());
        }
    }
    private static void deleteAccountFromFile(String username, String filePath) {
        try {
            List<String> fileContent = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(filePath));
            fileContent.removeIf(line -> line.split(",")[0].equals(username)); // Assuming username is the first field
            java.nio.file.Files.write(java.nio.file.Paths.get(filePath), fileContent);
            System.out.println("Account deleted successfully from file.");
        } catch (IOException e) {
            System.out.println("Error deleting account from file: " + e.getMessage());
        }
    }
    private static void updateSubscriptionPlanFile(String planName, int price, String action, String filePath) {
        try {
            List<String> fileContent = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(filePath));
            switch (action.toLowerCase()) {
                case "add":
                    fileContent.add(planName + "," + price);
                    break;
                case "modify":
                    for (int i = 0; i < fileContent.size(); i++) {
                        String[] parts = fileContent.get(i).split(",");
                        if (parts[0].equals(planName)) { // Assuming plan name is the first field
                            parts[1] = String.valueOf(price); // Update price
                            fileContent.set(i, String.join(",", parts));
                            break;
                        }
                    }
                    break;
                case "delete":
                    fileContent.removeIf(line -> line.split(",")[0].equals(planName));
                    break;
                default:
                    System.out.println("Invalid action for subscription plan update.");
                    return;
            }
            java.nio.file.Files.write(java.nio.file.Paths.get(filePath), fileContent);
            System.out.println("Subscription plan file updated successfully.");
        } catch (IOException e) {
            System.out.println("Error updating subscription plan file: " + e.getMessage());
        }
    }


    private static void handleSubscriptionManagement(Scanner scanner) {
        SubscriptionManagementSource subscriptionManager = new SubscriptionManagementSource();

        System.out.println("\nSubscription Management:");
        System.out.println("1. Add Subscription Plan");
        System.out.println("2. Modify Subscription Plan");
        System.out.println("3. Delete Subscription Plan");
        System.out.println("4. Reject Subscription Upgrade Request");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter plan name: ");
                String planName = scanner.nextLine();
                System.out.print("Enter price: ");
                int price = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                boolean addResult = subscriptionManager.addSubscriptionPlan(planName, price);
                if (addResult) {
                    System.out.println("Subscription plan added successfully.");
                    updateSubscriptionPlanFile(planName, price, "Add", "C:\\Users\\HP\\OneDrive\\Desktop\\software\\fit\\src\\main\\resources\\SubscriptionPlans.txt");
                } else {
                    System.out.println("Plan already exists.");
                }
                break;
            case 2:
                // Similar implementation for modifying and deleting subscription plans
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
  


    private static void handleProgramManagement(Scanner scanner) {
        ProgramManagement programManager = new ProgramManagement();

        System.out.println("\nProgram Management:");
        System.out.println("1. Create Program");
        System.out.println("2. Edit Program");
        System.out.println("3. Delete Program");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter program title: ");
                String title = scanner.nextLine();
                System.out.print("Enter duration: ");
                String duration = scanner.nextLine();
                System.out.print("Enter goals: ");
                String goals = scanner.nextLine();
                System.out.print("Enter price: ");
                String price = scanner.nextLine();
                System.out.println(programManager.createProgram(title, duration, goals, price));
                break;
            case 2:
                System.out.print("Enter program title to edit: ");
                String oldTitle = scanner.nextLine();
                System.out.print("Enter new duration: ");
                duration = scanner.nextLine();
                System.out.print("Enter new price: ");
                price = scanner.nextLine();
                System.out.println(programManager.editProgram(oldTitle, duration, price));
                break;
            case 3:
                System.out.print("Enter program title to delete: ");
                title = scanner.nextLine();
                System.out.println(programManager.deleteProgram(title));
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    
    private static void handleClientAccountManagement(Scanner scanner, String username) {
        System.out.println("\nClient Account Management:");
        System.out.println("1. Update Personal Details");
        System.out.println("2. Update Dietary Preferences");
        System.out.println("3. Update Password");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter your age: ");
                String age = scanner.nextLine();
                System.out.print("Enter your fitness goals: ");
                String fitnessGoals = scanner.nextLine();
                System.out.println(AccountManagement.updatePersonalDetails(username, age, fitnessGoals));
                break;
            case 2:
                System.out.print("Enter your dietary preferences: ");
                String dietaryPreferences = scanner.nextLine();
                System.out.println(AccountManagement.updateDietaryPreferences(username, dietaryPreferences));
                break;
            case 3:
                System.out.print("Enter your current password: ");
                String currentPassword = scanner.nextLine();
                System.out.print("Enter your new password: ");
                String newPassword = scanner.nextLine();
                System.out.println(AccountManagement.updatePassword(username, currentPassword, newPassword));
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }


    private static void handleClientInteraction(Scanner scanner) {
        boolean interactionRunning = true;
        while (interactionRunning) {
            System.out.println("\nClient Interaction:");
            System.out.println("1. Send Message to Client");
           // System.out.println("2. View Messages from Client");
            System.out.println("2. Provide Feedback to Client");
            System.out.println("3. View Client Progress");
           // System.out.println("4. Respond to Discussion");
            System.out.println("4. Back to Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter client name: ");
                    String clientName = scanner.nextLine();
                    System.out.print("Enter message: ");
                    String message = scanner.nextLine();
                    System.out.println(ClientInteraction.sendMessage("Instructor", clientName, message));
                    break;
                case 2:
                    System.out.print("Enter client name: ");
                    clientName = scanner.nextLine();
                    System.out.print("Enter feedback: ");
                    String feedback = scanner.nextLine();
                    System.out.println(ClientInteraction.provideFeedback(clientName, feedback));
                    break;
                case 3:
                    System.out.print("Enter client name: ");
                    clientName = scanner.nextLine();
                    System.out.println("Progress: " + ClientInteraction.viewProgress(clientName));
                    break;
                case 4:
                    interactionRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewNotifications(Scanner scanner, String username) {
        System.out.println("\nNotifications:");
        List<String> notifications = NotificationsAndUpdates.viewNotifications(username);
        if (notifications.isEmpty()) {
            System.out.println("No new notifications.");
        } else {
            for (String notification : notifications) {
                System.out.println(notification);
            }
        }
    }

   

    private static void sendClientNotifications(String username) {
        System.out.println("Sending notifications for new program.");
        NotificationsAndUpdates.sendNotification(username, "New program available: Yoga for Beginners");
        NotificationsAndUpdates.sendNotification(username, "Program updated: Yoga for Beginners");
        NotificationsAndUpdates.sendNotification(username, "Program canceled: Yoga for Beginners");
    }

    private static void sendInstructorNotifications(String username) {
        System.out.println("Sending notifications for instructor.");
        NotificationsAndUpdates.sendNotification(username, "Client Hiba enrolled in Yoga for Beginners");
        NotificationsAndUpdates.sendNotification(username, "New message from Hiba: I have a question about my schedule.");
    }
}
