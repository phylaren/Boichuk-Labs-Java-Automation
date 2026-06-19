import org.junit.jupiter.api.Test;
import seventh.practice.DrugProfile;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class DrugListTest {

    @Test
    void shouldVerifyListOfEvidenceBasedAlternatives() {
        List<DrugProfile> alternatives = List.of(
                new DrugProfile("Ібупрофен", true, "Ібупрофен", "НПЗЗ"),
                new DrugProfile("Парацетамол", true, "Парацетамол", "Анальгетик"),
                new DrugProfile("Напроксен", true, "Напроксен", "НПЗЗ")
        );

        assertThat(alternatives)
                .isNotEmpty()
                .hasSize(3)
                .doesNotContainNull();

        assertThat(alternatives)
                .extracting(DrugProfile::getName)
                .contains("Ібупрофен", "Парацетамол")
                .doesNotContain("Осцилококцінум");

        assertThat(alternatives)
                .extracting(DrugProfile::getName, DrugProfile::getCategory)
                .contains(
                        org.assertj.core.groups.Tuple.tuple("Ібупрофен", "НПЗЗ")
                );

        assertThat(alternatives)
                .allMatch(DrugProfile::isEvidenceBased, "Всі аналоги мають бути доказовими")
                .anyMatch(drug -> drug.getCategory().equals("Анальгетик"))
                .noneMatch(drug -> drug.getName().isBlank());

        List<String> categories = List.of("НПЗЗ", "Анальгетик", "НПЗЗ");

        assertThat(categories)
                .containsExactly("НПЗЗ", "Анальгетик", "НПЗЗ");

        assertThat(categories)
                .containsExactlyInAnyOrder("Анальгетик", "НПЗЗ", "НПЗЗ");
    }
}