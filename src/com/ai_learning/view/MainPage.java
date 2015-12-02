/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ai_learning.view;

import com.ai_learning.App;
import java.awt.CardLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author natanelia
 */
public class MainPage extends javax.swing.JFrame {
    private static final int CARD_KNN = 0;
    private static final int CARD_NB = 1;
    private String method;
    private int activeCard = CARD_KNN;
    private PrintStream standardOut;
    /**
     * Creates new form MainPage
     */
    public MainPage() {
        initComponents();
    }
    
    public class JTextAreaOutputStream extends OutputStream
    {
        private final JTextArea destination;

        public JTextAreaOutputStream (JTextArea destination)
        {
            if (destination == null)
                throw new IllegalArgumentException ("Destination is null");

            this.destination = destination;
        }

        @Override
        public void write(byte[] buffer, int offset, int length) throws IOException
        {
            final String text = new String (buffer, offset, length);
            SwingUtilities.invokeLater(new Runnable ()
                {
                    @Override
                    public void run() 
                    {
                        destination.append (text);
                    }
                });
        }

        @Override
        public void write(int b) throws IOException
        {
            write (new byte [] {(byte)b}, 0, 1);
        }
    }
    
    private void printMatrix(JTextArea area, Integer[][] matrix, ArrayList<String> classes) {
        for (int i=0; i < classes.size(); i++) {
            area.append(classes.get(i) + "\t");
        }
        area.append("\n");
        for (int i = 0; i < classes.size(); i++) {
            for (int j = 0; j < classes.size(); j++) {
                area.append(matrix[i][j].toString() + "\t");
            }
            area.append(" > should be " + classes.get(i) + "\n");
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

        fcTrain = new javax.swing.JFileChooser();
        fcTest = new javax.swing.JFileChooser();
        fcSave = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        txtTrainFile = new javax.swing.JTextField();
        btnOpenFile = new javax.swing.JButton();
        cmbType = new javax.swing.JComboBox<>();
        btnSubmit = new javax.swing.JButton();
        txtTestFile = new javax.swing.JTextField();
        btnOpenTest = new javax.swing.JButton();
        spnTarget = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnNB = new javax.swing.JButton();
        btnKNN = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnClassifier = new javax.swing.JButton();
        card = new javax.swing.JPanel();
        panelKNN = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taKNN = new javax.swing.JTextArea();
        spnK = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        btnSaveKNN = new javax.swing.JButton();
        panelNB = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taNB = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnSaveNB = new javax.swing.JButton();

        fcSave.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        fcSave.setApproveButtonText("Save");
        fcSave.setDialogTitle("Save file to...");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WeKaWe");
        setPreferredSize(new java.awt.Dimension(640, 480));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(80, 80, 80));
        jPanel1.setPreferredSize(new java.awt.Dimension(628, 120));

        btnOpenFile.setText("Open...");
        btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenFileActionPerformed(evt);
            }
        });

        cmbType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Full Training", "10-Fold" }));
        cmbType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTypeActionPerformed(evt);
            }
        });

        btnSubmit.setBackground(new java.awt.Color(132, 201, 253));
        btnSubmit.setFont(new java.awt.Font("Ubuntu Condensed", 0, 15)); // NOI18N
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnOpenTest.setText("Open...");
        btnOpenTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenTestActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(254, 254, 254));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Train Data File:");

        jLabel3.setForeground(new java.awt.Color(254, 254, 254));
        jLabel3.setText("Method:");

        jPanel2.setBackground(new java.awt.Color(80, 80, 80));

        jLabel5.setBackground(new java.awt.Color(207, 207, 207));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ai_learning/view/logo-weka-small-1.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Ubuntu Medium", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(254, 207, 21));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ai_learning/view/We-KaWe_yellow.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
        );

        jLabel6.setForeground(new java.awt.Color(254, 254, 254));
        jLabel6.setText("Target Column:");

        jLabel7.setForeground(new java.awt.Color(254, 254, 254));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Test Data File:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(spnTarget, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbType, 0, 216, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTrainFile)
                            .addComponent(txtTestFile))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnOpenFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOpenTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSubmit)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtTrainFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnOpenFile))
                                        .addGap(6, 6, 6))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTestFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnOpenTest)
                                    .addComponent(jLabel7))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spnTarget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6)))
                            .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(105, 105, 105));
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 288));

        btnNB.setBackground(java.awt.Color.lightGray);
        btnNB.setText("NaiveBayes");
        btnNB.setBorder(null);
        btnNB.setFocusPainted(false);
        btnNB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNBActionPerformed(evt);
            }
        });

        btnKNN.setBackground(new java.awt.Color(254, 207, 21));
        btnKNN.setText("KNN");
        btnKNN.setBorder(null);
        btnKNN.setDoubleBuffered(true);
        btnKNN.setFocusPainted(false);
        btnKNN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKNNActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(254, 207, 21));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Algorithm");

        btnClassifier.setBackground(new java.awt.Color(255, 187, 0));
        btnClassifier.setText("Classifier");
        btnClassifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClassifierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKNN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNB, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(btnClassifier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKNN, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNB, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                .addComponent(btnClassifier)
                .addContainerGap())
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.LINE_START);

        card.setLayout(new java.awt.CardLayout());

        panelKNN.setBackground(new java.awt.Color(136, 136, 136));

        jLabel10.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(254, 207, 21));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Result:");

        taKNN.setBackground(new java.awt.Color(1, 1, 1));
        taKNN.setColumns(20);
        taKNN.setForeground(new java.awt.Color(254, 207, 21));
        taKNN.setRows(5);
        jScrollPane1.setViewportView(taKNN);

        jLabel1.setForeground(new java.awt.Color(254, 254, 254));
        jLabel1.setText("KNN with K =");

        btnSaveKNN.setText("Save...");
        btnSaveKNN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveKNNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelKNNLayout = new javax.swing.GroupLayout(panelKNN);
        panelKNN.setLayout(panelKNNLayout);
        panelKNNLayout.setHorizontalGroup(
            panelKNNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKNNLayout.createSequentialGroup()
                .addGroup(panelKNNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelKNNLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel1)
                        .addGap(4, 4, 4)
                        .addComponent(spnK, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelKNNLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelKNNLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSaveKNN, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelKNNLayout.setVerticalGroup(
            panelKNNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKNNLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelKNNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addGap(9, 9, 9)
                .addComponent(btnSaveKNN)
                .addContainerGap())
        );

        card.add(panelKNN, "panelKNN");

        panelNB.setBackground(new java.awt.Color(136, 136, 136));

        taNB.setBackground(new java.awt.Color(1, 1, 1));
        taNB.setColumns(20);
        taNB.setForeground(new java.awt.Color(254, 207, 21));
        taNB.setRows(5);
        jScrollPane3.setViewportView(taNB);

        jLabel9.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(254, 207, 21));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Result:");

        jLabel11.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(254, 207, 21));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Naive Bayes");

        btnSaveNB.setText("Save...");
        btnSaveNB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveNBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelNBLayout = new javax.swing.GroupLayout(panelNB);
        panelNB.setLayout(panelNBLayout);
        panelNBLayout.setHorizontalGroup(
            panelNBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNBLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelNBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                    .addGroup(panelNBLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNBLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSaveNB, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelNBLayout.setVerticalGroup(
            panelNBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNBLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelNBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveNB)
                .addContainerGap())
        );

        card.add(panelNB, "panelNB");

        getContentPane().add(card, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKNNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKNNActionPerformed
        ((CardLayout)card.getLayout()).show(card, "panelKNN");
        activeCard = CARD_KNN;
        btnKNN.setBackground(new Color(254, 207, 21));
        btnNB.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_btnKNNActionPerformed

    private void btnNBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNBActionPerformed
        ((CardLayout)card.getLayout()).show(card, "panelNB");
        activeCard = CARD_NB;
        btnNB.setBackground(new Color(254, 207, 21));
        btnKNN.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_btnNBActionPerformed

    private void btnOpenTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenTestActionPerformed
        int result = fcTest.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fcTest.getSelectedFile();
            txtTestFile.setText(selectedFile.getAbsolutePath());
            fcTrain.setCurrentDirectory(fcTest.getCurrentDirectory());
        }
    }//GEN-LAST:event_btnOpenTestActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        taKNN.setText("");
        taNB.setText("");
        String algorithm = (activeCard == CARD_KNN) ? "knn" : "nb";
        System.out.println("ALGORITHM = " + algorithm);
        String trainFile = txtTrainFile.getText();
        String testFile = txtTestFile.getText();
        int target = ((int) spnTarget.getValue());
        int k = (int) spnK.getValue();
        method = (String) cmbType.getSelectedItem();
        //PrintStream printStream = new PrintStream(new CustomOutputStream(taKNN));

          if(algorithm.equals("knn")){
            App app = new App();
            app.run(algorithm, trainFile, testFile, target, method, k);
            taKNN.append("Relation Name : " + app.getRelationName());
            taKNN.append("\nInstances : " + app.getInstances());
            taKNN.append("\nCorrect : " + app.getCorrect());
            taKNN.append("\n\n\nConfusion Matrix : \n\n");
            printMatrix(taKNN,app.getConfusionMatrix(),app.getTargetValues());
         
            
        }   
        else {
            App app = new App();
            System.out.println(algorithm);
            app.run(algorithm, trainFile, testFile, target, method, 0);
            taNB.append("Relation Name : " + app.getRelationName());
            taNB.append("\nInstances : " + app.getInstances());
            taNB.append("\nCorrect : " + app.getCorrect());
            taNB.append("\n\n\nConfusion Matrix : \n\n");
            printMatrix(taNB,app.getConfusionMatrix(),app.getTargetValues());
         
        }

        //standardOut = System.out;
        //System.setOut(printStream);
        //System.setErr(printStream);
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void cmbTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTypeActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cmbTypeActionPerformed

    private void btnOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenFileActionPerformed
        int result = fcTrain.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fcTrain.getSelectedFile();
            txtTrainFile.setText(selectedFile.getAbsolutePath());
            fcTest.setCurrentDirectory(fcTrain.getCurrentDirectory());
        }
    }//GEN-LAST:event_btnOpenFileActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        spnK.setValue(1);
        spnTarget.setValue(1);
    }//GEN-LAST:event_formWindowOpened

    private void btnClassifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClassifierActionPerformed
        MainPageApp mpa = new MainPageApp();
        mpa.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        mpa.setVisible(true);
    }//GEN-LAST:event_btnClassifierActionPerformed

    private void btnSaveKNNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveKNNActionPerformed
        if (fcSave.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = fcSave.getSelectedFile();
            
            PrintWriter writer;
            try {
                writer = new PrintWriter(saveFile);
                writer.print(taKNN.getText());
                writer.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSaveKNNActionPerformed

    private void btnSaveNBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveNBActionPerformed
        if (fcSave.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = fcSave.getSelectedFile();
            
            PrintWriter writer;
            try {
                writer = new PrintWriter(saveFile);
                writer.print(taNB.getText());
                writer.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSaveNBActionPerformed

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
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClassifier;
    private javax.swing.JButton btnKNN;
    private javax.swing.JButton btnNB;
    private javax.swing.JButton btnOpenFile;
    private javax.swing.JButton btnOpenTest;
    private javax.swing.JButton btnSaveKNN;
    private javax.swing.JButton btnSaveNB;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JPanel card;
    private javax.swing.JComboBox<String> cmbType;
    private javax.swing.JFileChooser fcSave;
    private javax.swing.JFileChooser fcTest;
    private javax.swing.JFileChooser fcTrain;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelKNN;
    private javax.swing.JPanel panelNB;
    private javax.swing.JSpinner spnK;
    private javax.swing.JSpinner spnTarget;
    private javax.swing.JTextArea taKNN;
    private javax.swing.JTextArea taNB;
    private javax.swing.JTextField txtTestFile;
    private javax.swing.JTextField txtTrainFile;
    // End of variables declaration//GEN-END:variables
}
