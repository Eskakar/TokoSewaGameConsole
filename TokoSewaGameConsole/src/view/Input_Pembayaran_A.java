/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;


import controller.AdminController;
import javax.swing.JOptionPane;
import model.ConsoleModel;
import model.PembayaranModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author Anzio
 */
public class Input_Pembayaran_A extends javax.swing.JFrame {

    private AdminController adminControl;
    private ConsoleModel selectedConsole;
    private DefaultTableModel tableModel;
    private PembayaranModel pembayaranModel;
    private int targetConsoleId;
    
    //set variabel
    private String DiskonVoucher;

    public Input_Pembayaran_A() {
        //this.targetConsoleId = consoleId;
        initComponents();
        setupTable();
        pembayaranModel = new PembayaranModel();
        
        setLocationRelativeTo(null); 
    }

      private void setupTable() {
        String[] columnNames = {"ID", "Paket Console", "Harga per Hari", "Stock"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        jTable1.setModel(tableModel);
        
        // Set column widths
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(60);
        
        // Add selection listener
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow >= 0) {
                    int consoleId = (Integer) tableModel.getValueAt(selectedRow, 0);
                    //selectConsoleById(consoleId);
                    //calculateTotal();
                }
            }
        });
    }
    
    public void loadConsoleData() {
        selectedConsole = adminControl.getConsoleByID(this.targetConsoleId);
        tableModel.setRowCount(0); // Clear existing data
        
        // Hanya tampilkan console yang sesuai dengan targetConsoleId
        Object[] row = {
            selectedConsole.getId(),
            selectedConsole.getPaket(),
            "Rp " + selectedConsole.getHarga().toString(),
            selectedConsole.getStock()
        };
        tableModel.addRow(row);
   
    }   
    
    /**
     * Validasi input form
     */
    private boolean validateInput() {
        // Cek KTP
        if (jTextField2.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID KTP harus diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            jTextField2.requestFocus();
            return false;
        }
        if (jTextField2.getText().trim().length() != 15) {
            JOptionPane.showMessageDialog(this, "ID KTP harus 15 digit!", "Error", JOptionPane.ERROR_MESSAGE);
            jTextField2.requestFocus();
            return false;
        }
        
        // Cek Nama
        if (jTextField3.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama harus diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            jTextField3.requestFocus();
            return false;
        }
        
        // Cek Console dipilih
        if (selectedConsole == null) {
            JOptionPane.showMessageDialog(this, "Pilih console terlebih dahulu!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Cek Durasi
        try {
            int durasi = Integer.parseInt(jTextField5.getText().trim());
            if (durasi <= 0) {
                JOptionPane.showMessageDialog(this, "Durasi pinjam harus lebih dari 0!", "Validasi", JOptionPane.WARNING_MESSAGE);
                jTextField5.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Durasi pinjam harus berupa angka!", "Validasi", JOptionPane.WARNING_MESSAGE);
            jTextField5.requestFocus();
            return false;
        }
        
        // Cek Metode Pembayaran
        if (jTextField4.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Metode Pembayaran harus diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            jTextField4.requestFocus();
            return false;
        }
        
        // Cek Stock Console
        if (selectedConsole.getStock() <= 0) {
            JOptionPane.showMessageDialog(this, "Console yang dipilih sedang tidak tersedia (stock habis)!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    /**
     * Reset form ke kondisi awal
     */
    private void resetForm() {
        jTextField2.setText("");//ktp
        jTextField3.setText("");//Nama pembaeli
        jTextField4.setText("");//Metode Pembayaran
        jTextField5.setText("");//durasi hari
        jTextField6.setText("");//kode voucher
        jTextArea1.setText("");//catatan
        jTable1.clearSelection();
        selectedConsole = null;
    }
    private void setPembayaranData(){
        //int fk_console, String KTP, String nama_pelanggan,int lama_peminjaman, String kodeDiskon
        this.pembayaranModel.setFk_console(targetConsoleId);
        this.pembayaranModel.setKTP(jTextField2.getText());
        this.pembayaranModel.setNama_pelanggan(jTextField3.getText());
        this.pembayaranModel.setLama_peminjaman(Integer.parseInt(jTextField5.getText()));
        this.pembayaranModel.setCatatan(jTextArea1.getText());
        this.DiskonVoucher = jTextField6.getText();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label1 = new java.awt.Label();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        label1.setText("label1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(520, 500));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setMaximumSize(new java.awt.Dimension(800, 500));
        jPanel1.setMinimumSize(new java.awt.Dimension(800, 500));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID KTP           :");
        jLabel1.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel1.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel1.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 100, -1));

        jLabel3.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Metode Pembayaran :");
        jLabel3.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel3.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel3.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 160, -1));

        jLabel5.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nama             :");
        jLabel5.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel5.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel5.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 100, -1));

        jLabel4.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Durasi Pinjam:");
        jLabel4.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel4.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel4.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 100, -1));

        jTextField2.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 78, 290, 30));

        jTextField3.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 290, 30));

        jTextField4.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jPanel1.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 260, 230, 30));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 290, 60));

        jLabel6.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Catatan          :");
        jLabel6.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel6.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel6.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 100, -1));

        jLabel7.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Kode Voucher:");
        jLabel7.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel7.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel7.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 100, -1));
        jPanel1.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 290, 30));
        jPanel1.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 290, 30));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Paket Console", "Harga", "Stock"
            }
        ));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jTable1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, 390, 90));

        jButton1.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jButton1.setText("Confirm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 400, -1, -1));

        jLabel2.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Form Pembayaran");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, -1, -1));

        jButton3.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jButton3.setText("Kembali");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 400, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //tombol confirm disini
        if(!validateInput()){
            return;
        }
        setPembayaranData();
        //int idCon,int durasi,String kodeDiskon,String KTP,String namaPelanggan
        BigDecimal harga = adminControl.hitungTotalHarga(
                this.targetConsoleId,
                this.pembayaranModel.getLama_peminjaman(),
                this.DiskonVoucher,
                this.pembayaranModel.getKTP()
        );
        int option = JOptionPane.showConfirmDialog(
            this,  
            "Total Harga : " + harga,
            "Konfirmasi Pembayaran",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        if(option == 1){
            return;
        }
        //menyimpan data didatabase
        //int fk_admin, int fk_console, String KTP, String nama_pelanggan,
        //int lama_peminjaman, String kodeDiskon,String catatan
        if(!adminControl.addPembayaran(adminControl.getAdminModelID(), this.targetConsoleId, 
                this.pembayaranModel.getKTP(),this.pembayaranModel.getNama_pelanggan(), 
                this.pembayaranModel.getLama_peminjaman(), DiskonVoucher,
                this.pembayaranModel.getCatatan())){
            showMessage("Transaksi Gagal");
            return;
        }
        showMessage("Transaksi Berhasil");
        Menu m = adminControl.getMenuView();
        m.setVisible(true);
        // Menutup form saat ini (optional)
        this.dispose();
        resetForm();         
           
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //Kembali ke Menu
        Menu m = adminControl.getMenuView();
        m.setVisible(true);
        resetForm();
        // Menutup form saat ini (optional)
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    public void setSelectConsole(int idCon){
        this.targetConsoleId = idCon;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    public void setController(AdminController controller) {
        this.adminControl = controller;
    }

 


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables
}
