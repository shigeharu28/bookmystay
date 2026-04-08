import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UC12 {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        String filePath = "inventory_data.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();

        boolean loaded = persistenceService.loadInventory(inventory, filePath);

        if (!loaded) {
            System.out.println("No valid inventory data found. Starting fresh.");
        }

        System.out.println();
        System.out.println("Current Inventory:");
        System.out.println("Single: " + inventory.getAvailableCount("Single"));
        System.out.println("Double: " + inventory.getAvailableCount("Double"));
        System.out.println("Suite: " + inventory.getAvailableCount("Suite"));

        if (persistenceService.saveInventory(inventory, filePath)) {
            System.out.println("Inventory saved successfully.");
        } else {
            System.out.println("Failed to save inventory.");
        }
    }

    static class FilePersistenceService {

        public boolean saveInventory(RoomInventory inventory, String filePath) {
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write("Single=" + inventory.getAvailableCount("Single") + "\n");
                writer.write("Double=" + inventory.getAvailableCount("Double") + "\n");
                writer.write("Suite=" + inventory.getAvailableCount("Suite") + "\n");
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        public boolean loadInventory(RoomInventory inventory, String filePath) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                Map<String, Integer> loadedCounts = new HashMap<>();

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    if (parts.length != 2) {
                        return false;
                    }

                    String roomType = parts[0].trim();
                    int count = Integer.parseInt(parts[1].trim());
                    loadedCounts.put(roomType, count);
                }

                if (!loadedCounts.containsKey("Single") ||
                        !loadedCounts.containsKey("Double") ||
                        !loadedCounts.containsKey("Suite")) {
                    return false;
                }

                inventory.setAvailableCount("Single", loadedCounts.get("Single"));
                inventory.setAvailableCount("Double", loadedCounts.get("Double"));
                inventory.setAvailableCount("Suite", loadedCounts.get("Suite"));

                return true;

            } catch (IOException | NumberFormatException e) {
                return false;
            }
        }
    }

    static class RoomInventory {
        private Map<String, Integer> counts;

        public RoomInventory() {
            counts = new HashMap<>();
            counts.put("Single", 5);
            counts.put("Double", 3);
            counts.put("Suite", 2);
        }

        public int getAvailableCount(String roomType) {
            return counts.getOrDefault(roomType, 0);
        }

        public void setAvailableCount(String roomType, int count) {
            counts.put(roomType, count);
        }
    }
}