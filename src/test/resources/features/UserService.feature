Feature: User Service Acceptance Tests
  As a system administrator or user
  I want to manage user accounts including registration, login, and password recovery
  So that I can securely access the iRestaurant Portal

  Scenario: Successful User Registration
    Given a new user provides details "john_doe", "john@example.com", "SecurePass123"
    And "john_doe" or "john@example.com" does not exist in the database
    When the user submits the registration request
    Then the user account should be created successfully
    And the user should have the "User" role assigned

  Scenario: Registration fails for existing email
    Given an existing user is already registered with email "existing@example.com"
    When a new registration is attempted with "new_user", "existing@example.com", "Password123"
    Then the system should throw a "UserFoundException"

  Scenario: Successful User Login
    Given a registered user with email "alice@example.com" and password "AlicePass123"
    When the user attempts to log in with "alice@example.com" and "AlicePass123"
    Then a valid JWT token should be returned

  Scenario: Login fails with wrong password
    Given a registered user with email "alice@example.com" and password "AlicePass123"
    When the user attempts to log in with "alice@example.com" and "WrongPassword"
    Then an "InvalidCredentials" error should be raised

  Scenario: Forgot Password triggers email
    Given a registered user with email "bob@example.com"
    When the user triggers a forgot password request
    Then a reset token should be generated and stored for the user

  Scenario: Successful Password Reset
    Given a user has an active reset token "valid-token-123"
    When the user submits a password reset request with "valid-token-123" and new password "NewSecurePass88"
    Then the password is mathematically hashed and updated
    And the reset token is cleared from the account

  Scenario: Password Reset fails with expired token
    Given a user has a reset token "expired-token-456" which elapsed past 15 minutes
    When the user attempts to reset their password
    Then a "TokenExpired" error should be returned
