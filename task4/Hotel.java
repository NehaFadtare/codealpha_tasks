import java.io.*;
import java.util.*;

public class Hotel {
    private List<Room> rooms;
    private Map<String, Booking> bookings;  // Map bookingId to Booking
    
    private final String roomsFile = "rooms.dat";
    private final String bookingsFile = "bookings.dat";

    public Hotel() {
        rooms = new ArrayList<>();
        bookings = new HashMap<>();
        loadRooms();
        loadBookings();
    }

    // Load rooms from file or initialize if file doesn't exist
    private void loadRooms() {
        File file = new File(roomsFile);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                rooms = (List<Room>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Error loading rooms data, initializing default rooms.");
                initializeRooms();
                saveRooms();
            }
        } else {
            initializeRooms();
            saveRooms();
        }
    }

    // Load bookings from file
    private void loadBookings() {
        File file = new File(bookingsFile);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                bookings = (Map<String, Booking>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Error loading bookings data.");
                bookings = new HashMap<>();
            }
        } else {
            bookings = new HashMap<>();
        }
    }

    // Save rooms data
    private void saveRooms() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(roomsFile))) {
            oos.writeObject(rooms);
        } catch (IOException e) {
            System.out.println("Failed to save rooms data.");
        }
    }

    // Save bookings data
    private void saveBookings() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(bookingsFile))) {
            oos.writeObject(bookings);
        } catch (IOException e) {
            System.out.println("Failed to save bookings data.");
        }
    }

    // Initialize rooms with some default data
    private void initializeRooms() {
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));
    }

    // Search available rooms by category
    public List<Room> searchRooms(String category) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room r : rooms) {
            if (r.isAvailable() && r.getCategory().equalsIgnoreCase(category)) {
                availableRooms.add(r);
            }
        }
        return availableRooms;
    }

    // Book a room
    public Booking bookRoom(int roomNumber, String guestName) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                room.setAvailable(false);
                Booking booking = new Booking(roomNumber, guestName, room.getCategory());
                bookings.put(booking.getBookingId(), booking);
                saveRooms();
                saveBookings();
                return booking;
            }
        }
        return null;
    }

    // Cancel a booking by booking ID
    public boolean cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null) {
            // Set room available again
            for (Room room : rooms) {
                if (room.getRoomNumber() == booking.getRoomNumber()) {
                    room.setAvailable(true);
                    break;
                }
            }
            bookings.remove(bookingId);
            saveRooms();
            saveBookings();
            return true;
        }
        return false;
    }

    // Simulate payment for a booking
    public boolean makePayment(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null && !booking.isPaid()) {
            booking.setPaid(true);
            saveBookings();
            return true;
        }
        return false;
    }

    // Get booking details
    public Booking getBooking(String bookingId) {
        return bookings.get(bookingId);
    }

    // List all bookings for a guest
    public List<Booking> getBookingsByGuest(String guestName) {
        List<Booking> guestBookings = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getGuestName().equalsIgnoreCase(guestName)) {
                guestBookings.add(booking);
            }
        }
        return guestBookings;
    }

    // List all categories
    public Set<String> getRoomCategories() {
        Set<String> categories = new HashSet<>();
        for (Room room : rooms) {
            categories.add(room.getCategory());
        }
        return categories;
    }
}
