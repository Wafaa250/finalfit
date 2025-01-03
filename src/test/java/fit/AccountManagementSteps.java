package fit;

import io.cucumber.java.en.*;
import static org.junit.Assert.*;

import java.util.*;

public class AccountManagementSteps {

    private String result;
    private String email;
    private String password;
    private String username;
    private Map<String, String> preferences;

    @Given("I am logged in as a general client {string}")
    public void iAmLoggedInAsAGeneralClient(String clientName) {
        this.username = clientName; // تعيين اسم المستخدم
        System.out.println("Logged in as client: " + username);

        // التحقق من أن اسم المستخدم غير فارغ
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        // تأكيد أن اسم المستخدم غير فارغ (للاختبارات)
        assertNotNull("Username must not be null", username);
    }

  

    @When("I update my personal details to age {int} and fitness goal {string}")
    public void iUpdateMyPersonalDetails(int age, String fitnessGoal) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        System.out.println("Updating details for user: " + username);
        result = AccountManagement.updatePersonalDetails(username, String.valueOf(age), fitnessGoal);
    }

    @Then("I should see a confirmation message {string}")
    public void iShouldSeeAConfirmationMessage(String expectedMessage) {
        assertEquals("Confirmation message matches", expectedMessage, result);
    }

    @When("I update my dietary preferences to {string}")
    public void iUpdateMyDietaryPreferences(String preferences) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        System.out.println("Updating dietary preferences for user: " + username);
        result = AccountManagement.updateDietaryPreferences(username, preferences);
    }
    @When("I change my email to {string}")
    public void iChangeMyEmail(String newEmail) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        email = newEmail;
        System.out.println("Changing email for user: " + username + " to: " + newEmail);
        result = AccountManagement.changeEmail(username, newEmail);
    }
    
    @When("I change my password to {string}")
    public void iChangeMyPassword(String newPassword) {
        password = newPassword;
        result = AccountManagement.changePassword(username, newPassword);
    }

    @When("I confirm deletion of my account")
    public void iConfirmDeletionOfMyAccount() {
        result = AccountManagement.deleteAccount(username);
    }

    @Then("I should no longer have access to my account")
    public void iShouldNoLongerHaveAccessToMyAccount() {
        assertNull("Account should no longer exist", AccountManagement.getAccount(username));
    }

    @When("I view my account details")
    public void iViewMyAccountDetails() {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        System.out.println("Fetching account details for user: " + username);
        preferences = AccountManagement.getAccountDetails(username);
        assertNotNull("Account details exist", preferences);
    }

    @Then("I should see age {int}, fitness goal {string}, and dietary preferences {string}")
    public void iShouldSeeAgeFitnessGoalAndDietaryPreferences(int age, String fitnessGoal, String dietaryPreferences) {
        assertEquals("Age matches", String.valueOf(age), preferences.get("age"));
        assertEquals("Fitness goal matches", fitnessGoal, preferences.get("fitnessGoal"));
        assertEquals("Dietary preferences match", dietaryPreferences, preferences.get("dietaryPreferences"));
    }
    
    @When("I update my personal details with age {string} and fitness goals {string}")
    public void iUpdateMyPersonalDetailsWithAgeAndFitnessGoals(String age, String fitnessGoals) {
        System.out.println("Username: " + username); // Debugging
        result = AccountManagement.updatePersonalDetails(username, age, fitnessGoals);
    }

  
    
   
/*
    @Then("I should see the account management message {string}")
    public void iShouldSeeTheAccountManagementMessage(String expectedMessage) {
        assertNotNull("Result should not be null", result);
        assertEquals("Expected message matches result", expectedMessage.trim(), result.trim());
    }*/
    @Then("I should see the account management message {string}")
    public void iShouldSeeTheAccountManagementMessage(String expectedMessage) {
        assertNotNull("Result should not be null", result);
        System.out.println("Expected Message: " + expectedMessage); // Debugging
        System.out.println("Actual Result: " + result); // Debugging
        assertEquals("Expected message matches result", expectedMessage.trim(), result.trim());
    }


    
    
    @Given("I am logged in as an admin {string}")
    public void iAmLoggedInAsAnAdmin(String adminName) {
        this.username = adminName; // تعيين اسم المستخدم
        String adminAccount = AccountManagement.getAccount(adminName);
        if (adminAccount == null) {
            throw new IllegalArgumentException("Admin account does not exist");
        }
        assertNotNull("Admin is logged in", adminAccount);
    }

    @When("I select the user {string}")
    public void iSelectTheUser(String userName) {
        this.username = userName;
        assertNotNull("User exists", AccountManagement.getAccount(userName));
    }
    @When("I update the user's email to {string}")
    public void iUpdateTheUserSEmailTo(String newEmail) {
        result = AccountManagement.updateUserEmail(username, newEmail);
    }
    @Then("I should see as admin {string}")
    public void iShouldSeeAsAdmin(String expectedMessage) {
        assertEquals(expectedMessage, result);
    }
    
    @Given("I am not logged in")
    public void iAmNotLoggedIn() {
        this.username = null;
        this.email = null;
        this.password = null;
        result = "Access denied. Please log in to manage your account.";
        System.out.println("User is not logged in.");
    }

    
    @When("I update my password from {string} to {string}")
    public void iUpdateMyPasswordFromTo(String oldPassword, String newPassword) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        System.out.println("Attempting to update password for user: " + username);
        result = AccountManagement.updatePassword(username, oldPassword, newPassword);
        System.out.println("Password update result: " + result); // Debugging
    }


@When("I try to update my password from {string} to {string}")
public void iTryToUpdateMyPasswordFromTo(String invalidPassword, String newPassword) {
    if (username == null || username.isEmpty()) {
        throw new IllegalArgumentException("Username cannot be null or empty");
    }
    System.out.println("Attempting to update password with invalid password for user: " + username);
    result = AccountManagement.updatePassword(username, invalidPassword, newPassword);
    System.out.println("Password update result: " + result);
}
    @Then("I should see the list of all users:")
    public void iShouldSeeTheListOfAllUsers(io.cucumber.datatable.DataTable expectedUsersTable) {
        System.out.println("Fetching list of all users...");
        List<Map<String, String>> actualUsers = AccountManagement.viewAllUsers();
        List<Map<String, String>> expectedUsers = expectedUsersTable.asMaps(String.class, String.class);
        assertEquals("User lists should match", expectedUsers, actualUsers);
        System.out.println("Expected Users: " + expectedUsers);
        System.out.println("Actual Users: " + actualUsers);
    }

    @When("I click the delete button AM")
    public void iClickTheDeleteButton() {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        System.out.println("Attempting to delete user: " + username);
        result = AccountManagement.deleteUser(username);
        System.out.println("Delete result: " + result); // Debugging
    }

    
}


