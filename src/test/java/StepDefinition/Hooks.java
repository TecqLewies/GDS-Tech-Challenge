package StepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void testSetup() {
        browserSetup();
    }

    @After
    public void testTeardown() {
        browserClose();
    }

    public void browserSetup() {
        ChromeOptions options= new ChromeOptions();
        options.addArguments("incognito");
        driver= new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    public void browserClose() {
        driver.close();
    }

    public void scrollBy(int pixels) throws InterruptedException {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.scrollBy(0," + pixels + ")", "");
        Thread.sleep(500);
    }
    public void scrollToEnd() throws InterruptedException {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }
}
