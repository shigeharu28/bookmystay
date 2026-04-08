import java.util.*;

public class UC10 {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation and Inventory Rollback");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancellationService = new CancellationService();

        String roomId = inventory.allocateRoom("Single");
        Reservation reservation = new Reservation("RES101", "Abhi", "Single", roomId);
        history.addReservation(reservation);

        System.out.println("Confirmed Reservation ID: " + reservation.getReservationId());
        System.out.println("Allocated Room ID: " + reservation.getRoomId());

        System.out.print("Enter reservation ID to cancel: ");
        String id = scanner.nextLine();

        try {
            cancellationService.cancelReservation(id, history, inventory);
            System.out.println("Cancellation successful for reservation ID: " + id);
            System.out.println("Released Room ID: " + cancellationService.getLastReleasedRoomId());
            System.out.println("Available " + reservation.getRoomType() + " Rooms: " +
                    inventory.getAvailableCount(reservation.getRoomType()));
        } catch (InvalidCancellationException e) {
            System.out.println("Cancellation failed: " + e.getMessage());
        }

        scanner.close();
    }

    static class Reservation {
        private String reservationId;
        private String guestName;
        private String roomType;
        private String roomId;
        private boolean cancelled;

        public Reservation(String reservationId, String guestName, String roomType, String roomId) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
            this.roomId = roomId;
            this.cancelled = false;
        }

        public String getReservationId() { return reservationId; }
        public String getRoomType() { return roomType; }
        public String getRoomId() { return roomId; }
        public boolean isCancelled() { return cancelled; }
        public void cancel() { cancelled = true; }
    }

    static class RoomInventory {
        private Map<String, Stack<String>> rooms = new HashMap<>();

        public RoomInventory() {
            rooms.put("Single", new Stack<>());
            rooms.put("Double", new Stack<>());
            rooms.put("Suite", new Stack<>());

            rooms.get("Single").push("Single-2");
            rooms.get("Single").push("Single-1");
            rooms.get("Double").push("Double-2");
            rooms.get("Double").push("Double-1");
            rooms.get("Suite").push("Suite-1");
        }

        public String allocateRoom(String type) {
            if (!rooms.containsKey(type) || rooms.get(type).isEmpty()) return null;
            return rooms.get(type).pop();
        }

        public void releaseRoom(String type, String roomId) {
            if (rooms.containsKey(type)) {
                rooms.get(type).push(roomId);
            }
        }

        public int getAvailableCount(String type) {
            if (!rooms.containsKey(type)) return 0;
            return rooms.get(type).size();
        }
    }

    static class BookingHistory {
        private List<Reservation> list = new ArrayList<>();

        public void addReservation(Reservation r) {
            list.add(r);
        }

        public Reservation find(String id) {
            for (Reservation r : list) {
                if (r.getReservationId().equals(id)) return r;
            }
            return null;
        }
    }

    static class CancellationService {
        private Stack<String> rollback = new Stack<>();

        public void cancelReservation(String id, BookingHistory history, RoomInventory inventory)
                throws InvalidCancellationException {

            Reservation r = history.find(id);

            if (r == null) throw new InvalidCancellationException("Reservation does not exist.");
            if (r.isCancelled()) throw new InvalidCancellationException("Already cancelled.");

            rollback.push(r.getRoomId());
            inventory.releaseRoom(r.getRoomType(), r.getRoomId());
            r.cancel();
        }

        public String getLastReleasedRoomId() {
            return rollback.isEmpty() ? null : rollback.peek();
        }
    }

    static class InvalidCancellationException extends Exception {
        public InvalidCancellationException(String msg) {
            super(msg);
        }
    }
}