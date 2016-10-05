/**
 * Created by TLUBB59 on 5/10/2016.
 */

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.junit.Assert.assertNotNull;

public class PageAccessTest extends TestChromeBrowser {


  private String base_url = "http://localhost:3000/";

  @org.junit.Test(expected = NoSuchElementException.class)
  public void loginPageShouldNotReturnErrorPage() throws InterruptedException {
    driver().get(base_url + "/login");
    driver().findElement(By.id("error-stack-trace"));
  }

  @Test
  public void testPageTitleInBrowser() {
    driver().navigate().to("http://localhost:3000");
    String strPageTitle = driver().getTitle();
    System.out.println("Page title: - " + strPageTitle);
    Assert.assertTrue(strPageTitle.equalsIgnoreCase("Krinkel"), "Page title doesn't match");
  }


  @Test
  public void adminPagesShouldNotBeAccessableToPublic() throws InterruptedException {

    driver().get(base_url + "/home");
    WebElement element = driver().findElement(By.id("login"));
    assertNotNull(element);

    driver().get(base_url + "/register-participant");
    element = driver().findElement(By.id("login"));
    assertNotNull(element);

    driver().get(base_url + "/select-participant");
    element = driver().findElement(By.id("login"));
    assertNotNull(element);

    driver().get(base_url + "/register-employee");
    element = driver().findElement(By.id("login"));
    assertNotNull(element);

    driver().get(base_url + "/success");
    element = driver().findElement(By.id("login"));
    assertNotNull(element);
  }


}

