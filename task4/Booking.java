import java.io.Serializable;
import java.util.UUID;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bookingId;
    private int roomNumber;
    private String guestName;
    private String category;
    private boolean isPaid;

    public Booking(int roomNumber, String guestName, String category) {
        this.bookingId = UUID.randomUUID().toString();
        this.roomNumber = roomNumber;
        this.guestName = guestName;
        this.category = category;
        this.isPaid = false;
    }

    public String getBookingId() { return bookingId; }
    public int getRoomNumber() { return roomNumber; }
    public String getGuestName() { return guestName; }
    public String getCategory() { return category; }
    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { this.isPaid = paid; }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId + "\nGuest: " + guestName + "\nRoom: " + roomNumber + " (" + category + ")\nPayment: " + (isPaid ? "Completed" : "Pending");
    }
}
