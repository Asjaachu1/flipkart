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
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

import java.text.NumberFormat;
import java.util.Locale;
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
     Thread.sleep(5000);

    // Get discount elements
       List<WebElement> discountList = driver.findElements(
               By.xpath("//div[@class='ZFwe0M row']//div[@class='HQe8jr']/span")
       );
   
       HashMap<String, String> iphoneMap = new HashMap<>();
   
       for (WebElement productRow : discountList) {
   
           String discountText = productRow.getText();   // e.g. "20% off"
           int discountValue = Integer.parseInt(discountText.replaceAll("[^\\d]", ""));
        int discount = 17;
           if (discountValue > discount) {
   
               String title = productRow.findElement(
                       By.xpath("//div[@class='ZFwe0M row']//div[@class='HQe8jr']/span/./../../../../..//div[@class='RG5Slk']")
               ).getText();
   
               iphoneMap.put(discountText, title);
           }
       }
   
       // Print result
       for (Map.Entry<String, String> entry : iphoneMap.entrySet()) {
           System.out.println("Discount: " + entry.getKey() + " | Title: " + entry.getValue());
       }
   
       System.out.println("Ending Test Case 02");
   }
  @Test
public void testCase03() throws InterruptedException {

    wrapper.launchApplication();

    wrapper.enterText(
        By.xpath("//input[@title='Search for Products, Brands and More']"),
        "Coffee Mug"
    );

    Thread.sleep(3000);

    // Click 4★ & above
    driver.findElement(By.xpath("//div[contains(text(),'4★ & above')]")).click();

    Thread.sleep(3000);

    // Get review count elements
    List<WebElement> reviewElements = driver.findElements(
            By.xpath("//div[@class='a7saXW WDsrYC']//span[@class='PvbNMB']")
    );

    Set<Integer> reviewSet = new HashSet<>();

    for (WebElement element : reviewElements) {
        int count = Integer.parseInt(element.getText().replaceAll("[^\\d]", ""));
        reviewSet.add(count);
    }

    // Convert & sort
    List<Integer> reviewList = new ArrayList<>(reviewSet);
    Collections.sort(reviewList, Collections.reverseOrder());

    NumberFormat formatter = NumberFormat.getInstance(Locale.US);

    for (int i = 0; i < 5; i++) {

        String formattedCount = "(" + formatter.format(reviewList.get(i)) + ")";

        // Title
        String title = driver.findElement(By.xpath(
                "//div[@class='RGLWAk']//span[contains(text()'" + formattedCount + "')]/./../..//a[@class='pIpigb']"
        )).getText();

        // Image URL
        String imageUrl = driver.findElement(By.xpath(
                "//div[@class='RGLWAk']//span[contains(text(),'" + formattedCount + "')]/./../..//a[@class='GnxRXv']"
        )).getAttribute("src");

        System.out.println("Review: " + formattedCount + " | Title: " + title);
        System.out.println("Image URL: " + imageUrl);
    }

    System.out.println("Ending Test Case 03");

}
    

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}