/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecodigital.smartcardapdupro;

import com.ecodigital.smartcardapdupro.utils.UppercaseFilter;
import com.ecodigital.smartcardapdupro.utils.FormatterText;
import com.ecodigital.smartcardapdupro.apdu.Identificador;
import com.ecodigital.smartcardapdupro.apdu.InstructionsApdu;
import com.ecodigital.smartcardapdupro.jflex.Lexer;
import com.ecodigital.smartcardapdupro.apdu.TokenApdu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;

import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultEditorKit;
/**
 *
 * @author gluque
 */
public class JPanelLeft extends javax.swing.JPanel {

    private FormatterText formatterText;
    private List<InstructionsApdu> instructionsApdus;
    private HashMap<Integer, String> errors;
    private final InterfaceActionUI interfaceActionUI;
    /**
     * Creates new form JPanelLeft
     */
    private RSyntaxTextArea textArea = new RSyntaxTextArea(20, 20);
    
    
    public JPanelLeft(InterfaceActionUI interfaceActionUI) {
        initComponents();
        customUI();
        formatterText = new FormatterText();
        this.interfaceActionUI = interfaceActionUI;
    }
    
    public void cardTerminalDetected(boolean detected) {
        jButtonRunApdu.setEnabled(detected);
    }
    
    private void customUI(){
        jPanelContentEditor.removeAll();
        jPanelContentEditor.revalidate();
        jPanelContentEditor.repaint();


        BorderLayout borderLayout = new BorderLayout();
        jPanelContentEditor.setLayout(borderLayout);

        textArea = new RSyntaxTextArea(20, 20);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);



        // cursor color
        textArea.setCaretColor(Color.red);
        // fondo del texto cuando se selecciona
        textArea.setSelectionColor(Color.red);



        UppercaseFilter uppercaseFilter = new UppercaseFilter();
        ((AbstractDocument)textArea.getDocument()).setDocumentFilter(uppercaseFilter);        


        RTextScrollPane sp = new RTextScrollPane(textArea);
        jPanelContentEditor.add(sp, BorderLayout.CENTER);
        
        jButtonRunApdu.setEnabled(false);
    }
    
    
    private void runScanApdu() {
        jButtonRunApdu.setEnabled(false);
        try {
            preRunLexer();
            runLexer();
            textArea.requestFocusInWindow();
        } catch(Exception e){
            e.printStackTrace();
        }
        jButtonRunApdu.setEnabled(true);
    }
    
    private void preRunLexer() throws Exception{
        textArea.setText(formatterText.processData(textArea.getText()));
    }
    
    public  void runLexer() throws Exception {
        instructionsApdus       = new ArrayList<InstructionsApdu>();
        errors                  = new HashMap<Integer, String>();
        
        Reader reader = new StringReader(textArea.getText());
        Lexer lexer = new Lexer (reader);
        String result="";
        
        boolean finish = false;
        while ( ! finish) {
            TokenApdu token =lexer.yylex();
            String lexeme = lexer.yytext();
            if (token == null){
                //System.out.println("RESULTADO:"+result);
                finish = true;
                break;
            }
            
            switch (token){
                case RESET:
                    result=result+ "<RESET>";
                    instructionsApdus.add(new InstructionsApdu(token, lexeme));
                    System.out.println("indice reset"+lexer.zzCurrentPos);
                    break;
                case APDU:
                    result=result+ "<APDU>";
                    instructionsApdus.add(new InstructionsApdu(token, lexeme));
                    break;
                case APDU_LC:
                    result=result+ "<APDU_LC>";
                    instructionsApdus.add(new InstructionsApdu(token, lexeme));
                    break;
                case TraditionalComment:
                    result=result+ "<TraditionalComment>";
                    break;
                case EndOfLineComment:
                    result=result+ "<EndOfLineComment>";
                    break;
                case DocumentationComment:
                    result=result+ "<DocumentationComment>";
                    break;
                case ERROR:
                    result=result+ "<ERROR>";
                    errors.put(lexer.zzCurrentPos, lexeme);
                    break;
                default:
                    result=result+ "<"+ lexer.lexeme + "> ";
            }
        }
        
        if(errors.isEmpty()){
            interfaceActionUI.apduValids(instructionsApdus);
        } else {
            interfaceActionUI.apduErrorSemantic();
        }
    }
    
    
    private void checkWrapLines(){
        textArea.setLineWrap(jCheckBoxWrapLines.isSelected());
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonRunApdu = new javax.swing.JButton();
        jPanelContentEditor = new javax.swing.JPanel();
        jCheckBoxWrapLines = new javax.swing.JCheckBox();

        jButtonRunApdu.setText("Run");
        jButtonRunApdu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunApduActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelContentEditorLayout = new javax.swing.GroupLayout(jPanelContentEditor);
        jPanelContentEditor.setLayout(jPanelContentEditorLayout);
        jPanelContentEditorLayout.setHorizontalGroup(
            jPanelContentEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );
        jPanelContentEditorLayout.setVerticalGroup(
            jPanelContentEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 573, Short.MAX_VALUE)
        );

        jCheckBoxWrapLines.setText("Wrap Lines");
        jCheckBoxWrapLines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxWrapLinesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelContentEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRunApdu, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBoxWrapLines)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelContentEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxWrapLines)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRunApdu, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRunApduActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunApduActionPerformed
        runScanApdu();
    }//GEN-LAST:event_jButtonRunApduActionPerformed

    private void jCheckBoxWrapLinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxWrapLinesActionPerformed
        checkWrapLines();
    }//GEN-LAST:event_jCheckBoxWrapLinesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonRunApdu;
    private javax.swing.JCheckBox jCheckBoxWrapLines;
    private javax.swing.JPanel jPanelContentEditor;
    // End of variables declaration//GEN-END:variables
}
