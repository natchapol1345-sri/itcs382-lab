package bikeshare;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static final int STUDENT = 1;
    public static final int STAFF = 2;
    public static String[] stations = {"Library", "Canteen", "Dorm"};
    public static String[] bikeIds = {"B1", "B2", "B3", "B4", "B5", "B6"};
    public static int[] bikeStation = {0, 0, 1, 1, 2, 2};
    public static String[] bikeRenter = new String[6];
    public static int[] bikeRenterType = new int[6];

    public static void main(String[] args) {
        System.out.println("Commands: rent <memberId> <1=student|2=staff> <0=Library|1=Canteen|2=Dorm>,"
                + " return <memberId> <station 0-2> <minutes>, preview <memberId> <station 0-2> <minutes>, quit");
        try (Scanner in = new Scanner(System.in)) {
            while (in.hasNextLine()) {
                String[] w = in.nextLine().trim().split(" ");
                try {
                    if (w[0].equals("rent")) {
                        rent(w[1], Integer.parseInt(w[2]), Integer.parseInt(w[3]));
                    } else if (w[0].equals("return")) {
                        finish(w[1], Integer.parseInt(w[2]), Integer.parseInt(w[3]), false);
                    } else if (w[0].equals("preview")) {
                        finish(w[1], Integer.parseInt(w[2]), Integer.parseInt(w[3]), true);
                    } else if (w[0].equals("quit")) {
                        break;
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid command");
                }
            }
        }
    }

    static void rent(String member, int type, int station) {
        for (int i = 0; i < bikeIds.length; i++) {
            if (bikeStation[i] == station && bikeRenter[i] == null) {
                bikeRenter[i] = member;
                bikeRenterType[i] = type;
                System.out.println("Rented " + bikeIds[i] + " at " + stations[station]);
                return;
            }
        }
        System.out.println("No bike available at " + stations[station]);
    }

    static void finish(String member, int station, int minutes, boolean dryRun) {
        for (int i = 0; i < bikeIds.length; i++) {
            if (member.equals(bikeRenter[i])) {
                int fee = minutes * (bikeRenterType[i] == STUDENT ? 2 : 3);
                System.out.println("Receipt: bike " + bikeIds[i] + ", " + minutes + " minutes, " + fee + " baht");
                if (!dryRun) {
                    try (FileWriter out = new FileWriter("rides.csv", true)) {
                        out.write(member + "," + bikeIds[i] + "," + stations[station] + "," + minutes + "," + fee + "\n");
                    } catch (IOException e) {
                        System.out.println("Cannot save the ride: " + e.getMessage());
                    }
                    bikeStation[i] = station;
                    bikeRenter[i] = null;
                }
                return;
            }
        }
        System.out.println("No bike rented by " + member);
    }
}
