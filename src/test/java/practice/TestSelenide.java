package practice;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Browsers.EDGE;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class TestSelenide {

    @Test
    public void testSelenide() {

        Configuration.browser = EDGE;
        Selenide.open("https://www.lu.lv");
        //$(By.xpath("//ul[@class='mainMenu__menu']//li//button")).click();
        //$$(By.xpath("//ul[@class='mainMenu__menu']//li//button"));
        $(By.xpath("//ul[@class='mainMenu__menu']//li//button"))
                .shouldBe(appear)
                .shouldNot(be(disabled))
                .shouldHave(exactText("GRIBU STUDĒT"))
                .click();
    }
}
