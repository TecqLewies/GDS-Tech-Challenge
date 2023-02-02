package Modules;

import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Eligibility extends Commons {
    static HashMap getEligibilityData;
    List<String> selectedQuestions=new ArrayList<>();
    String questionNumber;
    public void checkEligibility(String yesno,String questionNum) throws FileNotFoundException, InterruptedException {
        validateQuestions();
        validateButtonsEnabled();
        clickButtons(yesno,questionNum);
    }
    public void validateQuestions() throws FileNotFoundException {
        getEligibilityData=getEligibilityData();
        int count= driver.findElements(By.xpath("//label[@class='control-label bgp-label']")).size();
        for(int i=1;i<=count;i++) {
            String question=driver.findElement(By.xpath("(//label[@class='control-label bgp-label'])["+i+"]")).getText();
            assertThat(question,containsString((String) getEligibilityData.get("question"+i)));
        }
    }
    public void validateButtonsEnabled() {
        int count= driver.findElements(By.xpath("//input[contains(@id,'react-eligibility')]")).size();
        for(int j=1;j<count;j++) {
            assertThat(driver.findElement(By.xpath("//input[@id='"+((String) getEligibilityData.get("button"+j))+"']")).isEnabled(),is(true));
        }
    }
    public void clickButtons(String yesno,String questionNum) throws FileNotFoundException, InterruptedException {
        HashMap<String,String> questionsList=getQuestionsList();
        getEligibilityData=getEligibilityData();
        if(questionNum.equals("All questions")) {
            for(Map.Entry<String,String> set: questionsList.entrySet()) {
                scrollBy(100);
                clickButton(yesno,set.getValue());
            }
        } else {
            questionNumber=questionNum.substring(questionNum.length()-1);
            int scrollTimes=Integer.valueOf(questionNumber);
            for(int i=1;i<=scrollTimes;i++)
                scrollBy(100);
            clickButton(yesno,questionsList.get(questionNumber));
        }
    }
    public void clickButton(String yesno,String question) {
        String yesOrNo="_check-true";
        if (yesno.equals("No"))
            yesOrNo="_check-false";
        String locator="//input[@id='react-eligibility-"+question+yesOrNo+"']";
        driver.findElement(By.xpath(locator)).click();
        selectedQuestions.add(locator);
        assertThat(driver.findElement(By.xpath("//input[@id='react-eligibility-"+question+yesOrNo+"']")).isSelected(),is(true));
    }
    public static void saveAndRefresh() throws InterruptedException {
        driver.findElement(By.xpath("//button[@id='save-btn']")).click();
        Thread.sleep(1000);
        driver.navigate().refresh();
    }
    public void validateOnSavedSelection() throws InterruptedException {
        Thread.sleep(2000);
        for(String selection: selectedQuestions) {
            WebElement selectionElement=driver.findElement(By.xpath(selection));
            assertThat(selectionElement.isSelected(), is(true));
        }
    }
    public void redirectToFAQ() {
        assertThat(driver.findElement(By.xpath("//div[@class='field-warning-text']//span")).isDisplayed(),is(true));
        driver.findElement(By.xpath("//div[@class='field-warning-text']//a[@target='_blank']")).click();

    }
    public void validateRedirectToFAQ() throws FileNotFoundException {
        ArrayList<String> tabs= new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        String currentURL=driver.getCurrentUrl();
        String expectedUrl= (String) getEligibilityData().get("redirectURL");
        assertThat(currentURL,equalToIgnoringCase(expectedUrl));
        driver.close();
        driver.switchTo().window(tabs.get(0));
    }
    public void eligibilityPageCheck() {
        assertThat(driver.findElement(By.xpath("(//h2[contains(text(),'Check Your Eligibility')])")).isDisplayed(),is(true));
    }
    public static HashMap<String,String> getQuestionsList() throws FileNotFoundException {
        getEligibilityData=getEligibilityData();
        HashMap<String,String> questionList=new HashMap<>();
        questionList.put((String) getEligibilityData.get("questionNum1"), (String) getEligibilityData.get("questionCheck"+(String) getEligibilityData.get("questionNum1")));
        questionList.put((String) getEligibilityData.get("questionNum2"),(String) getEligibilityData.get("questionCheck"+(String) getEligibilityData.get("questionNum2")));
        questionList.put((String) getEligibilityData.get("questionNum3"),(String) getEligibilityData.get("questionCheck"+(String) getEligibilityData.get("questionNum3")));
        questionList.put((String) getEligibilityData.get("questionNum4"),(String) getEligibilityData.get("questionCheck"+(String) getEligibilityData.get("questionNum4")));
        questionList.put((String) getEligibilityData.get("questionNum5"),(String) getEligibilityData.get("questionCheck"+(String) getEligibilityData.get("questionNum5")));
        return questionList;
    }
    public static HashMap<String,String> getEligibilityData() throws FileNotFoundException {
        String path= "src/test/java/TestData/EligibilityData.json";
        BufferedReader reader= new BufferedReader(new FileReader(path));
        Gson gson= new Gson();
        return gson.fromJson(reader, HashMap.class);
    }
}
