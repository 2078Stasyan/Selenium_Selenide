import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void formSubmission() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Рычков Никита");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79098565222");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        String actualText = driver.findElement(By.cssSelector("[data-test-id= 'order-success']")).getText().trim();
        assertEquals(expectedText, actualText);
    }

    @Test
    void sendingDoubleName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Рычков Ахмед-шах");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79098565222");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        String actualText = driver.findElement(By.cssSelector("[data-test-id= 'order-success']")).getText().trim();
        assertEquals(expectedText, actualText);
    }

    @Test
    void sendingDoubleLastName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Утробина-Раевская Анна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79098565222");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        String actualText = driver.findElement(By.cssSelector("[data-test-id= 'order-success']")).getText().trim();
        assertEquals(expectedText, actualText);
    }

    @Test
    void sendingLatinName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Rychkov Nikita");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79098565222");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expectedText, actualText);
    }

    @Test
    void sendEmptyFieldName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79098565222");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expectedText = "Поле обязательно для заполнения";

        String actualText = driver.findElement(By.cssSelector("[data-test-id= name].input_invalid .input__sub")).getText().trim();
        assertEquals(expectedText, actualText);
    }

    @Test
    void sendEmptyFieldPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Рычков Никита");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expectedText = "Поле обязательно для заполнения";

        String actualText = driver.findElement(By.cssSelector("[data-test-id= phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expectedText, actualText);
    }

    @Test
    void submittingFormWithTheWrongPhoneNumber() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Рычков Никита");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79098565");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        String actualText = driver.findElement(By.cssSelector("[data-test-id= phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expectedText, actualText);
    }

    @Test
    void submittingFormWithoutConsent() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Рычков Никита");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79098565222");
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expectedText = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";

        String actualText = driver.findElement(By.cssSelector("[data-test-id= agreement].input_invalid.checkbox")).getText().trim();
        assertEquals(expectedText, actualText);
    }
}
