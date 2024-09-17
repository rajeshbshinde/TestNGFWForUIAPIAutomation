import UI.base.SeleniumConfig;
import UI.pageEvents.GitHubHomePageEvents;
import UI.pageEvents.HomePageEvents;
import UI.pageEvents.LoginPageEvents;
import UI.pageObjects.GitHubHomePageElements;
import org.apache.log4j.lf5.LogLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Log;

public class GitRepoListTabTests extends SeleniumConfig {
    GitHubHomePageEvents gitHubHomePageEvents = new GitHubHomePageEvents();

    HomePageEvents homepage = new HomePageEvents();
    LoginPageEvents loginPage = new LoginPageEvents();


    @Test
    void verifyRepositoryCountIsCorrect(){
        logger.info("Verify the repo count should match");
        Log.Message("Verify the repo count should match",LogLevel.INFO);
        driver.get("https://github.com/rajeshbshinde?tab=repositories");
        int actualRepoCount = gitHubHomePageEvents.returnElementListCount("ID",GitHubHomePageElements.repositoryList);
        Assert.assertEquals(actualRepoCount,15, "Count does not match");
    }
}
