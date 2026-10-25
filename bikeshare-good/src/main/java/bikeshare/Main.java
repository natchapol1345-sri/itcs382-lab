package bikeshare;

public class Main {
    public static void main(String[] args) {
        new Menu(new BikeService(new FeeCalculator(), new CsvRideLog("rides.csv"))).run();
    }
}
