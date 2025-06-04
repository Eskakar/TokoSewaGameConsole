/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
import modal.*;
import controller.*;
import view.*;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            AdminModal model = new AdminModal();
            AdminView view = new AdminView();
            new AdminController(model, view);
            view.setVisible(true);
        });
    }
}
