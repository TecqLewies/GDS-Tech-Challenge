package StepDefinition;

import Modules.Eligibility;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.FileNotFoundException;

public class EligibilityCheckStepDef {
    Eligibility eligibility= new Eligibility();
    @When("Applicant fills in eligibility data with {string} to {string}")
    public void applicant_fills_in_eligibility_data_with_to(String yesno, String questionNum) throws FileNotFoundException, InterruptedException {
        eligibility.checkEligibility(yesno,questionNum);
    }


    @When("Applicant clicks on warning message")
    public void applicant_clicks_on_warning_message() {
        eligibility.redirectToFAQ();
    }

    @When("Applicant switches back to grant application tab")
    public void applicant_switches_back_to_grant_application_tab() {
        eligibility.eligibilityPageCheck();
    }

    @Then("Applicant is redirected to faq page")
    public void applicant_is_redirected_to_faq_page() throws FileNotFoundException {
        eligibility.validateRedirectToFAQ();
    }

    @Then("Validates on saved selection")
    public void validates_on_saved_selection() throws InterruptedException {
        eligibility.validateOnSavedSelection();
    }
    @Then("Direct applicant to contact details form")
    public void direct_applicant_to_contact_details_form() throws InterruptedException {
        eligibility.navigateToContactDetails();
    }
}
