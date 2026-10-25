package bikeshare;

import java.io.UncheckedIOException;
import java.util.Locale;
import java.util.Scanner;

public class Menu {
    private final BikeService service;

    public Menu(BikeService service) {
        this.service = service;
    }

    public void run() {
        System.out.println("Commands: rent <memberId> <student|staff> <library|canteen|dorm>,"
                + " return <memberId> <library|canteen|dorm> <minutes>, quit");
        try (Scanner in = new Scanner(System.in)) {
            while (in.hasNextLine()) {
                String[] words = in.nextLine().trim().split(" ");
                if (words[0].equals("quit")) {
                    break;
                }
                try {
                    handle(words);
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException | UncheckedIOException e) {
                    System.out.println("Cannot run the command: " + e.getMessage());
                }
            }
        }
    }

    private void handle(String[] words) {
        if (words[0].equals("rent")) {
            MemberType type = MemberType.valueOf(words[2].toUpperCase(Locale.ROOT));
            Station station = Station.valueOf(words[3].toUpperCase(Locale.ROOT));
            System.out.println(service.rent(words[1], type, station)
                    .map(bike -> "Rented " + bike.getId() + " at " + station.getLabel())
                    .orElse("No bike available at " + station.getLabel()));
        } else if (words[0].equals("return")) {
            Station station = Station.valueOf(words[2].toUpperCase(Locale.ROOT));
            int minutes = Integer.parseInt(words[3]);
            System.out.println(service.giveBack(words[1], station, minutes)
                    .map(ride -> "Receipt: bike " + ride.bikeId() + ", " + ride.minutes()
                            + " minutes, " + ride.fee() + " baht")
                    .orElse("No bike rented by " + words[1]));
        }
    }
}
