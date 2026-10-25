package bikeshare;

public class FeeCalculator {
    public int calculate(MemberType type, int minutes) {
        return type.getBahtPerMinute() * minutes;
    }
}
