package bikeshare;

public enum Station {
    LIBRARY("Library"), CANTEEN("Canteen"), DORM("Dorm");

    private final String label;

    Station(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
