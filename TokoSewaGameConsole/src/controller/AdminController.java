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
    
    public AdminController(){
        this.modelAdmin = new AdminModel();
    }
    public AdminController(AdminModel modelA, AdminView viewA) {
        this.modelAdmin = modelA;
        this.view = viewA;
        viewA.setController(this);
        loadInitialData();
    }

    private void loadInitialData() {
        loadPembayaranList();
        loadConsoleList();
        loadDiskonList();
        loadBerlanggananList();
    }

    // ===== PEMBAYARAN =====
    public ArrayList loadPembayaranList() {
        ArrayList<PembayaranModel> pembayaranList = model.getAllPembayaran();
        return pembayaranList;
    }

    public void addPembayaran(int fk_admin, int fk_console, String KTP, String nama_pelanggan,
                              int lama_peminjaman, String kodeDiskon) {

        // Step 1: Ambil data console berdasarkan ID
        ConsoleModel console = model.getConsoleById(fk_console);
        if (console == null) {
            view.showMessage("Console tidak ditemukan.");
            return;
        }else if(console.getStock() == 0){
            view.showMessage("Console Kehabisan Stock");
            return;
        }

        BigDecimal hargaDasar = console.getHarga();
        //perkalian Bigdecimal dan int
        BigDecimal totalHarga = hargaDasar.multiply(BigDecimal.valueOf(lama_peminjaman));

        // Step 2: Cek diskon dari kode diskon jika ada
        if (kodeDiskon != null && !kodeDiskon.isEmpty()) {
            ArrayList<DiskonModel> diskons = model.getAllDiskon();
            for (DiskonModel d : diskons) {
                if (d.getKodeUnik().equalsIgnoreCase(kodeDiskon)) {
                    BigDecimal potongan = (totalHarga.multiply(BigDecimal.valueOf(d.getDiskon()))).divide(BigDecimal.valueOf(100));
                    totalHarga = totalHarga.subtract(potongan); //pengurangan harga;
                    break;
                }
            }
        }

        // Step 3: Cek apakah pelanggan berlangganan
        ArrayList<BerlanggananModel> langganans = model.readAllBerlangganan();
        for (BerlanggananModel b : langganans) {
            if (b.getKtp().equals(KTP) && b.getStatus().equalsIgnoreCase("Aktif")) {
                BigDecimal potongan = new BigDecimal("0.2");
                // Isi otomatis nama
                nama_pelanggan = b.getNama();
                // DiskonModel tambahan 20%
                totalHarga = totalHarga.subtract(totalHarga.multiply(potongan));
                break;
            }
        }

        // Step 4: Tambahkan transaksi
        boolean success = model.addTransaksi(fk_admin, fk_console, KTP, nama_pelanggan, lama_peminjaman, totalHarga, "Belum Dikembalikan");
        if (success) {
            view.showMessage("Pembayaran berhasil ditambahkan!");
            loadPembayaranList();
        } else {
            view.showMessage("Gagal menambahkan pembayaran.");
        }
    }

    public void updatePembayaranStatus(int id, Date tanggal) {
        if (model.updatePembayaran(id, tanggal)) {
            view.showMessage("Status pembayaran berhasil diperbarui!");
            loadPembayaranList();
        } else {
            view.showMessage("Gagal memperbarui status pembayaran.");
        }
    }

    // ===== CONSOLE =====
    public ArrayList loadConsoleList() { //jika perlu untuk ambil data semua console
        ArrayList<ConsoleModel> consoleList = model.getAllConsoles();
        return consoleList;
        
    }

    public void addConsole(String nama, String deskripsi, int stock, int harga) {
        if (model.addConsole(nama, deskripsi, stock, harga)) {
            view.showMessage("Console berhasil ditambahkan!");
            loadConsoleList();
        } else {
            view.showMessage("Gagal menambahkan console.");
        }
    }

    public void updateConsole(int id, String nama, String deskripsi, int stock, int harga) {
        if (model.updateConsole(id, stock)) {
            view.showMessage("Console berhasil diperbarui!");
            loadConsoleList();
        } else {
            view.showMessage("Gagal memperbarui console.");
        }
    }
    /* jika perlu
    public void deleteConsole(int id) {
        if (model.deleteConsole(id)) {
            view.showMessage("Console berhasil dihapus!");
            loadConsoleList();
        } else {
            view.showMessage("Gagal menghapus console.");
        }
    }*/

    // ===== DISKON =====
    public void loadDiskonList() {
        ArrayList<DiskonModel> diskonList = model.getAllDiskon(); // jika perlu
        //view.showDiskonList(diskonList);
    }

    // ===== BERLANGGANAN =====
    public ArrayList loadBerlanggananList() {
        ArrayList<BerlanggananModel> berlanggananList = model.readAllBerlangganan();
       return berlanggananList;
    }

    public void updateStatusBerlangganan(String KTP, String newStatus) {
        if (model.updateStatusBerlangganan(KTP, newStatus)) {
            view.showMessage("Status berlangganan berhasil diperbarui!");
            loadBerlanggananList();
        } else {
            view.showMessage("Gagal memperbarui status berlangganan.");
        }
    }
}
