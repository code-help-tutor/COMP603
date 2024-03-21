WeChat: cstutorcs
QQ: 749389476
Email: tutorcs@163.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Database.DBmanager;
import Model.User;
import View.LoginPage;
import javax.swing.JOptionPane;
import View.BookingPage;
import GUI.RegisterPage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author spark
 */
public class LoginController extends MainController{
    private LoginPage loginPage;
    private DBmanager dbManager;
    private User currentUser;

    public LoginController() {
        dbManager = new DBmanager();
        loginPage = new LoginPage(this);
        showLoginPage(); // show the login page when the controller is initialized
    }
    
    @Override
    public void startApplication() {
        showLoginPage();
    }

     public void loginButtonClicked(String username, String password) {
         if (dbManager.validateUser(username, password)) {
        
        this.currentUser = fetchUserByUsername(username);
        JOptionPane.showMessageDialog(loginPage, "Logged in successfully!");

         // Create and show the BookingPage
        BookingController bookingController = new BookingController(this); // Pass the LoginController instance
        BookingPage bookingPage = new BookingPage(this);
        bookingPage.setVisible(true);

        // Hide the LoginPage
        hideLoginPage();
        } else {
            JOptionPane.showMessageDialog(loginPage, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

     public void registerButtonClicked() {
        RegisterPage registerPage = new RegisterPage(this);
        registerPage.setVisible(true);
        hideLoginPage(); // hide the login page when moving to register
    }
     
     // Method to show the login page
    public void showLoginPage() {
        if (loginPage != null) {
            loginPage.setVisible(true);
        }
    }

    // Method to hide the login page
    public void hideLoginPage() {
        if (loginPage != null) {
            loginPage.setVisible(false);
        }
    }
    
    public User createUser(String firstName, String lastName, String username, String password, String email, String mobile) {
        User newUser = new User(firstName, lastName, username, password, email, mobile);
        return newUser;
}
    
    //fetch username
    private User fetchUserByUsername(String username) {
    User user = null;
    try {
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            user = new User();
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setMobile(rs.getString("mobile"));
        }
        rs.close();
        pstmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return user;
}
    //getter for currentuser.
    public User getCurrentUser() {
        return this.currentUser;
    }

}
