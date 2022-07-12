package page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class MainPage {

    private final By firstNameField = By.id("fNameID");
    private final By clickOnMeButton = By.xpath("//button[@id='checkDataID']");
    private final By studentCheckBox = By.xpath("//input[@id='studentID'and @type='checkbox']");
    private final By checkResult = By.id("checkDataResultID");
    private final By universities = By.name("universities");
    private final By javaRadioButton = By.id("javaID");
    private final By linkedinURL = By.linkText("LinkedIn");
    private final By textArea = By.id("aboutMeID");
    private WebDriver driver;
    @FindBy(id = "fNameID")
    private WebElement firstName;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getLinkedinURL() {
        return driver.findElement(linkedinURL);
    }

    public WebElement getFirstNameField() {
        return driver.findElement(firstNameField);
    }

    public void firstName() {
        firstName.sendKeys("HelloWorld");
    }

    public void setFirstName(String value) {
        getFirstNameField().sendKeys(value);
    }

    public void clickOnMe() {
        driver.findElement(clickOnMeButton).click();
    }

    public WebElement clickOnMeButton() {
        return driver.findElement(clickOnMeButton);
    }

    public void selectStudentCheckBox() {
        WebElement element = driver.findElement(studentCheckBox);
        element.click();
        Assert.assertTrue(element.isDisplayed());
    }

    public Select getUniversities() {
        return new Select(driver.findElement(universities));
    }

    public List<WebElement> getUniversitiesAsList() {
        return getUniversities().getOptions();
    }

    public WebElement getJavaRadioButton() {
        return driver.findElement(javaRadioButton);
    }

    public WebElement getCheckResult() {
        return driver.findElement(checkResult);
    }

    public WebElement getTextAreaElement() {
        return driver.findElement(textArea);
    }
}
