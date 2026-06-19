import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import seventh.practice.DrugProfile;

class DrugProfileTest {

    @Test
    void shouldVerifyAllFieldsOfDrugProfile() {
        DrugProfile profile = new DrugProfile(
                "Афлубін",
                false,
                "Екстракти рослин",
                "Гомеопатія"
        );

        SoftAssertions softly = new SoftAssertions();


        softly.assertThat(profile.getName())
                .as("Перевірка назви препарату")
                .isEqualTo("Афлубін");

        softly.assertThat(profile.isEvidenceBased())
                .as("Препарат не повинен мати доказової бази")
                .isFalse();

        softly.assertThat(profile.getActiveSubstance())
                .as("Перевірка діючої речовини")
                .isNotNull()
                .isNotEmpty()
                .isEqualTo("Екстракти рослин");

        softly.assertThat(profile.getCategory())
                .as("Перевірка категорії")
                .startsWith("Гомео")
                .isEqualTo("Гомеопатія");

        softly.assertAll();
    }
}