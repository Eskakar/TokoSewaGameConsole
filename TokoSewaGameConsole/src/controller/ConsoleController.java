/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.AdminModel;
import model.ConsoleDAO;
import model.ConsoleModel;
import view.Gudang;


/**
 *
 * @author ASUS
 */
public class ConsoleController {
    private ConsoleDAO consoleDAO;
    private AdminModel currentAdmin;
    private Gudang viewGudang;
    
    public ConsoleController(AdminModel currentAdmin, Gudang gudang){
        consoleDAO = new ConsoleDAO();
        this.currentAdmin = currentAdmin;
        this.viewGudang= gudang;
    }
    
    
    //====================untuk gudang ============================
    public ArrayList loadConsoleListGudang() { //jika perlu untuk ambil data semua console
        return consoleDAO.getAllConsoles(); // ini langsung diambil datanya karena beberapa view 
                                            // langsung meload data bahkan sebelum login
    }
    public boolean addStockGudang(int id, int stock) {
        if ((currentAdmin.getNama() != null && (stock > 0)) ) {
            consoleDAO.addStock(id, stock);
            return true;
        }else {
            return false;
        }
    }
    public boolean reduceStockGudang(int id, int stock){
        if(consoleDAO.getConsoleById(id) == null){
            viewGudang.showMessage("tidak terdapat console dengan id tersebut");
            return false;
        }
        else if ((currentAdmin.getNama() != null) && (stock > 0)) {
            consoleDAO.reduceStock(id, stock);
            viewGudang.showMessage("Data Berhasil dirubah");
            //loadConsoleList();
            return true;
        }else if((currentAdmin.getNama() != null)){
            viewGudang.showMessage("Tidak terdapat perubahan");
            return false;
        }else {
            viewGudang.showMessage("Belum Login");
            return false;
        }
    }
    public boolean updateConsoleGudang(int id, int stock){
        return consoleDAO.updateStock(id,stock);
    }
    public ConsoleModel getConsoleByID(int idCon){
        return consoleDAO.getConsoleById(idCon);
    }
    //=========================================================================
    public void setAdminModel(AdminModel model){
        this.currentAdmin = model;
    }
}
