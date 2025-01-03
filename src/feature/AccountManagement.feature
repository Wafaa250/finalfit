Feature: Account Management
  As a client or admin, I want to manage my account details to ensure they are accurate and reflect my preferences.

  Scenario: Client updates personal details
    Given I am logged in as a general client "Hiba"
    When I navigate to the "Account Management" page
    And I update my personal details with age "25" and fitness goals "Weight loss"
    Then I should see the account management message "Personal details updated successfully"

  Scenario: Client updates dietary preferences
    Given I am logged in as a general client "Hiba"
    When I navigate to the "Account Management" page
    And I update my dietary preferences to "Vegetarian"
    Then I should see the account management message "Dietary preferences updated successfully"

  Scenario: Admin updates user details
    Given I am logged in as an admin "Wafaa"
    When I navigate to the "User Management" page
    And I select the user "Hiba"
    And I update the user's email to "hiba_updated@gmail.com"
    Then I should see as admin "User details updated successfully"

  Scenario: Client tries to update account without logging in
    Given I am not logged in
    When I navigate to the "Account Management" page
    Then I should see the account management message "Access denied. Please log in to manage your account."

  Scenario: Client updates password
    Given I am logged in as a general client "Sara"
    When I navigate to the "Account Management" page
    And I update my password from "H1515" to "sara@2024"
    Then I should see the account management message "Password updated successfully"

  Scenario: Admin deletes a user account 
    Given I am logged in as an admin "Wafaa"
    When I navigate to the "User Management" page
    And I select the user "Hiba"
    And I click the delete button AM
    Then I should see as admin "User account deleted successfully"
