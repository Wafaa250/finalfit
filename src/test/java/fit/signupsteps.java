package fit;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Signupsteps {

    SignupSource signupSource = new SignupSource();

    private String username;
    private String email;
    private String role;
    private String password;
    private String confirmPassword;
    private String actualMessage;

    @Given("I am on the sign-up page")
    public void iAmOnTheSignUpPage() {
        System.out.println("Navigating to the sign-up page.");
    }

    @When("I enter a valid username {string}")
    public void iEnterAValidUsername(String usernameInput) {
        username = usernameInput;
    }

    @When("I enter an invalid username {string}")
    public void iEnterAnInvalidUsername(String usernameInput) {
        username = usernameInput;
    }

    @When("I enter a valid email {string}")
    public void iEnterAValidEmail(String emailInput) {
        email = emailInput;
    }

    @When("I enter an invalid email {string}")
    public void iEnterAnInvalidEmail(String emailInput) {
        email = emailInput;
    }

    @When("I enter a valid role {string}")
    public void iEnterAValidRole(String roleInput) {
        role = roleInput;
    }

    @When("I enter an invalid role {string}")
    public void iEnterAnInvalidRole(String roleInput) {
        role = roleInput;
    }

    @When("I enter a valid password {string}")
    public void iEnterAValidPassword(String passwordInput) {
        password = passwordInput;
    }

    @When("I enter an invalid password {string}")
    public void iEnterAnInvalidPassword(String passwordInput) {
        password = passwordInput;
    }

    @When("I confirm the password {string}")
    public void iConfirmThePassword(String confirmPasswordInput) {
        confirmPassword = confirmPasswordInput;
    }

    @When("I click the sign-up button")
    public void iClickTheSignUpButton() {
        actualMessage = signupSource.validateSignUp(username, email, role, password, confirmPassword);
    }

    @Then("I should see {string}")
    public void iShouldSee(String expectedMessage) {
        assertEquals(expectedMessage, actualMessage);
        
    }
}
