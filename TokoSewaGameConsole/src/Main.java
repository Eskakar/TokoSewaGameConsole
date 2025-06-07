/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
import model.AdminModel;
import controller.*;
import view.*;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            AdminModel model = new AdminModel();
            AdminView view = new AdminView();
            new AdminController(model, view);
            view.setVisible(true);
            AdminController admin = new AdminController(model,view);
        });
    }
}
