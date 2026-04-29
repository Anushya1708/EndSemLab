package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class LoginTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
        driver.manage().window().maximize();
    }

    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][]{
        	{"Geetha_Kumari", "geetha12345"},      
            {"Geetha_kumari", "1234567890"},  
            {"wronguser", "test123"},    
            {"testuser", "wrongpass"}
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) {

        try {
            WebElement closeBtn = driver.findElement(By.xpath("//div[@id='logInModal']//button[@class='close']"));
            if (closeBtn.isDisplayed()) {
                closeBtn.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("logInModal")));
            }
        } catch (Exception e) {
           
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.id("login2"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModal")));

        driver.findElement(By.id("loginusername")).clear();
        driver.findElement(By.id("loginusername")).sendKeys(username);

        driver.findElement(By.id("loginpassword")).clear();
        driver.findElement(By.id("loginpassword")).sendKeys(password);

        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        try {
            WebElement user = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));
            System.out.println("Login is Successful for: " + username);

            // Logout for next iteration
            driver.findElement(By.id("logout2")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("nameofuser")));

        } catch (Exception e) {
            System.out.println("Login is Failed for: " + username);
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}

