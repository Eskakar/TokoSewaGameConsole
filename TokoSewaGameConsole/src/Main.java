/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Anzio
 */

import model.AdminModel;
import controller.*;
import view.Login;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            //AdminModal model = new AdminModal();
            Login login = new Login();
            //new AdminController(model, login);
            login.setVisible(true);

        });
    }
}