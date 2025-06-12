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
    RamalanCuaca ramalanCuacaView;
    //bikin object dari tiap controller
    private BerlanggananController berlanggananControl;
    private ConsoleController consoleControl;
    private DiskonController diskonControl;
    private PembayaranController pembayaranControl;
    
    public AdminController(DaftarSubs dafView, Gudang gudangView, HistoryPembayaran historyView,
            Input_Pembayaran_A inputPemView,ListSubs listSubsView,List_Console_A listconsole,
            Login login, Menu menu, SubMenuA subA,SubMenuB subB, RamalanCuaca cuaca) {
        this.modelAdmin = new AdminModel(0,null,null);
        this.berlanggananControl = new BerlanggananController(this.modelAdmin);
        this.loginDAO = new LoginDAO();
        consoleControl = new ConsoleController(modelAdmin,this.gudangView);
        diskonControl = new DiskonController(modelAdmin);
        pembayaranControl = new PembayaranController(modelAdmin,this.inputPemView,this.historyView);
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
        this.ramalanCuacaView = cuaca;
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
           login.showMessage("Connection Error");
           System.exit(0);
           return false;
       }
    }
    //2. check kredential Admin dan set modelAdmin
    public String authenticateUser(String username, String password){
        statusLogin = loginDAO.authenticateUser(username, password);
        if(statusLogin == "admin"){
            this.modelAdmin =loginDAO.getAdminByUsername(username);
            loadAdminModelController();
            berlanggananControl.updateExpiredToday();
        }else{
            statusLogin = null;
        }
        return statusLogin;
    }
    //B.Pembayaran
    //1.ambl semua data pembayaran --untuk History pembayaran
    public ArrayList loadDataPembayaran(){
        return pembayaranControl.loadPembayaranList();
        //dikasih fungsi penampilan sesuai view yang dipakai dihubunin dengan pembayaran Control
    }
    //2.upload data pembayaran ke DB --untuk input pembayaran view
    public boolean addPembayaran(int fk_admin, int fk_console, String KTP, String nama_pelanggan,
                              int lama_peminjaman, String kodeDiskon,String catatan){
        //fungsi untuk pop up setelah confirm pembarana, lalu ada detail semua pembayaran dan total harga
        return pembayaranControl.addPembayaran( fk_console, KTP, nama_pelanggan,lama_peminjaman,  kodeDiskon, catatan);     
    }
    //3.update pembayaran/ set status controler dikembalikan - di view history pembayaran
    public boolean updatePembayaran(int id, Date currentDate,String status){
        int idCon = pembayaranControl.getDataIDConPembayaran(id);
        if(pembayaranControl.updatePembayaranStatus(id, currentDate, idCon,status)){
            this.historyView.loadDataToTable();
            return true;
        }
        return false;
    }
    //4.delete history pembayaran
    public boolean deleteHistoryPembayaran(int idPem){
        if(pembayaranControl.deleteHistory(idPem)){
            return true;
        }
        return false;
    }
    //5.check apakah console sudah dikembalikan
    public boolean checkConsleStatus(int idPem){
        PembayaranModel pemModel = pembayaranControl.dataHistoryByIDPembayaran(idPem);
        return !"Dipinjam".equals(pemModel.getStatus_console());
    }
    //6. ngitung total harga
    public BigDecimal hitungTotalHarga(int idCon,int durasi,String kodeDiskon,String KTP){
        return pembayaranControl.hitungTotalHarga(idCon, durasi, kodeDiskon, KTP);
    }
    
   //C. subscirption
    //1. Ambil data berlangganan
    public ArrayList loadSubs(){
        return berlanggananControl.loadBerlanggananList();
    }
    //2.tambah subs
    public boolean addSubs(String ktp,String nama,int berapaLama){
        return berlanggananControl.addSubs(ktp,nama,berapaLama);
    }
    //3. getSubsByKTP
    public BerlanggananModel getSubByKTP(String ktp){
        return berlanggananControl.getSubsByKTP(ktp);
    }
    //4.hapus berlangganan
    public boolean deletBerlangganaByKtp(String ktp){
        return berlanggananControl.deletBerlangganaByKtp(ktp);
    }
    //5. edit data berlangganan
    public boolean editBerlanggana(BerlanggananModel data,EditSubscriptionForm editFormView){
        return berlanggananControl.updateSubscription(data,editFormView);
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
    public ConsoleModel getConsoleByID(int idCon){
        return consoleControl.getConsoleByID(idCon);
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
        //plus load data saat object dipanggil
        this.gudangView.loadConsoleData();
        return this.gudangView;
    }
    public List_Console_A getListConsoleAView(){
        return this.listconsole;
    }
    public HistoryPembayaran getHistoryPembayaranView(){
        this.historyView.loadDataToTable();
        return this.historyView;
    }
    public DaftarSubs getDaftarSubsView(){
        return this.dafView;
    }
    public ListSubs getListSubsView(){
        this.listSubsView.loadSubscriptionData();
        return this.listSubsView;
    }
    public Input_Pembayaran_A getInputPembayaranA(){
        return this.inputPemView;
    }
    public AdminModel getAdminModel(){
        return this.modelAdmin;
    }
    public int getAdminModelID(){
        return this.modelAdmin.getId();
    }
    public Login getLoginView(){
        return this.login;
    }
    public RamalanCuaca getRamalanCuaca(){
        return this.ramalanCuacaView;
    }
    
    public void setInpView(Input_Pembayaran_A inputview){
        pembayaranControl.setInpView(inputview);
    }
    public void setHistoryView(HistoryPembayaran hisview){
        pembayaranControl.setHistoryView(hisview);
    }

}
