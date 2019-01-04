/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecodigital.smartcardapdupro.apdu;

import com.ecodigital.smartcardapdupro.InterfaceActionUI;
import com.ecodigital.smartcardapdupro.utils.HexUtils;
import java.util.ArrayList;
import java.util.List;
import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.swing.SwingUtilities;

/**
 *
 * @author gluque
 */
public class CardManager {
    public static final String[] protocols = new String[]{"*", "T=0", "T=1", "T=CL"};
    private volatile String protocol;
    private volatile CardTerminal terminal      = null;
    private volatile Card card                  = null;
    private volatile CardChannel cardChannel    = null;
    private volatile boolean isCardPresent      = false;
    private static CardManager _instance        = null;
    private ScanCard scanCard                   = null;
    private ScanCardReader scanCardReader       = null;
    
    private InterfaceActionUI actionUI          = null;
    
    
    public static CardManager getInstance(){
        if(_instance == null) {
            _instance = new CardManager();
        }
        return _instance;
    }
    
    private CardManager() {        
        
    }

    public void setActionUI(InterfaceActionUI actionUI) {
        this.actionUI = actionUI;
    }
    
    
    
    private void openChannel() throws CardException {
        card = terminal.connect(protocol);
        cardChannel = card.getBasicChannel();
        
    }
    
    private void closeChannel() throws CardException {
        card.disconnect(true);
    }
    
    
    public ATR reset() throws CardException, Exception {
        if(isCardPresent) {
            card.disconnect(true);
            card = terminal.connect(protocol);
            cardChannel = card.getBasicChannel();
            return card.getATR();
        } else {
            throw new Exception("Card is not present");
        }
    }
    
    
    public byte[] getApdu(String command) {
        command = command.trim();
        if(command.contains("??")){
            return getApduLC(command);
        } else {
            command = command.trim().replaceAll(" ","");
            //System.out.println(command);
            System.out.println(HexUtils.hexify(HexUtils.hexStringToByteArray(command), true) );
            //System.out.println("-------");
            return HexUtils.hexStringToByteArray(command);
        }
    }
    
    public byte[] getApduLC(String command){
        command = command.trim();
        String[] com = command.split(" ");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < com.length ; i++){
            if(com[i].equals("??")){
                sb.append(HexUtils.hexify( (byte)(com.length - i - 1 & 0xFF) ));
            } else {
                sb.append(com[i]);
            }
        }
        return HexUtils.hexStringToByteArray(sb.toString());
    }
    
    
    public ResponseAPDU executeApdu(CommandAPDU commandAPDU) throws CardException, Exception {
        if(isCardPresent) {
            return cardChannel.transmit(commandAPDU);
        } else {
            throw new Exception("Card is not present");
        }
    }
    
    /***************************    SMARTCARD   *********************************/
    
    public void startScanCard(final CardTerminal ter, final String proto) {
        System.out.println(ter.getName());
        System.out.println(proto);
        this.terminal = ter;
        this.protocol = proto;
        if(scanCard == null) {
            scanCard = new ScanCard();
            scanCard.start();
        } else {
            scanCard.changeInput = true;
        }
    }
    
    public void stopScanCard(){
        if(scanCard != null){
            scanCard.stopScanCard = true;
            scanCard.interrupt();
            scanCard = null;
        }
    }
    
    class ScanCard extends Thread {
        public boolean changeInput = false;
        
        private boolean stopScanCard = false;
        private boolean deviceVerified = false;
        private boolean errorProtocol = false;
        private int retryMax = 0; // 4 retry 
        private int time = 400;
        @Override
        public void run() {
            while( !stopScanCard ){
                try {
                    if(changeInput) {
                        isCardPresent = deviceVerified =changeInput = false;
                        closeChannel();
                    }
                    
                    if(terminal.isCardPresent()) {
                        if( ! isCardPresent && ! deviceVerified) {
                            try {
                                openChannel();
                                cardReady();
                            } catch(Exception e){
                                e.printStackTrace();
                                retryMax++;
                                if(retryMax > 4) {
                                    cardErrorProtocol();
                                    deviceVerified = true;
                                }
                            }
                        }
                    } else {
                        deviceVerified = false;
                        if(isCardPresent || errorProtocol) {
                            cardNoAvailable();
                        }
                    }
                } catch(Exception e) {
                    if(isCardPresent) {
                        cardNoAvailable();
                    }
                }

                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                }
            }

            if(isCardPresent) {
                cardNoAvailable();
            }
        }
        
        
        private void cardReady() {
            isCardPresent = true;
            retryMax = 0;
            notifySwingUI();
        }
        
        private void cardNoAvailable() {
            errorProtocol = false;
            isCardPresent = false;
            deviceVerified = false;
            retryMax = 0;
            notifySwingUI();
        }
        
        private void cardErrorProtocol() {
            errorProtocol = true;
            isCardPresent = false;
            deviceVerified = false;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println("cardErrorProtocol()");
                    actionUI.cardErrorProtocol();
                }
            });
        }
        
        private void notifySwingUI(){
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    if(isCardPresent){
                        actionUI.cardReady();
                        System.out.println("Siiii presente");
                    } else {
                        actionUI.cardNoAvailable();
                        System.out.println("Nooo presente");
                    }
                }
            });
        }
    }
    
    
    /***************************    CARD  READER  *********************************/
    public void startScanCardReader() {
        if(scanCardReader == null){
            scanCardReader = new ScanCardReader();
            scanCardReader.start();
        }
    }
    
    public void stopScanCardReader() {
        scanCardReader.stop = true;
    }
    
    class ScanCardReader extends Thread {
    
        public volatile boolean stop = false;
        private List<CardTerminal> terminalAvailable = new ArrayList<>();
        private final int timeSleep = 400;


        @Override
        public void run() {
            while( ! stop && actionUI != null){
                List<CardTerminal> tmp = getListTerminals();
                if(tmp.size() != terminalAvailable.size()) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            actionUI.addRemoveCardReader(tmp);
                        }
                    });
                }
                terminalAvailable = tmp;
                sleep();
            }
        }


        private List<CardTerminal> getListTerminals() {
            try {
                TerminalFactory factory = TerminalFactory.getDefault();
                return factory.terminals().list();
            } catch( Exception e){
                return new ArrayList<CardTerminal>();
            }
        }

        private void sleep() {
            try {
                Thread.sleep(timeSleep);
            } catch(Exception e){
            }
        }

    }
    
}
