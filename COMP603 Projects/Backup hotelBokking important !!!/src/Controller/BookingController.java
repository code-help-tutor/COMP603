WeChat: cstutorcs
QQ: 749389476
Email: tutorcs@163.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import javax.swing.JOptionPane;
import View.BookingPage;
import Database.DBmanager;
import Model.Booking;
import java.util.List;
import Model.User;


/**
 *
 * @author spark
 */
public class BookingController extends MainController {

    private BookingPage bookingPage;
    private DBmanager dbManager;
    private LoginController loginController;

    public BookingController(LoginController loginController) {
        this.loginController = loginController;
        dbManager = new DBmanager();
        showBookingPage(); // show the booking page when the controller is initialized
    }

    @Override
    public void startApplication() {
        showBookingPage();
    }

    // Method to handle the action when the "Book" button is clicked
    public void bookButtonClicked(Booking booking) {
        if (dbManager.saveBooking(booking,getCurrentUsername())) {
            JOptionPane.showMessageDialog(bookingPage, "Booking successful!");

            // Refresh bookings in the JTable
            loadBookingsIntoTable();

        } else {
            JOptionPane.showMessageDialog(bookingPage, "Booking failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to populate the BookingPage's JTable with data from the database
    public void loadBookingsIntoTable() {
        List<Booking> bookings = dbManager.getAllBookings();
        bookingPage.setTableData(bookings); // Assuming BookingPage has a method called setTableData to populate the JTable
    }

    // Method to show the booking page
    public void showBookingPage() {
        if (bookingPage != null) {
            bookingPage.setVisible(true);
        }
    }

    // Method to hide the booking page
    public void hideBookingPage() {
        if (bookingPage != null) {
            bookingPage.setVisible(false);
        }
    }
    //added
    public String getCurrentUsername() {
        User user = loginController.getCurrentUser();
        if (user != null) {
            return user.getUsername();
        }
        return null; // No user is logged in
    }
}
