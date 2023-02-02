package Modules;

import StepDefinition.Hooks;
import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.util.HashMap;

public class Commons extends Hooks {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
    HashMap getLogin;

    public void navigateToPortal() throws FileNotFoundException {
        getLogin=getLoginData();
        String url= (String) getLogin.get("url");
        driver.navigate().to(url);
        assertThat(driver.findElement(By.xpath("(//span[contains(text(),'Sign in')])[2]")).isDisplayed(),is(true));
    }

    public void login() {
        String username= (String) getLogin.get("user");
        String password= (String) getLogin.get("password");
        driver.findElement(By.xpath("(//input[contains(@id,'signInFormUsername')])[2]")).sendKeys(username);
        driver.findElement(By.xpath("(//input[contains(@id,'signInFormPassword')])[2]")).sendKeys(password);
        driver.findElement(By.xpath("(//input[contains(@name,'signInSubmitButton')])[2]")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[contains(@id,'login-button')])")));
        assertThat(driver.findElement(By.xpath("(//div[contains(@id,'login-button')])")).isDisplayed(),is(true));
    }

    public void loginToBGP() {
        driver.findElement(By.xpath("(//div[contains(@id,'login-button')])")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h1[contains(text(),'Manual Log In')]")));

        String entityId= (String) getLogin.get("UEN");
        String ic= (String) getLogin.get("IC");
        String role= (String) getLogin.get("Role");
        String name= (String) getLogin.get("Name");

        driver.findElement(By.xpath("(//input[contains(@id,'entityId')])")).sendKeys(entityId);
        driver.findElement(By.xpath("(//input[contains(@id,'userId')])")).sendKeys(ic);
        driver.findElement(By.xpath("(//input[contains(@id,'userRole')])")).sendKeys(role);
        driver.findElement(By.xpath("(//input[contains(@id,'userFullName')])")).sendKeys(name);
        driver.findElement(By.xpath("(//button[contains(text(),'Log In')])")).click();


        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='dashboard-action-card']//h4[contains(text(),'Get new grant')]")));
        assertThat(driver.findElement(By.xpath("//div[@class='dashboard-action-card']//h4[contains(text(),'Get new grant')]")).isDisplayed(),is(true));
    }

    public void createNewGrantTillEligiblity() throws InterruptedException {
        driver.findElement(By.xpath("//div[@class='dashboard-action-card']//h4[contains(text(),'Get new grant')]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//h3[contains(text(),'Which sector best describes your business?')])")));
        driver.findElement(By.xpath("(//div[contains(text(),'IT')])")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//h3[contains(text(),'I need this grant to')])")));
        driver.findElement(By.xpath("(//div[contains(text(),'Bring my business overseas')])")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//h3[contains(text(),'What do you plan to do')])")));
        driver.findElement(By.xpath("(//div[contains(text(),'Market Readiness Assistance')])")).click();
        driver.findElement(By.xpath("(//button[contains(@id,'go-to-grant')])")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("(//button[contains(@id,'keyPage-form-button')])")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//h2[contains(text(),'Check Your Eligibility')])")));
        assertThat(driver.findElement(By.xpath("(//h2[contains(text(),'Check Your Eligibility')])")).isDisplayed(),is(true));
    }

    public void navigateToContactDetails() throws InterruptedException {
        driver.findElement(By.xpath("//button[@id='next-btn']")).click();
        Thread.sleep(2000);
        assertThat(driver.findElement(By.xpath("(//div[@class='subsection-title']//h3[contains(text(),'Main Contact Person')])")).isDisplayed(),is(true));
    }

    public HashMap<String,String> getLoginData() throws FileNotFoundException {
        String path= "src/test/java/TestData/login.json";
        BufferedReader reader= new BufferedReader(new FileReader(path));
        Gson gson= new Gson();
        return gson.fromJson(reader, HashMap.class);
    }
}
