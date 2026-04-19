Feature: Login Functionality

  Scenario Outline: LoginTest1
    Given User is on login page for test case "<TestCaseID>" from sheet "LoginData"
    When User enters login credentials and submits
    Then Login result should be validated for test case "<TestCaseID>"

    Examples:
      | TestCaseID |
      | LoginTest1 |


  Scenario Outline: LoginTest2
    Given User is on login page for test case "<TestCaseID>" from sheet "LoginData"
    When User enters login credentials and submits
    Then User should get error message "<TestCaseID>"
    Examples:
      | TestCaseID |
      | LoginTest2 |