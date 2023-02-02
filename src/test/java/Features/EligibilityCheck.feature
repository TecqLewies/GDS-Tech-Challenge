@Eligibility
Feature: Eligibility check
  Background:
    Given Applicant navigates to portal
    And Applicant logs in successfully
    And Applicant navigates to grant application form

  @Smoke @Functional @Positive @Integration
  Scenario: Answering Yes to all questions in Eligibility section
    When Applicant fills in eligibility data with 'Yes' to 'All questions'
    And Applicant saves and reloads the page
    Then Validates on saved selection
    And Direct applicant to contact details form

  @Functional @Negative @Integration
  Scenario Outline: Answering No to selected question number in Eligibility section
    When Applicant fills in eligibility data with 'No' to "<question>"
    And Applicant clicks on warning message
    Then Applicant is redirected to faq page
    When Applicant switches back to grant application tab
    And Applicant saves and reloads the page
    Then Validates on saved selection
    And Direct applicant to contact details form
    Examples:
      | question |
      | 1 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |