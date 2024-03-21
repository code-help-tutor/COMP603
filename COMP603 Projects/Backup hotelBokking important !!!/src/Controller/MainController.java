WeChat: cstutorcs
QQ: 749389476
Email: tutorcs@163.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Database.DBmanager;

/**
 *
 * @author spark
 */
public abstract class MainController {
    
    // For this example, I'm assuming there might be other controllers like `BookingController`, etc.
    // You can add references to them if needed.

    // You can also consider adding attributes related to application-wide state, 
    // like the currently logged-in user, etc.

    // Constructor
    public MainController() {
        // Any initialization for the main controller can go here.
    }
    
    // This abstract method must be implemented by any class that extends MainController
    public abstract void startApplication();

    // Placeholder for a method that might be used to switch between different parts of the application.
    public void navigateTo(String page) {
        switch (page) {
            // You can add cases for other pages/controllers here.
            // case "booking": 
            //    bookingController.showBookingPage();
            //    break;

            default:
                System.out.println("Unknown page: " + page);
                break;
        }
    }
    
    // Other common methods or attributes for all controllers can go here.
    
    // For example, if you want to log activities or errors across the application.
    public void logActivity(String activity) {
        // Logic for logging activities can go here.
        System.out.println(activity);
    }
    
    public void logError(String error) {
        // Logic for logging errors can go here.
        System.err.println(error);
    }
}