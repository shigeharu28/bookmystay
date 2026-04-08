import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UC7 {


    public static void main(String[] args) {

        // Create manager object to handle reservation-service mapping
        AddOnServiceManager manager = new AddOnServiceManager();

        // Example confirmed reservation ID
        String reservationId = "Single-1";

        // Create optional add-on services
        AddOnService breakfast = new AddOnService("Breakfast", 500.0);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 1000.0);

        // Attach services to reservation
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, airportPickup);

        // Calculate total cost of all selected services
        double totalAddOnCost = manager.calculateTotalServiceCost(reservationId);

        // Display output
        System.out.println("Add-On Service Selection");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalAddOnCost);
    }
}


class AddOnService {
    private String serviceName;
    private double cost;
    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }
    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}


class AddOnServiceManager {

    private Map<String, List<AddOnService>> servicesByReservation;
    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }
    public void addService(String reservationId, AddOnService service) {
        servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());
        servicesByReservation.get(reservationId).add(service);
    }
    public double calculateTotalServiceCost(String reservationId) {
        double total = 0.0;
        List<AddOnService> selectedServices = servicesByReservation.get(reservationId);

        if (selectedServices != null) {
            for (AddOnService service : selectedServices) {
                total += service.getCost();
            }
        }

        return total;
    }
}