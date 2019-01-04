/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecodigital.smartcardapdupro;

import com.ecodigital.smartcardapdupro.apdu.CardManager;
import com.ecodigital.smartcardapdupro.apdu.InstructionsApdu;
import com.ecodigital.smartcardapdupro.utils.MyColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.smartcardio.CardTerminal;
import javax.swing.UIManager;


/**
 *
 * @author gluque
 */
public class MainJFrame extends javax.swing.JFrame implements InterfaceActionUI{

    
    private JPanelLeft jPanelLeft;
    private JPanelRight jPanelRight;
    private List<CardTerminal> terminalAvailable;
    private String selectedTerminal = "";
    private String selectedProtocol = "";
    private boolean verifyTerminal = false;
    
    /**
     * Creates new form MainJFrame
     */
    public MainJFrame() {
        initComponents();
        terminalAvailable = new ArrayList<>();
        CardManager.getInstance().setActionUI(this);
        disableComboBox();
        customUI();
        startScanCardReader();
    }
    
    /*
    private void hola() {
        this.getinpu
        Keymap firstNameMap = this.getKeymap();
         KeyStroke altF3 = KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_MASK);
         
    }*/
    
    private void startScanCardReader(){
        CardManager.getInstance().startScanCardReader();
    }
    
    private void customUI() {
        jPanelLeft = new JPanelLeft(this);
        jPanelRight = new JPanelRight(this);
        jSplitPane1.setLeftComponent(jPanelLeft);
        jSplitPane1.setRightComponent(jPanelRight);
        this.setTitle("SmartCard Apdu Pro");
        
        jComboBoxDevices.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                verifyCardTerminal();
            }
        });
        
        jComboBoxProtocols.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                verifyCardTerminal();
            }
        });
    }
    
    
    private void disableComboBox() {
        verifyTerminal = false;
        jComboBoxDevices.setEnabled(false);
        jComboBoxProtocols.setEnabled(false);
        jComboBoxDevices.removeAllItems();
        jComboBoxDevices.addItem("Smartcard reader no conected");
        jComboBoxProtocols.removeAllItems();
        jComboBoxProtocols.addItem("Protocol");
        selectedTerminal = null;
        selectedProtocol = null;
        verifyTerminal = true;
        CardManager.getInstance().stopScanCard();
    }
    
    private void clearComboBox() {
        verifyTerminal = false;
        jComboBoxDevices.setEnabled(true);
        jComboBoxProtocols.setEnabled(true);
        jComboBoxDevices.removeAllItems();
        jComboBoxProtocols.removeAllItems();
        verifyTerminal = true;
    }
    
    private void updateCardTerminals(final List<CardTerminal> terminals) {
        verifyTerminal = false;
        this.terminalAvailable = terminals;
        boolean forceVerify = false;
        if(terminalAvailable.size() > 0) {
            clearComboBox();
            boolean foundSelected = false;
            for(CardTerminal cardTerminal : terminalAvailable){
                jComboBoxDevices.addItem(cardTerminal.getName());
                if(cardTerminal.getName().equals(selectedTerminal)){
                    foundSelected = true;
                }
            }
            if(foundSelected) {
                jComboBoxDevices.setSelectedItem(selectedTerminal);
            } else {
                jComboBoxDevices.setSelectedIndex(0);
                forceVerify = true;
            }
            
            for(String protocol : CardManager.protocols){
                jComboBoxProtocols.addItem(protocol);
            }
            
            if(selectedProtocol == null) {
                jComboBoxProtocols.setSelectedIndex(0);
                forceVerify = true;
            } else {
                jComboBoxProtocols.setSelectedItem(selectedProtocol);
            }
            
            verifyTerminal = true;
            if(forceVerify)
                verifyCardTerminal();
        } else {
            disableComboBox();
        }
    }
    
    private void verifyCardTerminal() {
        if(verifyTerminal && terminalAvailable.size() > 0 ) {
            selectedTerminal = (String)jComboBoxDevices.getSelectedItem();
            int indexTmp = 0;
            for(int i = 0; i < terminalAvailable.size(); i++)
                if(selectedTerminal.equals(terminalAvailable.get(i).getName()))
                    indexTmp = i;
            
            selectedProtocol = (String)jComboBoxProtocols.getSelectedItem();
            CardManager.getInstance().startScanCard(terminalAvailable.get(indexTmp), selectedProtocol);
        } else {
            jPanelLeft.cardTerminalDetected(false);
            //cardNoAvailable();
        }
    }
    

    @Override
    public void apduValids(final List<InstructionsApdu> instructionsApdus) {
        jPanelRight.executeAPDU(instructionsApdus);
    }

    @Override
    public void apduErrorSemantic() {
        // Error en la semantica del APDU.
    }
    

    @Override
    public void addRemoveCardReader(final List<CardTerminal> terminalAvailable) {
        System.out.println("addRemoveCardReader");
        updateCardTerminals(terminalAvailable);
    }

    @Override
    public void cardReady() {
        System.out.println("cardReady()");
        jPanelLeft.cardTerminalDetected(true);
        jLabelMessage.setText("Ready");
        jLabelMessage.setForeground(MyColor.TEXT_SUCCESS);
    }

    @Override
    public void cardNoAvailable() {
        System.out.println("cardNoAvailable()");
        jPanelLeft.cardTerminalDetected(false);
        jLabelMessage.setText("");
    }

    @Override
    public void cardErrorProtocol() {
        System.out.println("cardErrorProtocol()");
        jPanelLeft.cardTerminalDetected(false);
        
        jLabelMessage.setText("Error protocol");
        jLabelMessage.setForeground(MyColor.TEXT_DANGER);
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jComboBoxDevices = new javax.swing.JComboBox<>();
        jComboBoxProtocols = new javax.swing.JComboBox<>();
        jLabelMessage = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setDividerLocation(280);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 672, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1138, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Devices"));

        jComboBoxDevices.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxProtocols.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelMessage.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBoxDevices, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBoxProtocols, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelMessage)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jComboBoxDevices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jComboBoxProtocols, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabelMessage))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        */
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }
    
    
    static {
        try {
            String nativeLF = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(nativeLF);
        } catch (Exception ex) {}
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBoxDevices;
    private javax.swing.JComboBox<String> jComboBoxProtocols;
    private javax.swing.JLabel jLabelMessage;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}
