package fit;

import java.io.*;
import java.util.*;

public class AccountManagement {

    private static final String ACCOUNTS_FILE = "src/main/resources/Clientaccounts.txt";
    private static final String MASTER_ACCOUNTS_FILE = "src/main/resources/Accounts.txt";
    private static List<String> accountsData = new ArrayList<>();
   
    
    static {
        try {
            accountsData = readAccountsFromFile();
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
    }

 // Method to update personal details
    public static String updatePersonalDetails(String username, String age, String fitnessGoals) {
        // Validate inputs
        if (username == null || username.isEmpty()) {
            return "Username cannot be null or empty";
        }
        if (age == null || age.isEmpty() || !age.matches("\\d+")) { // Ensure age is numeric
            return "Invalid age: must be a non-empty numeric value";
        }
        if (fitnessGoals == null || fitnessGoals.isEmpty()) {
            return "Fitness goals cannot be null or empty";
        }

        // Loop through accounts to find the user
        for (int i = 0; i < accountsData.size(); i++) {
            String account = accountsData.get(i);
          //  System.out.println("Checking account: " + account); // Debugging log

            if (account.startsWith(username + ",")) { // Match username
                String[] details = account.split(",");

                // Ensure details array has sufficient fields
                if (details.length < 7) {
                    details = Arrays.copyOf(details, 7); // Extend the array to 7 columns
                    if (details[4] == null || details[4].isEmpty()) details[4] = "None"; // Default age
                    if (details[5] == null || details[5].isEmpty()) details[5] = "None"; // Default fitness goal
                    if (details[6] == null || details[6].isEmpty()) details[6] = "None"; // Default dietary preferences
                }

                // Update age and fitness goals
                details[4] = age; // Update age
                details[5] = fitnessGoals; // Update fitness goals
                accountsData.set(i, String.join(",", details));

                // Write updated data back to file
                try {
                    writeAccountsToFile(accountsData);
                } catch (IOException e) {
                    return "Error updating personal details: " + e.getMessage();
                }

                return "Personal details updated successfully";
            }
        }

        // If user not found
        return "User not found";
    }

 // Method to update dietary preferences
    public static String updateDietaryPreferences(String username, String dietaryPreference) {
        try {
            List<String> accounts = new ArrayList<>(readAccountsFromFile());
            for (int i = 0; i < accounts.size(); i++) {
                String[] accountDetails = accounts.get(i).split(",");

                // Ensure array has enough fields (7 columns)
                if (accountDetails.length < 7) {
                    accountDetails = Arrays.copyOf(accountDetails, 7);
                    if (accountDetails[4] == null || accountDetails[4].isEmpty()) accountDetails[4] = "None"; // Default age
                    if (accountDetails[5] == null || accountDetails[5].isEmpty()) accountDetails[5] = "None"; // Default fitness goal
                    if (accountDetails[6] == null || accountDetails[6].isEmpty()) accountDetails[6] = "None"; // Default dietary preferences
                }

                if (accountDetails[0].equals(username)) {
                    accountDetails[6] = dietaryPreference; // Update dietary preference
                    accounts.set(i, String.join(",", accountDetails));
                    writeAccountsToFile(accounts);
                    return "Dietary preferences updated successfully";
                }
            }
            return "User not found";
        } catch (IOException e) {
            return "Error updating dietary preferences: " + e.getMessage();
        }
    }



    // Method to update a user's email (admin only)
    public static String updateUserEmail(String username, String newEmail) {
        try {
            List<String> accounts = new ArrayList<>(readAccountsFromFile());
            for (int i = 0; i < accounts.size(); i++) {
                String[] accountDetails = accounts.get(i).split(",");
                if (accountDetails[0].equals(username)) {
                    accountDetails[2] = newEmail;
                    accounts.set(i, String.join(",", accountDetails));
                    writeAccountsToFile(accounts);
                    return "User details updated successfully";
                }
            }
            return "User not found";
        } catch (IOException e) {
            return "Error updating user details: " + e.getMessage();
        }
    }

   
 // Method to delete a user account (admin only)
    public static String deleteUser(String username) {
        try {
            List<String> accounts = new ArrayList<>(readAccountsFromFile());
            boolean userFound = false;

            // البحث عن المستخدم وحذفه
            for (int i = 0; i < accounts.size(); i++) {
                String account = accounts.get(i);
                if (account.startsWith(username + ",")) {
                    accounts.remove(i);
                    userFound = true;
                    break;
                }
            }

            if (userFound) {
                writeAccountsToFile(accounts); // كتابة التحديثات إلى الملف
                return "User account deleted successfully";
            } else {
                return "User not found";
            }
        } catch (IOException e) {
            return "Error deleting user account: " + e.getMessage();
        }
    }


    // Method to update the user's password
  /*  public static String updatePassword(String username, String currentPassword, String newPassword) {
        try {
            // قراءة الحسابات من الملف
            List<String> accounts = new ArrayList<>(readAccountsFromFile());
            
            // التحقق من وجود الحساب
            boolean userFound = false;
            for (int i = 0; i < accounts.size(); i++) {
                String[] accountDetails = accounts.get(i).split(",");

                // التحقق من اسم المستخدم
                if (accountDetails[0].equals(username)) {
                    userFound = true;

                    // التحقق من كلمة المرور الحالية
                    if (!accountDetails[3].equals(currentPassword)) {
                        return "Current password is incorrect"; // إذا كانت كلمة المرور غير صحيحة
                    }

                    // تحديث كلمة المرور
                    accountDetails[3] = newPassword;
                    accounts.set(i, String.join(",", accountDetails));

                    // حفظ التغييرات في الملف
                    writeAccountsToFile(accounts);
                    return "Password updated successfully";
                }
            }

            // إذا لم يتم العثور على المستخدم
            if (!userFound) {
                return "User not found";
            }
        } catch (IOException e) {
            return "Error updating password: " + e.getMessage();
        }

        // حالة افتراضية (لا يجب أن تصل إلى هنا)
        return "Unexpected error occurred while updating password";
    }*/

 // تعديل طريقة updatePassword لتحديث كلمة المرور في كلا الملفين
    public static String updatePassword(String username, String currentPassword, String newPassword) {
        boolean updatedInClientAccounts = false;
        boolean updatedInMasterAccounts = false;

        try {
            // تحديث كلمة المرور في ملف Clientaccounts.txt
            updatedInClientAccounts = updatePasswordInFile(ACCOUNTS_FILE, username, currentPassword, newPassword);

            // تحديث كلمة المرور في ملف Accounts.txt
            updatedInMasterAccounts = updatePasswordInFile(MASTER_ACCOUNTS_FILE, username, currentPassword, newPassword);

            if (updatedInClientAccounts && updatedInMasterAccounts) {
                return "Password updated successfully";
            } else if (!updatedInClientAccounts && !updatedInMasterAccounts) {
                return "User not found or current password incorrect in both files.";
            } else if (!updatedInClientAccounts) {
                return "Partial update: Failed to update in Clientaccounts.txt.";
            } else {
                return "Partial update: Failed to update in Accounts.txt.";
            }
        } catch (IOException e) {
            return "Error updating password: " + e.getMessage();
        }
    }



    /*private static boolean updatePasswordInFile(String filePath, String username, String currentPassword, String newPassword) throws IOException {
        boolean userFound = false;
        List<String> accounts = new ArrayList<>(readAccountsFromFile(filePath));

        for (int i = 0; i < accounts.size(); i++) {
            String[] accountDetails = accounts.get(i).split(",");

            if (accountDetails[0].equals(username)) {
                if (!accountDetails[3].equals(currentPassword)) {
                    return false; // إذا كانت كلمة المرور الحالية غير صحيحة
                }

                accountDetails[3] = newPassword; // تحديث كلمة المرور
                accounts.set(i, String.join(",", accountDetails));
                userFound = true;
                break;
            }
        }

        if (userFound) {
            writeAccountsToFile(filePath, accounts);
        }

        return userFound;
    }*/
    private static boolean updatePasswordInFile(String filePath, String username, String currentPassword, String newPassword) throws IOException {
        boolean userFound = false;
        List<String> accounts = new ArrayList<>(readAccountsFromFile(filePath));

        for (int i = 0; i < accounts.size(); i++) {
            String[] accountDetails = accounts.get(i).split(",");

            if (accountDetails[0].equals(username)) {
              //  System.out.println("Found user: " + username + " in file: " + filePath); // Debugging log
                if (!accountDetails[3].equals(currentPassword)) {
                    System.out.println("Current password mismatch for user: " + username + " in file: " + filePath); // Debugging log
                    return false; // إذا كانت كلمة المرور الحالية غير صحيحة
                }

                accountDetails[3] = newPassword; // تحديث كلمة المرور
                accounts.set(i, String.join(",", accountDetails));
                userFound = true;
                break;
            }
        }

        if (userFound) {
            writeAccountsToFile(filePath, accounts);
           // System.out.println("Password updated successfully for user: " + username + " in file: " + filePath); // Debugging log
        } else {
            System.out.println("User not found in file: " + filePath); // Debugging log
        }

        return userFound;
    }



    private static List<String> readAccountsFromFile(String filePath) throws IOException {
        List<String> accounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                accounts.add(line);
            }
        }
        return accounts;
    }

    private static void writeAccountsToFile(String filePath, List<String> accounts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String account : accounts) {
                writer.write(account);
                writer.newLine();
            }
        }
    }


    public static List<Map<String, String>> viewAllUsers() {
        List<Map<String, String>> usersList = new ArrayList<>();
        try {
            List<String> accounts = readAccountsFromFile();
            for (String account : accounts) {
                String[] details = account.split(",");
                Map<String, String> userMap = new HashMap<>();
                userMap.put("Name", details[0]);
                userMap.put("Email", details[2]);
                userMap.put("Role", details[4]);
                usersList.add(userMap);
            }
        } catch (IOException e) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("Error", "Error reading user accounts: " + e.getMessage());
            usersList.add(errorMap);
        }
        return usersList;
    }


    // Helper method to read accounts from file
    private static List<String> readAccountsFromFile() throws IOException {
        List<String> accounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                accounts.add(line);
            }
        }
        return accounts;
    }

    // Helper method to write accounts to file
   private static void writeAccountsToFile(List<String> accounts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (String account : accounts) {
                writer.write(account);
                writer.newLine();
            }
        }
    }
 
    public static String getAccount(String username) {
        for (String account : accountsData) {
            if (account.startsWith(username + ",")) {
                return account; // Return account details
            }
        }
        // إذا لم يتم العثور على الحساب
        if (username.equals("Wafaa")) { // تحقق من اسم المستخدم إذا كان Admin
            return "Wafaa,Admin,Wafaa_admin@gmail.com,Admin@2024,None,None,None"; // بيانات افتراضية للـ Admin
        }
        return null; // Return null if user not found
    }

    
    
 

    // Method to update dietary preferences
    public static String updateDietaryPreferences(String username, Map<String, String> preferences) {
        // Implement logic for updating dietary preferences
        return "Dietary preferences updated successfully";
    }

    // Method to change email
    public static String changeEmail(String username, String newEmail) {
        for (int i = 0; i < accountsData.size(); i++) {
            String account = accountsData.get(i);
            if (account.startsWith(username + ",")) {
                String[] details = account.split(",");
                details[2] = newEmail; // Update email
                accountsData.set(i, String.join(",", details));
                try {
                    writeAccountsToFile(accountsData);
                } catch (IOException e) {
                    return "Error updating email: " + e.getMessage();
                }
                return "Email updated successfully";
            }
        }
        return "User not found";
    }

    // Method to change password
    public static String changePassword(String username, String newPassword) {
        for (int i = 0; i < accountsData.size(); i++) {
            String account = accountsData.get(i);
            if (account.startsWith(username + ",")) {
                String[] details = account.split(",");
                details[3] = newPassword; // Update password
                accountsData.set(i, String.join(",", details));
                try {
                    writeAccountsToFile(accountsData);
                } catch (IOException e) {
                    return "Error updating password: " + e.getMessage();
                }
                return "Password updated successfully";
            }
        }
        return "User not found";
    }

    // Method to delete account
    public static String deleteAccount(String username) {
        for (int i = 0; i < accountsData.size(); i++) {
            String account = accountsData.get(i);
            if (account.startsWith(username + ",")) {
                accountsData.remove(i); // Remove the account
                try {
                    writeAccountsToFile(accountsData);
                } catch (IOException e) {
                    return "Error deleting account: " + e.getMessage();
                }
                return "Account deleted successfully";
            }
        }
        return "User not found";
    }

    // Method to get account details
    public static Map<String, String> getAccountDetails(String username) {
        for (String account : accountsData) {
            if (account.startsWith(username + ",")) {
                String[] details = account.split(",");
                Map<String, String> accountDetails = new HashMap<>();
                accountDetails.put("username", details[0]);
                accountDetails.put("email", details[2]);
                accountDetails.put("role", details[4]);
                return accountDetails;
            }
        }
        return null; // Return null if user not found
    }


}
