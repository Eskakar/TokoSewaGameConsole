/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.AdminModel;
import model.DiskonDAO;
import model.DiskonModel;

/**
 *
 * @author ASUS
 */
public class DiskonController {
    private DiskonDAO diskonDAO;
    private AdminModel currentAdmin;
    public DiskonController(AdminModel currentAdmin){
        diskonDAO = new DiskonDAO();
        this.currentAdmin = currentAdmin;
    }
    //ingat gak ada tampilan semua diskon
    public ArrayList loadDiskonList() {
        ArrayList<DiskonModel> diskonList = diskonDAO.getAllDiskon(); // jika perlu
        return diskonList;
    }
    public void setAdminModel(AdminModel model){
        this.currentAdmin = model;
    }
}
