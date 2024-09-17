package UI.pageEvents;

import UI.pageObjects.HomePageElements;
import utils.ElementFetch;

public class GitHubHomePageEvents {
    ElementFetch ele = new ElementFetch();

    public boolean isElementPresent( String identifierType,
                                     String identifierValue){
       return  ele.getWebElement(identifierType, identifierValue).isDisplayed();
    }

    public String getElementValueText( String identifierType,
                                     String identifierValue){
        return  ele.getWebElement(identifierType, identifierValue).getText();
    }

    public void clickElement( String identifierType,
                                       String identifierValue){
          ele.getWebElement(identifierType, identifierValue).click();
    }

    public int returnElementListCount( String identifierType,
                            String identifierValue){
        return ele.getWebElements(identifierType, identifierValue).size();
    }



}
