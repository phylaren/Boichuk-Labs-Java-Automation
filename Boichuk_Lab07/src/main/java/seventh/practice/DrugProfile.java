package seventh.practice;

public class DrugProfile {
    private String name;
    private boolean isEvidenceBased;
    private String activeSubstance;
    private String category;

    public DrugProfile(String name, boolean isEvidenceBased, String activeSubstance, String category) {
        this.name = name;
        this.isEvidenceBased = isEvidenceBased;
        this.activeSubstance = activeSubstance;
        this.category = category;
    }

    public String getName() { return name; }
    public boolean isEvidenceBased() { return isEvidenceBased; }
    public String getActiveSubstance() { return activeSubstance; }
    public String getCategory() { return category; }
}