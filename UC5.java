import java.util.LinkedList;
import java.util.Queue;

/**
 * Booking Request Queue
 * Handles guest booking requests in FIFO order.
 * @author Lakshmi M
 * @version 1.0
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    public void printReservation() {
        System.out.println("Reservation Request: " + guestName + " wants " + roomType + " room.");
    }
}

public class UC5 {
    /**
     * Entry point for booking request demonstration.
     */
    public static void main(String[] args) {
        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Single"));
        bookingQueue.add(new Reservation("Bob", "Double"));
        bookingQueue.add(new Reservation("Charlie", "Suite"));

        System.out.println("Booking Requests (FIFO):");
        for (Reservation r : bookingQueue) {
            r.printReservation();
        }
    }
}
