import forth.practice.NutritionService;
import forth.practice.OcrReceiptScanner;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@DisplayName("Тестування бізнес-логіки Freshify")
class FreshifyAnalyticsTest {

    private NutritionService nutritionService;
    private OcrReceiptScanner ocrScanner;

    @BeforeEach
    void setUp() {
        nutritionService = new NutritionService();
        ocrScanner = new OcrReceiptScanner();
        System.out.println("Налаштовано середовище для виконання тестів");
    }

    @Test
    @Tag("unit")
    @DisplayName("Базовий розрахунок загальної калорійності")
    void testBasicCalorieCalculation() {
        int calories = nutritionService.calculateCalories(20, 10, 5);
        assertEquals(165, calories, "Формула для розрахунку калорійності працює некоректно");
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 20, 45})
    @Tag("unit")
    @DisplayName("Валідація продуктів для кето-дієти (лише дозволені значення вуглеводів)")
    void testKetoDietCarbLimits(int carbs) {    //чому тут (!) і для нього є окрема команда?
        assertTrue(nutritionService.isKetoFriendly(carbs),
                "Продукт з " + carbs + "г вуглеводів підходить для кето");
    }

    @ParameterizedTest
    @CsvSource({
            "Яблуко, 2026-06-15, 2026-06-11, false",
            "Молоко, 2026-06-08, 2026-06-11, true",
            "Сир, 2026-06-11, 2026-06-11, false"
    })
    @Tag("unit")
    @DisplayName("Перевірка статусу терміну придатності")
    void testExpirationStatus(String productName, LocalDate expiryDate, LocalDate currentDate, boolean expectedExpired) {
        boolean actual = nutritionService.isExpired(expiryDate, currentDate);
        assertEquals(expectedExpired, actual, () -> productName + " має некоректний статус придатності");
    }

    @Test
    @Tag("integration")
    @DisplayName("Інтеграційне OCR-сканування чека (виконується лише за наявності GPU)")
    void testReceiptOcrScanning() {
        assumeTrue("true".equalsIgnoreCase(System.getenv("HAS_GPU_ACCELERATION")),
                "Тест сканування чеків пропущено: немає необхідного GPU для швидкого OCR");

        assertTrue(ocrScanner.scanBarcodeAndAnalyze("receipt_image.png"));
    }

    @Test
    @Tag("integration")
    @DisplayName("Інтеграційне OCR-сканування чека (виконується лише за наявності коректного GEMINI API_KEY)")
    void testReceiptOcrScanningKey() {
        assumeTrue("1234".equalsIgnoreCase(ocrScanner.getGEMINI_KEY()),
                "Тест сканування чеків пропущено: некоректний GEMINI API_KEY");

        assertTrue(ocrScanner.scanBarcodeAndAnalyze("receipt_image.png"));
    }

    @TestFactory
    @Tag("unit")
    @DisplayName("Динамічна валідація бази продуктів")
    Stream<DynamicTest> dynamicTestsForProductValidation() {
        record ProductData(String id, String barcode, boolean isValid) {}

        var products = Stream.of(
                new ProductData("p1", "4820014164478", true),
                new ProductData("p2", "", false),
                new ProductData("p3", null, false)
        );

        return products.map(prod ->
                dynamicTest("Валідація штрихкоду для продукту ID: " + prod.id(), () -> {
                    boolean result = nutritionService.validateBarcode(prod.barcode());
                    assertEquals(prod.isValid(), result);
                })
        );
    }
}

