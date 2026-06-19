package seventh.practice;

public class PatientRiskEvaluator {

    public String evaluateRisk(int age) {
        if (age >= 65) {
            return "HIGH_RISK";
        } else {
            return "STANDARD_RISK";
        }
    }
}
