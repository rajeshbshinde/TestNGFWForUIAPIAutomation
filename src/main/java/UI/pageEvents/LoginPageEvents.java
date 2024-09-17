package UI.pageEvents;

import UI.pageObjects.LoginPageElements;
import org.testng.Assert;
import utils.ElementFetch;

public class LoginPageEvents {
    ElementFetch ele = new ElementFetch();
    public void verifyIfLoginPageIsLoaded(){
        Assert.assertTrue(ele.getWebElements("XPATH", LoginPageElements.loginText).size()>0,
                "Element Not Found");
    }

    public void enterCredentials(){
        ele.getWebElement("XPATH", LoginPageElements.emailAddress).sendKeys("abcd@gmail.com");
        ele.getWebElement("XPATH", LoginPageElements.passwordField).sendKeys("abcd");
    }

    public void verifyIfLoginButtonIsLoaded(){
        Assert.assertTrue(ele.getWebElements("XPATH", LoginPageElements.loginButton).size()>0,
                "Element Not Found");
    }
}
