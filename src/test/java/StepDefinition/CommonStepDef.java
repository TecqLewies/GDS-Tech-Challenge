package StepDefinition;

import Modules.Commons;
import Modules.Eligibility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.io.FileNotFoundException;

public class CommonStepDef {

    Commons common=new Commons();
    Eligibility eligibility= new Eligibility();
    @Given("Applicant navigates to portal")
    public void applicant_navigates_to_portal() throws FileNotFoundException {
        common.navigateToPortal();
    }
    @Given("Applicant logs in successfully")
    public void applicant_logs_in_successfully() {
        common.login();
    }
    @Given("Applicant navigates to grant application form")
    public void applicant_navigates_to_grant_application_form() throws InterruptedException {
        common.loginToBGP();
        common.createNewGrantTillEligiblity();
    }

    @Given("Applicant navigates to contact details form")
    public void applicant_navigates_to_contact_details_form() throws InterruptedException {
        applicant_navigates_to_grant_application_form();
        common.navigateToContactDetails();
    }
    @When("Applicant saves and reloads the page")
    public void applicant_saves_and_reloads_the_page() throws InterruptedException {
        eligibility.saveAndRefresh();
    }
}
