/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecodigital.smartcardapdupro;

import com.ecodigital.smartcardapdupro.apdu.InstructionsApdu;
import java.util.List;
import javax.smartcardio.CardTerminal;

/**
 *
 * @author gluque
 */
public interface InterfaceActionUI {
    
    public void apduValids(final List<InstructionsApdu> instructionsApdus);
    
    public void apduErrorSemantic();
    
    public void addRemoveCardReader(final List<CardTerminal> terminalAvailable);
    
    public void cardErrorProtocol();
    
    public void cardReady();
    
    public void cardNoAvailable();
}
