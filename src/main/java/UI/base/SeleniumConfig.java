package UI.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.Constats;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SeleniumConfig {
    public static WebDriver driver;
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest logger;

    @BeforeTest
    public void beforeTestMethod(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);
        // Create the report file name with datetime
        String reportPath = System.getProperty("user.dir") + File.separator+"Reports"+
                File.separator;
        String reportFileName = "AutomationReport_" + formattedDateTime + ".html";
        String reportFile = System.getProperty("user.dir") + File.separator+"Reports"+
                File.separator+"AutomationReport.html";
        File originalFile = new File(reportPath+"AutomationReport.html");
        File newFile = new File(reportPath+reportFileName);
        if (originalFile.exists()) {
            if (originalFile.renameTo(newFile)) {
                System.out.println("File renamed successfully.");
            } else {
                System.out.println("Failed to rename file.");
            }
        }
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator+"Reports"+
                File.separator+"AutomationReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        sparkReporter.config().setTheme(Theme.DARK);
        extent.setSystemInfo("HostName","RHEL8");
        extent.setSystemInfo("UserName","root");
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Automation Tests Results by Rajesh");
    }
    @BeforeMethod
    @Parameters("browser")
    public void beforeMethod(String browser, Method testMethod){
        logger = extent.createTest(testMethod.getName());
        setupDriver(browser);
        driver.manage().window().maximize();
        driver.get(Constats.url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }
    @AfterMethod
    public void afterMethod(ITestResult result){
        if(result.getStatus() == ITestResult.FAILURE){
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test case Failed", ExtentColor.RED));
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test case Failed", ExtentColor.RED));
        }else if(result.getStatus() == ITestResult.SKIP){
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test case Skipped", ExtentColor.RED));
        }if(result.getStatus() == ITestResult.SUCCESS){
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test case PASS", ExtentColor.RED));
        }

    }
//    @AfterTest
//    public void afterTest(){
//        extent.flush();
//    }

    @AfterTest
    public void afterSuite(){
        extent.flush();
        driver.quit();
    }
    public void setupDriver(String browser){
        if(browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }else if(browser.equalsIgnoreCase("firefox")){
            WebDriverManager.chromedriver().setup();
            driver = new FirefoxDriver();
        }else if(browser.equalsIgnoreCase("edge")){
            WebDriverManager.chromedriver().setup();
            driver = new EdgeDriver();
        }
//        Duration maximumWaitTime = Duration.ofSeconds(30);
//        driver.manage().timeouts().implicitlyWait(maximumWaitTime);
    }
}
