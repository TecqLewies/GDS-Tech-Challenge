package Modules;

import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FormSubmission extends Commons{
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    static HashMap getFormData;
    HashMap<String,String> eligiblityAnswers=new HashMap<>();
    HashMap<String,String> contactDetailsAnswers=new HashMap<>();
    HashMap<String,String> proposalAnswers=new HashMap<>();
    HashMap<String,String> businessImpactAnswers=new HashMap<>();
    HashMap<String,String> costAnswers=new HashMap<>();
    HashMap<String,String> declareAndReviewQuestions=new HashMap<>();
    String refId;
    HashMap<String,String> warningMap=new HashMap<>();

    public void fillInSection(String completeness,String section) throws FileNotFoundException, InterruptedException, AWTException {
        switch(section) {
            case "Eligibility":
                fillEligibility(completeness);
                break;
            case "ContactDetails":
                fillContactDetails(completeness);
                break;
            case "Proposal":
                fillProposal(completeness);
                break;
            case "BusinessImpact":
                fillBusinessImpact(completeness);
                break;
            case "Cost":
                fillCost(completeness);
                break;
            case "DeclareAndReview":
                fillDeclareAndReview(completeness);
                break;
        }
    }
    public void fillEligibility(String completeness) throws InterruptedException, FileNotFoundException {
        getFormData=getFormData();
        String fillAll="Yes";
        HashMap<String,String> questionsList=Eligibility.getQuestionsList();
        if(completeness.equals("complete")) {
            for (Map.Entry<String, String> set : questionsList.entrySet()) {
                scrollBy(100);
                clickEligibilityButton(fillAll, set.getValue());
            }
        } else if (completeness.equals("incomplete")) {
            scrollBy(100);
            clickEligibilityButton("Yes", (String) getFormData.get("eligibilityIncompleteQ1"));
            scrollBy(100);
            clickEligibilityButton("Yes", (String) getFormData.get("eligibilityIncompleteQ2"));
            warningMap.put("Eligibility","3");
        } else if(completeness.equals("empty")) {
            warningMap.put("Eligibility","5");
        }
        goNextPage();
        assertThat(driver.findElement(By.xpath("//input[@id='react-contact_info-name']")).isDisplayed(),is(true));
    }
    public void fillContactDetails(String completeness) throws InterruptedException {
        if(completeness.equals("complete")) {
            fillInMainContactDetails((String) getFormData.get("contactDetailsValidName"), (String) getFormData.get("contactDetailsValidJob"), (String) getFormData.get("contactDetailsValidContactNum"), (String) getFormData.get("contactDetailsValidEmail"), (String) getFormData.get("contactDetailsValidAltEmail"));
            fillInAddress(completeness);
            fillInLOO(completeness);
        } else if (completeness.equals("completeinvalid")) {
            fillInMainContactDetails((String) getFormData.get("contactDetailsValidName"), (String) getFormData.get("contactDetailsValidJob"), (String) getFormData.get("contactDetailsInvalidContactNum"), (String) getFormData.get("contactDetailsValidEmail"), (String) getFormData.get("contactDetailsValidAltEmail"));
            fillInAddress("complete");
            fillInLOO("complete");
            warningMap.put("Contact Details","1");
        } else if (completeness.equals("incomplete")) {
            fillInMainContactDetails((String) getFormData.get("contactDetailsValidName"), (String) getFormData.get("contactDetailsValidJob"), (String) getFormData.get("contactDetailsValidContactNum"), (String) getFormData.get("contactDetailsValidEmail"), (String) getFormData.get("contactDetailsValidAltEmail"));
            warningMap.put("Contact Details","4");
        } else if (completeness.equals("empty")) {
            warningMap.put("Contact Details","8");
        }
        goNextPage();
        assertThat(driver.findElement(By.xpath("//input[@id='react-project-title']")).isDisplayed(),is(true));
    }
    public void fillProposal(String completeness) throws InterruptedException {
        WebElement projectTitle=driver.findElement(By.xpath("//input[@id='react-project-title']"));
        WebElement startDate=driver.findElement(By.xpath("//input[@id='react-project-start_date']"));
        WebElement endDate=driver.findElement(By.xpath("//input[@id='react-project-end_date']"));
        WebElement projectDescription=driver.findElement(By.xpath("//textarea[@id='react-project-description']"));
        WebElement activity=driver.findElement(By.xpath("//span[@id='react-select-project-activity--value']"));
        WebElement targetMarket = driver.findElement(By.xpath("//span[@id='react-select-project-primary_market--value']"));
        WebElement firstMarketOutside=driver.findElement(By.xpath("//input[@id='react-project-is_first_time_expand-true']"));
        if(completeness.equals("complete")) {
            projectTitle.sendKeys(getFormData.get("proposalProjectTitle").toString());
            startDate.sendKeys(getFormData.get("proposalStartDate").toString());
            endDate.sendKeys(getFormData.get("proposalEndDate").toString());
            projectDescription.sendKeys(getFormData.get("proposalProjectDescription").toString());
            scrollBy(400);
            activity.click();
            WebElement activityOption= driver.findElement(By.xpath("//div[contains(text(),'FTA & Trade Compliance Consultancy')]"));
            activityOption.click();
            targetMarket.click();
            WebElement targetMarketOption = driver.findElement(By.xpath("//div[contains(text(),'Malaysia, East')]"));
            targetMarketOption.click();
            firstMarketOutside.click();
            scrollBy(400);
            proposalAnswers.put(projectTitle.getAttribute("id"),projectTitle.getAttribute("value"));
            proposalAnswers.put(startDate.getAttribute("id"),startDate.getAttribute("value"));
            proposalAnswers.put(endDate.getAttribute("id"),endDate.getAttribute("value"));
            proposalAnswers.put(projectDescription.getAttribute("id"),projectDescription.getAttribute("value"));
            proposalAnswers.put(activity.getAttribute("id"),activity.getText());
            proposalAnswers.put(targetMarket.getAttribute("id"),targetMarket.getText());
            proposalAnswers.put(firstMarketOutside.getAttribute("id"), String.valueOf(firstMarketOutside.isSelected()));
        } else if(completeness.equals("completeinvalid")) {
            projectTitle.sendKeys(getFormData.get("proposalProjectTitle").toString());
            startDate.sendKeys(getFormData.get("proposalInvalidStartDate").toString());
            endDate.sendKeys(getFormData.get("proposalEndDate").toString());
            projectDescription.sendKeys(getFormData.get("proposalProjectDescription").toString());
            scrollBy(400);
            activity.click();
            WebElement activityOption= driver.findElement(By.xpath("//div[contains(text(),'FTA & Trade Compliance Consultancy')]"));
            activityOption.click();
            targetMarket.click();
            WebElement targetMarketOption = driver.findElement(By.xpath("//div[contains(text(),'Malaysia, East')]"));
            targetMarketOption.click();
            firstMarketOutside.click();
            scrollBy(400);
            warningMap.put("Proposal","2");
        } else if (completeness.equals("incomplete")) {
            projectTitle.sendKeys(getFormData.get("proposalProjectTitle").toString());
            startDate.sendKeys(getFormData.get("proposalStartDate").toString());
            endDate.sendKeys(getFormData.get("proposalEndDate").toString());
            projectDescription.sendKeys(getFormData.get("proposalProjectDescription").toString());
            warningMap.put("Proposal","3");
        } else if (completeness.equals("empty")) {
            warningMap.put("Proposal","7");
        }
        goNextPage();
        assertThat(driver.findElement(By.xpath("//input[@id='react-project_impact-fy_end_date_0']")).isDisplayed(),is(true));
    }
    public void fillBusinessImpact(String completeness) throws InterruptedException {
        WebElement FYEndDate1=driver.findElement(By.xpath("//input[@id='react-project_impact-fy_end_date_0']"));
        WebElement overseasSales1=driver.findElement(By.xpath("//input[@id='react-project_impact-overseas_sales_0']"));
        WebElement overseasSales2=driver.findElement(By.xpath("//input[@id='react-project_impact-overseas_sales_1']"));
        WebElement overseasSales3=driver.findElement(By.xpath("//input[@id='react-project_impact-overseas_sales_2']"));
        WebElement overseasSales4=driver.findElement(By.xpath("//input[@id='react-project_impact-overseas_sales_3']"));
        WebElement overseasInvestment1=driver.findElement(By.xpath("//input[@id='react-project_impact-overseas_investments_0']"));
        WebElement overseasInvestment2=driver.findElement(By.xpath("//input[@id='react-project_impact-overseas_investments_1']"));
        WebElement overseasInvestment3=driver.findElement(By.xpath("//input[@id='react-project_impact-overseas_investments_2']"));
        WebElement overseasInvestment4=driver.findElement(By.xpath("//input[@id='react-project_impact-overseas_investments_3']"));
        WebElement rationale=driver.findElement(By.xpath("//textarea[@id='react-project_impact-rationale_remarks']"));
        WebElement nonTangibleBenefits=driver.findElement(By.xpath("//textarea[@id='react-project_impact-benefits_remarks']"));
        if(completeness.equals("complete")) {
            FYEndDate1.sendKeys(getFormData.get("businessImpactFYEndDate").toString());
            overseasSales1.sendKeys(getFormData.get("businessImpactOverseasSales1").toString());
            overseasSales2.sendKeys(getFormData.get("businessImpactOverseasSales2").toString());
            overseasSales3.sendKeys(getFormData.get("businessImpactOverseasSales3").toString());
            overseasSales4.sendKeys(getFormData.get("businessImpactOverseasSales4").toString());
            overseasInvestment1.sendKeys(getFormData.get("businessImpactOverseasInvestment1").toString());
            overseasInvestment2.sendKeys(getFormData.get("businessImpactOverseasInvestment2").toString());
            overseasInvestment3.sendKeys(getFormData.get("businessImpactOverseasInvestment3").toString());
            overseasInvestment4.sendKeys(getFormData.get("businessImpactOverseasInvestment4").toString());
            rationale.sendKeys(getFormData.get("businessImpactRationale").toString());
            nonTangibleBenefits.sendKeys(getFormData.get("businessImpactNonTangibleBenefits").toString());
            Thread.sleep(2000);
            WebElement FYEndDate2=driver.findElement(By.xpath("//div[@id='react-project_impact-fy_end_date_1']"));
            WebElement FYEndDate3=driver.findElement(By.xpath("//div[@id='react-project_impact-fy_end_date_2']"));
            WebElement FYEndDate4=driver.findElement(By.xpath("//div[@id='react-project_impact-fy_end_date_3']"));
            businessImpactAnswers.put(FYEndDate1.getAttribute("id"),FYEndDate1.getAttribute("value"));
            businessImpactAnswers.put(FYEndDate2.getAttribute("id"),FYEndDate2.getText());
            businessImpactAnswers.put(FYEndDate3.getAttribute("id"),FYEndDate3.getText());
            businessImpactAnswers.put(FYEndDate4.getAttribute("id"),FYEndDate4.getText());
            businessImpactAnswers.put(overseasSales1.getAttribute("id"),overseasSales1.getAttribute("value"));
            businessImpactAnswers.put(overseasSales2.getAttribute("id"),overseasSales2.getAttribute("value"));
            businessImpactAnswers.put(overseasSales3.getAttribute("id"),overseasSales3.getAttribute("value"));
            businessImpactAnswers.put(overseasSales4.getAttribute("id"),overseasSales4.getAttribute("value"));
            businessImpactAnswers.put(overseasInvestment1.getAttribute("id"),overseasInvestment1.getAttribute("value"));
            businessImpactAnswers.put(overseasInvestment2.getAttribute("id"),overseasInvestment2.getAttribute("value"));
            businessImpactAnswers.put(overseasInvestment3.getAttribute("id"),overseasInvestment3.getAttribute("value"));
            businessImpactAnswers.put(rationale.getAttribute("id"),rationale.getText());
            businessImpactAnswers.put(nonTangibleBenefits.getAttribute("id"),nonTangibleBenefits.getText());
        } else if(completeness.equals("completeinvalid")) {
            FYEndDate1.sendKeys(getFormData.get("businessImpactInvalidFYEndDate").toString());
            overseasSales1.sendKeys(getFormData.get("businessImpactOverseasSales1").toString());
            overseasSales2.sendKeys(getFormData.get("businessImpactOverseasSales2").toString());
            overseasSales3.sendKeys(getFormData.get("businessImpactOverseasSales3").toString());
            overseasSales4.sendKeys(getFormData.get("businessImpactOverseasSales4").toString());
            overseasInvestment1.sendKeys(getFormData.get("businessImpactOverseasInvestment1").toString());
            overseasInvestment2.sendKeys(getFormData.get("businessImpactOverseasInvestment2").toString());
            overseasInvestment3.sendKeys(getFormData.get("businessImpactOverseasInvestment3").toString());
            overseasInvestment4.sendKeys(getFormData.get("businessImpactOverseasInvestment4").toString());
            rationale.sendKeys(getFormData.get("businessImpactRationale").toString());
            nonTangibleBenefits.sendKeys(getFormData.get("businessImpactNonTangibleBenefits").toString());
            Thread.sleep(2000);
            warningMap.put("Business Impact","1");
        } else if (completeness.equals("incomplete")) {
            FYEndDate1.sendKeys(getFormData.get("businessImpactFYEndDate").toString());
            overseasSales1.sendKeys(getFormData.get("businessImpactOverseasSales1").toString());
            overseasSales2.sendKeys(getFormData.get("businessImpactOverseasSales2").toString());
            overseasSales3.sendKeys(getFormData.get("businessImpactOverseasSales3").toString());
            overseasSales4.sendKeys(getFormData.get("businessImpactOverseasSales4").toString());
            overseasInvestment1.sendKeys(getFormData.get("businessImpactOverseasInvestment1").toString());
            overseasInvestment2.sendKeys(getFormData.get("businessImpactOverseasInvestment2").toString());
            overseasInvestment3.sendKeys(getFormData.get("businessImpactOverseasInvestment3").toString());
            overseasInvestment4.sendKeys(getFormData.get("businessImpactOverseasInvestment4").toString());
            Thread.sleep(2000);
            warningMap.put("Business Impact","2");
        } else if (completeness.equals("empty")) {
            warningMap.put("Business Impact","11");
        }
        goNextPage();
        assertThat(driver.findElement(By.xpath("//div[@id='react-project_cost-vendors-accordion-header']")).isDisplayed(),is(true));
    }
    public void fillCost(String completeness) throws InterruptedException, AWTException {
        WebElement thirdPartyVendors= driver.findElement(By.xpath("//div[@id='react-project_cost-vendors-accordion-header']"));
        thirdPartyVendors.click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@id='react-project_cost-vendors-add-item']")));
        WebElement addNewItem= driver.findElement(By.xpath("//button[@id='react-project_cost-vendors-add-item']"));
        addNewItem.click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//input[@id='react-project_cost-vendors-0-local_vendor-true']")));
        WebElement vendorRegistered= driver.findElement(By.xpath("//input[@id='react-project_cost-vendors-0-local_vendor-true']"));
        WebElement selectFiles= driver.findElement(By.xpath("//button[@id='react-project_cost-vendors-0-attachments-btn']"));
        WebElement estimatedCost= driver.findElement(By.xpath("//input[@id='react-project_cost-vendors-0-amount_in_billing_currency']"));
        if(completeness.equals("complete")) {
            vendorRegistered.click();
            WebElement vendorName= driver.findElement(By.xpath("//input[@id='react-project_cost-vendors-0-vendor_name']"));
            vendorName.sendKeys(getFormData.get("costVendorName").toString());
            Thread.sleep(500);
            driver.findElement(By.xpath("//span[@id='react-project_cost-vendors-0-vendor_name-vendor']")).click();
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@id='vendor-row-uen']")));
            driver.findElement(By.xpath("//span[@id='vendor-row-uen']")).click();
            selectFiles.click();
            Thread.sleep(2000);
            Robot rb = new Robot();
            StringSelection str = new StringSelection(getFormData.get("costUploadFileLocation").toString());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_CONTROL);
            rb.keyRelease(KeyEvent.VK_V);
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);
            estimatedCost.sendKeys(getFormData.get("costEstimatedCost").toString());
            costAnswers.put(vendorRegistered.getAttribute("id"), String.valueOf(vendorRegistered.isSelected()));
            costAnswers.put(vendorName.getAttribute("id"),vendorName.getAttribute("value"));
            costAnswers.put(estimatedCost.getAttribute("id"),estimatedCost.getAttribute("value"));
        }else if(completeness.equals("completeinvalid")) {
            vendorRegistered.click();
            WebElement vendorName= driver.findElement(By.xpath("//input[@id='react-project_cost-vendors-0-vendor_name']"));
            vendorName.sendKeys(getFormData.get("costVendorName").toString());
            Thread.sleep(500);
            selectFiles.click();
            Thread.sleep(2000);
            Robot rb = new Robot();
            StringSelection str = new StringSelection(getFormData.get("costUploadFileLocation").toString());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_CONTROL);
            rb.keyRelease(KeyEvent.VK_V);
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);
            estimatedCost.sendKeys(getFormData.get("costEstimatedCost").toString());
            warningMap.put("Cost","1");
        } else if (completeness.equals("incomplete")) {
            vendorRegistered.click();
            WebElement vendorName= driver.findElement(By.xpath("//input[@id='react-project_cost-vendors-0-vendor_name']"));
            vendorName.sendKeys(getFormData.get("costVendorName").toString());
            Thread.sleep(500);
            driver.findElement(By.xpath("//span[@id='react-project_cost-vendors-0-vendor_name-vendor']")).click();
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@id='vendor-row-uen']")));
            driver.findElement(By.xpath("//span[@id='vendor-row-uen']")).click();
            Thread.sleep(2000);
            warningMap.put("Cost","2");
        } else if (completeness.equals("empty")) {
            warningMap.put("Cost","4");
        }
        goNextPage();
        assertThat(driver.findElement(By.xpath("//input[@id='react-declaration-criminal_liability_check-false']")).isDisplayed(),is(true));
    }
    public void fillDeclareAndReview(String completeness) throws InterruptedException {
        WebElement criminalLiability=driver.findElement(By.xpath("//input[@id='react-declaration-criminal_liability_check-false']"));
        WebElement civilProceedings=driver.findElement(By.xpath("//input[@id='react-declaration-civil_proceeding_check-false']"));
        WebElement insolvencyProceedings=driver.findElement(By.xpath("//input[@id='react-declaration-insolvency_proceeding_check-false']"));
        WebElement projectIncentives=driver.findElement(By.xpath("//input[@id='react-declaration-project_incentives_check-false']"));
        WebElement otherIncentives=driver.findElement(By.xpath("//input[@id='react-declaration-other_incentives_check-false']"));
        WebElement projectCommence=driver.findElement(By.xpath("//input[@id='react-declaration-project_commence_check-false']"));
        WebElement relatedParty=driver.findElement(By.xpath("//input[@id='react-declaration-related_party_check-false']"));
        WebElement debarment=driver.findElement(By.xpath("//input[@id='react-declaration-debarment_check-false']"));
        WebElement declaration=driver.findElement(By.xpath("//input[@id='react-declaration-consent_acknowledgement_check']"));
        if(completeness.equals("complete")) {
            criminalLiability.click();
            declareAndReviewQuestions.put(criminalLiability.getAttribute("id"),criminalLiability.getAttribute("value"));
            scrollBy(150);
            civilProceedings.click();
            declareAndReviewQuestions.put(civilProceedings.getAttribute("id"),civilProceedings.getAttribute("value"));
            scrollBy(150);
            insolvencyProceedings.click();
            declareAndReviewQuestions.put(insolvencyProceedings.getAttribute("id"),insolvencyProceedings.getAttribute("value"));
            scrollBy(150);
            projectIncentives.click();
            declareAndReviewQuestions.put(projectIncentives.getAttribute("id"),projectIncentives.getAttribute("value"));
            scrollBy(150);
            otherIncentives.click();
            declareAndReviewQuestions.put(otherIncentives.getAttribute("id"),otherIncentives.getAttribute("value"));
            scrollBy(150);
            projectCommence.click();
            declareAndReviewQuestions.put(projectCommence.getAttribute("id"),projectCommence.getAttribute("value"));
            scrollBy(150);
            relatedParty.click();
            declareAndReviewQuestions.put(relatedParty.getAttribute("id"),relatedParty.getAttribute("value"));
            scrollBy(150);
            debarment.click();
            declareAndReviewQuestions.put(debarment.getAttribute("id"),debarment.getAttribute("value"));
            scrollBy(150);
            declaration.click();
            declareAndReviewQuestions.put(declaration.getAttribute("id"), String.valueOf(declaration.isSelected()));
        } else if (completeness.equals("incomplete")) {
            criminalLiability.click();
            scrollBy(150);
            civilProceedings.click();
            scrollBy(150);
            insolvencyProceedings.click();
            scrollBy(900);
            declaration.click();
            warningMap.put("Declare & Review","5");
        } else if (completeness.equals("empty")) {
            scrollBy(1200);
            declaration.click();
            warningMap.put("Declare & Review","8");
        }
    }
    public void goNextPage() throws InterruptedException {
        driver.findElement(By.xpath("//button[@id='next-btn']")).click();
        Thread.sleep(2000);
    }
    public void clickEligibilityButton(String yesno,String question) {
        String yesOrNo="_check-true";
        if (yesno.equals("No"))
            yesOrNo="_check-false";
        String locator="//input[@id='react-eligibility-"+question+yesOrNo+"']";
        driver.findElement(By.xpath(locator)).click();
        eligiblityAnswers.put("react-eligibility-"+question,yesOrNo);
        assertThat(driver.findElement(By.xpath("//input[@id='react-eligibility-"+question+yesOrNo+"']")).isSelected(),is(true));
    }
    public void fillInMainContactDetails(String name,String job,String contactNum,String email,String altEmail) throws InterruptedException {
        WebElement mainContactName=driver.findElement(By.xpath("//input[@id='react-contact_info-name']"));
        WebElement mainContactJobTitle=driver.findElement(By.xpath("//input[@id='react-contact_info-designation']"));
        WebElement mainContactContactNum=driver.findElement(By.xpath("//input[@id='react-contact_info-phone']"));
        WebElement mainContactEmail=driver.findElement(By.xpath("//input[@id='react-contact_info-primary_email']"));
        WebElement mainContactAltEmail=driver.findElement(By.xpath("//input[@id='react-contact_info-secondary_email']"));

        mainContactName.sendKeys(name);
        mainContactJobTitle.sendKeys(job);
        mainContactContactNum.sendKeys(contactNum);
        mainContactEmail.sendKeys(email);
        mainContactAltEmail.sendKeys(altEmail);
        contactDetailsAnswers.put(mainContactName.getAttribute("id"),mainContactName.getAttribute("value"));
        contactDetailsAnswers.put(mainContactJobTitle.getAttribute("id"),mainContactJobTitle.getAttribute("value"));
        contactDetailsAnswers.put(mainContactContactNum.getAttribute("id"),mainContactContactNum.getAttribute("value"));
        contactDetailsAnswers.put(mainContactEmail.getAttribute("id"),mainContactEmail.getAttribute("value"));
        contactDetailsAnswers.put(mainContactAltEmail.getAttribute("id"),mainContactAltEmail.getAttribute("value"));
        assertThat(mainContactName.getAttribute("value"),is(name));
        assertThat(mainContactJobTitle.getAttribute("value"),is(job));
        assertThat(mainContactContactNum.getAttribute("value"),is(contactNum));
        assertThat(mainContactEmail.getAttribute("value"),is(email));
        assertThat(mainContactAltEmail.getAttribute("value"),is(altEmail));

        scrollBy(350);
    }
    public void fillInAddress(String completeness) throws InterruptedException {
        WebElement addressPostalCode=driver.findElement(By.xpath("//input[@id='react-contact_info-correspondence_address-postal']"));
        WebElement addressLevel=driver.findElement(By.xpath("//input[@id='react-contact_info-correspondence_address-level']"));
        WebElement addressUnit=driver.findElement(By.xpath("//input[@id='react-contact_info-correspondence_address-unit']"));
        WebElement addressBuildingName=driver.findElement(By.xpath("//input[@id='react-contact_info-correspondence_address-building_name']"));
        if(completeness.equals("complete")) {
            WebElement mailingAddressCheckbox=driver.findElement(By.xpath("//input[@id='react-contact_info-correspondence_address-copied']"));
            assertThat(mailingAddressCheckbox.isEnabled(),is(true));
            mailingAddressCheckbox.click();
            Thread.sleep(2000);
            assertThat(addressPostalCode.getAttribute("value"),is(getFormData.get("contactDetailsPresetPostalCode").toString()));
            assertThat(addressLevel.getAttribute("value"),is(getFormData.get("contactDetailsPresetLevel").toString()));
            assertThat(addressUnit.getAttribute("value"),is(getFormData.get("contactDetailsPresetUnit").toString()));
            assertThat(addressBuildingName.getAttribute("value"),is(getFormData.get("contactDetailsPresetBuildingName").toString()));
            contactDetailsAnswers.put(addressPostalCode.getAttribute("id"),addressPostalCode.getAttribute("value"));
            contactDetailsAnswers.put(addressLevel.getAttribute("id"),addressLevel.getAttribute("value"));
            contactDetailsAnswers.put(addressUnit.getAttribute("id"),addressUnit.getAttribute("value"));
            contactDetailsAnswers.put(addressBuildingName.getAttribute("id"),addressBuildingName.getAttribute("value"));
        }


    }
    public void fillInLOO(String completeness) throws InterruptedException {
        WebElement LOOName=driver.findElement(By.xpath("//input[@id='react-contact_info-offeree_name']"));
        WebElement LOOJobTitle=driver.findElement(By.xpath("//input[@id='react-contact_info-offeree_designation']"));
        WebElement LOOEmail=driver.findElement(By.xpath("//input[@id='react-contact_info-offeree_email']"));
        if(completeness.equals("complete")) {
            WebElement LOOSamePersonCheckbox=driver.findElement(By.xpath("//input[@id='react-contact_info-copied']"));
            assertThat(LOOSamePersonCheckbox.isEnabled(),is(true));
            LOOSamePersonCheckbox.click();
            Thread.sleep(2000);
            assertThat(LOOName.getAttribute("value"),is(driver.findElement(By.xpath("//input[@id='react-contact_info-name']")).getAttribute("value")));
            assertThat(LOOJobTitle.getAttribute("value"),is(driver.findElement(By.xpath("//input[@id='react-contact_info-designation']")).getAttribute("value")));
            assertThat(LOOEmail.getAttribute("value"),is(driver.findElement(By.xpath("//input[@id='react-contact_info-primary_email']")).getAttribute("value")));
            contactDetailsAnswers.put(LOOName.getAttribute("id"),LOOName.getAttribute("value"));
            contactDetailsAnswers.put(LOOJobTitle.getAttribute("id"),LOOJobTitle.getAttribute("value"));
            contactDetailsAnswers.put(LOOEmail.getAttribute("id"),LOOEmail.getAttribute("value"));
        }

    }
    public void clickOnReviewButton() throws InterruptedException {
        driver.findElement(By.xpath("//button[@id='review-btn']")).click();
        Thread.sleep(2000);
    }
    public void validateReviewApplication() throws InterruptedException {
        Thread.sleep(2000);
        scrollBy(1200);
        validateEligibilityAnswers();
        validateContactDetailsAnswers();
        validateProposalAnswers();
        validateBusinessImpact();
        validateCost();
        validateDeclareAndReview();
    }
    public void validateEligibilityAnswers() {
        for (Map.Entry<String, String> entry : eligiblityAnswers.entrySet()) {
            String eligibilityCheck;
            if(entry.getValue().equals("_check-true"))
                eligibilityCheck="Yes";
            else
                eligibilityCheck="No";
            WebElement answer=driver.findElement(By.xpath("//div[@id='"+entry.getKey()+"_check']"));
            assertThat(answer.getText(),is(eligibilityCheck));
        }
    }
    public void validateContactDetailsAnswers() {
        for (Map.Entry<String, String> entry : contactDetailsAnswers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(key.equals("react-contact_info-correspondence_address-unit")||key.equals("react-contact_info-correspondence_address-building_name")||key.equals("react-contact_info-correspondence_address-level")||key.equals("react-contact_info-correspondence_address-postal")) {
                WebElement cdAnswer=driver.findElement(By.xpath("//div[@id='react-contact_info-correspondence_address']"));
                assertThat(cdAnswer.getText(),containsString(value));
            }
            else {
                WebElement cdAnswer=driver.findElement(By.xpath("//div[contains(@id,'"+key+"')]"));
                assertThat(cdAnswer.getText(),is(value));
            }
        }
    }
    public void validateProposalAnswers() {
        for (Map.Entry<String, String> entry : proposalAnswers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(key.equals("react-select-project-primary_market--value")||key.equals("react-select-project-activity--value")) {
                String locator=key.substring(13,key.length()-7);
                WebElement pAnswer=driver.findElement(By.xpath("//div[contains(@id,'"+locator+"')]"));
                assertThat(pAnswer.getText(),is(value));
            }
            else if(key.equals("react-project-is_first_time_expand-true")) {
                String locator=key.substring(14,key.length()-5);
                WebElement pAnswer=driver.findElement(By.xpath("//div[contains(@id,'"+locator+"')]"));
                assertThat(pAnswer.getText(),is("Yes"));
            }
            else {
                WebElement pAnswer=driver.findElement(By.xpath("//div[@id='"+key+"']"));
                assertThat(pAnswer.getText(),is(value));
            }
        }
    }
    public void validateBusinessImpact() {
        for (Map.Entry<String, String> entry : businessImpactAnswers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            WebElement biAnswer=driver.findElement(By.xpath("//div[@id='"+key+"']"));
            assertThat(biAnswer.getText(),is(value));
        }
    }
    public void validateCost() {
        for (Map.Entry<String, String> entry : costAnswers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(key.equals("react-project_cost-vendors-0-amount_in_billing_currency")) {
                WebElement cAnswer=driver.findElement(By.xpath("//div[@id='"+key+"']"));
                assertThat(cAnswer.getText(),containsString(value));
            }
            else if(key.equals("react-project_cost-vendors-0-local_vendor-true")) {
                String locator=key.substring(0,key.length()-5);
                WebElement cAnswer=driver.findElement(By.xpath("//div[@id='"+locator+"']"));
                assertThat(cAnswer.getText(),is("Singapore"));
            } else {
                WebElement cAnswer=driver.findElement(By.xpath("//div[@id='"+key+"']"));
                assertThat(cAnswer.getText(),is(value));
            }
        }
    }
    public void validateDeclareAndReview() {
        for (Map.Entry<String, String> entry : declareAndReviewQuestions.entrySet()) {
            String declareCheck;
            String key = entry.getKey();
            String value = entry.getValue();
            if(value.equals("true")) {
                declareCheck="Yes";
            }
            else {
                declareCheck="No";
                key=key.substring(0,key.length()-6);
            }
            WebElement darAnswer=driver.findElement(By.xpath("//div[@id='"+key+"']"));
            assertThat(darAnswer.getText(),is(declareCheck));
        }
    }
    public void truthfulnessCheck() throws InterruptedException {
        scrollToEnd();
        driver.findElement(By.xpath("//input[@id='react-declaration-info_truthfulness_check']")).click();
    }
    public void submitApplication() {
        driver.findElement(By.xpath("//button[@id='submit-btn']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[@class='card']")));
    }
    public void validateSuccessSubmission() {
        assertThat(driver.findElement(By.xpath("//table[@class='key-status-section']//span[1]")).getText(),is("Enterprise Singapore"));
        refId=driver.findElement(By.xpath("//section[@class='card']//table[@class='key-status-section']//tbody//tr//td[2]")).getText();
    }
    public void checkApplication() {
        driver.findElement(By.xpath("//a[@id='sgds-nav-start']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[@class='dashboard-tab-container']")));
        driver.findElement(By.xpath("//div[@class='dashboard-container']//div//section[2]//ul[@class='nav nav-tabs dashboard-tabs']//li[3]//a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='db-apps-processing']//tbody[@class='tab-content']")));
        assertThat(driver.findElement(By.xpath("//table[@id='db-apps-processing']//tbody[@class='tab-content']//tr//td[1]")).getText(),is(refId));

        driver.findElement(By.xpath("//div[@id='logout-button']")).click();
    }
    public void logout() {
        driver.findElement(By.xpath("//div[@id='logout-button']")).click();
    }
    public void incompleteRedirection() {
        for (Map.Entry<String, String> entry : warningMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            WebElement error= driver.findElement(By.xpath("//span[contains(text(),'"+key+"')]/following-sibling::div[@class='sidebar-label']/span[@class='label label-error']"));
            assertThat(error.getText(),is(value));
        }
        logout();
    }
    public HashMap<String,String> getFormData() throws FileNotFoundException {
        String path= "src/test/java/TestData/FormData.json";
        BufferedReader reader= new BufferedReader(new FileReader(path));
        Gson gson= new Gson();
        return gson.fromJson(reader, HashMap.class);
    }
}
