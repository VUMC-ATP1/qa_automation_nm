package practice;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import page_object.MainPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j
public class BrowserTest {

    private final String GOOGLE_URL = "https://www.google.lv/";
    private final String LOCAL_FILE = "file://" + this.getClass().getResource("/elements.html").getPath();
    //    ChromeDriver driver;
    WebDriver driver;

    MainPage mainPage;

    WebDriverWait wait;

    @BeforeTest
    public void setProperties() {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
    }

    @BeforeMethod(alwaysRun = true)
    public void openBrowser() throws MalformedURLException {
        log.info("Initializing ChromeDriver");
//        driver = new ChromeDriver();
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("browserName", "Safari");
        caps.setCapability("appium:deviceName", "iPad Pro (12.9 inch) (4th generation) Simulator");
        caps.setCapability("appium:platformVersion", "15.4");
        MutableCapabilities sauceOptions = new MutableCapabilities();
        caps.setCapability("sauce:options", sauceOptions);
//        driver = new RemoteWebDriver(new URL("http://192.168.0.129:4444/"), options);
        driver = new RemoteWebDriver(new URL("https://oauth-nikita-3fa38:82e6f657-8bd5-4dd4-90fb-74ae7940dff3@ondemand.us-west-1.saucelabs.com:443/wd/hub"), caps);
        mainPage = new MainPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void chromeDriverTest() {
        Assert.assertEquals(driver.getTitle(), "Google");
        Assert.assertEquals(driver.getCurrentUrl(), GOOGLE_URL);
    }

    @Test
    public void elementTest() {
        driver.get(LOCAL_FILE);
        driver.findElement(By.id("fNameID")).sendKeys("John");
        driver.findElement(By.id("lNameID")).sendKeys("Doe");
        driver.findElement(By.xpath("//textarea[@id='aboutMeID']")).sendKeys("Hello World!");
        driver.findElement(By.id("checkDataID")).click();

        WebElement nameField = driver.findElement(By.id("fNameID"));
        nameField.sendKeys("John");

        WebElement lastNameField = driver.findElement(By.id("lNameID"));
        lastNameField.sendKeys("Doe");


        Select universityDropDown = new Select(driver.findElement(By.name("universities")));
        universityDropDown.selectByValue("RSU");
        universityDropDown.selectByVisibleText("Rīgas Tehniskā universitāte");
        Assert.assertEquals(universityDropDown.getFirstSelectedOption().getText(), "Rīgas Tehniskā universitāte");
    }

    @Test
    public void pageObject() {
        driver.get(LOCAL_FILE);
        mainPage.setFirstName("John");
        System.out.println();
    }

    @Test
    public void testElements() {
        driver.get(LOCAL_FILE);

        mainPage.getFirstNameField().sendKeys("John");
        mainPage.selectStudentCheckBox();
        mainPage.getUniversities().selectByValue("RSU");
        Assert.assertEquals(mainPage.getUniversities().getFirstSelectedOption().getText(), "Rīgas Stradiņa universitāte");

        mainPage.clickOnMe();
        wait.until(ExpectedConditions.textToBePresentInElement(mainPage.getCheckResult(), "Viss ir labi!"));
        Assert.assertEquals(driver.findElement(By.id("checkDataResultID")).getText(), "Viss ir labi!");
    }

    @Test
    public void testBrowserTab() {

        // Open elements.html file
        driver.get(LOCAL_FILE);

        mainPage.getLinkedinURL().click();

        // Switch between tabs
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.close();
        driver.switchTo().window(tabs.get(0));
        tabs = new ArrayList<>(driver.getWindowHandles());
    }

    @Test
    public void testJsExecutor() {
        driver.get("https://www.lu.lv");
        WebElement kontakti = driver.findElement(By.linkText("Kontakti"));
        // Object casting
        // Scroll to the element
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", kontakti);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", kontakti);
        System.out.println();
        // Scroll by Y axis
        //((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1000)", kontakti);
    }

    @Test
    public void testActions() {
        driver.get(LOCAL_FILE);
        Actions actions = new Actions(driver);
        // COMMAND + A / CTRL + A
        // COMMAND + C / CTRL + C
        // COMMAND + V / CTRL + V
        // COMMAND + V / CTRL + V
        mainPage.getTextAreaElement().click();
        actions.moveToElement(mainPage.getTextAreaElement()).keyDown(Keys.COMMAND).sendKeys("acvv").keyUp(Keys.COMMAND).perform();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("Closing ChromeDriver");
        driver.close();
        driver.quit();
    }
}