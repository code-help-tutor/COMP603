WeChat: cstutorcs
QQ: 749389476
Email: tutorcs@163.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author spark
 */
public class Booking {

    private String roomNumber;
    private String checkIn;
    private String checkOut;
    private String price;
    private String fullName;
    private String referenceCode;

    // Default constructor
    public Booking() {}

    // Parameterized constructor
    public Booking(String roomNumber, String checkIn, String checkOut, 
                   String price, String fullName, String referenceCode) {
            setRoomNumber(roomNumber);
            setCheckIn(checkIn);
            setCheckOut(checkOut);
            setPrice(price);  // Using the setter here ensures the price logic is applied
            setFullName(fullName);
            setReferenceCode(referenceCode);
    }

    // Getters and setters
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        if (price == null || price.trim().isEmpty()) {
            this.price = "0"; // default value if null or empty
        } else {
            this.price = price;
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    @Override
    public String toString() {
        return "Booking for " + fullName + " in room " + roomNumber + " from " + checkIn + " to " + checkOut + ".";
    }

}
