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
        this.username = clientName;
        assertNotNull("Username must not be null", username);
    }

    @Given("I am not logged in")
    public void iAmNotLoggedIn() {
        this.username = null;
        this.email = null;
        this.password = null;
        result = "Access denied. Please log in to manage your account.";
    }

    @When("I update my personal details with age {string} and fitness goals {string}")
    public void iUpdateMyPersonalDetailsWithAgeAndFitnessGoals(String age, String fitnessGoals) {
        result = AccountManagement.updatePersonalDetails(username, age, fitnessGoals);
    }

    @When("I update my dietary preferences to {string}")
    public void iUpdateMyDietaryPreferences(String dietaryPreference) {
        result = AccountManagement.updateDietaryPreferences(username, dietaryPreference);
    }

    @When("I update my password from {string} to {string}")
    public void iUpdateMyPasswordFromTo(String oldPassword, String newPassword) {
        result = AccountManagement.updatePassword(username, oldPassword, newPassword);
    }

    

    @When("I select the user {string}")
    public void iSelectTheUser(String userName) {
        this.username = userName;
        assertNotNull("User exists", AccountManagement.getAccount(userName));
    }

    @When("I update the user's email to {string}")
    public void iUpdateTheUsersEmailTo(String newEmail) {
        result = AccountManagement.updateUserEmail(username, newEmail);
    }

 

    @When("I click the delete button AM")
    public void iClickTheDeleteButton() {
        result = AccountManagement.deleteUser(username);
    }

    @Then("I should see the account management message {string}")
    public void iShouldSeeTheAccountManagementMessage(String expectedMessage) {
        assertNotNull("Result should not be null", result);
        assertEquals(expectedMessage.trim(), result.trim());
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
    @Then("I should see as admin {string}")
    public void iShouldSeeAsAdmin(String expectedMessage) {
        assertNotNull("Result should not be null", result);
        assertEquals("Expected message matches result", expectedMessage, result);
    }

}