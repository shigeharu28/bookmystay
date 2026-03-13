import java.util.*;

/**
 * Reservation Confirmation & Room Allocation
 * Ensures safe allocation and inventory consistency.
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
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void registerRoomType(String type, int available) {
        inventory.put(type, available);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrementAvailability(String type) {
        inventory.put(type, getAvailability(type) - 1);
    }
}

class BookingService {
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms;
    private Set<String> allRoomIds;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
        this.allRoomIds = new HashSet<>();
    }

    public String allocateRoom(Reservation reservation) {
        String type = reservation.getRoomType();
        if (inventory.getAvailability(type) > 0) {
            String roomId = generateUniqueRoomId(type);
            inventory.decrementAvailability(type);
            allocatedRooms.computeIfAbsent(type, k -> new HashSet<>()).add(roomId);
            allRoomIds.add(roomId);
            return roomId;
        } else {
            return null;
        }
    }

    private String generateUniqueRoomId(String type) {
        String roomId;
        do {
            roomId = type.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 6);
        } while (allRoomIds.contains(roomId));
        return roomId;
    }

    public void printAllocatedRooms() {
        System.out.println("Allocated Rooms:");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + ": " + allocatedRooms.get(type));
        }
    }
}

public class UC6 {
    /**
     * Entry point for reservation confirmation demonstration.
     */
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoomType("Single", 2);
        inventory.registerRoomType("Double", 1);
        inventory.registerRoomType("Suite", 1);

        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Single"));
        bookingQueue.add(new Reservation("Bob", "Double"));
        bookingQueue.add(new Reservation("Charlie", "Suite"));
        bookingQueue.add(new Reservation("Diana", "Single"));

        BookingService bookingService = new BookingService(inventory);

        System.out.println("Processing Booking Requests:");
        while (!bookingQueue.isEmpty()) {
            Reservation r = bookingQueue.poll();
            String roomId = bookingService.allocateRoom(r);
            if (roomId != null) {
                System.out.println("Reservation confirmed for " + r.getGuestName() + ": Room ID " + roomId);
            } else {
                System.out.println("No available rooms for " + r.getGuestName() + " (" + r.getRoomType() + ")");
            }
        }
        bookingService.printAllocatedRooms();
    }
}
