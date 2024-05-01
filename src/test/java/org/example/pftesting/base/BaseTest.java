package org.example.pftesting.base;

import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.testng.ScreenShooter;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Listeners({ ScreenShooter.class})
public class BaseTest {
    @BeforeAll
    static void setupAllureReports() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .includeSelenideSteps(true)
                .screenshots(true)
                .savePageSource(true)
        );

    }

    @AfterMethod
    public void attachScreenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            attachScreenshotPNG();
        }
    }

//    This method is used to take a screenshot of the entire current screen and attach it to the Allure report
//    @Attachment(value = "screenshot", type = "image/png", fileExtension = ".png")
//    public byte[] attachScreenshotPNG() {
//        try {
//            Robot robot = new Robot();
//            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
//            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(screenFullImage, "png", baos);
//            return baos.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new byte[0];
//        }
//    }

    @Attachment(value = "screenshot", type = "image/png", fileExtension = ".png")
    public byte[] attachScreenshotPNG() {
        try {
            Path screenshot = Paths.get(Screenshots.takeScreenShotAsFile().getAbsolutePath());
            return Files.readAllBytes(screenshot);
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
