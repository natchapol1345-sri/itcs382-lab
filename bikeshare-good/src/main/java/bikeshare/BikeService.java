package bikeshare;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BikeService {
    private static final int BIKES_PER_STATION = 2;

    private final List<Bike> bikes = new ArrayList<>();
    private final FeeCalculator feeCalculator;
    private final RideLog rideLog;

    public BikeService(FeeCalculator feeCalculator, RideLog rideLog) {
        this.feeCalculator = feeCalculator;
        this.rideLog = rideLog;
        for (Station station : Station.values()) {
            for (int i = 0; i < BIKES_PER_STATION; i++) {
                bikes.add(new Bike("B" + (bikes.size() + 1), station));
            }
        }
    }

    public Optional<Bike> rent(String memberId, MemberType type, Station station) {
        Optional<Bike> bike = bikes.stream().filter(b -> b.isAvailableAt(station)).findFirst();
        bike.ifPresent(b -> b.rent(memberId, type));
        return bike;
    }

    public Optional<Ride> giveBack(String memberId, Station station, int minutes) {
        Optional<Bike> bike = bikes.stream().filter(b -> b.isRentedBy(memberId)).findFirst();
        if (bike.isEmpty()) {
            return Optional.empty();
        }
        Bike found = bike.get();
        int fee = feeCalculator.calculate(found.getRenterType(), minutes);
        Ride ride = new Ride(memberId, found.getId(), station, minutes, fee);
        rideLog.append(ride);
        found.giveBack(station);
        return Optional.of(ride);
    }
}
