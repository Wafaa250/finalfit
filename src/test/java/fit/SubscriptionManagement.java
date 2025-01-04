package fit;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

public class SubscriptionManagement {

    SubscriptionManagementSource subscription = new SubscriptionManagementSource();
    private boolean result;

   

    @When("I add a new plan {string} with price {int}")
    public void iAddANewPlanWithPrice(String planName, int price) {
        result = subscription.addSubscriptionPlan(planName, price);
    }

    @Then("the plan should be added")
    public void thePlanShouldBeAdded() {
        assertTrue(result, "The plan was not added successfully.");
    }

    @Given("the plan {string} exists")
    public void thePlanExists(String planName) {
        result = subscription.addSubscriptionPlan(planName, 100); // Default price
        assertTrue(result, "The plan was not added successfully.");
    }

    @When("I delete the plan {string}")
    public void iDeleteThePlan(String planName) {
        result = subscription.deleteSubscriptionPlan(planName);
    }

    @Then("it should be removed")
    public void itShouldBeRemoved() {
        assertTrue(result, "The plan was not removed successfully.");
    }

    @Given("a client has requested an upgrade with email {string}")
    public void aClientHasRequestedAnUpgradeWithEmail(String email) {
        subscription.loadUsers();
        subscription.requestUpgrade(email);
    }

    @When("I reject the upgrade for client with email {string}")
    public void iRejectTheUpgradeForClientWithEmail(String email) {
        result = subscription.rejectSubscriptionUpgrade(email);
    }

    @Then("the client's subscription should stay the same")
    public void theClientSSubscriptionShouldStayTheSame() {
        assertTrue(result, "The subscription upgrade was not correctly rejected.");
    }

    @Then("the client should be notified")
    public void theClientShouldBeNotified() {
        System.out.println("The client has been notified.");
    }

//
    @Test
    public void testModifySubscriptionPlanSuccess() {
        SubscriptionManagementSource source = new SubscriptionManagementSource();
        source.addSubscriptionPlan("Basic", 100); // أضف خطة أولاً
        boolean result = source.modifySubscriptionPlan("Basic", "Premium", 200);
        assertTrue(result, "Plan modification failed for an existing plan.");
    }

    @Test
    public void testModifySubscriptionPlanFail() {
        SubscriptionManagementSource source = new SubscriptionManagementSource();
        boolean result = source.modifySubscriptionPlan("NonExistent", "Premium", 200);
        assertFalse(result, "Plan modification succeeded for a non-existent plan.");
    }




    @Test
    public void testAddSubscriptionSuccess() {
        SubscriptionManagementSource source = new SubscriptionManagementSource();
        source.loadUsers(); // تحميل المستخدمين
        boolean result = source.addSubscription("test@example.com", "Basic Plan");
        assertTrue(result, "Subscription addition failed for an existing user.");
    }

    @Test
    public void testAddSubscriptionFail() {
        SubscriptionManagementSource source = new SubscriptionManagementSource();
        boolean result = source.addSubscription("nonexistent@example.com", "Basic Plan");
        assertFalse(result, "Subscription addition succeeded for a non-existent user.");
    }




}
