package seventh.practice;

import java.util.Optional;

public interface DrugRepository {
    Optional<Drug> findByName(String name);
    Drug save(Drug drug);
}
