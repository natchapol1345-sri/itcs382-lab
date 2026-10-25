package bikeshare;

public record Ride(String memberId, String bikeId, Station returnStation, int minutes, int fee) {
}
