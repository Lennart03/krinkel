/**
 * Created by TLUBB59 on 5/10/2016.
 */

import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

public class PageAccessTest extends TestChromeBrowser {


    private String base_url = "http://localhost:3000/";

    @org.junit.Test(expected = NoSuchElementException.class)
    public void loginPageShouldNotReturnErrorPage() throws InterruptedException {
        driver().get(base_url + "login");
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

        driver().get(base_url + "home");
        assertEquals("home page is still visible", base_url + "login", driver().getCurrentUrl());

        driver().get(base_url + "register-participant");
        assertEquals("register-participant page is still visible", base_url + "login", driver().getCurrentUrl());

        driver().get(base_url + "register-volunteer");
        assertEquals("register-volunteer page is still visible", base_url + "login", driver().getCurrentUrl());

        driver().get(base_url + "success");
        assertEquals("success page is still visible", base_url + "login", driver().getCurrentUrl());
    }

    @Test
    public void testSuccesLogin() throws Exception {
        driver().get(base_url + "login");
        driver().findElement(By.id("user")).clear();
        driver().findElement(By.id("user")).sendKeys("PJ");
        driver().findElement(By.id("password")).clear();
        driver().findElement(By.id("password")).sendKeys("password");
        driver().findElement(By.name("btn_login")).click();
        assertFalse("Succes login", isElementPresent(By.id("toast-container")));
    }

    @Test
    public void testFailLogin() throws Exception {
        driver().get(base_url + "login");
        driver().findElement(By.id("user")).clear();
        driver().findElement(By.id("user")).sendKeys("LOLO");
        driver().findElement(By.id("password")).clear();
        driver().findElement(By.id("password")).sendKeys("lolo");
        driver().findElement(By.name("btn_login")).click();
        System.out.println(isElementPresent(By.id("toast-container")));
        assertTrue("Failed login", isElementPresent(By.id("toast-container")));
    }
}

