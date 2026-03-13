Feature: Account Service Financial Analytics
  As a financial manager
  I want to analyze revenues, expenses, and profits
  So that I can monitor the fiscal health of various restaurant branches

  Scenario: Calculate total expenses for a specific branch
    Given historical invoices for branch "Branch A" totaling 500 dollars marked as added
    And historical invoices for branch "Branch A" totaling 200 dollars marked as outgoing
    When the financial engine calculates expenses for "Branch A"
    Then the result should be exactly 500.0

  Scenario: Measure net profit for a branch
    Given a branch "Branch B" has 5000 dollars in gross revenue
    And total expenses amounting to 3000 dollars for the same period
    When the service calculates the profit for "Branch B"
    Then the resulting profit value should be 2000.0

  Scenario: Generate Performance Heatmap sorted by profit
    Given daily revenue is generated uniquely for exactly 5 branches
    When a request is made for the performance heatmap
    Then the output list should be sorted precisely descending based on highest revenue score
    And each entry must contain a branch ID, profit, and month string
    And no empty or undefined branch instances should be included
