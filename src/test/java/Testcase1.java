import UI.base.SeleniumConfig;
import org.testng.Assert;
import org.testng.annotations.Test;
import UI.pageEvents.HomePageEvents;
import UI.pageEvents.LoginPageEvents;

public class Testcase1 extends SeleniumConfig {
    HomePageEvents homepage = new HomePageEvents();
    LoginPageEvents loginPage = new LoginPageEvents();
    @Test(description = "Validate that the homepage and login page is loaded",priority = 1)
    public void testLoginPage(){
        logger.info("Signin into Login Page");
        homepage.signInButton();
        logger.info("Verify if Login Page is loaded");
        loginPage.verifyIfLoginPageIsLoaded();
        logger.info("Enter the credentials");
        loginPage.enterCredentials();
    }
    @Test(description = "Validate the login button is loaded",priority = 2)
    public void testLoginPageControls(){

        Assert.assertTrue(homepage.isSigninButtonPresent(),
                "Element Not Found");
    }
}
