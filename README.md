# GDS-Tech-Challenge

## Tech Challenge
This project was done as part of a technical assessment for GovTech - Government Digital Services Divison. The context of the assessment is to test a website that facilitates the application of grants for businesses. 

To apply for the grant, applicants would need to log in to their CorpPass, navigate to their desired grant, fill in 6 sections to explain their company's situation, and then to review and submit their application. They would be provided with a reference id when their application is successfully submitted that they can use to monitor their application progress on their CorpPass main page.

This assessment is broken down to 2 user stories to test the 'Eligibility' and 'Contact Details' sections of the grant application, as well as a larger *Epic* user story to test the end-to-end scenario for an applicant.

## Running the script
Users can run this automation test script by first cloning the repository to their local machines and running it via the TestRunner class or programmatically on their command prompt. More information on cloning a repo can be found here: https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository

After opening the script on a local IDE, users should change the location of test.xlsx in the FormData.json file. They can do this by navigating to FormData.json in src/test/java/TestData/ and right clicking test.xlsx found in the same folder, selecting copy path/reference and choosing absolute path. Paste this path in field *costUploadFileLocation* in FormData.json

After opening the script on a local IDE, users can then navigate to the TestRunner class under src/test/java/TestRunners/TestRunner.java and pressing Ctrl+Shift+F10, or right-clicking and selecting Run 'TestRunner'. Users may wish to change the tags field to define which testcases to be run as found in the feature files

Alternatively, users can run the script programmatically through their terminal windows using line 

```
mvn test 
```


Users can specify which testcases they wish to run by adding the -Dcucumber.options tags to their line
```
mvn test -Dcucumber.options="--tag '@Tag'"
```
Where @Tag can be changed to whichever tags found in the feature files

## Libraries
This project was done using Java as the programming language of choice, Cucumber as a BDD tool and jUnit as the unit testing framework. Selenium was used for UI testing of web elements and Hamcrest matchers were used for assertions. Extent reports was also used to introduce a cleaner, more readable report.

| Programming language | Version |
| --- | --- |
| Java | 17.0.5 |

| Libraries | Version |
| --- | --- |
| cucumber-java | 7.11.0 |
| cucumber-junit | 7.11.0 |
| junit-jupiter-api | 5.9.2 |
| Hamcrest | 2.2 |
| Selenium-java | 4.8.0 |
| GSON | 2.10.1 |
| Extentreports-cucumber7-adapter | 1.9.2 |

| WebDrivers | Version |
| --- | --- |
| ChromeDriver | 108.0.5359.71 |

## Testcase Tags
Scenarios for each feature file were tagged accordingly to the type of testing they cover and its coverage.

@Eligibility,@ContactDetails - Represent the testcases which covered the specific sections of *Eligibility* and *Contact Details* in the grant application process

@FormSubmission - Represent the testcases which covered the end to end scenario of all 6 sections of the grant application process

@Positive - Testcases which covers the desired flow of the process, where users are using the form as business had intended

@Negative - Testcases which covered undesired flows. Usually where users are misusing the application form by not conforming to instructions or how the application process has been set up

@Functional - Covers testcases which seeks to tests how well the system or component is able to perform tasks according to specification. This tag covers ALL testcase scenarios in this project

@Smoke - Covers testcases that are testing the most vital functionalities of the system or component

@Integration - Refers to testcases which tests specific components within a system

@Scenarios - Refers to testcases which tests the end-to-end flow of the system under test

## Improvements
### Non-functional testing
For the purpose of this project, only functional testing was done with regards to the system. Ideally, non-functional testing should also be planned for and conducted for such a system. It can include performance testing to simulate web traffic to the system, and determining how well the system is able to handle varying loads. Security testing can also be done such as penetration testing to identify vulnerabilities especially as the system under test is handling sensitive personal data.


### Service improvements
As it stands, the system under test is able to perform its task as intended. However, one area which can be slow is during the loading of main page, after the applicant logs into their CorpPass. It currently takes around 20-30 seconds to load and represents 30-40% of each test case runtime. Additively, this results in a large time commitment just waiting for the page to load. If resources allow for it, it would be good to invest effort into increasing the efficiency of the page loading

### Portability
For this project, only Chrome was used as the browser of choice on a standard computer. Given more time, a more holistic approach would be to run the test scripts on other browsers such as FireFox and Edge, and emulate mobile devices accessing the website. 


## Defects
As a result of integration testing, defects have been found on the *Contact Details* section of the application process. 
### Defect 1
+ Defect description: On the contact details section, when no data is being passed for each mandatory field, the error message does not show up. The error message is only triggered once the user types and deletes completely in each field
+ Expected results: Error message shown on fields that are mandatory but are ignored
+ Actions to take: Introduce restriction that prevents the user from proceeding to the next page without first filling in mandatory fields, or throwing the missing data error message once the user clicks on a field further down in the web page than the initial mandatory field being ignored
+ Steps to reproduce: Login to CorpPass, clicking on 'Get New Grant', choosing a sector, then choosing a 'Development Area(Bring my business overseas)', then 'Functional Area (Market Readiness Assistance)'. Click on 'Next' at the bottom of the page when on Eligibility section. Fill in fields in Contact Details but ignore some of the fields marked with *. No error message is shown
+ Browser: Chrome 108.0.5359.71
+ Automation script tags: @ContactDetails, @Functional, @Negative, @Integration
+ TestData: Test cases which sends no data for each field in contact details section

### Defect 2
+ Defect description: On the contact details section, when invalid data is passed for some fields, there are no error message being shown or constraints in the type of data being sent. Fields such as 'name' can accept numbers and 'unit' can accept special characters which should not be the case
+ Expected results: Error message being shown for invalid data on specific fields, or invalid data being restricted from being sent for specific fields
+ Actions to take: Introduce restrictions for specific fields such as not being able to send numbers for 'name' or special characters for 'unit, or throwing error messages if such data are entered
+ Steps to reproduce: Login to CorpPass, clicking on 'Get New Grant', choosing a sector, then choosing a 'Development Area(Bring my business overseas)', then 'Functional Area (Market Readiness Assistance)'. Click on 'Next' at the bottom of the page when on Eligibility section. Fill in fields in Contact Details with invalid data, No error message is shown
+ Browser: Chrome 108.0.5359.71
+ Automation script tags: @ContactDetails, @Functional, @Negative, @Integration. Test scripts with such invalid data are currently being skipped.
+ TestData: Test cases which sends invalid data for each field in contact details section


## Test results
Test passed - 129

Test failed - 9

Test skipped - 24

A test document 'GDS Tech Challenge.xlsx' has been attached that specifies the passed/failed/skipped for each test case and user story in more detail, including test description and test steps. It can be found under /src/test/java/

An extent report can also be found under /src/test/java/reports/ which has the in-built funcitonality of visualising the test script results. It has been configured to save each report with datetime appended to the filename such that testers and their team can compare between runs easier.
