package utils;

import UI.base.SeleniumConfig;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IAnnotationTransformer;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.lf5.LogLevel;

/**
 * The ITestListener interface in Selenium is a crucial component for implementing custom listeners to enhance the test execution process. It provides a set of methods that are invoked at various stages of a test's lifecycle, allowing you to customize the behavior and gather valuable information.
 *
 * Key Methods in ITestListener Interface:
 *
 * onTestStart(ITestResult result): Called before a test starts.
 * onTestSuccess(ITestResult result): Called after a test passes.
 * onTestFailure(ITestResult result): Called after a test fails.
 * onTestSkipped(ITestResult result): Called after a test is skipped.
 * onTestFailedButWithinSuccessPercentage(ITestResult result): Called after a test fails but is within the configured success percentage.
 * onStart(ITestContext context): Called before the entire test suite starts.
 * onFinish(ITestContext context): Called after the entire test suite finishes.
 * Why Use ITestListener Interface?
 *
 * Custom Reporting:
 *
 * Create tailored reports with specific information, such as test execution time, screenshots, logs, and more.
 * Integrate with external reporting tools like Allure, ExtentReports, or custom reporting frameworks.
 * Test Execution Control:
 *
 * Modify test execution behavior based on certain conditions.
 * For example, you can stop the test execution if a critical failure occurs or skip certain tests based on environmental factors.
 * Data Collection:
 *
 * Gather performance metrics, test data, or other relevant information for analysis.
 * Use this data to identify bottlenecks, improve test efficiency, or track progress.
 * Test Status Notifications:
 *
 * Send notifications or alerts to stakeholders when tests fail or complete.
 * Keep the team informed about the test execution status.
 * Integration with External Systems:
 *
 * Connect your tests with other tools or systems, such as defect management tools or CI/CD pipelines.
 * Automate processes and streamline workflows.
 */


public class SuiteListner implements ITestListener, IAnnotationTransformer {
    private static final Logger logger = Logger.getLogger(SuiteListner.class);

    public void onTestFailure(ITestResult result){
        Log.Message("Test failed: " + result.getName() + result.getThrowable().getMessage(), LogLevel.ERROR);
        String filename = System.getProperty("user.dir")+ File.separator+Constats.directoryScreenshots+File.separator+result.getMethod().getMethodName();
        File file = ((TakesScreenshot) SeleniumConfig.driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(filename+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Code to execute before a test starts
        Log.Message("Test started: " + result.getName(),LogLevel.INFO);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Log.Message("Test passed: " + result.getName(),LogLevel.INFO);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Log.Message("Test skipped: " + result.getName(),LogLevel.WARN);
    }

    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
