/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.AdminModel;
import model.ConsoleDAO;
import view.Gudang;

/**
 *
 * @author ASUS
 */
public class ConsoleController {
    private ConsoleDAO consoleDAO;
    private AdminModel currentAdmin;
    private Gudang view;
    
    public ConsoleController(AdminModel currentAdmin){
        consoleDAO = new ConsoleDAO();
        this.currentAdmin = currentAdmin;
    }
    
    public ArrayList loadConsoleList() { //jika perlu untuk ambil data semua console
        if(currentAdmin.getNama() != null){
            return consoleDAO.getAllConsoles();
        }else{
            view.showMessage("Belum Login");
            return null;     
        }
    }

    public void addStock(int id, int stock) {
        if(consoleDAO.getConsoleById(id) == null){
            view.showMessage("tidak terdapat console dengan id tersebut");
        }
        else if ((currentAdmin.getNama() != null) && (stock > 0)) {
            consoleDAO.addStock(id, stock);
            view.showMessage("Data Berhasil dirubah");
            loadConsoleList();
        }else if((currentAdmin.getNama() != null)){
            view.showMessage("Tidak terdapat perubahan");
        }else {
            view.showMessage("Belum Login");
        }
    }
    public void reduceStock(int id, int stock){
        if(consoleDAO.getConsoleById(id) == null){
            view.showMessage("tidak terdapat console dengan id tersebut");
        }
        else if ((currentAdmin.getNama() != null) && (stock > 0)) {
            consoleDAO.reduceStock(id, stock);
            view.showMessage("Data Berhasil dirubah");
            loadConsoleList();
        }else if((currentAdmin.getNama() != null)){
            view.showMessage("Tidak terdapat perubahan");
        }else {
            view.showMessage("Belum Login");
        }
    }
    
    public void setAdminModel(AdminModel model){
        this.currentAdmin = model;
    }
}
