
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by TLUBB59 on 5/10/2016.
 */
public abstract class TestChromeBrowser {

  static String driverPath = "C:\\Users\\TLUBB59\\Downloads\\chromedriver_win32\\";
  private static WebDriver driver;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass
  public void setUp() {
    System.out.println("*******************");
    System.out.println("launching chrome browser");
    System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
    driver = new ChromeDriver();
    driver.manage().window().maximize();
  }





  @AfterClass
  public void tearDown() {
    if (driver != null) {
      System.out.println("Closing chrome browser");
      driver.quit();
    }
  }
  public boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }


  protected WebDriver driver() {
    return driver;
  }
}
