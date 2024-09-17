import UI.base.SeleniumConfig;
import UI.pageEvents.GitHubHomePageEvents;
import UI.pageObjects.GitHubHomePageElements;
import org.apache.log4j.lf5.LogLevel;
import org.testng.Assert;

import UI.pageEvents.HomePageEvents;
import UI.pageEvents.LoginPageEvents;
import org.testng.annotations.Test;
import utils.Log;

public class GitOverviewTabTests extends SeleniumConfig {
    GitHubHomePageEvents gitHubHomePageEvents = new GitHubHomePageEvents();

    HomePageEvents homepage = new HomePageEvents();
    LoginPageEvents loginPage = new LoginPageEvents();

    @Test
    void verifyUserNameIsCorrectOnOverviewTab(){
        logger.info("Verify the User Name on the Overview Tab");
        // Arrange
        String user = "rajeshbshinde";
        // Act
        String actualUserName = gitHubHomePageEvents.getElementValueText("XPATH", GitHubHomePageElements.userName);
        //Assert
        Assert.assertEquals(actualUserName,user,"User name are not equal");
    }
}
