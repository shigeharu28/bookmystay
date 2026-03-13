import java.util.HashMap;
import java.util.Map;

/**
 * Room Search & Availability Check
 * Enables guests to view available rooms and details.
 * @author Lakshmi M
 * @version 1.0
 */
abstract class Room {
    protected String type;
    protected int beds;
    protected double size;
    protected double price;

    public Room(String type, int beds, double size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getType() { return type; }
    public int getBeds() { return beds; }
    public double getSize() { return size; }
    public double getPrice() { return price; }

    public void printDetails() {
        System.out.println(type + " Room: " + beds + " beds, " + size + " sqm, $" + price + " per night");
    }
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single", 1, 20.0, 50.0); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double", 2, 30.0, 80.0); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite", 3, 50.0, 150.0); }
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

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

class SearchService {
    private RoomInventory inventory;
    private Map<String, Room> roomTypes;

    public SearchService(RoomInventory inventory, Map<String, Room> roomTypes) {
        this.inventory = inventory;
        this.roomTypes = roomTypes;
    }

    public void showAvailableRooms() {
        System.out.println("Available Rooms:");
        for (String type : roomTypes.keySet()) {
            int available = inventory.getAvailability(type);
            if (available > 0) {
                roomTypes.get(type).printDetails();
                System.out.println("Available: " + available);
            }
        }
    }
}

public class UC4 {
    /**
     * Entry point for room search demonstration.
     */
    public static void main(String[] args) {
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        Map<String, Room> roomTypes = new HashMap<>();
        roomTypes.put("Single", single);
        roomTypes.put("Double", doubleRoom);
        roomTypes.put("Suite", suite);

        RoomInventory inventory = new RoomInventory();
        inventory.registerRoomType("Single", 5);
        inventory.registerRoomType("Double", 3);
        inventory.registerRoomType("Suite", 0); // Suite unavailable

        SearchService search = new SearchService(inventory, roomTypes);
        search.showAvailableRooms();
    }
}
