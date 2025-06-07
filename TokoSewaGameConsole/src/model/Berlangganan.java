/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.time.LocalDate;

/**
 *
 * @author Anzio
 */
public class Berlangganan {
    private String ktp;
    private String nama;
    private String status;
    private LocalDate tanggalExpired;
    
    // Constructor kosong
    public Berlangganan() {}
    
    // Constructor dengan parameter
    public Berlangganan(String ktp, String nama, String status, LocalDate tanggalExpired) {
        this.ktp = ktp;
        this.nama = nama;
        this.status = status;
        this.tanggalExpired = tanggalExpired;
    }
    
    // Getter dan Setter
    public String getKtp() {
        return ktp;
    }
    
    public void setKtp(String ktp) {
        this.ktp = ktp;
    }
    
    public String getNama() {
        return nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDate getTanggalExpired() {
        return tanggalExpired;
    }
    
    public void setTanggalExpired(LocalDate tanggalExpired) {
        this.tanggalExpired = tanggalExpired;
    }
    
    @Override
    public String toString() {
        return "Berlangganan{" +
                "ktp='" + ktp + '\'' +
                ", nama='" + nama + '\'' +
                ", status='" + status + '\'' +
                ", tanggalExpired=" + tanggalExpired +
                '}';
    }
}