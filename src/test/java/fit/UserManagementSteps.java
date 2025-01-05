package fit;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class UserManagementSteps {

    private UserManagementSource userManagement;
    private String resultMessage;

    @Given("I am logged in as an admin")
    public void iAmLoggedInAsAnAdmin() {
        userManagement = new UserManagementSource(); // Initialize the UserManagement system
        userManagement.addUser("Rasmiya", "Nablus", "rasmiya@gmail.com", "password123", "Instructor");
        userManagement.addUser("Hiba", "Nablus", "hiba@gmail.com", "password456", "Client");
    }

    @When("I add a new user {string} with email {string} and role {string}")
    public void iAddANewUser(String username, String email, String role) {
        resultMessage = userManagement.addUser(username, "Nablus", email, "password789", role);
    }

    @Then("the user {string} should be added successfully")
    public void theUserShouldBeAddedSuccessfully(String username) {
        assertEquals("User " + username + " added successfully.", resultMessage);
    }

    @When("I update the role of user {string} to {string}")
    public void iUpdateTheRoleOfUser(String email, String newRole) {
        resultMessage = userManagement.updateUserRole(email, newRole);
    }

    @Then("the user's role should be updated to {string}")
    public void theUsersRoleShouldBeUpdatedTo(String newRole) {
        assertEquals("User Rasmiya's role updated to " + newRole + ".", resultMessage);
    }

    @When("I disable the account of user {string}")
    public void iDisableTheAccountOfUser(String email) {
        resultMessage = userManagement.disableUser(email);
    }

    @Then("the account of {string} should be disabled")
    public void theAccountOfShouldBeDisabled(String email) {
        assertEquals("User " + email.split("@")[0] + "'s account has been disabled.", resultMessage);
    }

    @When("I approve the registration for {string}")
    public void iApproveTheRegistrationFor(String email) {
        resultMessage = userManagement.approveInstructorRegistration(email);
    }

    @Then("{string} should be approved as an instructor")
    public void shouldBeApprovedAsAnInstructor(String email) {
        assertEquals("Instructor Rasmiya has been approved.", resultMessage);
    }
    @Test
    public void testAddUser() {
        UserManagementSource ums = new UserManagementSource();
        String result1 = ums.addUser("John", "CityX", "john@example.com", "pass123", "User");
        assertEquals("User John added successfully.", result1);

        String result2 = ums.addUser("John", "CityX", "john@example.com", "pass123", "User");
        assertEquals("User already exists.", result2);
    }

    
   
}
