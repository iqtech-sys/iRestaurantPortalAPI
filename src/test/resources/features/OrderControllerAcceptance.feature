Feature: Order Controller Operational Acceptance
  As a client application
  I want to fetch order-related metrics via STOMP
  So that I can visualize branch operations

  Scenario: Fetch Unique Branches
    Given a valid JWT token in the session headers
    When a message is sent to "/order.branches"
    Then the response on "/queue/order-branches" should contain a list of branch IDs

  Scenario: Fetch Recent Orders
    Given a valid JWT token in the session headers
    And a request for recent orders with limit 5
    When the message is sent to "/order.recentOrders"
    Then the response on "/queue/recent-orders" should contain 5 order records

  Scenario: Fetch Best Performing Branch
    Given a valid JWT token in the session headers
    When the message is sent to "/order.bestPerformingBranch"
    Then the response on "/queue/best-performing-branch" should contain sorted branch data
