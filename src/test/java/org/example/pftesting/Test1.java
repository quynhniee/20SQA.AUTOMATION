package org.example.pftesting;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class Test1  {
    ChromeDriver driver;
    @BeforeSuite
    public void Setup() {

        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Users/bbadmin/chrome/mac_arm-122.0.6261.69/chrome-mac-arm64/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing");
        options.addArguments("--user-data-dir=/Users/bbadmin/Library/Application Support/Google/Chrome for Testing");
        options.addArguments("--profile-directory=Profile 1");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
    }

    @AfterSuite
    public void Teardown() {
        driver.quit();
    }

    public WebDriverWait waitFunc (Integer seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    @Test (description = "Open new home page then click Don't remind me again on Enable autosave? modal")
    public void Run() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        String id = UUID.randomUUID().toString();
        driver.get("https://admin.shopify.com/store/quynhquynhiee/apps/pageflybackend-9/editor?type=home&id="+id );
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("app-iframe"));
        LocalStorage local = ((WebStorage) driver).getLocalStorage();
        local.removeItem("no-auto-save");
        local.setItem("warning_publish_home", "true");

        //        driver.switchTo().frame("app-iframe");
        driver.findElement(By.cssSelector("button#catalog--add-element-btn")).click();
        WebElement catalogMenu = driver.findElement(By.id("catalog-menu-section"));
        catalogMenu.findElement(By.cssSelector("button#catalog--catalog-list--heading")).click();
        WebElement catalogItems = driver.findElement(By.id("catalog-items"));
        driver.findElement(By.cssSelector("button#catalog--add-shopify-element-btn"));

        TimeUnit.SECONDS.sleep(1);
        WebElement from = driver.findElement(By.className("Catalog-Image"));
        WebElement to = driver.findElement(By.cssSelector("iframe"));
        Actions builder = new Actions(driver);
        builder.dragAndDrop(from, to).perform();

        WebElement title = driver.findElement(By.cssSelector("#editor-header-bar--page-title"));
        title.click();
        title.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END), id);
        driver.findElement(By.cssSelector("button#editor-header-bar--other-save-actions-btn")).click();
        driver.findElement(By.cssSelector("button#editor-header-bar--save-and-publish-page-btn")).click();

        WebElement portal = driver.findElement(By.id("PolarisPortalsContainer"));
        WebElement beforeSaveModal = portal.findElement(By.cssSelector("div[role=dialog]"));
        beforeSaveModal.findElement(By.id("menubar--save-modal--primary")).click();

        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.cssSelector(".Polaris-Frame-ToastManager .Polaris-Frame-Toast")), "Publishing page..."));
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("editor-header-bar--status")), "Saved"));
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("editor-header-bar--status")), "Published"));

        WebElement autoSaveModal = portal.findElement(By.cssSelector("div[role=dialog]"));
        Assert.assertTrue(autoSaveModal.getText().contains("Enable autosave?"));

        autoSaveModal.findElement(By.className("Polaris-Checkbox")).click();
        autoSaveModal.findElement(By.xpath("//button/*[text()='Enable']")).click();
        wait.until(ExpectedConditions.invisibilityOf(autoSaveModal));
    }

    @Test (description = "Publish another home page, expect not to show Enable autosave? modal")
    public void Run2() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        String id = UUID.randomUUID().toString();
        driver.get("https://admin.shopify.com/store/quynhquynhiee/apps/wip-pagefly/editor?type=home&id="+id );
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("app-iframe"));
        LocalStorage local = ((WebStorage) driver).getLocalStorage();

        //        driver.switchTo().frame("app-iframe");
        driver.findElement(By.cssSelector("button#catalog--add-element-btn")).click();
        WebElement catalogMenu = driver.findElement(By.id("catalog-menu-section"));
        catalogMenu.findElement(By.cssSelector("button#catalog--catalog-list--heading")).click();
        WebElement catalogItems = driver.findElement(By.id("catalog-items"));
        driver.findElement(By.cssSelector("button#catalog--add-shopify-element-btn"));

        TimeUnit.SECONDS.sleep(1);
        WebElement from = driver.findElement(By.className("Catalog-Image"));
        WebElement to = driver.findElement(By.cssSelector("iframe"));
        Actions builder = new Actions(driver);
        builder.dragAndDrop(from, to).perform();

        WebElement title = driver.findElement(By.cssSelector("#editor-header-bar--page-title"));
        title.click();
        title.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END), id);
        driver.findElement(By.cssSelector("button#editor-header-bar--other-save-actions-btn")).click();
        driver.findElement(By.cssSelector("button#editor-header-bar--save-and-publish-page-btn")).click();

        WebElement portal = driver.findElement(By.id("PolarisPortalsContainer"));
        WebElement beforeSaveModal = portal.findElement(By.cssSelector("div[role=dialog]"));
        beforeSaveModal.findElement(By.id("menubar--save-modal--primary")).click();

        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.cssSelector(".Polaris-Frame-ToastManager .Polaris-Frame-Toast")), "Publishing page..."));
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("editor-header-bar--status")), "Saved"));
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("editor-header-bar--status")), "Published"));

        Assert.assertThrows(NoSuchElementException.class, () -> {
            try {
                portal.findElement(By.cssSelector("div[role=dialog]"));
            } catch (Exception e) {
                throw new NoSuchElementException(e.getMessage());
            }
        });
    }

}
