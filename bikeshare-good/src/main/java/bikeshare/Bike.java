package bikeshare;

public class Bike {
    private final String id;
    private Station station;
    private String renterId;
    private MemberType renterType;

    public Bike(String id, Station station) {
        this.id = id;
        this.station = station;
    }

    public String getId() {
        return id;
    }

    public MemberType getRenterType() {
        return renterType;
    }

    public boolean isAvailableAt(Station where) {
        return renterId == null && station == where;
    }

    public boolean isRentedBy(String memberId) {
        return memberId.equals(renterId);
    }

    public void rent(String memberId, MemberType type) {
        this.renterId = memberId;
        this.renterType = type;
    }

    public void giveBack(Station where) {
        this.station = where;
        this.renterId = null;
        this.renterType = null;
    }
}
