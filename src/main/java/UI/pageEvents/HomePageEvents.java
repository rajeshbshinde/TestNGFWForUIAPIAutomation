package UI.pageEvents;

import UI.pageObjects.HomePageElements;
import utils.ElementFetch;

public class HomePageEvents {
    ElementFetch ele = new ElementFetch();
    public void signInButton(){
        ele.getWebElement("XPATH", HomePageElements.SignInButtonText).click();
    }

    public boolean isElementPresent( String identifierType,
                                     String identifierValue){
       return  ele.getWebElement(identifierType, identifierValue).isDisplayed();
    }
    public boolean isSigninButtonPresent(){
        return isElementPresent("XPATH", HomePageElements.SignInButtonText);
    }
}
