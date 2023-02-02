package Modules;

import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ContactDetails extends Commons{
    WebElement mainContactName=driver.findElement(By.xpath("//input[@id='react-contact_info-name']"));
    WebElement mainContactJobTitle=driver.findElement(By.xpath("//input[@id='react-contact_info-designation']"));
    WebElement mainContactContactNum=driver.findElement(By.xpath("//input[@id='react-contact_info-phone']"));
    WebElement mainContactEmail=driver.findElement(By.xpath("//input[@id='react-contact_info-primary_email']"));
    WebElement mainContactAltEmail=driver.findElement(By.xpath("//input[@id='react-contact_info-secondary_email']"));
    WebElement addressPostalCode=driver.findElement(By.xpath("//input[@id='react-contact_info-correspondence_address-postal']"));
    WebElement addressLevel=driver.findElement(By.xpath("//input[@id='react-contact_info-correspondence_address-level']"));
    WebElement addressUnit=driver.findElement(By.xpath("//input[@id='react-contact_info-correspondence_address-unit']"));
    WebElement addressBuildingName=driver.findElement(By.xpath("//input[@id='react-contact_info-correspondence_address-building_name']"));
    WebElement LOOName=driver.findElement(By.xpath("//input[@id='react-contact_info-offeree_name']"));
    WebElement LOOJobTitle=driver.findElement(By.xpath("//input[@id='react-contact_info-offeree_designation']"));
    WebElement LOOEmail=driver.findElement(By.xpath("//input[@id='react-contact_info-offeree_email']"));
    HashMap getContactDetailsData;
    String MCName;
    String MCJob;
    String MCContact;
    String MCEmail;
    String MCAltEmail;
    String APostalCode;
    String ALevel;
    String AUnit;
    String ABuildingName;
    String addresseeName;
    String addresseeJob;
    String addresseeEmail;
    public void fillInMainContactPersonInfo(String name,String job,String contactNum,String email,String altEmail) throws InterruptedException {
        mainContactName.sendKeys(name);
        mainContactJobTitle.sendKeys(job);
        mainContactContactNum.sendKeys(contactNum);
        mainContactEmail.sendKeys(email);
        mainContactAltEmail.sendKeys(altEmail);
        name=checkLength(name,66);
        job=checkLength(job,66);
        contactNum=checkLength(contactNum,8);
        MCName=mainContactName.getAttribute("value");
        MCJob=mainContactJobTitle.getAttribute("value");
        MCContact=mainContactContactNum.getAttribute("value");
        MCEmail=mainContactEmail.getAttribute("value");
        MCAltEmail=mainContactAltEmail.getAttribute("value");
        assertThat(MCName,is(name));
        assertThat(MCJob,is(job));
        assertThat(MCEmail,is(email));
        assertThat(MCAltEmail,is(altEmail));
        try {
            Integer.parseInt(contactNum);

            assertThat(MCContact,is(contactNum));
        } catch (NumberFormatException e) {
            System.out.println("Do not assert if contactNum input has non-digit characters");
        }

        scrollBy(350);
    }

    public void fillInMailingAddress(String postalcode,String level,String unit,String buildingName) throws InterruptedException {
        addressPostalCode.sendKeys(postalcode);
        Thread.sleep(2000);
        addressLevel.sendKeys(level);
        addressUnit.sendKeys(unit);
        addressBuildingName.sendKeys(buildingName);
        setAddressDetails();
        postalcode=checkLength(postalcode,6);
        level=checkLength(level,3);
        unit=checkLength(unit,5);
        buildingName=checkLength(buildingName,66);
        try {
            Integer.parseInt(postalcode);
            assertThat(APostalCode,is(postalcode));
        } catch (NumberFormatException e) {
            System.out.println("Do not assert if contactNum input has non-digit characters");
        }
        assertThat(ALevel,is(level));
        assertThat(AUnit,is(unit));
        assertThat(ABuildingName,is(buildingName));

        scrollBy(350);
    }

    public void fillInLOOInfo(String LOOname,String LOOjob,String LOOemail) throws InterruptedException {
        LOOName.sendKeys(LOOname);
        LOOJobTitle.sendKeys(LOOjob);
        LOOEmail.sendKeys(LOOemail);
        setLOODetails();
        LOOname=checkLength(LOOname,66);
        LOOjob=checkLength(LOOjob,66);
        Thread.sleep(2000);
        assertThat(addresseeName,is(LOOname));
        assertThat(addresseeJob,is(LOOjob));
        assertThat(addresseeEmail,is(LOOemail));
    }

    public void checkSameAddress() throws InterruptedException, FileNotFoundException {
        WebElement mailingAddressCheckbox=driver.findElement(By.xpath("//input[@id='react-contact_info-correspondence_address-copied']"));
        assertThat(mailingAddressCheckbox.isEnabled(),is(true));
        mailingAddressCheckbox.click();
        Thread.sleep(2000);
        checkMailingAddress();
    }
    public void checkLOOSameContactPerson() throws InterruptedException {
        WebElement LOOSamePersonCheckbox=driver.findElement(By.xpath("//input[@id='react-contact_info-copied']"));
        assertThat(LOOSamePersonCheckbox.isEnabled(),is(true));
        LOOSamePersonCheckbox.click();
        Thread.sleep(2000);
        checkLOODetails();
    }
    public void navigateToProposalSubmission() throws InterruptedException {
        scrollBy(800);
        Thread.sleep(500);
        driver.findElement(By.xpath("//button[@id='next-btn']")).click();
        Thread.sleep(2000);
        assertThat(driver.findElement(By.xpath("//input[@id='react-project-title']")).isDisplayed(),is(true));
    }
    public void checkMailingAddress() throws FileNotFoundException {
        setAddressDetails();
        getContactDetailsData=getContactDetailsData();
        assertThat(APostalCode,is(getContactDetailsData.get("presetPostalCode")));
        assertThat(ALevel,is(getContactDetailsData.get("presetLevel")));
        assertThat(AUnit,is(getContactDetailsData.get("presetUnit")));
        assertThat(ABuildingName,is(getContactDetailsData.get("presetBuildingName")));
    }
    public void checkLOODetails() {
        setLOODetails();
        assertThat(addresseeName,is(MCName));
        assertThat(addresseeJob,is(MCJob));
        assertThat(addresseeEmail,is(MCEmail));
    }

    public void validateMainContactPerson(String field,String truefalse) {
        if(Boolean.parseBoolean(truefalse))
            assertThat(driver.findElements(By.xpath("//p[@id='react-contact_info-"+field+"-alert']")).size(),lessThan(1));
        else
            assertThat(driver.findElement(By.xpath("//p[@id='react-contact_info-"+field+"-alert']")).isDisplayed(),is(true));
    }
    public HashMap<String,String> getContactDetailsData() throws FileNotFoundException {
        String path= "src/test/java/TestData/ContactDetailsData.json";
        BufferedReader reader= new BufferedReader(new FileReader(path));
        Gson gson= new Gson();
        return gson.fromJson(reader, HashMap.class);
    }
    public String checkLength(String checkValue,int maxLength) {
        int valueLength=checkValue.length();
        if (valueLength>maxLength)
            return(checkValue.substring(0,maxLength));
        else
            return checkValue;
    }
    public void setAddressDetails() {
        APostalCode=addressPostalCode.getAttribute("value");
        ALevel=addressLevel.getAttribute("value");
        AUnit=addressUnit.getAttribute("value");
        ABuildingName=addressBuildingName.getAttribute("value");
    }
    public void setLOODetails() {
        addresseeName=LOOName.getAttribute("value");
        addresseeJob=LOOJobTitle.getAttribute("value");
        addresseeEmail=LOOEmail.getAttribute("value");
    }
    public void contactDetailsSaveAndRefresh() throws InterruptedException {
        Eligibility.saveAndRefresh();
        Thread.sleep(2000);
        assertThat(driver.findElement(By.xpath("//input[@id='react-contact_info-name']")).getAttribute("value"),is(MCName));
        assertThat(driver.findElement(By.xpath("//input[@id='react-contact_info-designation']")).getAttribute("value"),is(MCJob));
        assertThat(driver.findElement(By.xpath("//input[@id='react-contact_info-phone']")).getAttribute("value"),is(MCContact));
        assertThat(driver.findElement(By.xpath("//input[@id='react-contact_info-primary_email']")).getAttribute("value"),is(MCEmail));
        assertThat(driver.findElement(By.xpath("//input[@id='react-contact_info-secondary_email']")).getAttribute("value"),is(MCAltEmail));

        assertThat(driver.findElement(By.xpath("//input[@id='react-contact_info-correspondence_address-postal']")).getAttribute("value"),is(APostalCode));
        assertThat(driver.findElement(By.xpath("//input[@id='react-contact_info-correspondence_address-level']")).getAttribute("value"),is(ALevel));
        assertThat(driver.findElement(By.xpath("//input[@id='react-contact_info-correspondence_address-unit']")).getAttribute("value"),is(AUnit));
        assertThat(driver.findElement(By.xpath("//input[@id='react-contact_info-correspondence_address-building_name']")).getAttribute("value"),is(ABuildingName));

        assertThat(driver.findElement(By.xpath("//input[@id='react-contact_info-offeree_name']")).getAttribute("value"),is(addresseeName));
        assertThat(driver.findElement(By.xpath("//input[@id='react-contact_info-offeree_designation']")).getAttribute("value"),is(addresseeJob));
        assertThat(driver.findElement(By.xpath("//input[@id='react-contact_info-offeree_email']")).getAttribute("value"),is(addresseeEmail));
    }

}
