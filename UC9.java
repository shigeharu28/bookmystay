import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class UC9 {

    public static void main(String[] args) {

        System.out.println("Booking Validation");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            validator.validate(guestName, roomType, inventory);

            bookingQueue.addRequest(guestName, roomType);
            inventory.bookRoom(roomType);

            System.out.println("Booking request accepted successfully.");

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class ReservationValidator {

    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!roomType.equals("Single") && !roomType.equals("Double") && !roomType.equals("Suite")) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (!inventory.isAvailable(roomType)) {
            throw new InvalidBookingException("Selected room type is not available.");
        }
    }
}

class RoomInventory {

    private int singleRooms;
    private int doubleRooms;
    private int suiteRooms;

    public RoomInventory() {
        singleRooms = 2;
        doubleRooms = 2;
        suiteRooms = 1;
    }

    public boolean isAvailable(String roomType) {
        if (roomType.equals("Single")) return singleRooms > 0;
        if (roomType.equals("Double")) return doubleRooms > 0;
        if (roomType.equals("Suite")) return suiteRooms > 0;
        return false;
    }

    public void bookRoom(String roomType) throws InvalidBookingException {
        if (roomType.equals("Single")) {
            if (singleRooms <= 0) throw new InvalidBookingException("No Single rooms available.");
            singleRooms--;
        } else if (roomType.equals("Double")) {
            if (doubleRooms <= 0) throw new InvalidBookingException("No Double rooms available.");
            doubleRooms--;
        } else if (roomType.equals("Suite")) {
            if (suiteRooms <= 0) throw new InvalidBookingException("No Suite rooms available.");
            suiteRooms--;
        } else {
            throw new InvalidBookingException("Invalid room type selected.");
        }
    }
}

class BookingRequestQueue {

    private Queue<String> requests;

    public BookingRequestQueue() {
        requests = new LinkedList<>();
    }

    public void addRequest(String guestName, String roomType) {
        requests.offer("Guest: " + guestName + ", Room Type: " + roomType);
    }
    public boolean isAvailable(String roomType) {
        if (roomType.equals("Single")) return singleRooms > 0;
        if (roomType.equals("Double")) return doubleRooms > 0;
        if (roomType.equals("Suite")) return suiteRooms > 0;
        return false;
    }

    public void bookRoom(String roomType) throws InvalidBookingException {
        if (roomType.equals("Single")) {
            if (singleRooms <= 0) throw new InvalidBookingException("No Single rooms available.");
            singleRooms--;
        } else if (roomType.equals("Double")) {
            if (doubleRooms <= 0) throw new InvalidBookingException("No Double rooms available.");
            doubleRooms--;
        } else if (roomType.equals("Suite")) {
            if (suiteRooms <= 0) throw new InvalidBookingException("No Suite rooms available.");
            suiteRooms--;
        } else {
            throw new InvalidBookingException("Invalid room type selected.");
        }
    }
}