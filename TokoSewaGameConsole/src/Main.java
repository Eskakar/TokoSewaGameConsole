/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Asus
 */


import model.AdminModel;
import controller.*;
import view.*;
import view.Login;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            //buat smeua object view di sini
            Login login = new Login();
            DaftarSubs dafView = new DaftarSubs();
            Gudang gudangView = new Gudang();
            HistoryPembayaran historyView = new HistoryPembayaran();
            Input_Pembayaran_A inputPemView = new Input_Pembayaran_A();
            ListSubs listSubsView = new ListSubs();
            List_Console_A listconsole = new List_Console_A();
            Menu menu = new Menu();
            SubMenuA subA = new SubMenuA();
            SubMenuB subB = new SubMenuB();
            //sekalian inisialisasi ke Admincontroller
            AdminController adminController = new AdminController(
                    dafView,  
                    gudangView,  
                    historyView,  
                    inputPemView,  
                    listSubsView,  
                    listconsole,  
                    login,  
                    menu,  
                    subA,  
                    subB  
            );
            //set controller biar pakai admincontroller semua
            dafView.setController(adminController);
            gudangView.setController(adminController);
            historyView.setController(adminController);
            inputPemView.setController(adminController);
            listSubsView.setController(adminController);
            listconsole.setController(adminController);
            login.setController(adminController);
            menu.setController(adminController);
            subA.setController(adminController);
            subB.setController(adminController);

            
            //menu login
            login.setLocationRelativeTo(null);
            login.setVisible(true);

        });
    }
}