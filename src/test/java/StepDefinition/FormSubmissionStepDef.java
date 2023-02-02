package StepDefinition;

import Modules.FormSubmission;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.awt.*;
import java.io.FileNotFoundException;


public class FormSubmissionStepDef {
    FormSubmission form=new FormSubmission();
    @Given("Applicant fills in {string} data for {string}")
    public void applicant_fills_in_data_for(String dataCompleteness, String section) throws FileNotFoundException, InterruptedException, AWTException {
        form.fillInSection(dataCompleteness,section);
    }
    @When("Applicant clicks on review button")
    public void applicant_clicks_on_review_button() throws InterruptedException {
        form.clickOnReviewButton();
    }
    @Then("Applicant is directed to a read-only summary page")
    public void applicant_is_directed_to_a_read_only_summary_page() throws InterruptedException {
        form.validateReviewApplication();
    }
    @When("Applicant Checks Consent and Acknowledgement checkbox")
    public void applicant_checks_consent_and_acknowledgement_checkbox() throws InterruptedException {
        form.truthfulnessCheck();
    }
    @When("Submits application")
    public void submits_application() {
        form.submitApplication();
    }
    @Then("Success message box is shown with refID and agency details")
    public void success_message_box_is_shown_with_ref_id_and_agency_details() {
        form.validateSuccessSubmission();
    }
    @Then("Applicants My Grants dashboard shows application")
    public void applicants_my_grants_dashboard_shows_application() {
        form.checkApplication();
    }
    @Then("Applicant is redirected to section")
    public void applicant_is_redirected_to_section() {
        form.incompleteRedirection();
    }
}
