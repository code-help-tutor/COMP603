WeChat: cstutorcs
QQ: 749389476
Email: tutorcs@163.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import View.LoginPage;
import Controller.LoginController;
import Controller.MainController;

/**
 *
 * @author spark
 */
public class HotelBookingSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            MainController controller = new LoginController();
            controller.startApplication();
        });
    }
}