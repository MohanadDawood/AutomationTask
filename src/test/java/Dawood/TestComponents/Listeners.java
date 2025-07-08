package Dawood.TestComponents;

import Dawood.Resources.ExtentReporterNG;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listeners extends BaseTest implements ITestListener {
    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //Thread safe
    @Override
    public void onTestStart(ITestResult result) {
        //before running any test
        //create a test entry , that happens before any test run because of the onTestStart listener method
         test = extent.createTest(result.getMethod().getMethodName());
         //pushing the test object inside the ThreadLocal object by using set method
         extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        //getting the pushed object by usnig get method
        extentTest.get().log(Status.PASS,"Test Passed");

    }

    @Override
    public void onTestFailure(ITestResult result) {
        //test.log(Status.FAIL,"Test failed");
        //get the error message
        extentTest.get().fail(result.getThrowable());

        try {

            //getTestClass --> goes to the testng.xml file and get the test that it refers to for example ErrorValidationsTest
            //getRealClass --> it actually goes to the real class which is ErrorValidationsTest
            //getField --> get the field of driver from the real class ErrorValidationsTest from that class whatever the field driver is using , get it
            //why are we going Class level not method level ? because Fields are part of class not part of methods , you can't say like getMethod.getField, they are associated on class level
            //after getting the driver pass it to the getScreenshot method
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());

            //add Exception clas before e object and just throw an error or print the error
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            //getting the filepath you want to get screenshot from
           String filePath = getScreenshot(result.getMethod().getMethodName(),driver);

           //adding the screenshot to your report
            extentTest.get().addScreenCaptureFromPath(filePath,result.getMethod().getMethodName());
        } catch (IOException e) {
            //if screenshot is not found/does not exist return the exception error message
            throw new RuntimeException(e);
        }

    }

    //this is the method that runs at last before it shows the report
    @Override
    public void onFinish(ITestContext context) {
        //you have to flush your test so you delete all your report old files and generate a new one
        extent.flush();
    }
}
