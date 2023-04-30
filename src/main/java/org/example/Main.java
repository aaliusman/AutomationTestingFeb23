package org.example;

import com.google.common.annotations.VisibleForTesting;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {

    WebDriver driver;
    String expectedSuggestionValue = "United States (USA)";
    List<String> expectedDropDown = new ArrayList<>(Arrays.asList("Select", "Option1", "Option2", "Option3"));
    String alertText = "Usman";
    String expectedAlertMessage = "Hello " + alertText + ", share this practice page and share your knowledge";

    @Test
    public void testGoogle() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com");
        driver.findElement(By.id("APjFqb")).sendKeys("Java course for beginners");
        driver.findElement(By.id("jZ2SBf")).click();
        driver.close();
    }

    @Test
    public void testRadioButton() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.findElement(By.xpath("//input[@value='radio1']")).click();
        driver.findElement(By.xpath("//input[@value='radio2']")).click();
        driver.findElement(By.xpath("//input[@value='radio3']")).click();
        driver.close();
    }

    @Test
    public void testSuggestionClass() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.findElement(By.cssSelector(".inputs.ui-autocomplete-input")).sendKeys("United");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//li[@class='ui-menu-item']/div[text()='United States (USA)']")).click();
        String actualSuggestionText = driver.findElement(By.cssSelector("#autocomplete")).getAttribute("value");
        Assert.assertEquals(actualSuggestionText, expectedSuggestionValue);
        driver.close();
    }

    @Test
    public void testSelectDropDown() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        Select select = new Select(driver.findElement(By.name("dropdown-class-example")));
        select.selectByIndex(1);
        Thread.sleep(1000);
        select.selectByVisibleText("Option2");
        Thread.sleep(1000);
        select.selectByValue("option3");

        List<WebElement> elements = select.getOptions();
        for (int i = 0; i < elements.size(); i++) {
            Assert.assertEquals(elements.get(i).getText(), expectedDropDown.get(i));
        }

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getText().contains("Option2")) {
                System.out.println("Option " + i + " contains Option2");
            } else {
                System.out.println("Option " + i + " does not contain Option2");
            }
        }
        driver.close();
    }

    @Test
    public void testCheckBox() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.findElement(By.cssSelector("#checkBoxOption1")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector("#checkBoxOption1")).isSelected());
        driver.findElement(By.name("checkBoxOption2")).click();
        Assert.assertTrue(driver.findElement(By.name("checkBoxOption2")).isSelected());
        Assert.assertFalse(driver.findElement(By.name("checkBoxOption3")).isSelected());
        driver.close();
    }

    @Test
    public void testAlert() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.findElement(By.cssSelector("#name")).sendKeys(alertText);
        driver.findElement(By.id(("alertbtn"))).click();
        Thread.sleep(2000);
        String actualAlertMessage = driver.switchTo().alert().getText();
        Assert.assertEquals(actualAlertMessage, expectedAlertMessage);
        driver.switchTo().alert().accept();
        driver.close();
    }

    @Test
    public void testSwitchTab () throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        //window 1
        String mainWindow = driver.getWindowHandle();
        driver.findElement(By.partialLinkText("Open Tab")).click();
        Thread.sleep(1000);
        // it could be 2 or more windows
        Set<String> windows = driver.getWindowHandles();
        for (String w: windows) {
            if (!w.equalsIgnoreCase(mainWindow)) {
                Thread.sleep(1000);
                driver.switchTo().window(w);
                Assert.assertEquals(driver.getCurrentUrl(), "https://www.qaclickacademy.com/");
                Thread.sleep(1000);
                Assert.assertEquals(driver.getTitle(), "QAClick Academy - A Testing Academy to Learn, Earn and Shine");
                String  courses = driver.findElement(By.linkText("Courses")).getText();
                Assert.assertEquals(courses, "Courses", "Text do not match");
                driver.close();
            }
        }
        driver.switchTo().window(mainWindow);
        Assert.assertEquals(driver.getTitle(), "Practice Page");
        driver.close();

    }

    @Test
    public void checkElementDisplayed() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        Assert.assertTrue(driver.findElement(By.name("show-hide")).isDisplayed(), "Element is not displayed");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("#hide-textbox")).click();
        Assert.assertFalse(driver.findElement(By.name("show-hide")).isDisplayed(), "Element is displayed");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("#show-textbox")).click();
        Assert.assertTrue(driver.findElement(By.name("show-hide")).isDisplayed(), "Element is not displayed");
        driver.close();

    }
}