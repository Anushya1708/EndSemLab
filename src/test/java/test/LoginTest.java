package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

    WebDriver driver;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.demoblaze.com/");
        driver.manage().window().maximize();
    }

    @Test
    public void loginTest() throws InterruptedException {

        // Click Login button
        driver.findElement(By.id("login2")).click();
        Thread.sleep(2000);

        // Enter Username
        driver.findElement(By.id("loginusername")).sendKeys("testuser");

        // Enter Password
        driver.findElement(By.id("loginpassword")).sendKeys("test123");

        // Click Login
        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        Thread.sleep(3000);

        // Verify login success (username displayed)
        WebElement user = driver.findElement(By.id("nameofuser"));

        if(user.isDisplayed()) {
            System.out.println("Login is Successful");
        } else {
            System.out.println("Login is Failed");
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
