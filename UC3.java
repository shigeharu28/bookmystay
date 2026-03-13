import java.util.HashMap;
import java.util.Map;

/**
 * Centralized Room Inventory Management
 * Uses HashMap for scalable availability tracking.
 * @author Lakshmi M
 * @version 1.0
 */
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

    public void updateAvailability(String type, int newCount) {
        inventory.put(type, newCount);
    }

    public void printInventory() {
        System.out.println("Current Room Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type) + " available");
        }
    }
}

public class UC3 {
    /**
     * Entry point for inventory management demonstration.
     */
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoomType("Single", 5);
        inventory.registerRoomType("Double", 3);
        inventory.registerRoomType("Suite", 2);
        inventory.printInventory();
    }
}
