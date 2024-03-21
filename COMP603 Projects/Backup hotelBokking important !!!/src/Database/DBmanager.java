WeChat: cstutorcs
QQ: 749389476
Email: tutorcs@163.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;//added
import java.math.BigDecimal;//added
import java.sql.Date;//added
import java.util.ArrayList;//added
import java.util.List;//added
import Model.Booking;

import java.text.SimpleDateFormat;
import java.text.ParseException;


import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public final class DBmanager {

    private static final String dbName = "hotel_ebd";
    private static final String URL = "jdbc:derby:" + dbName + "; create=true";
    private static final String USERNAME = "hotel";
    private static final String PASSWORD = "hotel";
    Connection connection;

    public DBmanager() {
        establishConnection();
        setupTables();
    }

    public void establishConnection() {
        if (this.connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Connected: " + dbName);
            }
            catch (SQLException ex) {
                System.out.println("Unable to connect: " + dbName);
                System.out.println(ex.getMessage());
            }
        }
    }

    public Connection getConnection() {
        if (this.connection != null) 
            return this.connection;
        else 
            return null;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnected: " + dbName);
            }
            catch (SQLException ex) { 
                System.out.println(ex.getMessage());
            }
        }
    }

    public void update(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public void updateBatch(String... sql) throws SQLException {
        Statement statement = connection.createStatement();
        for (String s : sql) 
            statement.addBatch(s);
        statement.executeBatch();
    }

    public ResultSet query(String sql) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean insertUser(String firstName, String lastName, String username, 
                              String password, String email, String mobile) {
        try {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            String sql = "INSERT INTO users (firstName, lastName, username, password, email, mobile) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, username);
            pstmt.setString(4, hashedPassword); 
            pstmt.setString(5, email);
            pstmt.setString(6, mobile);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateUser(String username, String password) {
        try {
            String sql = "SELECT password FROM users WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    rs.close();
                    pstmt.close();
                    return true;
                }
            }
            rs.close();
            pstmt.close();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // setup the tables for the database.
    private void setupTables() {
        try {
            // Check if 'users' table exists
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "USERS", null);
            if (!tables.next()) {
                // Table doesn't exist, create it
                String usersTableSQL = "CREATE TABLE users (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "firstName VARCHAR(255), " +
                    "lastName VARCHAR(255), " +
                    "username VARCHAR(255) UNIQUE, " +
                    "password VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "mobile VARCHAR(255))";
                update(usersTableSQL);
            }
            
         //-----------------------added for Bookings table----------------------
            // Setup 'bookings' table
        ResultSet bookingsTable = dbm.getTables(null, null, "BOOKINGS", null);
        if (!bookingsTable.next()) {
            // 'bookings' Table doesn't exist, create it
            String bookingTableSQL = "CREATE TABLE bookings (" +
                "bookingID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "roomNumber VARCHAR(255), " +
                "checkIn DATE, " +
                "checkOut DATE, " +
                "price DECIMAL(10, 2), " +
                "fullName VARCHAR(255), " +
                "referenceCode VARCHAR(255), " +
                "username VARCHAR(255) REFERENCES users(username))";
            update(bookingTableSQL);
        }
        
        //---------------------------added for Bookings table------------------------------
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean saveBooking(Booking booking, String username) {
        try {
            String sql = "INSERT INTO bookings (roomNumber, checkIn, checkOut, price, fullName, referenceCode, username) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, booking.getRoomNumber());

            // Convert and set the checkIn date
            java.util.Date checkInUtilDate = new SimpleDateFormat("dd-MM-yyyy").parse(booking.getCheckIn());
            java.sql.Date checkInSqlDate = new java.sql.Date(checkInUtilDate.getTime());
            pstmt.setDate(2, checkInSqlDate);

            // Convert and set the checkOut date
            java.util.Date checkOutUtilDate = new SimpleDateFormat("dd-MM-yyyy").parse(booking.getCheckOut());
            java.sql.Date checkOutSqlDate = new java.sql.Date(checkOutUtilDate.getTime());
            pstmt.setDate(3, checkOutSqlDate);

            pstmt.setBigDecimal(4, new BigDecimal(booking.getPrice()));
            pstmt.setString(5, booking.getFullName());
            pstmt.setString(6, booking.getReferenceCode());
            pstmt.setString(7, username); // ---------------------------problem here---------------------------------

            int affectedRows = pstmt.executeUpdate();
            pstmt.close();

            return affectedRows > 0;

        } catch (SQLException | ParseException e) { // Added ParseException here
            e.printStackTrace();
            return false;
        }
}
    
   private static String reformatDate(String date) {
    try {
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDateObj = originalFormat.parse(date);
        
        // Convert java.util.Date to java.sql.Date
        java.sql.Date sqlDateObj = new java.sql.Date(utilDateObj.getTime());
        
        return targetFormat.format(sqlDateObj);
    } catch (ParseException e) {
        e.printStackTrace();
        return null;
    }
} 

public List<Booking> getAllBookings() {
    List<Booking> bookings = new ArrayList<>();
    try {
        String sql = "SELECT * FROM bookings";
        ResultSet rs = query(sql);

        while (rs.next()) {
            Booking booking = new Booking();
            booking.setRoomNumber(rs.getString("roomNumber"));
            booking.setCheckIn(rs.getDate("checkIn").toString());
            booking.setCheckOut(rs.getDate("checkOut").toString());
            booking.setPrice(rs.getBigDecimal("price").toString());
            booking.setFullName(rs.getString("fullName"));
            booking.setReferenceCode(rs.getString("referenceCode"));

            bookings.add(booking);
        }
        rs.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return bookings;
}

   //method for getting the full name //added for BookingPage
    public String getFullNameForCurrentUser(String username) {
    String fullName = null;
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        connection = getConnection();  // Assuming you have a method to get a DB connection
         String query = "SELECT firstName, lastName FROM users WHERE username = ?";
        ps = connection.prepareStatement(query);
        ps.setString(1, username);
        
        rs = ps.executeQuery();
        
        if(rs.next()) {
            fullName = rs.getString("firstName") + " " + rs.getString("lastName");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // close resources if they were opened
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return fullName;
}
}