package utils;

import UI.base.SeleniumConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElementFetch {
    public WebElement getWebElement(String identifierType, String identifierValue){
        switch (identifierType){
            case "XPATH":
                return SeleniumConfig.driver.findElement(By.xpath(identifierValue));
            case "CSS":
                return SeleniumConfig.driver.findElement(By.cssSelector(identifierValue));
            case "ID":
                return SeleniumConfig.driver.findElement(By.id(identifierValue));
            case "NAME":
                return SeleniumConfig.driver.findElement(By.name(identifierValue));
            case "TAGNAME":
                return SeleniumConfig.driver.findElement(By.tagName(identifierValue));
            case "CLASSNAME":
                return SeleniumConfig.driver.findElement(By.className(identifierValue));
            default:
                return null;
        }
    }

    public List<WebElement> getWebElements(String identifierType, String identifierValue){
        switch (identifierType){
            case "XPATH":
                return SeleniumConfig.driver.findElements(By.xpath(identifierValue));
            case "CSS":
                return SeleniumConfig.driver.findElements(By.cssSelector(identifierValue));
            case "ID":
                return SeleniumConfig.driver.findElements(By.id(identifierValue));
            case "NAME":
                return SeleniumConfig.driver.findElements(By.name(identifierValue));
            case "TAGNAME":
                return SeleniumConfig.driver.findElements(By.tagName(identifierValue));
            case "CLASSNAME":
                return SeleniumConfig.driver.findElements(By.className(identifierValue));
            default:
                return null;
        }
    }
}
