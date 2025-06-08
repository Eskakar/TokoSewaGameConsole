/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class DiskonModel {
    private String kodeUnik;
    private int diskon;

    public DiskonModel(String kodeUnik, int diskon) {
        this.kodeUnik = kodeUnik;
        this.diskon = diskon;
    }

    // Getter
    public String getKodeUnik() {
        return kodeUnik;
    }

    public int getDiskon() {
        return diskon;
    }

    // Setter
    public void setKodeUnik(String kodeUnik) {
        this.kodeUnik = kodeUnik;
    }

    public void setDiskon(int diskon) {
        this.diskon = diskon;
    }

    // Override toString
    @Override
    public String toString() {
        return "DiskonModel{" +
                "kodeUnik='" + kodeUnik + '\'' +
                ", diskon=" + diskon +
                '}';
    }
}
