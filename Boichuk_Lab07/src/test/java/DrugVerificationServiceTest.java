import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import seventh.practice.Drug;
import seventh.practice.DrugRepository;
import seventh.practice.DrugVerificationService;
import seventh.practice.NotificationService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class DrugVerificationServiceTest {

    @Mock
    private DrugRepository drugRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private DrugVerificationService drugVerificationService;

    @Test
    void shouldReturnEvidenceBasedStatus_WhenDrugIsProven() {
        String drugName = "Ібупрофен";
        Drug provenDrug = new Drug(drugName, true);
        when(drugRepository.findByName(drugName)).thenReturn(Optional.of(provenDrug));

        String result = drugVerificationService.checkEvidenceBase(drugName);

        assertEquals("EVIDENCE_BASED", result);
    }

    @Test
    void shouldReturnUnprovenStatus_WhenDrugIsHomeopathy() {
        String drugName = "Осцилококцінум";
        Drug unprovenDrug = new Drug(drugName, false);
        when(drugRepository.findByName(drugName)).thenReturn(Optional.of(unprovenDrug));

        String result = drugVerificationService.checkEvidenceBase(drugName);

        assertEquals("UNPROVEN", result);
    }

    @Test
    void shouldReturnSavedDrug_WhenRegisteringNewDrug() {
        Drug newDrug = new Drug("НовийПрепарат", true);
        when(drugRepository.save(newDrug)).thenReturn(newDrug);

        Drug savedDrug = drugVerificationService.registerNewDrug(newDrug);

        assertEquals("НовийПрепарат", savedDrug.getName());
        assertTrue(savedDrug.isEvidenceBased());
    }

    @Test
    void shouldSendAlert_WhenDrugIsUnknown() {
        String unknownDrug = "ЯкийсьПрепарат";
        when(drugRepository.findByName(unknownDrug)).thenReturn(Optional.empty());

        drugVerificationService.reportSuspiciousDrug(unknownDrug);

        verify(notificationService).sendAlert("Увага: Запит на невідомий препарат - " + unknownDrug);
    }

    @Test
    void shouldSendAlertMultipleTimes_WhenMassAlertRequested() {
        List<String> warnings = List.of("Помилка 1", "Помилка 2", "Помилка 3");

        drugVerificationService.sendMassAlert(warnings);

        verify(notificationService, times(3)).sendAlert(anyString());
    }

    @Test
    void shouldNotSendAlert_WhenDrugIsEvidenceBased() {
        String provenDrugName = "Парацетамол";
        Drug provenDrug = new Drug(provenDrugName, true);
        when(drugRepository.findByName(provenDrugName)).thenReturn(Optional.of(provenDrug));

        drugVerificationService.reportSuspiciousDrug(provenDrugName);

        verify(notificationService, never()).sendAlert(anyString());
    }
}