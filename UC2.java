/**
 * Basic Room Types & Static Availability
 * Demonstrates object modeling with inheritance and abstraction.
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

public class UC2 {
    /**
     * Entry point for room type demonstration.
     * Prints room details and static availability.
     */
    public static void main(String[] args) {
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("Room Types and Availability:");
        single.printDetails();
        System.out.println("Available: " + singleAvailable);
        doubleRoom.printDetails();
        System.out.println("Available: " + doubleAvailable);
        suite.printDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}
