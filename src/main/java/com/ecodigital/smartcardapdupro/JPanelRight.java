/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecodigital.smartcardapdupro;

import com.ecodigital.smartcardapdupro.apdu.ApduListResponseISO7816;
import com.ecodigital.smartcardapdupro.apdu.ApduStatusWord;
import com.ecodigital.smartcardapdupro.apdu.CardManager;
import com.ecodigital.smartcardapdupro.apdu.InstructionsApdu;
import com.ecodigital.smartcardapdupro.utils.HexUtils;
import com.ecodigital.smartcardapdupro.utils.UppercaseFilter;
import java.awt.BorderLayout;
import java.nio.ByteBuffer;
import java.util.List;
import javax.smartcardio.ATR;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.swing.text.AbstractDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 *
 * @author gluque
 */
public class JPanelRight extends javax.swing.JPanel {

    private final String lineSeparator = "\r\n";
    private RSyntaxTextArea textArea = new RSyntaxTextArea(20, 50);
    private List<InstructionsApdu> instructionsApdus;
    private ApduListResponseISO7816 iSO7816;
    private final InterfaceActionUI interfaceActionUI;
    
    /**
     * Creates new form JPanelRight
     */
    public JPanelRight(InterfaceActionUI interfaceActionUI) {
        initComponents();
        customUI();
        iSO7816 = new ApduListResponseISO7816();
        this.interfaceActionUI = interfaceActionUI;
    }

    private void customUI(){
        jPanelContentEditor.removeAll();
        jPanelContentEditor.revalidate();
        jPanelContentEditor.repaint();
        
        
        BorderLayout borderLayout = new BorderLayout();
        jPanelContentEditor.setLayout(borderLayout);
        
        textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        
        
        /*
        UppercaseFilter uppercaseFilter = new UppercaseFilter();
        ((AbstractDocument)textArea.getDocument()).setDocumentFilter(uppercaseFilter);
        */
        
        RTextScrollPane sp = new RTextScrollPane(textArea);
        jPanelContentEditor.add(sp, BorderLayout.CENTER);
    }    
    
    
    public void executeAPDU(List<InstructionsApdu> instructionsApdus) {
        for(InstructionsApdu ia : instructionsApdus) {
            switch(ia.getTokenApdu()) {
                case APDU:
                    executeApdu(CardManager.getInstance().getApdu(ia.getInstructions()));
                    break;
                case APDU_LC:
                    executeApdu(CardManager.getInstance().getApduLC(ia.getInstructions()));
                    break;
                case RESET:
                    executeReset();
                    break;
            }

        }
    }
    
    private void executeReset(){
        StringBuilder sb = new StringBuilder();
        sb.append("RESET:"+lineSeparator);
        sb.append("Received: ");
        try {
            ATR atr  = CardManager.getInstance().reset();    
            sb.append(HexUtils.hexify(atr.getBytes(), true)+lineSeparator);
        } catch(Exception e) {
            e.printStackTrace();
            sb.append("Error al ejecutar RESET:"+e.getMessage() +lineSeparator);
        }
        sb.append(lineSeparator);
        appendTextArea(sb);
    }
    
    private void executeApdu(byte[] cmd){
        StringBuilder sb = new StringBuilder();
        sb.append("Sending : "+HexUtils.hexify(cmd, true) +lineSeparator);
        sb.append("Received: ");
        try {
            ResponseAPDU res = CardManager.getInstance().executeApdu(new CommandAPDU(cmd));
            ApduStatusWord apduStatusWord = iSO7816.searchResponse(res);
            ByteBuffer buffer = ByteBuffer.allocate(res.getData().length + 2);
            buffer.put(res.getData());
            buffer.put((byte)res.getSW1());
            buffer.put((byte)res.getSW2());
            sb.append(HexUtils.hexify(buffer.array(), true, 10) +lineSeparator);
            if(apduStatusWord != null){
                sb.append(apduStatusWord.getDescription() + lineSeparator);
            } else {
                sb.append("No se encontro descripcion ISO."+lineSeparator);
            }
        } catch(Exception e) {
            e.printStackTrace();
            if(e.getMessage() != null)
                sb.append("Exception:"+e.getMessage() +lineSeparator);
            else
                sb.append("Exception:"+e.toString() +lineSeparator);
        }
        sb.append(lineSeparator);
        appendTextArea(sb);
    }
    
    
    private void appendTextArea(StringBuilder sb){
        textArea.append(sb.toString());
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelBottom = new javax.swing.JPanel();
        jPanelContentEditor = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanelBottomLayout = new javax.swing.GroupLayout(jPanelBottom);
        jPanelBottom.setLayout(jPanelBottomLayout);
        jPanelBottomLayout.setHorizontalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 457, Short.MAX_VALUE)
        );
        jPanelBottomLayout.setVerticalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelContentEditorLayout = new javax.swing.GroupLayout(jPanelContentEditor);
        jPanelContentEditor.setLayout(jPanelContentEditorLayout);
        jPanelContentEditorLayout.setHorizontalGroup(
            jPanelContentEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelContentEditorLayout.setVerticalGroup(
            jPanelContentEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 573, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelContentEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanelContentEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelContentEditor;
    // End of variables declaration//GEN-END:variables
}
