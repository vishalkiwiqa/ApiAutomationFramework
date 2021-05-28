//package com.MBS.Utility;
//
//import org.apache.log4j.Logger;
//import org.jbehave.core.failures.UUIDExceptionWrapper;
//import org.openqa.selenium.*;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.FluentWait;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//import java.util.List;
//
//public class WebOperations {
//
//    private WebDriver driver = ConfigDriver.getInstance().getDriver();
//    public static Logger LOG = Logger.getLogger(WebOperations.class.getName());
//
//    public void enterData(By locator, String inputData) {
//
//        WebElement element = fluentlyWait(locator);
//        if (element != null) {
//            //  driver.findElement(locator).clear();
//            //driver.findElement(locator).sendKeys(inputData);
//            element.clear();
//            element.sendKeys(inputData);
//        } else {
//            LOG.info("====Element not found by locator= " + locator);
//        }
//    }
//
//    public WebElement findElement(By locator) {
//
////        WebElement element = explicitWait(locator,7);
//        WebElement element = fluentlyWait(locator);
//        if (element != null) {
//            return element;
//        } else {
//            LOG.info("^^^$$$$Element not found by locator= " + locator);
//            return null;
//        }
//
//    }
//
//    public List<WebElement> findElements(By locator) {
//
//        List<WebElement> elList = driver.findElements(locator);
//
//        if (elList != null) {
//            return elList;
//        } else {
//            //return throw new SwitchException()
//            LOG.info("^^^$$$$ Element list not found by locator= " + locator);
//            return null;
//        }
//
//    }
//
//    public boolean eventClick(By locator) {
//
//        try {
//            explicitWait(locator, 10);
//            WebElement element = driver.findElement(locator);
//            element.click();
//            LOG.info("**Clicked on Element: " + element);
//            return true;
//
//        } catch (NoSuchElementException e) {
//            LOG.info("Element doesn't found by locator>> " + locator);
//            e.getMessage();
//            return false;
//
//        } catch (ElementNotVisibleException e1) {
//            LOG.info("Element by locator : " + locator + " is not visible ");
//            e1.getMessage();
//            return false;
//        } catch (Exception e) {
//            LOG.error("$$Some exception occurred while clicking element:" + locator +
//                    " $$$Error is : " + e.getMessage());
//            return false;
//        }
//
//    }
//
//    public void moveToElement(By locator) {
//        Actions actions = new Actions(driver);
//        actions.moveToElement(driver.findElement(locator));
//        driver.findElement(locator).click();
//
//    }
//
//    public boolean javaScriptClick(By locator) {
//        try {
//            WebElement ele = driver.findElement(locator);
//            JavascriptExecutor executor = (JavascriptExecutor) driver;
//            executor.executeScript("arguments[0].click();", ele);
//            LOG.info("Successfully clicked on Element.......");
//            return true;
//        } catch (Exception e) {
//            LOG.error("--->>"+e.getMessage());
//            return false;
//        }
//    }
//
//    public WebElement explicitWait(By locator, int waitTime) {
//        WebDriverWait wait = new WebDriverWait(driver, waitTime);
//        wait.until(ExpectedConditions.elementToBeClickable(locator));
//        return wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
//    }
//
//    public WebElement fluentlyWait(By locator) {
//
//        FluentWait<WebDriver> wait = new FluentWait<>(driver);
//        wait.withTimeout(Duration.ofSeconds(10))
//                .pollingEvery(Duration.ofMillis(200))
//                .ignoring(NoSuchElementException.class, NoSuchFrameException.class);
////        wait.until(ExpectedConditions.presenceOfElementLocated(locator)); // wait for element to be present in the dom
//        try {
//            WebElement element = wait.until(
//                    webDriver -> driver.findElement(locator)
//            );
//            return element;
//        } catch (TimeoutException e) {
//            LOG.info("***** Element doesn't found, Exception occurred: >> " + e.getMessage());
//            return null;
//        } catch (StaleElementReferenceException stale) {
//            driver.navigate().refresh();
//            return fluentlyWait(locator);
//        } catch (UUIDExceptionWrapper e) {
//            LOG.info("##JBehave Exception UUIDException occurred: " + e.getMessage());
//            return null;
//        } catch (Exception e) {
//            LOG.error("$$ Some exception occurred in fluent wait: " + e.getMessage());
//            return null;
//        }
//
//    }
//
//    public boolean waitUntilElNotDisplayed(By locator) {
//
//        FluentWait<WebDriver> wait = new FluentWait<>(driver).pollingEvery(Duration.ofSeconds(100)).withTimeout(Duration.ofSeconds(10));
//
//        wait.until(wdriver -> {
//            try {
//                WebElement element = wdriver.findElement(locator);
//                element.isDisplayed();
//                LOG.info("** Element is display ... ");
//                return false;
//            } catch (Exception e) {
//                LOG.info("** Element is gone ... ");
//                return true;
//            }
//        });
//        return false;
//    }
//
//    public void scrollDown(By locator) {
//        JavascriptExecutor jse = (JavascriptExecutor) driver;
//        jse.executeScript("arguments[0].scrollIntoView()", driver.findElement(locator));
//    }
//
//}
