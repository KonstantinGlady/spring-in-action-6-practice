package tacos;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomePageBrowserTest {

    @LocalServerPort
    private int port;
    private static HtmlUnitDriver browser;

    @BeforeAll
    public static void setup() {
        browser = new HtmlUnitDriver();
        browser.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void tearDown() {
        browser.quit();
    }

    @Test
    void testHomePage() {

        String homePage = "http://localhost:" + port;
        browser.get(homePage);

        String title = browser.getTitle();
        assertEquals("Taco Cloud", title);

        String h1Text = browser.findElementByTagName("h1").getText();
        assertEquals("Welcome to...", h1Text);

        String imgSrc = browser.findElementByTagName("img")
                .getAttribute("src");
        assertEquals(homePage + "/images/TacoCloud.png", imgSrc);
    }

}
