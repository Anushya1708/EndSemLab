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

    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][]{
            {"Geetha_Kumari", "geetha12345"},      // valid
            {"Geetha_kumari", "1234567890"},  // valid (create in site)
            {"wronguser", "test123"},    // invalid
            {"testuser", "wrongpass"}    // invalid
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) throws InterruptedException {

        // Click Login button
        driver.findElement(By.id("login2")).click();
        Thread.sleep(2000);

        // Enter Username
        driver.findElement(By.id("loginusername")).clear();
        driver.findElement(By.id("loginusername")).sendKeys(username);

        // Enter Password
        driver.findElement(By.id("loginpassword")).clear();
        driver.findElement(By.id("loginpassword")).sendKeys(password);

        // Click Login
        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        Thread.sleep(3000);

        // Check success
        try {
            WebElement user = driver.findElement(By.id("nameofuser"));
            if (user.isDisplayed()) {
                System.out.println("Login Successful for: " + username);
                driver.findElement(By.id("logout2")).click(); // logout for next test
            }
        } catch (Exception e) {
            System.out.println("Login Failed for: " + username);
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}