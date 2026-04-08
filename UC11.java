import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;

public class UC11 {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                )
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                )
        );

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        System.out.println();
        System.out.println("Remaining Inventory:");
        System.out.println("Single: " + inventory.getAvailableCount("Single"));
        System.out.println("Double: " + inventory.getAvailableCount("Double"));
        System.out.println("Suite: " + inventory.getAvailableCount("Suite"));
    }

    static class ConcurrentBookingProcessor implements Runnable {

        private BookingRequestQueue bookingQueue;
        private RoomInventory inventory;
        private RoomAllocationService allocationService;

        public ConcurrentBookingProcessor(
                BookingRequestQueue bookingQueue,
                RoomInventory inventory,
                RoomAllocationService allocationService
        ) {
            this.bookingQueue = bookingQueue;
            this.inventory = inventory;
            this.allocationService = allocationService;
        }

        @Override
        public void run() {
            while (true) {
                Reservation reservation;

                synchronized (bookingQueue) {
                    if (bookingQueue.isEmpty()) {
                        break;
                    }
                    reservation = bookingQueue.getNextRequest();
                }

                synchronized (inventory) {
                    allocationService.allocateRoom(reservation, inventory);
                }
            }
        }
    }

    static class Reservation {
        private String guestName;
        private String roomType;
        private String roomId;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }
    }

    static class BookingRequestQueue {
        private Queue<Reservation> requests;

        public BookingRequestQueue() {
            requests = new LinkedList<>();
        }

        public void addRequest(Reservation reservation) {
            requests.offer(reservation);
        }

        public Reservation getNextRequest() {
            return requests.poll();
        }

        public boolean isEmpty() {
            return requests.isEmpty();
        }
    }

    static class RoomInventory {
        private Map<String, Queue<String>> availableRooms;
        private Map<String, Integer> remainingCounts;

        public RoomInventory() {
            availableRooms = new HashMap<>();
            remainingCounts = new HashMap<>();

            Queue<String> singleRooms = new LinkedList<>();
            Queue<String> doubleRooms = new LinkedList<>();
            Queue<String> suiteRooms = new LinkedList<>();

            singleRooms.offer("Single-1");
            singleRooms.offer("Single-2");
            singleRooms.offer("Single-3");
            singleRooms.offer("Single-4");
            singleRooms.offer("Single-5");

            doubleRooms.offer("Double-1");
            doubleRooms.offer("Double-2");
            doubleRooms.offer("Double-3");

            suiteRooms.offer("Suite-1");
            suiteRooms.offer("Suite-2");

            availableRooms.put("Single", singleRooms);
            availableRooms.put("Double", doubleRooms);
            availableRooms.put("Suite", suiteRooms);

            remainingCounts.put("Single", 5);
            remainingCounts.put("Double", 3);
            remainingCounts.put("Suite", 2);
        }

        public String allocateAvailableRoom(String roomType) {
            Queue<String> rooms = availableRooms.get(roomType);

            if (rooms == null || rooms.isEmpty()) {
                return null;
            }

            String roomId = rooms.poll();
            remainingCounts.put(roomType, remainingCounts.get(roomType) - 1);
            return roomId;
        }

        public int getAvailableCount(String roomType) {
            return remainingCounts.getOrDefault(roomType, 0);
        }
    }

    static class RoomAllocationService {

        public void allocateRoom(Reservation reservation, RoomInventory inventory) {
            String roomId = inventory.allocateAvailableRoom(reservation.getRoomType());

            if (roomId != null) {
                reservation.setRoomId(roomId);
                System.out.println("Booking confirmed for Guest: " +
                        reservation.getGuestName() +
                        ", Room ID: " + reservation.getRoomId());
            } else {
                System.out.println("Booking failed for Guest: " +
                        reservation.getGuestName() +
                        ", No " + reservation.getRoomType() + " rooms available.");
            }
        }
    }
}