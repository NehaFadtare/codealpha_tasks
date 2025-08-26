import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class HotelGUI extends JFrame {
    private Hotel hotel;

    private JComboBox<String> categoryComboBox;
    private JTextArea outputArea;
    private JTextField guestNameField;
    private JTextField roomNumberField;
    private JTextField bookingIdField;

    public HotelGUI() {
        hotel = new Hotel();

        setTitle("Hotel Reservation System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel for controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(10, 2, 5, 5));

        // Components
        controlPanel.add(new JLabel("Guest Name:"));
        guestNameField = new JTextField();
        controlPanel.add(guestNameField);

        controlPanel.add(new JLabel("Room Category:"));
        categoryComboBox = new JComboBox<>(hotel.getRoomCategories().toArray(new String[0]));
        controlPanel.add(categoryComboBox);

        JButton searchButton = new JButton("Search Available Rooms");
        controlPanel.add(searchButton);

        controlPanel.add(new JLabel("Room Number to Book:"));
        roomNumberField = new JTextField();
        controlPanel.add(roomNumberField);

        JButton bookButton = new JButton("Book Room");
        controlPanel.add(bookButton);

        controlPanel.add(new JLabel("Booking ID for Cancel/Pay/View:"));
        bookingIdField = new JTextField();
        controlPanel.add(bookingIdField);

        JButton cancelButton = new JButton("Cancel Booking");
        controlPanel.add(cancelButton);

        JButton payButton = new JButton("Make Payment");
        controlPanel.add(payButton);

        JButton viewButton = new JButton("View Booking Details");
        controlPanel.add(viewButton);

        JButton refreshCategoriesButton = new JButton("Refresh Categories");
        controlPanel.add(refreshCategoriesButton);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Layout
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Action listeners
        searchButton.addActionListener(e -> searchRooms());
        bookButton.addActionListener(e -> bookRoom());
        cancelButton.addActionListener(e -> cancelBooking());
        payButton.addActionListener(e -> makePayment());
        viewButton.addActionListener(e -> viewBooking());
        refreshCategoriesButton.addActionListener(e -> refreshCategories());
    }

    private void searchRooms() {
        String category = (String) categoryComboBox.getSelectedItem();
        if (category == null) return;

        List<Room> rooms = hotel.searchRooms(category);
        outputArea.setText("Available rooms in " + category + ":\n");
        if (rooms.isEmpty()) {
            outputArea.append("No rooms available.\n");
        } else {
            for (Room r : rooms) {
                outputArea.append(r.toString() + "\n");
            }
        }
    }

    private void bookRoom() {
        String guestName = guestNameField.getText().trim();
        if (guestName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name.");
            return;
        }
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(roomNumberField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid room number.");
            return;
        }

        Booking booking = hotel.bookRoom(roomNumber, guestName);
        if (booking != null) {
            outputArea.setText("Booking successful!\nBooking ID: " + booking.getBookingId());
        } else {
            outputArea.setText("Booking failed. Room may not be available.");
        }
    }

    private void cancelBooking() {
        String bookingId = bookingIdField.getText().trim();
        if (bookingId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter booking ID.");
            return;
        }

        boolean result = hotel.cancelBooking(bookingId);
        outputArea.setText(result ? "Booking cancelled successfully." : "Booking ID not found.");
    }

    private void makePayment() {
        String bookingId = bookingIdField.getText().trim();
        if (bookingId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter booking ID.");
            return;
        }

        boolean paid = hotel.makePayment(bookingId);
        outputArea.setText(paid ? "Payment successful." : "Payment failed or already paid.");
    }

    private void viewBooking() {
        String bookingId = bookingIdField.getText().trim();
        if (bookingId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter booking ID.");
            return;
        }

        Booking booking = hotel.getBooking(bookingId);
        outputArea.setText(booking != null ? booking.toString() : "Booking ID not found.");
    }

    private void refreshCategories() {
        categoryComboBox.removeAllItems();
        for (String cat : hotel.getRoomCategories()) {
            categoryComboBox.addItem(cat);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HotelGUI gui = new HotelGUI();
            gui.setVisible(true);
        });
    }
}
