package testBase;


import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
    public static WebDriver driver;
   
   
    @BeforeTest
    public void setUpDriver() {
         WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
         driver.get("https://www.tarladalal.com/");
         driver.manage().window().maximize();
         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
 		driver.manage().timeouts().pageLoadTimeout(3600, TimeUnit.SECONDS);
      
      //  options.addArguments("--headless");
       // driver = new ChromeDriver(options);
       
    }
  
    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}