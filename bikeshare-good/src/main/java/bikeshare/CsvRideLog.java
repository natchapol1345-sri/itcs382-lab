package bikeshare;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;

public class CsvRideLog implements RideLog {
    private final String fileName;

    public CsvRideLog(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void append(Ride ride) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(ride.memberId() + "," + ride.bikeId() + "," + ride.returnStation().getLabel()
                    + "," + ride.minutes() + "," + ride.fee() + "\n");
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot save the ride to " + fileName, e);
        }
    }
}
