package UI.pageObjects;

public interface GitHubHomePageElements {
    String userName = "//span[@class='p-nickname vcard-username d-block']";
    String repoCount = "//div[@class='UnderlineNav width-full box-shadow-none js-responsive-underlinenav overflow-md-x-hidden']//span[@title='15'][normalize-space()='15']";
    String firstRepoLink = "//span[normalize-space()='CWPInstallerPowershell']";

    String repositoryList = "//div[@id='user-repositories-list']//li";

    String repolist = "//div[@class='UnderlineNav width-full box-shadow-none js-responsive-underlinenav overflow-md-x-hidden']//nav[@aria-label='User profile']";

}
