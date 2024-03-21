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
public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String mobile;

    // Default constructor
    public User() {}

    // Parameterized constructor
    public User(String firstName, String lastName, String username, String password, String email, String mobile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
    }
    
//-----added for checking the error
    /*
    public User(String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
//----added for checking the error
*/
    
    // Getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    // Override toString method for easy display of user data

   @Override
    public String toString() {
        return "User " + firstName + " " + lastName + " is created.";
    }

    // Override equals and hashCode for correct comparison and hashing of User objects

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        User user = (User) o;
        
        return username != null ? username.equals(user.username) : user.username == null;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
