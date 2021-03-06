package Selenium_homework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.internal.Utils;

import java.time.Duration;

public class SixTestClass {
    private WebDriver driver;

    @BeforeMethod
    public void setUpDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/");
    }

    @Test(invocationCount = 5)
    public void checkThatAlertDisappear() {
        String expectedResult = "This alert appeared after 5 seconds";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement adArrow = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("close-fixedban")));
        adArrow.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("close-fixedban")));

        WebElement trackButton = driver.findElement
                (By.xpath("//div[@class='card-body']/h5[text()='Alerts, Frame & Windows']"));
        Utils.scrollToElement(driver, trackButton);
        trackButton.click();

        WebElement clickButton = driver.findElement(By.xpath("//li[@id='item-1']/span[text()='Alerts']"));
        Utils.scrollToElement(driver, clickButton);
        clickButton.click();


        WebElement clickButtonClickMe = driver.findElement(By.id("timerAlertButton"));
        clickButtonClickMe.click();

        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();
        String actualAlertLabel = alert.getText();

        alert.accept();

        Assertions.assertThat(actualAlertLabel)
                .as("nnnnn")
                .isEqualTo(expectedResult);

        boolean isAlertStillPresent;
        try {
            driver.switchTo().alert();
            isAlertStillPresent = true;
        } catch (NoAlertPresentException e) {
            isAlertStillPresent = false;
        }

        Assertions.assertThat(isAlertStillPresent)
                .as("After pressing the button we check whether the alert is closed")
                .isEqualTo(false);

    }
    @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        driver.quit();
    }
}

