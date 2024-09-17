import UI.base.SeleniumConfig;
import UI.pageEvents.GitHubHomePageEvents;
import UI.pageEvents.HomePageEvents;
import UI.pageEvents.LoginPageEvents;
import UI.pageObjects.GitHubHomePageElements;
import org.apache.log4j.lf5.LogLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Log;

public class GitRepoDetailsTabTests extends SeleniumConfig {
    GitHubHomePageEvents gitHubHomePageEvents = new GitHubHomePageEvents();

    HomePageEvents homepage = new HomePageEvents();
    LoginPageEvents loginPage = new LoginPageEvents();

    @Test()
    void verifyClickOnRepoGoesToCorrectRepo(){
        logger.info("Verify the clicking on specific repo navigate to appropriate link");
        Log.Message("Verify the clicking on specific repo navigate to appropriate link", LogLevel.INFO);
        gitHubHomePageEvents.clickElement("XPATH", GitHubHomePageElements.firstRepoLink);
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl,"https://github.com/rajeshbshinde/CWPInstallerPowershell", "URL are not equal");
    }

}
