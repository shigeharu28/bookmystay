import java.util.ArrayList;
import java.util.List;


public class UC8 {


    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        BookingReportService reportService = new BookingReportService();

        System.out.println("Booking History and Reporting");
        System.out.println();

        reportService.generateReport(history);
    }
}


class Reservation1 {


    private String guestName;


    private String roomType;


    public Reservation1(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }


    public String getGuestName() {
        return guestName;
    }


    public String getRoomType() {
        return roomType;
    }
}


class BookingHistory {


    private List<Reservation> confirmedReservations1;


    public BookingHistory() {
        confirmedReservations1 = new ArrayList<>();
    }


    public void addReservation(Reservation reservation) {
        confirmedReservations1.add(reservation);
    }


    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations1;
    }
}


class BookingReportService {


    public void generateReport(BookingHistory history) {
        System.out.println("Booking History Report");
        System.out.println();

        for (Reservation reservation : history.getConfirmedReservations()) {
            System.out.println("Guest: " + reservation.getGuestName() + ", Room Type: " + reservation.getRoomType());
        }
    }
}