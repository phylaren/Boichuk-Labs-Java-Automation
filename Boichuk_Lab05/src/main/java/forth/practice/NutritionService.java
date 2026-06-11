package forth.practice;

import java.time.LocalDate;

public class NutritionService {
    public int calculateCalories(int p, int c, int f) {
        return p * 4 + c * 4 + f * 9;
    }

    public boolean isKetoFriendly(int carbs) {
        return carbs <= 50;
    }

    public boolean isExpired(LocalDate exp, LocalDate curr) {
        return curr.isAfter(exp);
    }

    public boolean validateBarcode(String barcode) {
        return barcode != null && !barcode.isBlank();
    }
}
