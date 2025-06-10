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
import view.*;
import java.util.ArrayList;
import java.sql.Date;
import model.BerlanggananModel;
import model.ConsoleModel;
import view.Login;

public class AdminController {
    private AdminModel modelAdmin;
    private LoginDAO loginDAO;
    private String statusLogin;
    private Login loginView;
    
    //bikin object dari tiap view
    private DaftarSubs dafView;
    private EditSubscriptionForm editSUbsForm;
    private Gudang gudangView;
    private HistoryPembayaran historyView;
    private Input_Pembayaran_A inputPemView;
    private ListSubs listSubsView;
    private List_Console_A listconsole;
    private Login login;
    private Menu menu;
    private SubMenuA subA;
    private SubMenuB subB;
    //bikin object dari tiap controller
    private BerlanggananController berlanggananControl;
    private ConsoleController consoleControl;
    private DiskonController diskonControl;
    private PembayaranController pembayaranControl;
    
    public AdminController(DaftarSubs dafView, Gudang gudangView, HistoryPembayaran historyView,
            Input_Pembayaran_A inputPemView,ListSubs listSubsView,List_Console_A listconsole,Login login, Menu menu, SubMenuA subA,SubMenuB subB) {
        this.modelAdmin = new AdminModel(0,null,null);
        this.berlanggananControl = new BerlanggananController(this.modelAdmin);
        this.loginDAO = new LoginDAO();
        consoleControl = new ConsoleController(modelAdmin,this.gudangView);
        diskonControl = new DiskonController(modelAdmin);
        pembayaranControl = new PembayaranController(modelAdmin,this.inputPemView);
        //set agar menggunakan view yang sudah dibuat di main
        this.dafView = dafView;
        this.gudangView = gudangView;
        this.historyView = historyView;
        this.inputPemView = inputPemView;
        this.listSubsView = listSubsView;
        this.listconsole = listconsole;
        this.login = login;
        this.menu = menu;
        this.subA = subA;
        this.subB = subB;
    }
    private void loadAdminModelController(){//ini belum terload kalau belum login
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
           loginView.showMessage("Connection Error");
           return false;
       }
    }
    //2. check kredential Admin dan set modelAdmin
    public String authenticateUser(String username, String password){
        statusLogin = loginDAO.authenticateUser(username, password);
        if(statusLogin == "admin"){
            this.modelAdmin =loginDAO.getAdminByUsername(username);
            loadAdminModelController();
        }else{
            statusLogin = null;
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
        pembayaranControl.addPembayaran( fk_console, KTP, nama_pelanggan,lama_peminjaman,  kodeDiskon);     
        //kasih view pop up pembayaran setlah input pembayaran dan total pembayaran
    }
   //C. subscirption
    //1. Ambil data berlangganan
    public ArrayList loadSubs(){
        return berlanggananControl.loadBerlanggananList();
    }
    //2.tambah subs
    public void addSubs(BerlanggananModel berlanggananModel){
        berlanggananControl.addSubs(berlanggananModel);
    }
    //D.Gudang atau data Console
    public ArrayList loadConsoleList(){
        return consoleControl.loadConsoleListGudang();
    }
    public boolean addStockGudang(int id, int stock){
        return consoleControl.addStockGudang(id,stock);
    }
    public boolean reduceStockGudang(int id, int stock){
        return consoleControl.reduceStockGudang(id, stock);
    }
    public boolean updateStockGudang(int id, int stock){
        return consoleControl.updateConsoleGudang(id,stock);
    }
    
    
    //========================================================================================
    //bagian get View
    public Menu getMenuView(){
        return this.menu;
    }
    public SubMenuA getSubMenuAView(){
        return this.subA;
    }
    public SubMenuB getSubMenuBView(){
        return this.subB;
    }
    public Gudang getGudangView(){
        return this.gudangView;
    }
    public List_Console_A getListConsoleAView(){
        return this.listconsole;
    }
    public HistoryPembayaran getHistoryPembayaranView(){
        return this.historyView;
    }
    public DaftarSubs getDaftarSubsView(){
        return this.dafView;
    }
    public ListSubs getListSubsView(){
        return this.listSubsView;
    }
}
