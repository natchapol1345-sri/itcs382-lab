package parcelpoint;

public enum Size {
    SMALL(10.0), MEDIUM(15.0), LARGE(20.0);

    private final double dailyFee;

    Size(double dailyFee) {
        this.dailyFee = dailyFee;
    }

    public double getDailyFee() {
        return dailyFee;
    }

    public boolean canHold(Size other) {
        return compareTo(other) >= 0;
    }

    public static Size fromCode(int code) {
        return switch (code) {
            case 1 -> SMALL;
            case 2 -> MEDIUM;
            case 3 -> LARGE;
            default -> throw new IllegalArgumentException("Size must be 1, 2 or 3: " + code);
        };
    }
}
