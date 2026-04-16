package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import java.beans.Transient;

import java.beans.Transient;


// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    Wrappers wrapper;


    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        wrapper = new Wrappers(driver);
        wrapper.launchApplication();
    }
    @Test
    public void testCase01() throws InterruptedException
    {
wrapper.enterText(
    By.xpath("//form[@class='lilxh_ header-form-search']//input[@title='Search for Products, Brands and More']"),
    "Washing Machine");        
    List<WebElement> sort = driver.findElements(By.xpath("//div[@class='WNv7PR']"));
        for(WebElement sr:sort)
        {
            String sc =sr.getText();
            if(sc.trim().equalsIgnoreCase("Popularity"))
            {
                sr.click();
            }
        }
        Thread.sleep(3000);
        List<WebElement> pop = driver.findElements(By.xpath("//div[@class='col col-7-12']//div[@class='MKiFS6']"));
        int count = 0;
        for(WebElement p:pop)
        {
          double rating = Double.parseDouble(p.getText());

    if (rating <= 4.0) {
        count++;
    }
        }
        System.out.println("The count is " + count);

    }
    @Test 
    public void testCase02() throws InterruptedException
    {
        wrapper.launchApplication();
        wrapper.enterText(
    By.xpath("//form[@class='lilxh_ header-form-search']//input[@title='Search for Products, Brands and More']"),
    "iPhone"); 
    Thread.sleep(5000);
    WebElement sd = driver.findElement(By.xpath("//div[@class='kMXMaw']//div[@title='10% or more']"));
    String s = sd.getText();
    System.out.println(s);
    sd.click();
    Thread.sleep(3000);
    List <WebElement> per = driver.findElements(By.xpath("//div[@class='HQe8jr']"));
    List <WebElement> title = driver.findElements(By.xpath("//div[@class='RG5Slk']"));
    for (int i = 0; i < per.size() && i < title.size(); i++) {

    String discountText = per.get(i).getText()
                             .replaceAll("[^0-9]", "")
                             .trim();

    int discount = Integer.parseInt(discountText);

    if (discount > 17) {
        System.out.println("Title: " + title.get(i).getText());
        System.out.println("Discount: " + discount + "%");
        System.out.println("-------------------");
    }}


    }
    @Test 
    public void testCase03()
    {
        wrapper.launchApplication();
        wrapper.enterText(By.xpath("//form[@class='lilxh_ header-form-search']//input[@title='Search for Products, Brands and More']"),
       "Coffee Mug"); 
       List<WebElement> ratings = driver.findElements(
    By.xpath("//div[@class='RGLWAk']//div[@class='MKiFS6']")
);

List<WebElement> productNames = driver.findElements(
    By.xpath("//div[@class='RGLWAk']//a[@class='pIpigb']")
);

List<WebElement> productLinks = driver.findElements(
    By.xpath("//div[@class='RGLWAk']//a[@class='GnxRXv']")
);

for (int i = 0; i < ratings.size() 
        && i < productNames.size() 
        && i < productLinks.size(); i++) {

    String ratingText = ratings.get(i).getText().trim();
    double rating = Double.parseDouble(ratingText);

    if (rating > 4.0) {
        String name = productNames.get(i).getText();
        String url = productLinks.get(i).getAttribute("href");

        System.out.println("Product: " + name);
        System.out.println("Rating: " + rating);
        System.out.println("URL: " + url);
        System.out.println("---------------------");
    }

}





    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}