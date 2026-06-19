package pit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seventh.practice.PatientRiskEvaluator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatientRiskEvaluatorTest {


    @Test
    void shouldEvaluateRisk_Weak() {
        PatientRiskEvaluator evaluator = new PatientRiskEvaluator();

        assertEquals("HIGH_RISK", evaluator.evaluateRisk(80));
        assertEquals("STANDARD_RISK", evaluator.evaluateRisk(30));
    }

    //@Disabled("wanna see what happen")
    @Test
    void shouldEvaluateRisk_Fixed() {
        PatientRiskEvaluator evaluator = new PatientRiskEvaluator();

        assertEquals("HIGH_RISK", evaluator.evaluateRisk(65));

        assertEquals("STANDARD_RISK", evaluator.evaluateRisk(64));

        assertEquals("HIGH_RISK", evaluator.evaluateRisk(80));
        assertEquals("STANDARD_RISK", evaluator.evaluateRisk(30));
    }
}