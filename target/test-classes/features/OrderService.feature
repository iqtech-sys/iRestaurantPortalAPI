Feature: Order Service Management and Metrics
  As a restaurant operations manager
  I want to track order volumes, top items, and branch performance
  So that I can optimize the menu and operational efficiency

  Scenario: Retrieve unique branch identifiers
    Given 50 historical orders spread across "Branch X", "Branch Y", and "Branch X"
    When the service retrieves unique branch IDs
    Then the result should contain exactly 2 unique branch IDs

  Scenario: Fetch recent orders with a limit
    Given a branch "Branch Y" processed 300 total orders
    When a request is made for recent orders with a limit of 10
    Then exactly 10 order records should be returned
    And each record must contain a valid ID, Order Number, and Total Amount

  Scenario: Rank top-selling items globally
    Given "Item A" was sold 50 times across invoices
    And "Item B" was sold 20 times across invoices
    When the service compiles the top sold items globally
    Then "Item A" should dynamically rank higher than "Item B"

  Scenario: Multi-branch comparison metrics
    Given multiple branches recorded transactions in the database
    When a user queries branch comparisons specifying 2024 ranges
    Then the engine computes Revenue, Profit, Currency, and Orders amount specifically grouped by distinct locations

  Scenario: Identify the best performing branch
    Given "Branch North" has higher net profit than "Branch South"
    When the best performing branch analysis is triggered
    Then "Branch North" should appear at the top of the performance list

  Scenario: Measure standard compliance metrics
    Given orders for "Branch Alpha" have an average preparation time of 15 minutes
    And the actual average delivery time was 12 minutes
    When compliance metrics are calculated for "Branch Alpha"
    Then the compliance status should be marked as "True"

  Scenario: Centralized menu performance analysis
    Given "Pizza" was sold in "Branch A" for 1000 total and "Branch B" for 500 total
    When the menu performance is analyzed
    Then "Pizza" should report a total sales of 1500
    And "Branch A" should be identified as the best sell branch
    And "Branch B" should be identified as the worst sell branch
