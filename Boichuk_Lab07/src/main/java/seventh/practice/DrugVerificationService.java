package seventh.practice;

import java.util.List;
import java.util.Optional;

public class DrugVerificationService {
    private final DrugRepository drugRepository;
    private final NotificationService notificationService;

    public DrugVerificationService(DrugRepository drugRepository, NotificationService notificationService) {
        this.drugRepository = drugRepository;
        this.notificationService = notificationService;
    }

    public String checkEvidenceBase(String name) {
        Optional<Drug> drugOpt = drugRepository.findByName(name);
        if (drugOpt.isEmpty()) {
            return "UNKNOWN";
        }
        return drugOpt.get().isEvidenceBased() ? "EVIDENCE_BASED" : "UNPROVEN";
    }

    public Drug registerNewDrug(Drug drug) {
        return drugRepository.save(drug);
    }

    public void reportSuspiciousDrug(String drugName) {
        Optional<Drug> drugOpt = drugRepository.findByName(drugName);

        if (drugOpt.isEmpty()) {
            notificationService.sendAlert("Увага: Запит на невідомий препарат - " + drugName);
        } else if (!drugOpt.get().isEvidenceBased()) {
            notificationService.sendAlert("Попередження: Виявлено препарат без доказової бази - " + drugName);
        }
    }

    public void sendMassAlert(List<String> warnings) {
        for (String warning : warnings) {
            notificationService.sendAlert(warning);
        }
    }
}