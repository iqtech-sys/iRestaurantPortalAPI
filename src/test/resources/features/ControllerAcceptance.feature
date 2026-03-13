Feature: Controller Acceptance Tests - Authentication and Account Management
  As a client application using STOMP
  I want to interact with authentication and account endpoints
  So that I can manage my session and view financial summaries

  Scenario: User Login request via STOMP
    Given a valid login payload for "test@example.com"
    When the login message is sent to "/auth.login"
    Then the user should receive a successful AuthResponse on "/queue/login" containing a JWT

  Scenario: User Registration request via STOMP
    Given a valid registration payload for "newuser"
    When the registration message is sent to "/auth.register"
    Then the user should receive a successful AuthResponse on "/queue/register"

  Scenario: Retrieve Sales Gadgets via STOMP
    Given a valid JWT token in the session headers
    And a request for sales gadgets for branch "MainBranch"
    When the request is sent to "/account.salesGadgets"
    Then the response should contain valid expense, profit, and revenue data on "/queue/sales-gadgets"

  Scenario: Performance Heatmap request via STOMP
    Given a valid JWT token in the session headers
    And a request for performance heatmap for branch "MainBranch"
    When the request is sent to "/account.performanceHeatmap"
    Then the response should contain a list of heatmap data on "/queue/performance-heatmap"
