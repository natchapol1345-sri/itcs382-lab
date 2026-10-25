package parcelpoint;

public class Parcel {
    private String code;
    private String studentId;
    private Size size;
    private String status;

    public Parcel(String code, String studentId, Size size) {
        this.code = code;
        this.studentId = studentId;
        this.size = size;
        this.status = "NEW";
    }

    public String getCode() {
        return code;
    }

    public String getStudentId() {
        return studentId;
    }

    public Size getSize() {
        return size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
