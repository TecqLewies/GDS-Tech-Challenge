@ContactDetails
Feature: Contact Details check
  Background:
    Given Applicant navigates to portal
    And Applicant logs in successfully
    And Applicant navigates to contact details form

  @Smoke @Functional @Positive @Integration
  Scenario: Manually enter all sub-sections and their fields in Contact Details section
    When Applicant enters data for "Lewies", "Test Engineer", "91239123", "lewiesthor@ecquaria.com", "lewiesthoryunsheng@ecquaria.com"
    And Applicant manually enters mailing address with "650320", "03", "33", ""
    And Applicant manually enters letter of offer with "Lewies Thor", "QA Engineer", "lewies@ecquaria.com"
    And Applicant saves and reloads the page
    Then Direct applicant to proposal submission

  @Functional @Positive @Integration
  Scenario: Manually enter main contact and LOO sub-section and their fields and checking address checkbox in Contact Details section
    When Applicant enters data for "Lewies", "Test Engineer", "91239123", "lewiesthor@ecquaria.com", "lewiesthoryunsheng@ecquaria.com"
    And Applicant indicates same address as company profile
    And Applicant manually enters letter of offer with "Lewies Thor", "QA Engineer", "lewies@ecquaria.com"
    And Applicant saves and reloads the page
    Then Direct applicant to proposal submission

  @Functional @Positive @Integration
  Scenario: Manually enter main contact and address sub-section and their fields and checking LOO checkbox in Contact Details section
    When Applicant enters data for "Lewies", "Test Engineer", "91239123", "lewiesthor@ecquaria.com", "lewiesthoryunsheng@ecquaria.com"
    And Applicant manually enters mailing address with "650320", "03", "33", ""
    And Applicant indicates LOO addressee is same as main contact person
    And Applicant saves and reloads the page
    Then Direct applicant to proposal submission

  @Smoke @Functional @Positive @Integration
  Scenario: Manually enter main contact sub-section and their fields and checking address and LOO checkbox in Contact Details section
    When Applicant enters data for "Lewies", "Test Engineer", "91239123", "lewiesthor@ecquaria.com", "lewiesthoryunsheng@ecquaria.com"
    And Applicant indicates same address as company profile
    And Applicant indicates LOO addressee is same as main contact person
    And Applicant saves and reloads the page
    Then Direct applicant to proposal submission

  @Functional @Negative @Integration
  Scenario Outline: Invalid Data for manual entry of main contact details for field name
    When Applicant enters data for "<name>", "Test Engineer", "91239123", "lewiesthor@ecquaria.com", "lewiesthoryunsheng@ecquaria.com"
    And Applicant manually enters mailing address with "650320", "03", "33", ""
    And Applicant manually enters letter of offer with "Lewies Thor", "QA Engineer", "lewies@ecquaria.com"
    Then Check error message for "name" field "<truefalse>"
    When Applicant saves and reloads the page and checks the values on section
    Then Direct applicant to proposal submission
    Examples:
      | name | truefalse |
      | Lewies | true |
      | lewies | true |
      | LEWIES | true |
      | LewiesLewiesLewiesLewiesLewiesLewiesLewiesLewiesLewiesLewiesLewiesLewies | true |
      |  | false      |
#      | Lewies1                                                                  | false |
#      | Lewies!                                                                  | false |
#      | 123                                                                      | false |
#      | !@#                                                                      | false |

  @Functional @Negative @Integration
  Scenario Outline: Invalid Data for manual entry of main contact details for field jobtitle
    When Applicant enters data for "Lewies", "<job>", "91239123", "lewiesthor@ecquaria.com", "lewiesthoryunsheng@ecquaria.com"
    And Applicant manually enters mailing address with "650320", "03", "33", ""
    And Applicant manually enters letter of offer with "Lewies Thor", "QA Engineer", "lewies@ecquaria.com"
    Then Check error message for "designation" field "<truefalse>"
    When Applicant saves and reloads the page
    Then Direct applicant to proposal submission
    Examples:
      | job | truefalse |
      | Test Engineer | true |
      | test engineer | true |
      | TEST ENGINEER | true |
      | Test Engineer 1                                                                | true |
      | Test EngineerTest EngineerTest EngineerTest EngineerTest EngineerTest Engineer | true |
      |                                                                                | false |
#      | Test Engineer !                                                                | false |
#      | 123                                                                            | false |
#      | !@#                                                                            | false |


  @Functional @Negative @Integration
  Scenario Outline: Invalid Data for manual entry of main contact details for field contactnumber
    When Applicant enters data for "Lewies", "Test Engineer", "<contactnumber>", "lewiesthor@ecquaria.com", "lewiesthoryunsheng@ecquaria.com"
    And Applicant manually enters mailing address with "650320", "03", "33", ""
    And Applicant manually enters letter of offer with "Lewies Thor", "QA Engineer", "lewies@ecquaria.com"
    Then Check error message for "phone" field "<truefalse>"
    When Applicant saves and reloads the page
    Then Direct applicant to proposal submission
    Examples:
      | contactnumber | truefalse |
      | 91239123 | true |
      | 912391234 | true |
      | 91239123912391239123912391239123912391239123912391239123912391239123 | true |
      | 9123912a                                                            | true  |
      | 9123912!                                                            | true  |
      | abc                                                                 | true  |
      | !@#                                                                 | true  |
      | 9123912   | false |
      |                                                                      | false |

  @Functional @Negative @Integration
  Scenario Outline: Invalid Data for manual entry of main contact details for field name email
    When Applicant enters data for "Lewies", "Test Engineer", "91239123", "<email>", "lewiesthoryunsheng@ecquaria.com"
    And Applicant manually enters mailing address with "650320", "03", "33", ""
    And Applicant manually enters letter of offer with "Lewies Thor", "QA Engineer", "lewies@ecquaria.com"
    Then Check error message for "primary_email" field "<truefalse>"
    When Applicant saves and reloads the page
    Then Direct applicant to proposal submission
    Examples:
      | email | truefalse |
      | lewiesthor@ecquaria.com | true |
      | lewiesthor@ecquaria.com.sg | true |
      | lewies.thor@ecquaria.com   | true |
      | lewies-thor@ecquaria.com   | true |
      | lewies_thor@ecquaria.com   | true |
      | lewies+thor@ecquaria.com   | true |
      | lewiesthor123@ecquaria.com  | true |
      | 123123@ecquaria.com         | true |
      | lewiesthor@toppan-ecquaria.com  | true |
      | lewiesthor@ecquariacom          | false |
      | lewiesthor@@ecquaria.com        | false |
      | lewiesthor@ecquaria..com        | false |
      | @ecquaria.com                   | false |
      |                                 | false |
      | lewiesthor@ecquaria             | false |
      | lewiesthor@12345.54321.12345    | false |
      | lewiesthor@ecquaria.com trailing  | false |
      | lewiesthor@                       | false |

  @Functional @Negative @Integration
  Scenario Outline: Invalid Data for manual entry of main contact details for field secondaryEmail
    When Applicant enters data for "Lewies", "Test Engineer", "91239123", "lewiesthor@ecquaria.com", "<altEmail>"
    And Applicant manually enters mailing address with "650320", "03", "33", ""
    And Applicant manually enters letter of offer with "Lewies Thor", "QA Engineer", "lewies@ecquaria.com"
    Then Check error message for "secondary_email" field "<truefalse>"
    When Applicant saves and reloads the page
    Then Direct applicant to proposal submission
    Examples:
      | altEmail | truefalse |
      | lewiesthoryunsheng@ecquaria.com | true |
      | lewiesthor@ecquaria.com.sg | true |
      | lewies.thor@ecquaria.com   | true |
      | lewies-thor@ecquaria.com   | true |
      | lewies_thor@ecquaria.com   | true |
      | lewies+thor@ecquaria.com   | true |
      | lewiesthor123@ecquaria.com  | true |
      | 123123@ecquaria.com         | true |
      | lewiesthor@toppan-ecquaria.com  | true |
      |                                 | true |
      | lewiesthor@ecquaria.com         | false |
      | lewiesthor@ecquariacom          | false |
      | lewiesthor@@ecquaria.com        | false |
      | lewiesthor@ecquaria..com        | false |
      | @ecquaria.com                   | false |
      | lewiesthor@ecquaria             | false |
      | lewiesthor@12345.54321.12345    | false |
      | lewiesthor@ecquaria.com trailing  | false |
      | lewiesthor@                       | false |

  @Functional @Negative @Integration
  Scenario Outline: Invalid Data for manual entry of mailing address for field postalcode
    When Applicant enters data for "Lewies", "Test Engineer", "91239123", "lewiesthor@ecquaria.com", "lewiesthoryunsheng@ecquaria.com"
    And Applicant manually enters mailing address with "<postalcode>", "03", "33", ""
    And Applicant manually enters letter of offer with "Lewies Thor", "QA Engineer", "lewies@ecquaria.com"
    Then Check error message for "correspondence_address-postal" field "<truefalse>"
    When Applicant saves and reloads the page
    Then Direct applicant to proposal submission
    Examples:
      | postalcode | truefalse |
      | 650320 | true          |
      | 6503201 | true        |
      | 650320650320 | true   |
      | 65032a       | true   |
      | 65032!       | true   |
      | abc          | true   |
      | !@#          | true   |
      | 65032   | false        |
      |              | false   |


  @Functional @Negative @Integration
  Scenario Outline: Invalid Data for manual entry of mailing address for field level
    When Applicant enters data for "Lewies", "Test Engineer", "91239123", "lewiesthor@ecquaria.com", "lewiesthoryunsheng@ecquaria.com"
    And Applicant manually enters mailing address with "650320", "<level>", "33", ""
    And Applicant manually enters letter of offer with "Lewies Thor", "QA Engineer", "lewies@ecquaria.com"
    Then Check error message for "correspondence_address-level" field "<truefalse>"
    When Applicant saves and reloads the page
    Then Direct applicant to proposal submission
    Examples:
      | level | truefalse |
      | 03 | true         |
      |    | true         |
      | 99999 | true     |
#      | 3a    | false     |
#      | 3!    | false     |
#      | abc   | false     |
#      | !@#   | false     |


  @Functional @Negative @Integration
  Scenario Outline: Invalid Data for manual entry of mailing address for field unit
    When Applicant enters data for "Lewies", "Test Engineer", "91239123", "lewiesthor@ecquaria.com", "lewiesthoryunsheng@ecquaria.com"
    And Applicant manually enters mailing address with "650320", "03", "<unit>", ""
    And Applicant manually enters letter of offer with "Lewies Thor", "QA Engineer", "lewies@ecquaria.com"
    Then Check error message for "correspondence_address-unit" field "<truefalse>"
    When Applicant saves and reloads the page
    Then Direct applicant to proposal submission
    Examples:
      | unit | truefalse |
      | 33 | true        |
      | 33a | true       |
      |     | true       |
      | 12345678 | true |
#      | 33!      | false |
#      | abc      | false |
#      | !@#      | false |

  @Functional @Negative @Integration
  Scenario Outline: Invalid Data for manual entry of mailing address for field buildingname
    When Applicant enters data for "Lewies", "Test Engineer", "91239123", "lewiesthor@ecquaria.com", "lewiesthoryunsheng@ecquaria.com"
    And Applicant manually enters mailing address with "650320", "03", "33", "<buildingname>"
    And Applicant manually enters letter of offer with "Lewies Thor", "QA Engineer", "lewies@ecquaria.com"
    Then Check error message for "correspondence_address-building_name" field "<truefalse>"
    When Applicant saves and reloads the page
    Then Direct applicant to proposal submission
    Examples:
      | buildingname | truefalse |
      | Building | true          |
      | BUILDING | true          |
      | building | true          |
      | Building123 | true       |
      |             | true       |
      | BuildingBuildingBuildingBuildingBuildingBuildingBuildingBuildingBuilding | true |
#      | Building!@#                                                              | false |
#      | 123123123                                                                | false |
#      | !@#!@#!@#                                                                | false |

  @Functional @Negative @Integration
  Scenario Outline: Invalid Data for manual entry of LOO details for field name
    When Applicant enters data for "Lewies", "Test Engineer", "91239123", "lewiesthor@ecquaria.com", "lewiesthoryunsheng@ecquaria.com"
    And Applicant manually enters mailing address with "650320", "03", "33", ""
    And Applicant manually enters letter of offer with "<LOOname>", "QA Engineer", "lewies@ecquaria.com"
    Then Check error message for "offeree_name" field "<truefalse>"
    When Applicant saves and reloads the page
    Then Direct applicant to proposal submission
    Examples:
      | LOOname | truefalse |
      | Lewies | true |
      | lewies | true |
      | LEWIES | true |
      | LewiesLewiesLewiesLewiesLewiesLewiesLewiesLewiesLewiesLewiesLewiesLewies | true |
      |  | false      |
#      | Lewies1                                                                  | false |
#      | Lewies!                                                                  | false |
#      | 123                                                                      | false |
#      | !@#                                                                      | false |

  @Functional @Negative @Integration
  Scenario Outline: Invalid Data for manual entry of LOO details for field name jobtitle
    When Applicant enters data for "Lewies", "Test Engineer", "91239123", "lewiesthor@ecquaria.com", "lewiesthoryunsheng@ecquaria.com"
    And Applicant manually enters mailing address with "650320", "03", "33", ""
    And Applicant manually enters letter of offer with "Lewies Thor", "<LOOjob>", "lewies@ecquaria.com"
    Then Check error message for "offeree_designation" field "<truefalse>"
    When Applicant saves and reloads the page
    Then Direct applicant to proposal submission
    Examples:
      | LOOjob | truefalse |
      | Test Engineer | true |
      | test engineer | true |
      | TEST ENGINEER | true |
      | Test Engineer 1                                                                | true |
      | Test EngineerTest EngineerTest EngineerTest EngineerTest EngineerTest Engineer | true |
      |                                                                                | false |
#      | Test Engineer !                                                                | false |
#      | 123                                                                            | false |
#      | !@#                                                                            | false |

  @Functional @Negative @Integration
  Scenario Outline: Invalid Data for manual entry of LOO details for field email
    When Applicant enters data for "Lewies", "Test Engineer", "91239123", "lewiesthor@ecquaria.com", "lewiesthoryunsheng@ecquaria.com"
    And Applicant manually enters mailing address with "650320", "03", "33", ""
    And Applicant manually enters letter of offer with "Lewies Thor", "QA Engineer", "<LOOemail>"
    Then Check error message for "offeree_email" field "<truefalse>"
    When Applicant saves and reloads the page
    Then Direct applicant to proposal submission
    Examples:
      | LOOemail | truefalse |
      | lewiesthor@ecquaria.com | true |
      | lewiesthor@ecquaria.com.sg | true |
      | lewies.thor@ecquaria.com   | true |
      | lewies-thor@ecquaria.com   | true |
      | lewies_thor@ecquaria.com   | true |
      | lewies+thor@ecquaria.com   | true |
      | lewiesthor123@ecquaria.com  | true |
      | 123123@ecquaria.com         | true |
      | lewiesthor@toppan-ecquaria.com  | true |
      | lewiesthor@ecquariacom          | false |
      | lewiesthor@@ecquaria.com        | false |
      | lewiesthor@ecquaria..com        | false |
      | @ecquaria.com                   | false |
      |                                 | false |
      | lewiesthor@ecquaria             | false |
      | lewiesthor@12345.54321.12345    | false |
      | lewiesthor@ecquaria.com trailing  | false |
      | lewiesthor@                       | false |