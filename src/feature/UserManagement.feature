Feature: User Management
  As an admin
  I want to manage users
  So that I can add, update, disable, and approve instructors.

  Scenario: Adding a new user
    Given I am logged in as an admin
    When I add a new user "omar" with email "omar@gmail.com" and role "Instructor"
    Then the user "omar" should be added successfully

  Scenario: Updating a user's role
    Given I am logged in as an admin
    When I update the role of user "rasmiya@gmail.com" to "Client"
    Then the user's role should be updated to "Client"

  Scenario: Disabling a user's account
    Given I am logged in as an admin
    When I disable the account of user "hiba@gmail.com"
    Then the account of "Hiba" should be disabled

  Scenario: Approving a new instructor registration
    Given I am logged in as an admin
    When I approve the registration for "rasmiya@gmail.com"
    Then "Rasmiya" should be approved as an instructor
