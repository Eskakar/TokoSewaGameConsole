/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author ASUS
 */
import java.math.BigDecimal;
import model.*;
import view.AdminView;
import java.util.ArrayList;
import java.sql.Date;
import model.BerlanggananModel;
import model.ConsoleModel;
import view.Login;

public class AdminController {
    private AdminModel modelAdmin;
    private AdminView view;
    private LoginDAO loginDAO;
    private Login login;
    private String statusLogin;
    
    //bikin object dari tiap controller
    private BerlanggananController berlanggananControl;
    private ConsoleController consoleControl;
    private DiskonController diskonControl;
    private PembayaranController pembayaranControl;
    
    public AdminController(){
        //this.modelAdmin = new AdminModel();
    }
    public AdminController(AdminView viewA) {
        this.modelAdmin = new AdminModel();
        berlanggananControl = new BerlanggananController(this.modelAdmin);
        consoleControl = new ConsoleController(modelAdmin);
        diskonControl = new DiskonController(modelAdmin);
        pembayaranControl = new PembayaranController(modelAdmin);
        this.view = viewA;
        viewA.setController(this);
        //loadInitialData();
    }
    private void loadAdminModelController(){
        berlanggananControl.setAdminModel(modelAdmin);
        consoleControl.setAdminModel(modelAdmin);
        diskonControl.setAdminModel(modelAdmin);
        pembayaranControl.setAdminModel(modelAdmin);
    }

    /*private void loadInitialData() {
        loadPembayaranList();
        loadConsoleList();
        loadDiskonList();
        loadBerlanggananList();
    }
    */
    //===========================================
    //A.Login 
    //1. Check connection
    public boolean checkConnDB(){
       if( loginDAO.isConnectionValid()){
           return true;
       }else{
           login.showMessage("Koneksi Error");
           return false;
       }
    }
    //2. check kredential Admin dan set modelAdmin
    public String authenticateUser(String username, String password){
        statusLogin = loginDAO.authenticateUser(username, password);
        if(statusLogin == "admin"){
            this.modelAdmin =loginDAO.getAdminByUsername(username);
            loadAdminModelController();
        }
        return statusLogin;
    }
    //B.Pembayaran
    //1.ambl semua data berlangganan --untuk History pembayaran
    public void loadDataPembayaran(){
        pembayaranControl.loadPembayaranList();
        //dikasih fungsi penampilan sesuai view yang dipakai dihubunin dengan pembayaran Control
    }
    //2.upload data pembayaran ke DB --untuk input pembayaran view
    public void addPembayaran(int fk_admin, int fk_console, String KTP, String nama_pelanggan,
                              int lama_peminjaman, String kodeDiskon){
        //fungsi untuk pop up setelah confirm pembarana, lalu ada detail semua pembayaran dan total harga
        addPembayaran(fk_admin,  fk_console, KTP, nama_pelanggan,lama_peminjaman,  kodeDiskon);      
    }
    
    
}
