package parcelpoint;

import java.util.ArrayList;
import java.util.List;

public class ParcelService {
    private List<Locker> lockers = new ArrayList<>();
    private List<Parcel> parcels = new ArrayList<>();
    private final ParcelRepository repository;

    public ParcelService(ParcelRepository repository) {
        this.repository = repository;
        lockers.add(new Locker(1, Size.SMALL));
        lockers.add(new Locker(2, Size.MEDIUM));
        lockers.add(new Locker(3, Size.LARGE));
    }

    public List<Locker> getLockers() {
        return lockers;
    }

    public Locker storeParcel(Parcel parcel) {
        for (Locker locker : lockers) {
            if (locker.isFree() && locker.getSize().canHold(parcel.getSize())) {
                locker.setParcel(parcel);
                parcel.setStatus("STORED");
                parcels.add(parcel);
                System.out.println("SMS to " + parcel.getStudentId() + ": parcel "
                        + parcel.getCode() + " is in locker " + locker.getNumber());
                repository.save(parcels);
                return locker;
            }
        }
        return null;
    }

    public double pickUp(String code, int days) {
        for (Locker locker : lockers) {
            Parcel parcel = locker.getParcel();
            if (parcel != null && parcel.getCode().equals(code)) {
                locker.setParcel(null);
                parcel.setStatus("PICKED_UP");
                repository.save(parcels);
                return fee(parcel, days);
            }
        }
        return 0;
    }

    public double fee(Parcel parcel, int days) {
        if (days <= 3) {
            return 0;
        }
        return (days - 3) * parcel.getSize().getDailyFee();
    }

    public void printParcels(boolean forStaff) {
        for (Parcel parcel : parcels) {
            if (forStaff) {
                System.out.println(parcel.getCode() + " " + parcel.getStudentId() + " " + parcel.getStatus());
            } else if (parcel.getStatus().equals("STORED")) {
                System.out.println(parcel.getCode() + " is waiting");
            }
        }
    }
}
