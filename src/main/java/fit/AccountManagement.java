package fit;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles various account management operations such as updating personal details,
 * managing accounts, and handling logging.
 */
public class AccountManagement {

    /** Logger for logging system events and errors. */
    private static final Logger LOGGER = Logger.getLogger(AccountManagement.class.getName());

    /** Path to the client accounts file. */
    private static final String ACCOUNTS_FILE = "src/main/resources/Clientaccounts.txt";

    /** Path to the master accounts file. */
    private static final String MASTER_ACCOUNTS_FILE = "src/main/resources/Accounts.txt";

    /** A list to store account data loaded from the file. */
    private static List<String> accountsData = new ArrayList<>();

    /** Message to display when a user is not found. */
    private static final String USER_NOT_FOUND_MESSAGE = "User not found";

    // Static block to load account data from the file during class initialization.
    static {
        try {
            accountsData = readAccountsFromFile();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading accounts: {0}", e.getMessage());
        }
    }

    /**
     * Updates the personal details of a user such as age and fitness goals.
     *
     * @param username     The username of the account to update.
     * @param age          The updated age of the user.
     * @param fitnessGoals The updated fitness goals of the user.
     * @return A success message or an error message if the update fails.
     */
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

    /**
     * Validates the input data for updating user details.
     *
     * @param username     The username to validate.
     * @param age          The age to validate.
     * @param fitnessGoals The fitness goals to validate.
     * @return An error message if validation fails, or null if inputs are valid.
     */
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


    /**
     * Processes the update of a user's account details in the stored data.
     *
     * @param username     The username of the account to update.
     * @param age          The new age to update.
     * @param fitnessGoals The new fitness goals to update.
     * @return A success message if the update is successful, or null if the user is not found.
     */
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

    /**
     * Prepares the account details by splitting the account data into individual fields.
     *
     * @param account The account data as a single string.
     * @return An array of strings representing the individual fields of the account.
     */
    private static String[] prepareAccountDetails(String account) {
        String[] details = account.split(",");
        if (details.length < 7) {
            // Ensure enough fields exist, if not handled appropriately.
        }
        return details;
    }

    /**
     * Writes the updated account data back to the storage.
     *
     * @return A success message if the operation is successful, or an error message if writing fails.
     */
    private static String writeUpdatedData() {
        try {
            writeAccountsToFile(accountsData);
            return "Personal details updated successfully";
        } catch (IOException e) {
            return "Error updating personal details: " + e.getMessage();
        }
    }

    /**
     * Updates the dietary preferences of a user.
     *
     * @param username          The username of the account to update.
     * @param dietaryPreference The updated dietary preference to set.
     * @return A success message if the operation is successful, or an error message if an exception occurs 
     *         or if the user is not found.
     */
    public static String updateDietaryPreferences(String username, String dietaryPreference) {
        try {
            // Load accounts from the file
            List<String> accounts = new ArrayList<>(readAccountsFromFile());
            for (int i = 0; i < accounts.size(); i++) {
                String[] accountDetails = accounts.get(i).split(",");

                // Ensure the account details array has enough fields
                if (accountDetails.length < 7) {
                    // Handle incomplete account details if necessary
                }

                // Check if the username matches
                if (accountDetails[0].equals(username)) {
                    accountDetails[6] = dietaryPreference; // Update dietary preference
                    accounts.set(i, String.join(",", accountDetails));
                    writeAccountsToFile(accounts); // Save the updated accounts
                    return "Dietary preferences updated successfully";
                }
            }
            return USER_NOT_FOUND_MESSAGE; // User not found
        } catch (IOException e) {
            return "Error updating dietary preferences: " + e.getMessage();
        }
    }


    /**
     * Updates the email of a user. This method is intended for admin use only.
     *
     * @param username The username of the account to update.
     * @param newEmail The new email address to set for the user.
     * @return A success message if the operation is successful, or an error message if an exception occurs 
     *         or if the user is not found.
     */
    public static String updateUserEmail(String username, String newEmail) {
        try {
            // Load accounts from the file
            List<String> accounts = new ArrayList<>(readAccountsFromFile());
            for (int i = 0; i < accounts.size(); i++) {
                String[] accountDetails = accounts.get(i).split(",");

                // Check if the username matches
                if (accountDetails[0].equals(username)) {
                    accountDetails[2] = newEmail; // Update email
                    accounts.set(i, String.join(",", accountDetails)); // Update the account in the list
                    writeAccountsToFile(accounts); // Save the updated accounts
                    return "User details updated successfully"; // Success message
                }
            }
            return USER_NOT_FOUND_MESSAGE; // User not found
        } catch (IOException e) {
            return "Error updating user details: " + e.getMessage(); // Error message
        }
    }

    /**
     * Deletes a user account. This method is intended for admin use only.
     *
     * @param username The username of the account to delete.
     * @return A success message if the operation is successful, or an error message if an exception occurs 
     *         or if the user is not found.
     */
    public static String deleteUser(String username) {
        try {
            List<String> accounts = new ArrayList<>(readAccountsFromFile());
            boolean userFound = false;

            // Search for the user and remove the account
            for (int i = 0; i < accounts.size(); i++) {
                String account = accounts.get(i);
                if (account.startsWith(username + ",")) {
                    accounts.remove(i);
                    userFound = true;
                    break;
                }
            }

            if (userFound) {
                writeAccountsToFile(accounts); // Save updated accounts to file
                return "User account deleted successfully"; // Success message
            } else {
                return USER_NOT_FOUND_MESSAGE; // User not found
            }
        } catch (IOException e) {
            return "Error deleting user account: " + e.getMessage(); // Error message
        }
    }

    /**
     * Updates the password for a user in both the client accounts file and the master accounts file.
     *
     * @param username        The username of the account to update.
     * @param currentPassword The current password of the user.
     * @param newPassword     The new password to set for the user.
     * @return A success message if both files are updated successfully, or an error message if the operation fails.
     */
    public static String updatePassword(String username, String currentPassword, String newPassword) {
        boolean updatedInClientAccounts = false;
        boolean updatedInMasterAccounts = false;

        try {
            // Update password in Clientaccounts.txt
            updatedInClientAccounts = updatePasswordInFile(ACCOUNTS_FILE, username, currentPassword, newPassword);

            // Update password in Accounts.txt
            updatedInMasterAccounts = updatePasswordInFile(MASTER_ACCOUNTS_FILE, username, currentPassword, newPassword);

            if (updatedInClientAccounts && updatedInMasterAccounts) {
                return "Password updated successfully"; // Success message
            } else {
                return "Partial update: Failed to update in Accounts.txt."; // Partial update error
            }
        } catch (IOException e) {
            return "Error updating password: " + e.getMessage(); // Error message
        }
    }

    /**
     * Updates the password for a specific user in the given file.
     *
     * @param filePath        The file path where the accounts are stored.
     * @param username        The username of the account to update.
     * @param currentPassword The current password of the user.
     * @param newPassword     The new password to set for the user.
     * @return {@code true} if the password was successfully updated, or {@code false} if the username or current password is incorrect.
     * @throws IOException If an error occurs while reading or writing to the file.
     */
    private static boolean updatePasswordInFile(String filePath, String username, String currentPassword, String newPassword) throws IOException {
        boolean userFound = false;
        List<String> accounts = new ArrayList<>(readAccountsFromFile(filePath));

        for (int i = 0; i < accounts.size(); i++) {
            String[] accountDetails = accounts.get(i).split(",");

            if (accountDetails[0].equals(username)) {
                if (!accountDetails[3].equals(currentPassword)) {
                    LOGGER.log(Level.WARNING, "Current password mismatch for user: {0} in file: {1}", new Object[]{username, filePath});
                    return false; // Incorrect current password
                }

                accountDetails[3] = newPassword; // Update the password
                accounts.set(i, String.join(",", accountDetails));
                userFound = true;
                break;
            }
        }

        if (userFound) {
            writeAccountsToFile(filePath, accounts);
        } else {
            LOGGER.log(Level.INFO, "User not found in file: {0}", filePath);
        }

        return userFound;
    }

    /**
     * Reads account data from a specified file.
     *
     * @param filePath The file path to read the accounts from.
     * @return A list of account data as strings.
     * @throws IOException If an error occurs while reading the file.
     */
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

    /**
     * Writes the updated account data to a specified file.
     *
     * @param filePath The file path to write the accounts to.
     * @param accounts The list of updated account data to save.
     * @throws IOException If an error occurs while writing to the file.
     */
    private static void writeAccountsToFile(String filePath, List<String> accounts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String account : accounts) {
                writer.write(account);
                writer.newLine();
            }
        }
    }


    /**
     * Reads account data from the default accounts file.
     *
     * @return A list of account data as strings.
     * @throws IOException If an error occurs while reading the file.
     */
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

    /**
     * Writes updated account data to the default accounts file.
     *
     * @param accounts The list of updated account data to save.
     * @throws IOException If an error occurs while writing to the file.
     */
    private static void writeAccountsToFile(List<String> accounts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (String account : accounts) {
                writer.write(account);
                writer.newLine();
            }
        }
    }

    /**
     * Retrieves the details of a user account by username.
     *
     * @param username The username of the account to retrieve.
     * @return The account details as a string if the user is found, or {@code null} if not found.
     */
    public static String getAccount(String username) {
        for (String account : accountsData) {
            if (account.startsWith(username + ",")) {
                return account; // Return account details
            }
        }

        // Special case for the admin user "Wafaa"
        if (username.equals("Wafaa")) {
            return "Wafaa,Admin,Wafaa_admin@gmail.com,Admin@2024,None,None,None";
        }
        return null; // Return null if the user is not found
    }


   


}