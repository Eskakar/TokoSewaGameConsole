/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import model.PembayaranDAO;
import model.ConsoleModel;
import model.Pembayaran;
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
    private PembayaranDAO pembayaranDAO;
    private List<ConsoleModel> consoleList;
    private ConsoleModel selectedConsole;
    private DefaultTableModel tableModel;
    private int targetConsoleId;
    /**
     * Creates new form Input_Pembayaran_A
     */
    public Input_Pembayaran_A(int consoleId) {
        pembayaranDAO = new PembayaranDAO();
        this.targetConsoleId = consoleId;
        initComponents();
        setupTable();
        loadConsoleData();
        setupEventHandlers();
        
        if (consoleId > 0) {
            selectConsoleById(consoleId);
        }
        
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
                    selectConsoleById(consoleId);
                    calculateTotal();
                }
            }
        });
    }
      
    private void loadConsoleData() {
        consoleList = pembayaranDAO.getAllConsoles();
        tableModel.setRowCount(0); // Clear existing data
        
        // Hanya tampilkan console yang sesuai dengan targetConsoleId
        for (ConsoleModel console : consoleList) {
            // Jika targetConsoleId > 0, hanya tampilkan console dengan ID tersebut
            // Jika targetConsoleId <= 0, tampilkan semua console (untuk backward compatibility)
            if (targetConsoleId <= 0 || console.getId() == targetConsoleId) {
                Object[] row = {
                    console.getId(),
                    console.getPaket(),
                    "Rp " + console.getHarga().toString(),
                    console.getStock()
                };
                tableModel.addRow(row);
            }
        }
        
        // Jika hanya ada satu console yang ditampilkan, otomatis pilih console tersebut
        if (tableModel.getRowCount() == 1) {
            jTable1.setRowSelectionInterval(0, 0);
            int consoleId = (Integer) tableModel.getValueAt(0, 0);
            selectConsoleById(consoleId);
        }
    }   
    
    private void selectConsoleById(int consoleId) {
        selectedConsole = pembayaranDAO.getConsoleById(consoleId);
        if (selectedConsole != null) {
            // Highlight selected row in table
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if ((Integer) tableModel.getValueAt(i, 0) == consoleId) {
                    jTable1.setRowSelectionInterval(i, i);
                    break;
                }
            }
        }
    }
    
     private void setupEventHandlers() {
        // Event handler untuk durasi pinjam
        jTextField5.addCaretListener(e -> calculateTotal());
        
        // Event handler untuk kode voucher
        jTextField6.addCaretListener(e -> calculateTotal());
        
        // Event handler untuk tombol confirm
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmPayment();
            }
        });
    }
    
     private void confirmPayment() {
        try {
            // Validasi input
            if (!validateInput()) {
                return;
            }
            
            // Buat object Pembayaran
            Pembayaran pembayaran = new Pembayaran();
            pembayaran.setKtp(jTextField2.getText().trim());
            pembayaran.setNamaPelanggan(jTextField3.getText().trim());
            pembayaran.setFkConsole(selectedConsole.getId());
            pembayaran.setLamaPeminjaman(Integer.parseInt(jTextField5.getText().trim()));
            pembayaran.setCatatan(jTextArea1.getText().trim());
            pembayaran.setKodeVoucher(jTextField6.getText().trim());
            pembayaran.setMetodePembayaran(jTextField4.getText().trim());
            
            // Hitung total harga
            int persenDiskon = pembayaranDAO.getDiskonByKode(pembayaran.getKodeVoucher());
            int totalHarga = pembayaranDAO.hitungTotalHarga(
                selectedConsole.getHarga(), 
                pembayaran.getLamaPeminjaman(), 
                persenDiskon
            );
            pembayaran.setTotalHarga(totalHarga);
            
            // Simpan ke database
            boolean success = pembayaranDAO.insertPembayaran(pembayaran);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Pembayaran berhasil disimpan!\n" +
                    "Total: Rp " + totalHarga + "\n" +
                    "ID Transaksi: " + pembayaran.getId(),
                    "Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Reset form
                resetForm();
                // Reload console data (untuk update stock)
                loadConsoleData();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Gagal menyimpan pembayaran!\nSilakan coba lagi.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Terjadi kesalahan: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextArea1.setText("");
        jTable1.clearSelection();
        selectedConsole = null;
    }
    /**
     * Hitung total harga berdasarkan console yang dipilih, durasi, dan voucher
     */
    private void calculateTotal() {
        if (selectedConsole == null) return;
        
        try {
            String durasiText = jTextField5.getText().trim();
            if (durasiText.isEmpty()) return;
            
            int durasi = Integer.parseInt(durasiText);
            if (durasi <= 0) return;
            
            String kodeVoucher = jTextField6.getText().trim();
            int persenDiskon = pembayaranDAO.getDiskonByKode(kodeVoucher);
            
            int totalHarga = pembayaranDAO.hitungTotalHarga(
                selectedConsole.getHarga(), durasi, persenDiskon
            );
            
            // Update label atau field untuk menampilkan total
            // Anda bisa menambahkan label untuk menampilkan total harga
            
        } catch (NumberFormatException e) {
            // Invalid duration format
        }
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

        label1.setText("label1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(520, 500));

        jPanel1.setMaximumSize(new java.awt.Dimension(800, 500));
        jPanel1.setMinimumSize(new java.awt.Dimension(800, 500));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("ID KTP                  :");
        jLabel1.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel1.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel1.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 100, -1));

        jLabel3.setText("Metode Pembayaran :");
        jLabel3.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel3.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel3.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 130, -1));

        jLabel5.setText("Nama                   :");
        jLabel5.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel5.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel5.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 100, -1));

        jLabel4.setText("Durasi Pinjam    :");
        jLabel4.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel4.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel4.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 100, -1));

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 78, 250, 30));
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 250, 30));
        jPanel1.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 230, 30));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 250, 60));

        jLabel6.setText("Catatan                :");
        jLabel6.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel6.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel6.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 100, -1));

        jLabel7.setText("Kode Voucher    :");
        jLabel7.setMaximumSize(new java.awt.Dimension(40, 20));
        jLabel7.setMinimumSize(new java.awt.Dimension(40, 20));
        jLabel7.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 100, -1));
        jPanel1.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 250, 30));
        jPanel1.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 250, 30));

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

        jButton1.setText("Confirm");
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 400, -1, -1));

        jLabel2.setText("Form Pembayaran");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, -1, -1));

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

    /**
     * @param args the command line arguments
     */
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
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
