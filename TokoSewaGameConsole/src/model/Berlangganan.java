/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author ASUS
 */
public  class Berlangganan {
    public String KTP;
    public String nama;
    public String status;
    public Date tanggal_expired;

    public Berlangganan(String KTP, String nama, String status,Date tanggal) {
        this.KTP = KTP;
        this.nama = nama;
        this.status = status;
        this.tanggal_expired = tanggal;
    }
}