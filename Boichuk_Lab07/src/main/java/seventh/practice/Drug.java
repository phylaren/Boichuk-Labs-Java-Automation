package seventh.practice;

public class Drug {
    private String name;
    private boolean isEvidenceBased;

    public Drug(String name, boolean isEvidenceBased) {
        this.name = name;
        this.isEvidenceBased = isEvidenceBased;
    }

    public String getName() { return name; }
    public boolean isEvidenceBased() { return isEvidenceBased; }
}
