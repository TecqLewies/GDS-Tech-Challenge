@FormSubmission
Feature: Form Submission check
  Background:
    Given Applicant navigates to portal
    And Applicant logs in successfully
    And Applicant navigates to grant application form

  @Smoke @Functional @Positive @Scenarios
  Scenario: Applicant fills in each section properly and submit application
    Given Applicant fills in "complete" data for "Eligibility"
    And Applicant fills in "complete" data for "ContactDetails"
    And Applicant fills in "complete" data for "Proposal"
    And Applicant fills in "complete" data for "BusinessImpact"
    And Applicant fills in "complete" data for "Cost"
    And Applicant fills in "complete" data for "DeclareAndReview"
    When Applicant clicks on review button
    Then Applicant is directed to a read-only summary page
    When Applicant Checks Consent and Acknowledgement checkbox
    And Submits application
    Then Success message box is shown with refID and agency details
    And Applicants My Grants dashboard shows application

  @Functional @Negative @Scenarios
  Scenario Outline: Applicant fills in sections with some error
    Given Applicant fills in "<dataCompleteness1>" data for "Eligibility"
    And Applicant fills in "<dataCompleteness2>" data for "ContactDetails"
    And Applicant fills in "<dataCompleteness3>" data for "Proposal"
    And Applicant fills in "<dataCompleteness4>" data for "BusinessImpact"
    And Applicant fills in "<dataCompleteness5>" data for "Cost"
    And Applicant fills in "<dataCompleteness6>" data for "DeclareAndReview"
    When Applicant clicks on review button
    Then Applicant is redirected to section
    Examples:
      | dataCompleteness1 | dataCompleteness2 | dataCompleteness3 | dataCompleteness4 | dataCompleteness5 | dataCompleteness6 |
      | incomplete | complete | complete | complete | complete | complete |
      | complete | incomplete | complete | complete | complete | complete |
      | complete | complete | incomplete | complete | complete | complete |
      | complete | complete | complete | incomplete | complete | complete |
      | complete | complete | complete | complete | incomplete | complete |
      | complete | complete | complete | complete | complete | incomplete |
      | incomplete | incomplete | incomplete | incomplete | incomplete | incomplete |
      | complete | completeinvalid | complete | complete | complete | complete |
      | complete | complete | completeinvalid | complete | complete | complete |
      | complete | complete | complete | completeinvalid | complete | complete |
      | complete | complete | complete | complete | completeinvalid | complete |
      | complete | completeinvalid | completeinvalid | completeinvalid | completeinvalid | complete |
      | empty | complete | complete | complete | complete | complete |
      | complete | empty | complete | complete | complete | complete |
      | complete | complete | empty | complete | complete | complete |
      | complete | complete | complete | empty | complete | complete |
      | complete | complete | complete | complete | empty | complete |
      | complete | complete | complete | complete | complete | empty |
      | empty | empty | empty | empty | empty | empty |
      | incomplete | complete | completeinvalid | complete | empty | complete |
