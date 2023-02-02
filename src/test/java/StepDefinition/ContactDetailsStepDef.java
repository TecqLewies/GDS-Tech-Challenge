package StepDefinition;

import Modules.ContactDetails;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.FileNotFoundException;

public class ContactDetailsStepDef {
    ContactDetails contactDetails= new ContactDetails();
    @When("Applicant enters data for {string}, {string}, {string}, {string}, {string}")
    public void applicant_enters_data_for(String name, String job, String contactNum, String email, String altEmail) throws InterruptedException {
        contactDetails.fillInMainContactPersonInfo(name,job,contactNum,email,altEmail);
    }
    @When("Applicant manually enters mailing address with {string}, {string}, {string}, {string}")
    public void applicant_manually_enters_mailing_address_with(String postalcode, String level, String unit, String buildingName) throws InterruptedException {
        contactDetails.fillInMailingAddress(postalcode,level,unit,buildingName);
    }
    @When("Applicant manually enters letter of offer with {string}, {string}, {string}")
    public void applicant_manually_enters_letter_of_offer_with(String LOOname, String LOOjob, String LOOemail) throws InterruptedException {
        contactDetails.fillInLOOInfo(LOOname,LOOjob,LOOemail);
    }
    @Then("Direct applicant to proposal submission")
    public void direct_applicant_to_proposal_submission() throws InterruptedException {
        contactDetails.navigateToProposalSubmission();
    }
    @When("Applicant indicates same address as company profile")
    public void applicant_indicates_same_address_as_company_profile() throws InterruptedException, FileNotFoundException {
        contactDetails.checkSameAddress();
    }
    @When("Applicant indicates LOO addressee is same as main contact person")
    public void applicant_indicates_loo_addressee_is_same_as_main_contact_person() throws InterruptedException {
        contactDetails.checkLOOSameContactPerson();
    }
    @When("Applicant saves and reloads the page and checks the values on section")
    public void applicant_saves_and_reloads_the_page_and_checks_the_values_on_section() throws InterruptedException {
        contactDetails.contactDetailsSaveAndRefresh();
    }
    @Then("Check error message for {string} field {string}")
    public void check_error_message_for_field(String field, String truefalse) {
        contactDetails.validateMainContactPerson(field,truefalse);
    }
}
