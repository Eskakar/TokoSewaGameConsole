/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;

/**
 *
 * @author Anzio
 */
public class ConsoleModel {
    private int id;
    private String paket;
    private String deskripsi;
    private int stock;
    private BigDecimal harga;
    
    // Constructor
    public ConsoleModel() {}
    
    public ConsoleModel(int id, String paket, String deskripsi, int stock, BigDecimal harga) {
        this.id = id;
        this.paket = paket;
        this.deskripsi = deskripsi;
        this.stock = stock;
        this.harga = harga;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getPaket() {
        return paket;
    }
    
    public void setPaket(String paket) {
        this.paket = paket;
    }
    
    public String getDeskripsi() {
        return deskripsi;
    }
    
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    
    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public BigDecimal getHarga() {
        return harga;
    }
    
    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }
    
    @Override
    public String toString() {
        return "Console{" +
                "id=" + id +
                ", paket='" + paket + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", stock=" + stock +
                ", harga=" + harga +
                '}';
    }
}