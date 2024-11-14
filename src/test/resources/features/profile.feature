Feature: User Profile Management

  Scenario: Creating a new user profile
    Given I have a new user profile
    When I send a request to create the user profile
    Then the profile should be created successfully
    And I should receive a confirmation with UserID "Usuario registrado exitosamente"

  Scenario: Retrieving an existing user profile
    Given a user profile exists with UserID "673578ebc8a505278a3b57a8"
    When I request the user profile with UserID "673578ebc8a505278a3b57a8"
    Then I should receive the user profile with the following details:
      | personal_url | Nickname | ContactPublic | Address | Biography | Organization | Country | SocialLinks                       |
      | ejemplo      | ejemplo  | false         | ejemplo | ejemplo   | ejemplo      | ejemplo | ejemplito.com,ejemplito2.com      |

  Scenario: Updating an existing user profile
    Given a user profile exists with UserID "673578ebc8a505278a3b57a8"
    When I update the biography of the user profile with UserID "673578ebc8a505278a3b57a8" to "New biography"
    Then the profile should be updated successfully
    And when I request the user profile with UserID "673578ebc8a505278a3b57a8"
    Then the biography should be "New biography"