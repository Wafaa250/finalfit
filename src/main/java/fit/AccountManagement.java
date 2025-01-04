package fit;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountManagement {
	private static final Logger LOGGER = Logger.getLogger(AccountManagement.class.getName());

	
	  private AccountManagement() {
	        throw new UnsupportedOperationException("Utility class");
	    }


    private static final String ACCOUNTS_FILE = "src/main/resources/Clientaccounts.txt";
    private static final String MASTER_ACCOUNTS_FILE = "src/main/resources/Accounts.txt";
    private static List<String> accountsData = new ArrayList<>();
    private static final String USER_NOT_FOUND_MESSAGE = "User not found";
   
    
    static {
        try {
            accountsData = readAccountsFromFile();
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, "Error loading accounts: {0}", e.getMessage());
        }
    }

    public static String updatePersonalDetails(String username, String age, String fitnessGoals) {
        // Validate inputs
        String validationError = validateInputs(username, age, fitnessGoals);
        if (validationError != null) {
            return validationError;
        }

        // Process account update
        String updateResult = processAccountUpdate(username, age, fitnessGoals);
        if (updateResult != null) {
            return updateResult;
        }

        return USER_NOT_FOUND_MESSAGE;
    }

    private static String validateInputs(String username, String age, String fitnessGoals) {
        if (username == null || username.isEmpty()) {
            return "Username cannot be null or empty";
        }
        if (age == null || age.isEmpty() || !age.matches("\\d+")) {
            return "Invalid age: must be a non-empty numeric value";
        }
        if (fitnessGoals == null || fitnessGoals.isEmpty()) {
            return "Fitness goals cannot be null or empty";
        }
        return null;
    }

    private static String processAccountUpdate(String username, String age, String fitnessGoals) {
        for (int i = 0; i < accountsData.size(); i++) {
            String account = accountsData.get(i);

            if (account.startsWith(username + ",")) { // Match username
                String[] details = prepareAccountDetails(account);

                // Update age and fitness goals
                details[4] = age;
                details[5] = fitnessGoals;
                accountsData.set(i, String.join(",", details));

                return writeUpdatedData();
            }
        }
        return null;
    }

    private static String[] prepareAccountDetails(String account) {
        String[] details = account.split(",");
        if (details.length < 7) {
        //    details = Arrays.copyOf(details, 7); // Extend the array to 7 columns
         //   if (details[4] == null || details[4].isEmpty()) details[4] = "None"; // Default age
        //    if (details[5] == null || details[5].isEmpty()) details[5] = "None"; // Default fitness goal
        //    if (details[6] == null || details[6].isEmpty()) details[6] = "None"; // Default dietary preferences
        }
        return details;
    }

    private static String writeUpdatedData() {
        try {
            writeAccountsToFile(accountsData);
            return "Personal details updated successfully";
        } catch (IOException e) {
            return "Error updating personal details: " + e.getMessage();
        }
    }


 // Method to update dietary preferences
    public static String updateDietaryPreferences(String username, String dietaryPreference) {
        try {
            List<String> accounts = new ArrayList<>(readAccountsFromFile());
            for (int i = 0; i < accounts.size(); i++) {
                String[] accountDetails = accounts.get(i).split(",");

                // Ensure array has enough fields (7 columns)
                if (accountDetails.length < 7) {
                 //   accountDetails = Arrays.copyOf(accountDetails, 7);
                  //  if (accountDetails[4] == null || accountDetails[4].isEmpty()) accountDetails[4] = "None"; // Default age
                  //  if (accountDetails[5] == null || accountDetails[5].isEmpty()) accountDetails[5] = "None"; // Default fitness goal
                 //   if (accountDetails[6] == null || accountDetails[6].isEmpty()) accountDetails[6] = "None"; // Default dietary preferences
                }

                if (accountDetails[0].equals(username)) {
                    accountDetails[6] = dietaryPreference; // Update dietary preference
                    accounts.set(i, String.join(",", accountDetails));
                    writeAccountsToFile(accounts);
                    return "Dietary preferences updated successfully";
                }
            }
            return USER_NOT_FOUND_MESSAGE;
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
            return USER_NOT_FOUND_MESSAGE;
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
            	return USER_NOT_FOUND_MESSAGE;
            }
        } catch (IOException e) {
            return "Error deleting user account: " + e.getMessage();
        }
    }
  



   

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
            } /*else if (!updatedInClientAccounts && !updatedInMasterAccounts) {
                return "User not found or current password incorrect in both files.";
            } else if (!updatedInClientAccounts) {
                return "Partial update: Failed to update in Clientaccounts.txt.";
            }*/ else {
                return "Partial update: Failed to update in Accounts.txt.";
            }
        } catch (IOException e) {
            return "Error updating password: " + e.getMessage();
        }
    }

    private static boolean updatePasswordInFile(String filePath, String username, String currentPassword, String newPassword) throws IOException {
        boolean userFound = false;
        List<String> accounts = new ArrayList<>(readAccountsFromFile(filePath));

        for (int i = 0; i < accounts.size(); i++) {
            String[] accountDetails = accounts.get(i).split(",");

            if (accountDetails[0].equals(username)) {
              //  System.out.println("Found user: " + username + " in file: " + filePath); // Debugging log
                if (!accountDetails[3].equals(currentPassword)) {
                	 LOGGER.log(Level.WARNING, "Current password mismatch for user: {0} in file: {1}", new Object[]{username, filePath}); // Debugging log
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
            LOGGER.log(Level.INFO, "User not found in file: {0}", filePath); // Debugging log
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
       
        if (username.equals("Wafaa")) { 
            return "Wafaa,Admin,Wafaa_admin@gmail.com,Admin@2024,None,None,None"; 
        }
        return null; // Return null if user not found
    }

    
    
 


   


}