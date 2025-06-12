/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import model.AdminModel;
import model.BerlanggananDAO;
import model.BerlanggananModel;
import model.ConsoleDAO;
import model.ConsoleModel;
import model.DiskonDAO;
import model.DiskonModel;
import model.PembayaranDAO;
import model.PembayaranModel;
import view.HistoryPembayaran;
import view.Input_Pembayaran_A;

/**
 *
 * @author ASUS
 */
public class PembayaranController {
    private PembayaranDAO pembayaranDAO;
    private AdminModel currentAdmin;
    private Input_Pembayaran_A inptView;
    private HistoryPembayaran hisView;
    private ConsoleDAO consoleDAO;
    private DiskonDAO diskonDAO;
    private BerlanggananDAO berlangDAO;
    
    public PembayaranController(AdminModel currentAdmin,Input_Pembayaran_A inputView,HistoryPembayaran historyVIew){
        pembayaranDAO = new PembayaranDAO();
        this.inptView = inputView;
        this.hisView = historyVIew;
        this.currentAdmin = currentAdmin;
        diskonDAO = new DiskonDAO();
        berlangDAO = new BerlanggananDAO();
        consoleDAO = new ConsoleDAO();
        
    }
    public ArrayList loadPembayaranList() {
        if(currentAdmin.getNama() == null){
            return null;
        }
        return pembayaranDAO.getAllPembayaran();
    }

    public boolean addPembayaran(int fk_console, String KTP, String nama_pelanggan,
                              int lama_peminjaman, String kodeDiskon,String catatan) {

        // Step 1: Ambil data console berdasarkan ID
        ConsoleModel console = consoleDAO.getConsoleById(fk_console);
        if (console == null) {
            return false;
        }else if(console.getStock() == 0){
            return false;
        }

        BigDecimal hargaDasar = console.getHarga();
        //perkalian Bigdecimal dan int
        BigDecimal totalHarga = hargaDasar.multiply(BigDecimal.valueOf(lama_peminjaman));

        // Step 2: Cek diskon dari kode diskon jika ada
        if (kodeDiskon != null && !kodeDiskon.isEmpty()) {
            ArrayList<DiskonModel> diskons = diskonDAO.getAllDiskon();
            for (DiskonModel d : diskons) {
                if (d.getKodeUnik().equalsIgnoreCase(kodeDiskon)) {
                    BigDecimal potongan = (totalHarga.multiply(BigDecimal.valueOf(d.getDiskon()))).divide(BigDecimal.valueOf(100));
                    totalHarga = totalHarga.subtract(potongan); //pengurangan harga;
                    break;
                }
            }
        }

        // Step 3: Cek apakah pelanggan berlangganan
        BerlanggananModel subs = berlangDAO.getSubscriptionByKTP(KTP);
        if(subs != null && "Aktif".equalsIgnoreCase(subs.getStatus().trim())){
            BigDecimal potongan = new BigDecimal("0.2");
            // Isi otomatis nama
            nama_pelanggan = subs.getNama();
            // DiskonModel tambahan 20%
            totalHarga = totalHarga.subtract(totalHarga.multiply(potongan));
        }
        

        // Step 4: Tambahkan transaksi
        boolean success = pembayaranDAO.addTransaksi(currentAdmin.getId(), fk_console, KTP, nama_pelanggan, lama_peminjaman, totalHarga, "Dipinjam",catatan);
        if (success) {
            //mengurangi stock console
            consoleDAO.reduceStock(fk_console, 1);
            //loadPembayaranList(); ini masih aneh
            return true;
        } else {
            return false;
        }
    }
    //mengubah apakah sudah kembali atau belum controllernya
    public boolean updatePembayaranStatus(int idPembayaran, Date tanggal,int idCon,String status) {
        if(tanggal == null){
            //this.hisView.showMessage("Mohon data tanggal diisi!");
            return false;
        }
        else if (pembayaranDAO.updatePembayaran(idPembayaran, tanggal,status)) {
            //this.hisView.showMessage("Status pembayaran berhasil diperbarui!");
            //nambah stock console
            consoleDAO.addStock(idCon, 1);
            //loadPembayaranList(); ambigu!!!
            return true;
        } else {
            //this.hisView.showMessage("Gagal memperbarui status pembayaran.");
            return false;
        }
    }
    //menghitung pembayaran
    public BigDecimal hitungTotalHarga(int idCon,int durasi,String kodeDiskon,String KTP){
        // Step 1: Ambil data console berdasarkan ID
        ConsoleModel console = consoleDAO.getConsoleById(idCon);

        BigDecimal hargaDasar = console.getHarga();
        //perkalian Bigdecimal dan int
        BigDecimal totalHarga = hargaDasar.multiply(BigDecimal.valueOf(durasi));

        // Step 2: Cek diskon dari kode diskon jika ada
        if (kodeDiskon != null && !kodeDiskon.isEmpty()) {
            ArrayList<DiskonModel> diskons = diskonDAO.getAllDiskon();
            for (DiskonModel d : diskons) {
                if (d.getKodeUnik().equalsIgnoreCase(kodeDiskon)) {
                    BigDecimal potongan = (totalHarga.multiply(BigDecimal.valueOf(d.getDiskon()))).divide(BigDecimal.valueOf(100));
                    totalHarga = totalHarga.subtract(potongan); //pengurangan harga;
                    break;
                }
            }
        }

        // Step 3: Cek apakah pelanggan berlangganan
        BerlanggananModel subs = berlangDAO.getSubscriptionByKTP(KTP);
        if((subs != null) && (subs.getStatus() == "Aktif")){
            BigDecimal potongan = new BigDecimal("0.2");
            // DiskonModel tambahan 20%
            totalHarga = totalHarga.subtract(totalHarga.multiply(potongan));
        }
        
        
        return totalHarga;
    }
    public int getDataIDConPembayaran(int idPem){
        PembayaranModel pembayaranData;
        pembayaranData = pembayaranDAO.getPembayaranById(idPem);
        return pembayaranData.getFk_console();
    }
    public PembayaranModel dataHistoryByIDPembayaran(int idPem){
        PembayaranModel pembayaranData;
        pembayaranData = pembayaranDAO.getPembayaranById(idPem);
        return pembayaranData;
    }
    //hapus histroty pembayaran (harusnya gak boleh)
    public boolean deleteHistory(int idPem){
        return pembayaranDAO.deletePembayaran(idPem);
    }
    public void setInpView(Input_Pembayaran_A inputview){
        if(this.inptView == null){
            this.inptView = inputview;
        }
    }
    public void setHistoryView(HistoryPembayaran hisview){
        if(this.hisView == null){
            this.hisView = hisview;
        }
    }
    
    
    //set adminModel
    public void setAdminModel(AdminModel model){
        this.currentAdmin = model;
    }
}
