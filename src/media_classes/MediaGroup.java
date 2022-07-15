package media_classes;

public class MediaGroup {
    String name;
    MediaType type;
    Affiliation affiliation;

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    @Override
    public String toString() {
        return name + " " + type + " " + affiliation;
    }
}

