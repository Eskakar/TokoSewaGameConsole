/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;


import controller.AdminController;
import java.sql.SQLException;
import model.*;
import view.SubMenuB;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DaftarSubs extends javax.swing.JFrame {
    private AdminController adminControl;
    private BerlanggananModel berlanggananModel;
    private int berapaBulan;

    /**
     * Creates new form Subscriptions
     */
    public DaftarSubs(){
        initComponents();
        berlanggananModel = new BerlanggananModel();
        setLocationRelativeTo(null);
    }

    //butuh fungsi yang ngeupdate status berlanggganan dari exp ke belum exp
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        setMaximumSize(new java.awt.Dimension(500, 500));
        setMinimumSize(new java.awt.Dimension(500, 300));


        jPanel1.setMaximumSize(new java.awt.Dimension(500, 500));
        jPanel1.setMinimumSize(new java.awt.Dimension(500, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Daftar Subscriptions");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, -1, -1));

        jLabel3.setText("ID KTP                  :");
        jLabel3.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel3.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel3.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 100, -1));

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 78, 250, 30));

        jLabel5.setText("Nama                   :");
        jLabel5.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel5.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel5.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 100, -1));
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 250, 30));

        jLabel4.setText("Tanggal Expired :");
        jLabel4.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel4.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel4.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 100, -1));
        jPanel1.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 250, 30));

        jButton2.setText("Confirm");
        jButton2.setMaximumSize(new java.awt.Dimension(123, 28));
        jButton2.setMinimumSize(new java.awt.Dimension(123, 28));
        jButton2.setPreferredSize(new java.awt.Dimension(123, 28));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 160, -1));

        jButton3.setText("Kembali ke Menu");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (!validateInput()) {
            return;
        }
        //butuh check apakah sudah pernah terdaftar belum ktpnya
        if(adminControl.getSubByKTP(berlanggananModel.getKtp()) != null){
            this.showMessage("Sudah Pernah Terdaftar");
            return;
        }       
        if(!adminControl.addSubs(
                berlanggananModel.getKtp(),
                berlanggananModel.getNama(),
                this.berapaBulan
        )){
            showMessage("Gagal Menambah Data berlangganan");
            return;
        }    
        this.showMessage("Berhasil Menambahkan Data Berlangganan");
        clearForm();
        SubMenuB subB= adminControl.getSubMenuBView();
        subB.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        SubMenuB subB = adminControl.getSubMenuBView();
        subB.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private boolean validateInput() {
        String ktp = jTextField2.getText().trim();
        String nama = jTextField3.getText().trim();
        String expiredText = jTextField5.getText().trim(); // ambil teks mentah dulu

        if (ktp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID KTP tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            jTextField2.requestFocus();
            return false;
        }

        if (ktp.length() != 15) {
            JOptionPane.showMessageDialog(this, "ID KTP harus 15 digit!", "Error", JOptionPane.ERROR_MESSAGE);
            jTextField2.requestFocus();
            return false;
        }

        if (!ktp.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "ID KTP hanya boleh berisi angka!", "Error", JOptionPane.ERROR_MESSAGE);
            jTextField2.requestFocus();
            return false;
        }

        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            jTextField3.requestFocus();
            return false;
        }

        if (nama.length() < 2) {
            JOptionPane.showMessageDialog(this, "Nama minimal 2 karakter!", "Error", JOptionPane.ERROR_MESSAGE);
            jTextField3.requestFocus();
            return false;
        }

        // Validasi tanggalExpired
        if (expiredText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tanggal expired tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            jTextField5.requestFocus();
            return false;
        }

        int tanggalExpired;
        try {
            tanggalExpired = Integer.parseInt(expiredText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tanggal expired harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            jTextField5.requestFocus();
            return false;
        }

        // Lolos semua validasi
        setBerlanggananModel(ktp, nama, tanggalExpired);
        return true;
    }

    
     private void clearForm() {
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField5.setText("");
        jTextField2.requestFocus();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DaftarSubs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DaftarSubs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DaftarSubs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DaftarSubs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new DaftarSubs().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(DaftarSubs.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    public void setController(AdminController controller) {
        this.adminControl = controller;
    }
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    private BerlanggananModel setBerlanggananModel(String ktp,String nama,int berapabulan){
        berlanggananModel.setKtp(ktp);
        berlanggananModel.setNama(nama);
        this.berapaBulan = berapabulan;
        //berlanggananModel.setTanggalExpired(date);
        //tanggal exp 6 bulan dari pendaftaran
        return berlanggananModel;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
