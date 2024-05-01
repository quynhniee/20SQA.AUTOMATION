package keywords;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class WebUI {

    @Step("Wait for the element {0} to be visible")
    public static SelenideElement waitForElementVisible(By by) {
        SelenideElement element = $(by);
        element.shouldBe(Condition.visible);
        return element;
    }

    @Step("Click on the element {0}")
    public static void clickElement(By by) {
        waitForElementVisible(by).click();
    }

    @Step("Click on the element containing text \"{0}\"")
    public static void clickElementWithText(String text) {
        SelenideElement element = $x("//*[contains(text(),'" + text + "')]");
        element.shouldBe(Condition.visible).click();
    }

    @Step("Click on the button containing text \"{0}\"")
    public static void clickButtonWithText(String text) {
        SelenideElement element = $x("//button//*[contains(text(),'" + text + "')]");
        element.shouldBe(Condition.visible).click();
    }

    @Step("Click on the button with text \"{1}\" inside the modal {0}")
    public static void clickButtonInModal(SelenideElement modal, String buttonText) {
        modal.$x(".//button//*[contains(text(),'" + buttonText + "')]").shouldBe(Condition.visible).click();
    }

    @Step("Wait until a modal with title \"{0}\" is visible")
    public static SelenideElement waitForModalWithTitle(String title) {
        By modalSelector = By.xpath("//h2[contains(text(),'" + title + "')]/ancestor::div[contains(@class, 'Polaris-Modal-Dialog__Modal')]");
//        By modalTitleSelector = By.xpath("//div[contains(@class, 'Polaris-Modal-Dialog__Modal')]//*//h2[contains(text(),'" + title + "')]");
        return waitForElementVisible(modalSelector);
    }

    @Step("Assert that the modal located by {0} is open")
    public static void assertModalIsOpen(By modalBy) {
        $(modalBy).shouldBe(Condition.visible);
    }

    @Step("Assert that the modal located by {0} is closed")
    public static void assertModalIsClosed(SelenideElement modalBy) {
        modalBy.shouldNotBe(Condition.visible);
    }

    @Step("Assert that the modal with title \"{0}\" is closed")
    public static void assertModalWithTitleIsClosed(String title) {
        By modalTitleSelector = By.xpath("//div[contains(@class, 'Polaris-Modal-Dialog__Modal')]//*//h2[contains(text(),'" + title + "')]");
        $(modalTitleSelector).shouldNotBe(Condition.visible);
    }

    @Step("Clear the input field {0}")
    public static void clearInputField(By by) {
        SelenideElement element = waitForElementVisible(by).shouldBe(Condition.enabled);
        String inputValue = element.getValue() == null ? "" : element.getValue();
        for (int i = 0; i < inputValue.length(); i++) {
            element.sendKeys(Keys.BACK_SPACE);
        }
    }

    @Step("Clear and fill the input field {0} with value \"{1}\"")
    public static void clearAndFillValue(By by, String value) {
        SelenideElement element = waitForElementVisible(by).shouldBe(Condition.enabled);
        String inputValue = element.getValue() == null ? "" : element.getValue();
        for (int i = 0; i < inputValue.length(); i++) {
            element.sendKeys(Keys.BACK_SPACE);
        }
        element.setValue(value);
    }

    public static void waitForOptionsToLoad(By selectBy) {
        $(selectBy).$$("option").shouldBe(CollectionCondition.sizeGreaterThanOrEqual(1));
    }

    @Step("Select the option with value \"{1}\" in the dropdown {0}")
    public static void selectOptionByValue(By by, String value) {
        waitForOptionsToLoad(by);
        SelenideElement dropdown = $(by);
        dropdown.selectOptionByValue(value);
    }

    @Step("Select the option with text \"{1}\" in the dropdown {0}")
    public static void selectOptionByText(By by, String text) {
        SelenideElement dropdown = $(by);
        dropdown.selectOption(text);
    }

    @Step("Assert that the input field {0} has error text \"{1}\"")
    public static void assertInputHasErrorText(String inputId, String expectedErrorText) {
        String errorId = inputId + "Error";
        String actualErrorText = $(By.id(errorId)).getText();
        assert actualErrorText.equals(expectedErrorText) : "Expected error text: " + expectedErrorText + ", but was: " + actualErrorText;
        $(By.id(errorId)).shouldHave(Condition.text(expectedErrorText));
    }

    @Step("Assert that the element {0} has text \"{1}\"")
    public static void assertElementHasText(String elementId, String expectedText) {
        String actualText = $(By.id(elementId)).getText();
        assert actualText.equals(expectedText) : "Expected text: " + expectedText + ", but was: " + actualText;
        $(By.id(elementId)).shouldHave(Condition.text(expectedText));
    }

    @Step("Confirm the alert has content \"{0}\"")
    public static void confirmAlertHasContent(String expectedContent) {
        String actualContent = Selenide.confirm(expectedContent);
        assert Objects.equals(actualContent, expectedContent) : "Expected alert content: " + expectedContent + ", but was: " + actualContent;
    }

    @Step("Assert that the text \"{0}\" is displayed")
    public static void assertTextIsDisplayed(String expectedText) {
        assert $x("//*[contains(text(),'" + expectedText + "')]").isDisplayed() : "The text: " + expectedText + " is not displayed on the page.";
    }


    @Step("Ensure the button {0} is enabled")
    public static void ensureButtonIsEnabled(By locator) {
        SelenideElement button = $(locator);
        assert button.isEnabled() : "Button is not enabled";
    }

    @Step("Ensure the button {0} is disabled")
    public static void ensureButtonIsDisabled(By locator) {
        SelenideElement button = $(locator);
        assert !button.isEnabled() : "Button is not disabled";
    }

    @Step("Ensure the button {0} is disabled by class")
    public static void ensureButtonIsDisabledByClass(By locator) {
        SelenideElement button = $(locator);
        assert button.getAttribute("class").contains("--disabled") : "Button is not disabled";
    }

    @Step("Ensure the button {0} is not disabled by class")
    public static void ensureButtonIsNotDisabledByClass(By locator) {
        SelenideElement button = $(locator);
        assert !button.getAttribute("class").contains("--disabled") : "Button is disabled";
    }

    @Step("Select an option with label \"{1}\"")
    public static void selectOptionInPopoverByLabel(String label) {
        SelenideElement popover = $(".Polaris-Popover");
        SelenideElement option = popover.$$x(".//li//*[text()='" + label + "']").first();
        option.click();
    }
}