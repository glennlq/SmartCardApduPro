/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecodigital.smartcardapdupro.apdu;

import com.ecodigital.smartcardapdupro.apdu.ApduStatusWord.ModeSW;
import com.ecodigital.smartcardapdupro.utils.HexUtils;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.smartcardio.ResponseAPDU;

/**
 *
 * @author gluque
 */
public class ApduListResponseISO7816 {    
    private List<ApduStatusWord> listResponseAPDU;
    private List<ApduStatusWord> listUnique;
    private List<ApduStatusWord> listGeneric; // ordenados primero los que mas x tienen.

    public ApduListResponseISO7816() {
        loadISO7816();
        sortList();
    }
    
    
    private void sortList(){
        listUnique = new ArrayList<>();
        listGeneric = new ArrayList<>();
        for(ApduStatusWord asw : listResponseAPDU){
            if(asw.getMode() == ModeSW.UNIQUE) {
                listUnique.add(asw);
            } else {
                listGeneric.add(asw);
            }
        }
        Collections.sort(listGeneric, new SortApduStatusWord());
    }
    
    public ApduStatusWord searchResponse(ResponseAPDU responseAPDU) {
        for(ApduStatusWord asw : listUnique){
            if( responseAPDU.getSW1() == asw.getIsw1() && responseAPDU.getSW2() == asw.getIsw2()) {
                return asw;
            }
        }
        
        ApduStatusWord tmp = null;
        String str = HexUtils.hexify((byte)responseAPDU.getSW1()) + HexUtils.hexify((byte)responseAPDU.getSW2());
        for(ApduStatusWord asw : listGeneric) {
            String str2 = asw.getSw1() + asw.getSw2();
            boolean found = true;
            for(int i=0; i < 2; i++){
                if(str2.charAt(i) != ApduStatusWord.neutre.charAt(0) && str2.charAt(i) != str.charAt(i) ){
                    found = false;
                }
            }
            if(found)
                tmp = asw;

        }
        
        return tmp;
    }
    
    
    class SortApduStatusWord implements Comparator<ApduStatusWord> {
        @Override
        public int compare(ApduStatusWord o1, ApduStatusWord o2) {
            if(o1 != null && o2 != null){
                if (o1.getCountX() > o2.getCountX()) {
                    return -1;
                } else {
                    return 1;
                }
            }
            return 0;
        }
    }

    
    private void loadISO7816() {
        listResponseAPDU = new ArrayList<>();
        //listResponseAPDU.add(new ApduStatusWord("06","",ApduStatusWord.TypeSW.ERROR,"Class not supported."));
        listResponseAPDU.add(new ApduStatusWord("06","XX",ApduStatusWord.TypeSW.ERROR,"Class not supported."));
        listResponseAPDU.add(new ApduStatusWord("61","XX",ApduStatusWord.TypeSW.INFO,"Command successfully executed; 'XX' bytes of data are available and can be requested using GET RESPONSE."));
        listResponseAPDU.add(new ApduStatusWord("62","00",ApduStatusWord.TypeSW.WARNING,"No information given (NV-Ram not changed)"));
        listResponseAPDU.add(new ApduStatusWord("62","01",ApduStatusWord.TypeSW.WARNING,"NV-Ram not changed 1."));
        listResponseAPDU.add(new ApduStatusWord("62","81",ApduStatusWord.TypeSW.WARNING,"Part of returned data may be corrupted"));
        listResponseAPDU.add(new ApduStatusWord("62","82",ApduStatusWord.TypeSW.WARNING,"End of file/record reached before reading Le bytes"));
        listResponseAPDU.add(new ApduStatusWord("62","83",ApduStatusWord.TypeSW.WARNING,"Selected file invalidated"));
        listResponseAPDU.add(new ApduStatusWord("62","84",ApduStatusWord.TypeSW.WARNING,"Selected file is not valid. FCI not formated according to ISO"));
        listResponseAPDU.add(new ApduStatusWord("62","85",ApduStatusWord.TypeSW.WARNING,"No input data available from a sensor on the card. No Purse Engine enslaved for R3bc"));
        listResponseAPDU.add(new ApduStatusWord("62","A2",ApduStatusWord.TypeSW.WARNING,"Wrong R-MAC"));
        listResponseAPDU.add(new ApduStatusWord("62","A4",ApduStatusWord.TypeSW.WARNING,"Card locked (during reset( ))"));
        listResponseAPDU.add(new ApduStatusWord("62","CX",ApduStatusWord.TypeSW.WARNING,"Counter with value x (command dependent)"));
        listResponseAPDU.add(new ApduStatusWord("62","F1",ApduStatusWord.TypeSW.WARNING,"Wrong C-MAC"));
        listResponseAPDU.add(new ApduStatusWord("62","F3",ApduStatusWord.TypeSW.WARNING,"Internal reset"));
        listResponseAPDU.add(new ApduStatusWord("62","F5",ApduStatusWord.TypeSW.WARNING,"Default agent locked"));
        listResponseAPDU.add(new ApduStatusWord("62","F7",ApduStatusWord.TypeSW.WARNING,"Cardholder locked"));
        listResponseAPDU.add(new ApduStatusWord("62","F8",ApduStatusWord.TypeSW.WARNING,"Basement is current agent"));
        listResponseAPDU.add(new ApduStatusWord("62","F9",ApduStatusWord.TypeSW.WARNING,"CALC Key Set not unblocked"));
        listResponseAPDU.add(new ApduStatusWord("62","FX",ApduStatusWord.TypeSW.WARNING,"-"));
        listResponseAPDU.add(new ApduStatusWord("62","XX",ApduStatusWord.TypeSW.WARNING,"RFU"));
        listResponseAPDU.add(new ApduStatusWord("63","00",ApduStatusWord.TypeSW.WARNING,"No information given (NV-Ram changed)"));
        listResponseAPDU.add(new ApduStatusWord("63","81",ApduStatusWord.TypeSW.WARNING,"File filled up by the last write. Loading/updating is not allowed."));
        listResponseAPDU.add(new ApduStatusWord("63","82",ApduStatusWord.TypeSW.WARNING,"Card key not supported."));
        listResponseAPDU.add(new ApduStatusWord("63","83",ApduStatusWord.TypeSW.WARNING,"Reader key not supported."));
        listResponseAPDU.add(new ApduStatusWord("63","84",ApduStatusWord.TypeSW.WARNING,"Plaintext transmission not supported."));
        listResponseAPDU.add(new ApduStatusWord("63","85",ApduStatusWord.TypeSW.WARNING,"Secured transmission not supported."));
        listResponseAPDU.add(new ApduStatusWord("63","86",ApduStatusWord.TypeSW.WARNING,"Volatile memory is not available."));
        listResponseAPDU.add(new ApduStatusWord("63","87",ApduStatusWord.TypeSW.WARNING,"Non-volatile memory is not available."));
        listResponseAPDU.add(new ApduStatusWord("63","88",ApduStatusWord.TypeSW.WARNING,"Key number not valid."));
        listResponseAPDU.add(new ApduStatusWord("63","89",ApduStatusWord.TypeSW.WARNING,"Key length is not correct."));
        listResponseAPDU.add(new ApduStatusWord("63","C0",ApduStatusWord.TypeSW.WARNING,"Verify fail, no try left."));
        listResponseAPDU.add(new ApduStatusWord("63","C1",ApduStatusWord.TypeSW.WARNING,"Verify fail, 1 try left."));
        listResponseAPDU.add(new ApduStatusWord("63","C2",ApduStatusWord.TypeSW.WARNING,"Verify fail, 2 tries left."));
        listResponseAPDU.add(new ApduStatusWord("63","C3",ApduStatusWord.TypeSW.WARNING,"Verify fail, 3 tries left."));
        listResponseAPDU.add(new ApduStatusWord("63","CX",ApduStatusWord.TypeSW.WARNING,"The counter has reached the value 'x' (0 = x = 15) (command dependent)."));
        listResponseAPDU.add(new ApduStatusWord("63","F1",ApduStatusWord.TypeSW.WARNING,"More data expected."));
        listResponseAPDU.add(new ApduStatusWord("63","F2",ApduStatusWord.TypeSW.WARNING,"More data expected and proactive command pending."));
        listResponseAPDU.add(new ApduStatusWord("63","FX",ApduStatusWord.TypeSW.WARNING,"-"));
        listResponseAPDU.add(new ApduStatusWord("63","XX",ApduStatusWord.TypeSW.WARNING,"RFU"));
        listResponseAPDU.add(new ApduStatusWord("64","00",ApduStatusWord.TypeSW.ERROR,"No information given (NV-Ram not changed)"));
        listResponseAPDU.add(new ApduStatusWord("64","01",ApduStatusWord.TypeSW.ERROR,"Command timeout. Immediate response required by the card."));
        listResponseAPDU.add(new ApduStatusWord("64","XX",ApduStatusWord.TypeSW.ERROR,"RFU"));
        listResponseAPDU.add(new ApduStatusWord("65","00",ApduStatusWord.TypeSW.ERROR,"No information given"));
        listResponseAPDU.add(new ApduStatusWord("65","01",ApduStatusWord.TypeSW.ERROR,"Write error. Memory failure. There have been problems in writing or reading the EEPROM. Other hardware problems may also bring this error."));
        listResponseAPDU.add(new ApduStatusWord("65","81",ApduStatusWord.TypeSW.ERROR,"Memory failure"));
        listResponseAPDU.add(new ApduStatusWord("65","FX",ApduStatusWord.TypeSW.ERROR,"-"));
        listResponseAPDU.add(new ApduStatusWord("65","XX",ApduStatusWord.TypeSW.ERROR,"RFU"));
        listResponseAPDU.add(new ApduStatusWord("66","00",ApduStatusWord.TypeSW.SECURITY,"Error while receiving (timeout)"));
        listResponseAPDU.add(new ApduStatusWord("66","01",ApduStatusWord.TypeSW.SECURITY,"Error while receiving (character parity error)"));
        listResponseAPDU.add(new ApduStatusWord("66","02",ApduStatusWord.TypeSW.SECURITY,"Wrong checksum"));
        listResponseAPDU.add(new ApduStatusWord("66","03",ApduStatusWord.TypeSW.SECURITY,"The current DF file without FCI"));
        listResponseAPDU.add(new ApduStatusWord("66","04",ApduStatusWord.TypeSW.SECURITY,"No SF or KF under the current DF"));
        listResponseAPDU.add(new ApduStatusWord("66","69",ApduStatusWord.TypeSW.SECURITY,"Incorrect Encryption/Decryption Padding"));
        listResponseAPDU.add(new ApduStatusWord("66","XX",ApduStatusWord.TypeSW.SECURITY,"-"));
        listResponseAPDU.add(new ApduStatusWord("67","00",ApduStatusWord.TypeSW.ERROR,"Wrong length"));
        listResponseAPDU.add(new ApduStatusWord("67","XX",ApduStatusWord.TypeSW.ERROR,"length incorrect (procedure)(ISO 7816-3)"));
        listResponseAPDU.add(new ApduStatusWord("68","00",ApduStatusWord.TypeSW.ERROR,"No information given (The request function is not supported by the card)"));
        listResponseAPDU.add(new ApduStatusWord("68","81",ApduStatusWord.TypeSW.ERROR,"Logical channel not supported"));
        listResponseAPDU.add(new ApduStatusWord("68","82",ApduStatusWord.TypeSW.ERROR,"Secure messaging not supported"));
        listResponseAPDU.add(new ApduStatusWord("68","83",ApduStatusWord.TypeSW.ERROR,"Last command of the chain expected"));
        listResponseAPDU.add(new ApduStatusWord("68","84",ApduStatusWord.TypeSW.ERROR,"Command chaining not supported"));
        listResponseAPDU.add(new ApduStatusWord("68","FX",ApduStatusWord.TypeSW.ERROR,"-"));
        listResponseAPDU.add(new ApduStatusWord("68","XX",ApduStatusWord.TypeSW.ERROR,"RFU"));
        listResponseAPDU.add(new ApduStatusWord("69","00",ApduStatusWord.TypeSW.ERROR,"No information given (Command not allowed)"));
        listResponseAPDU.add(new ApduStatusWord("69","01",ApduStatusWord.TypeSW.ERROR,"Command not accepted (inactive state)"));
        listResponseAPDU.add(new ApduStatusWord("69","81",ApduStatusWord.TypeSW.ERROR,"Command incompatible with file structure"));
        listResponseAPDU.add(new ApduStatusWord("69","82",ApduStatusWord.TypeSW.ERROR,"Security condition not satisfied."));
        listResponseAPDU.add(new ApduStatusWord("69","83",ApduStatusWord.TypeSW.ERROR,"Authentication method blocked"));
        listResponseAPDU.add(new ApduStatusWord("69","84",ApduStatusWord.TypeSW.ERROR,"Referenced data reversibly blocked (invalidated)"));
        listResponseAPDU.add(new ApduStatusWord("69","85",ApduStatusWord.TypeSW.ERROR,"Conditions of use not satisfied."));
        listResponseAPDU.add(new ApduStatusWord("69","86",ApduStatusWord.TypeSW.ERROR,"Command not allowed (no current EF)"));
        listResponseAPDU.add(new ApduStatusWord("69","87",ApduStatusWord.TypeSW.ERROR,"Expected secure messaging (SM) object missing"));
        listResponseAPDU.add(new ApduStatusWord("69","88",ApduStatusWord.TypeSW.ERROR,"Incorrect secure messaging (SM) data object"));
        listResponseAPDU.add(new ApduStatusWord("69","8D",ApduStatusWord.TypeSW.NOTHING ,"Reserved"));
        listResponseAPDU.add(new ApduStatusWord("69","96",ApduStatusWord.TypeSW.ERROR,"Data must be updated again"));
        listResponseAPDU.add(new ApduStatusWord("69","E1",ApduStatusWord.TypeSW.ERROR,"POL1 of the currently Enabled Profile prevents this action."));
        listResponseAPDU.add(new ApduStatusWord("69","F0",ApduStatusWord.TypeSW.ERROR,"Permission Denied"));
        listResponseAPDU.add(new ApduStatusWord("69","F1",ApduStatusWord.TypeSW.ERROR,"Permission Denied - Missing Privilege"));
        listResponseAPDU.add(new ApduStatusWord("69","FX",ApduStatusWord.TypeSW.ERROR,"-"));
        listResponseAPDU.add(new ApduStatusWord("69","XX",ApduStatusWord.TypeSW.ERROR,"RFU"));
        listResponseAPDU.add(new ApduStatusWord("6A","00",ApduStatusWord.TypeSW.ERROR,"No information given (Bytes P1 and/or P2 are incorrect)"));
        listResponseAPDU.add(new ApduStatusWord("6A","80",ApduStatusWord.TypeSW.ERROR,"The parameters in the data field are incorrect."));
        listResponseAPDU.add(new ApduStatusWord("6A","81",ApduStatusWord.TypeSW.ERROR,"Function not supported"));
        listResponseAPDU.add(new ApduStatusWord("6A","82",ApduStatusWord.TypeSW.ERROR,"File not found"));
        listResponseAPDU.add(new ApduStatusWord("6A","83",ApduStatusWord.TypeSW.ERROR,"Record not found"));
        listResponseAPDU.add(new ApduStatusWord("6A","84",ApduStatusWord.TypeSW.ERROR,"There is insufficient memory space in record or file"));
        listResponseAPDU.add(new ApduStatusWord("6A","85",ApduStatusWord.TypeSW.ERROR,"Lc inconsistent with TLV structure"));
        listResponseAPDU.add(new ApduStatusWord("6A","86",ApduStatusWord.TypeSW.ERROR,"Incorrect P1 or P2 parameter."));
        listResponseAPDU.add(new ApduStatusWord("6A","87",ApduStatusWord.TypeSW.ERROR,"Lc inconsistent with P1-P2"));
        listResponseAPDU.add(new ApduStatusWord("6A","88",ApduStatusWord.TypeSW.ERROR,"Referenced data not found"));
        listResponseAPDU.add(new ApduStatusWord("6A","89",ApduStatusWord.TypeSW.ERROR,"File already exists"));
        listResponseAPDU.add(new ApduStatusWord("6A","8A",ApduStatusWord.TypeSW.ERROR,"DF name already exists."));
        listResponseAPDU.add(new ApduStatusWord("6A","F0",ApduStatusWord.TypeSW.ERROR,"Wrong parameter value"));
        listResponseAPDU.add(new ApduStatusWord("6A","FX",ApduStatusWord.TypeSW.ERROR,"-"));
        listResponseAPDU.add(new ApduStatusWord("6A","XX",ApduStatusWord.TypeSW.ERROR,"RFU"));
        listResponseAPDU.add(new ApduStatusWord("6B","00",ApduStatusWord.TypeSW.ERROR,"Wrong parameter(s) P1-P2"));
        listResponseAPDU.add(new ApduStatusWord("6B","XX",ApduStatusWord.TypeSW.ERROR,"Reference incorrect (procedure byte), (ISO 7816-3)"));
        listResponseAPDU.add(new ApduStatusWord("6C","00",ApduStatusWord.TypeSW.ERROR,"Incorrect P3 length."));
        listResponseAPDU.add(new ApduStatusWord("6C","XX",ApduStatusWord.TypeSW.ERROR,"Bad length value in Le; 'xx' is the correct exact Le"));
        listResponseAPDU.add(new ApduStatusWord("6D","00",ApduStatusWord.TypeSW.ERROR,"Instruction code not supported or invalid"));
        listResponseAPDU.add(new ApduStatusWord("6D","XX",ApduStatusWord.TypeSW.ERROR,"Instruction code not programmed or invalid (procedure byte), (ISO 7816-3)"));
        listResponseAPDU.add(new ApduStatusWord("6E","00",ApduStatusWord.TypeSW.ERROR,"Class not supported"));
        listResponseAPDU.add(new ApduStatusWord("6E","XX",ApduStatusWord.TypeSW.ERROR,"Instruction class not supported (procedure byte), (ISO 7816-3)"));
        listResponseAPDU.add(new ApduStatusWord("6F","00",ApduStatusWord.TypeSW.ERROR,"Command aborted - more exact diagnosis not possible (e.g., operating system error)."));
        listResponseAPDU.add(new ApduStatusWord("6F","FF",ApduStatusWord.TypeSW.ERROR,"Card dead (overuse, …)"));
        listResponseAPDU.add(new ApduStatusWord("6F","XX",ApduStatusWord.TypeSW.ERROR,"No precise diagnosis (procedure byte), (ISO 7816-3)"));
        listResponseAPDU.add(new ApduStatusWord("90","00",ApduStatusWord.TypeSW.INFO,"Command successfully executed (OK)."));
        listResponseAPDU.add(new ApduStatusWord("90","04",ApduStatusWord.TypeSW.WARNING,"PIN not succesfully verified, 3 or more PIN tries left"));
        listResponseAPDU.add(new ApduStatusWord("90","08",ApduStatusWord.TypeSW.NOTHING,"Key/file not found"));
        listResponseAPDU.add(new ApduStatusWord("90","80",ApduStatusWord.TypeSW.WARNING,"Unblock Try Counter has reached zero"));
        listResponseAPDU.add(new ApduStatusWord("91","00",ApduStatusWord.TypeSW.NOTHING,"OK"));
        listResponseAPDU.add(new ApduStatusWord("91","01",ApduStatusWord.TypeSW.NOTHING ,"States.activity, States.lock Status or States.lockable has wrong value"));
        listResponseAPDU.add(new ApduStatusWord("91","02",ApduStatusWord.TypeSW.NOTHING ,"Transaction number reached its limit"));
        listResponseAPDU.add(new ApduStatusWord("91","0C",ApduStatusWord.TypeSW.NOTHING ,"No changes"));
        listResponseAPDU.add(new ApduStatusWord("91","0E",ApduStatusWord.TypeSW.NOTHING ,"Insufficient NV-Memory to complete command"));
        listResponseAPDU.add(new ApduStatusWord("91","1C",ApduStatusWord.TypeSW.NOTHING ,"Command code not supported"));
        listResponseAPDU.add(new ApduStatusWord("91","1E",ApduStatusWord.TypeSW.NOTHING ,"CRC or MAC does not match data"));
        listResponseAPDU.add(new ApduStatusWord("91","40",ApduStatusWord.TypeSW.NOTHING ,"Invalid key number specified"));
        listResponseAPDU.add(new ApduStatusWord("91","7E",ApduStatusWord.TypeSW.NOTHING ,"Length of command string invalid"));
        listResponseAPDU.add(new ApduStatusWord("91","9D",ApduStatusWord.TypeSW.NOTHING ,"Not allow the requested command"));
        listResponseAPDU.add(new ApduStatusWord("91","9E",ApduStatusWord.TypeSW.NOTHING ,"Value of the parameter invalid"));
        listResponseAPDU.add(new ApduStatusWord("91","A0",ApduStatusWord.TypeSW.NOTHING ,"Requested AID not present on PICC"));
        listResponseAPDU.add(new ApduStatusWord("91","A1",ApduStatusWord.TypeSW.NOTHING ,"Unrecoverable error within application"));
        listResponseAPDU.add(new ApduStatusWord("91","AE",ApduStatusWord.TypeSW.NOTHING ,"Authentication status does not allow the requested command"));
        listResponseAPDU.add(new ApduStatusWord("91","AF",ApduStatusWord.TypeSW.NOTHING ,"Additional data frame is expected to be sent"));
        listResponseAPDU.add(new ApduStatusWord("91","BE",ApduStatusWord.TypeSW.NOTHING ,"Out of boundary"));
        listResponseAPDU.add(new ApduStatusWord("91","C1",ApduStatusWord.TypeSW.NOTHING ,"Unrecoverable error within PICC"));
        listResponseAPDU.add(new ApduStatusWord("91","CA",ApduStatusWord.TypeSW.NOTHING ,"Previous Command was not fully completed"));
        listResponseAPDU.add(new ApduStatusWord("91","CD",ApduStatusWord.TypeSW.NOTHING ,"PICC was disabled by an unrecoverable error"));
        listResponseAPDU.add(new ApduStatusWord("91","CE",ApduStatusWord.TypeSW.NOTHING ,"Number of Applications limited to 28"));
        listResponseAPDU.add(new ApduStatusWord("91","DE",ApduStatusWord.TypeSW.NOTHING ,"File or application already exists"));
        listResponseAPDU.add(new ApduStatusWord("91","EE",ApduStatusWord.TypeSW.NOTHING ,"Could not complete NV-write operation due to loss of power"));
        listResponseAPDU.add(new ApduStatusWord("91","F0",ApduStatusWord.TypeSW.NOTHING ,"Specified file number does not exist"));
        listResponseAPDU.add(new ApduStatusWord("91","F1",ApduStatusWord.TypeSW.NOTHING ,"Unrecoverable error within file"));
        listResponseAPDU.add(new ApduStatusWord("92","0x",ApduStatusWord.TypeSW.INFO,"Writing to EEPROM successful after 'x' attempts."));
        listResponseAPDU.add(new ApduStatusWord("92","10",ApduStatusWord.TypeSW.ERROR,"Insufficient memory. No more storage available."));
        listResponseAPDU.add(new ApduStatusWord("92","40",ApduStatusWord.TypeSW.ERROR,"Writing to EEPROM not successful."));
        listResponseAPDU.add(new ApduStatusWord("93","01",ApduStatusWord.TypeSW.NOTHING ,"Integrity error"));
        listResponseAPDU.add(new ApduStatusWord("93","02",ApduStatusWord.TypeSW.NOTHING ,"Candidate S2 invalid"));
        listResponseAPDU.add(new ApduStatusWord("93","03",ApduStatusWord.TypeSW.ERROR,"Application is permanently locked"));
        listResponseAPDU.add(new ApduStatusWord("94","00",ApduStatusWord.TypeSW.ERROR,"No EF selected."));
        listResponseAPDU.add(new ApduStatusWord("94","01",ApduStatusWord.TypeSW.NOTHING ,"Candidate currency code does not match purse currency"));
        listResponseAPDU.add(new ApduStatusWord("94","02",ApduStatusWord.TypeSW.NOTHING ,"Candidate amount too high"));
        listResponseAPDU.add(new ApduStatusWord("94","02",ApduStatusWord.TypeSW.ERROR,"Address range exceeded."));
        listResponseAPDU.add(new ApduStatusWord("94","03",ApduStatusWord.TypeSW.NOTHING ,"Candidate amount too low"));
        listResponseAPDU.add(new ApduStatusWord("94","04",ApduStatusWord.TypeSW.ERROR,"FID not found, record not found or comparison pattern not found."));
        listResponseAPDU.add(new ApduStatusWord("94","05",ApduStatusWord.TypeSW.NOTHING ,"Problems in the data field"));
        listResponseAPDU.add(new ApduStatusWord("94","06",ApduStatusWord.TypeSW.ERROR,"Required MAC unavailable"));
        listResponseAPDU.add(new ApduStatusWord("94","07",ApduStatusWord.TypeSW.NOTHING ,"Bad currency : purse engine has no slot with R3bc currency"));
        listResponseAPDU.add(new ApduStatusWord("94","08",ApduStatusWord.TypeSW.NOTHING ,"R3bc currency not supported in purse engine"));
        listResponseAPDU.add(new ApduStatusWord("94","08",ApduStatusWord.TypeSW.ERROR,"Selected file type does not match command."));
        listResponseAPDU.add(new ApduStatusWord("95","80",ApduStatusWord.TypeSW.NOTHING ,"Bad sequence"));
        listResponseAPDU.add(new ApduStatusWord("96","81",ApduStatusWord.TypeSW.NOTHING ,"Slave not found"));
        listResponseAPDU.add(new ApduStatusWord("97","00",ApduStatusWord.TypeSW.NOTHING ,"PIN blocked and Unblock Try Counter is 1 or 2"));
        listResponseAPDU.add(new ApduStatusWord("97","02",ApduStatusWord.TypeSW.NOTHING ,"Main keys are blocked"));
        listResponseAPDU.add(new ApduStatusWord("97","04",ApduStatusWord.TypeSW.NOTHING ,"PIN not succesfully verified, 3 or more PIN tries left"));
        listResponseAPDU.add(new ApduStatusWord("97","84",ApduStatusWord.TypeSW.NOTHING ,"Base key"));
        listResponseAPDU.add(new ApduStatusWord("97","85",ApduStatusWord.TypeSW.NOTHING ,"Limit exceeded - C-MAC key"));
        listResponseAPDU.add(new ApduStatusWord("97","86",ApduStatusWord.TypeSW.NOTHING ,"SM error - Limit exceeded - R-MAC key"));
        listResponseAPDU.add(new ApduStatusWord("97","87",ApduStatusWord.TypeSW.NOTHING ,"Limit exceeded - sequence counter"));
        listResponseAPDU.add(new ApduStatusWord("97","88",ApduStatusWord.TypeSW.NOTHING ,"Limit exceeded - R-MAC length"));
        listResponseAPDU.add(new ApduStatusWord("97","89",ApduStatusWord.TypeSW.NOTHING ,"Service not available"));
        listResponseAPDU.add(new ApduStatusWord("98","02",ApduStatusWord.TypeSW.ERROR,"No PIN defined."));
        listResponseAPDU.add(new ApduStatusWord("98","04",ApduStatusWord.TypeSW.ERROR,"Access conditions not satisfied, authentication failed."));
        listResponseAPDU.add(new ApduStatusWord("98","35",ApduStatusWord.TypeSW.ERROR,"ASK RANDOM or GIVE RANDOM not executed."));
        listResponseAPDU.add(new ApduStatusWord("98","40",ApduStatusWord.TypeSW.ERROR,"PIN verification not successful."));
        listResponseAPDU.add(new ApduStatusWord("98","50",ApduStatusWord.TypeSW.ERROR,"INCREASE or DECREASE could not be executed because a limit has been reached."));
        listResponseAPDU.add(new ApduStatusWord("98","62",ApduStatusWord.TypeSW.ERROR,"Authentication Error, application specific (incorrect MAC)"));
        listResponseAPDU.add(new ApduStatusWord("99","00",ApduStatusWord.TypeSW.NOTHING ,"1 PIN try left"));
        listResponseAPDU.add(new ApduStatusWord("99","04",ApduStatusWord.TypeSW.NOTHING ,"PIN not succesfully verified, 1 PIN try left"));
        listResponseAPDU.add(new ApduStatusWord("99","85",ApduStatusWord.TypeSW.NOTHING ,"Wrong status - Cardholder lock"));
        listResponseAPDU.add(new ApduStatusWord("99","86",ApduStatusWord.TypeSW.ERROR,"Missing privilege"));
        listResponseAPDU.add(new ApduStatusWord("99","87",ApduStatusWord.TypeSW.NOTHING ,"PIN is not installed"));
        listResponseAPDU.add(new ApduStatusWord("99","88",ApduStatusWord.TypeSW.NOTHING ,"Wrong status - R-MAC state"));
        listResponseAPDU.add(new ApduStatusWord("9A","00",ApduStatusWord.TypeSW.NOTHING ,"2 PIN try left"));
        listResponseAPDU.add(new ApduStatusWord("9A","04",ApduStatusWord.TypeSW.NOTHING ,"PIN not succesfully verified, 2 PIN try left"));
        listResponseAPDU.add(new ApduStatusWord("9A","71",ApduStatusWord.TypeSW.NOTHING ,"Wrong parameter value - Double agent AID"));
        listResponseAPDU.add(new ApduStatusWord("9A","72",ApduStatusWord.TypeSW.NOTHING ,"Wrong parameter value - Double agent Type"));
        listResponseAPDU.add(new ApduStatusWord("9D","05",ApduStatusWord.TypeSW.ERROR,"Incorrect certificate type"));
        listResponseAPDU.add(new ApduStatusWord("9D","07",ApduStatusWord.TypeSW.ERROR,"Incorrect session data size"));
        listResponseAPDU.add(new ApduStatusWord("9D","08",ApduStatusWord.TypeSW.ERROR,"Incorrect DIR file record size"));
        listResponseAPDU.add(new ApduStatusWord("9D","09",ApduStatusWord.TypeSW.ERROR,"Incorrect FCI record size"));
        listResponseAPDU.add(new ApduStatusWord("9D","0A",ApduStatusWord.TypeSW.ERROR,"Incorrect code size"));
        listResponseAPDU.add(new ApduStatusWord("9D","10",ApduStatusWord.TypeSW.ERROR,"Insufficient memory to load application"));
        listResponseAPDU.add(new ApduStatusWord("9D","11",ApduStatusWord.TypeSW.ERROR,"Invalid AID"));
        listResponseAPDU.add(new ApduStatusWord("9D","12",ApduStatusWord.TypeSW.ERROR,"Duplicate AID"));
        listResponseAPDU.add(new ApduStatusWord("9D","13",ApduStatusWord.TypeSW.ERROR,"Application previously loaded"));
        listResponseAPDU.add(new ApduStatusWord("9D","14",ApduStatusWord.TypeSW.ERROR,"Application history list full"));
        listResponseAPDU.add(new ApduStatusWord("9D","15",ApduStatusWord.TypeSW.ERROR,"Application not open"));
        listResponseAPDU.add(new ApduStatusWord("9D","17",ApduStatusWord.TypeSW.ERROR,"Invalid offset"));
        listResponseAPDU.add(new ApduStatusWord("9D","18",ApduStatusWord.TypeSW.ERROR,"Application already loaded"));
        listResponseAPDU.add(new ApduStatusWord("9D","19",ApduStatusWord.TypeSW.ERROR,"Invalid certificate"));
        listResponseAPDU.add(new ApduStatusWord("9D","1A",ApduStatusWord.TypeSW.ERROR,"Invalid signature"));
        listResponseAPDU.add(new ApduStatusWord("9D","1B",ApduStatusWord.TypeSW.ERROR,"Invalid KTU"));
        listResponseAPDU.add(new ApduStatusWord("9D","1D",ApduStatusWord.TypeSW.ERROR,"MSM controls not set"));
        listResponseAPDU.add(new ApduStatusWord("9D","1E",ApduStatusWord.TypeSW.ERROR,"Application signature does not exist"));
        listResponseAPDU.add(new ApduStatusWord("9D","1F",ApduStatusWord.TypeSW.ERROR,"KTU does not exist"));
        listResponseAPDU.add(new ApduStatusWord("9D","20",ApduStatusWord.TypeSW.ERROR,"Application not loaded"));
        listResponseAPDU.add(new ApduStatusWord("9D","21",ApduStatusWord.TypeSW.ERROR,"Invalid Open command data length"));
        listResponseAPDU.add(new ApduStatusWord("9D","30",ApduStatusWord.TypeSW.ERROR,"Check data parameter is incorrect (invalid start address)"));
        listResponseAPDU.add(new ApduStatusWord("9D","31",ApduStatusWord.TypeSW.ERROR,"Check data parameter is incorrect (invalid length)"));
        listResponseAPDU.add(new ApduStatusWord("9D","32",ApduStatusWord.TypeSW.ERROR,"Check data parameter is incorrect (illegal memory check area)"));
        listResponseAPDU.add(new ApduStatusWord("9D","40",ApduStatusWord.TypeSW.ERROR,"Invalid MSM Controls ciphertext"));
        listResponseAPDU.add(new ApduStatusWord("9D","41",ApduStatusWord.TypeSW.ERROR,"MSM controls already set"));
        listResponseAPDU.add(new ApduStatusWord("9D","42",ApduStatusWord.TypeSW.ERROR,"Set MSM Controls data length less than 2 bytes"));
        listResponseAPDU.add(new ApduStatusWord("9D","43",ApduStatusWord.TypeSW.ERROR,"Invalid MSM Controls data length"));
        listResponseAPDU.add(new ApduStatusWord("9D","44",ApduStatusWord.TypeSW.ERROR,"Excess MSM Controls ciphertext"));
        listResponseAPDU.add(new ApduStatusWord("9D","45",ApduStatusWord.TypeSW.ERROR,"Verification of MSM Controls data failed"));
        listResponseAPDU.add(new ApduStatusWord("9D","50",ApduStatusWord.TypeSW.ERROR,"Invalid MCD Issuer production ID"));
        listResponseAPDU.add(new ApduStatusWord("9D","51",ApduStatusWord.TypeSW.ERROR,"Invalid MCD Issuer ID"));
        listResponseAPDU.add(new ApduStatusWord("9D","52",ApduStatusWord.TypeSW.ERROR,"Invalid set MSM controls data date"));
        listResponseAPDU.add(new ApduStatusWord("9D","53",ApduStatusWord.TypeSW.ERROR,"Invalid MCD number"));
        listResponseAPDU.add(new ApduStatusWord("9D","54",ApduStatusWord.TypeSW.ERROR,"Reserved field error"));
        listResponseAPDU.add(new ApduStatusWord("9D","55",ApduStatusWord.TypeSW.ERROR,"Reserved field error"));
        listResponseAPDU.add(new ApduStatusWord("9D","56",ApduStatusWord.TypeSW.ERROR,"Reserved field error"));
        listResponseAPDU.add(new ApduStatusWord("9D","57",ApduStatusWord.TypeSW.ERROR,"Reserved field error"));
        listResponseAPDU.add(new ApduStatusWord("9D","60",ApduStatusWord.TypeSW.ERROR,"MAC verification failed"));
        listResponseAPDU.add(new ApduStatusWord("9D","61",ApduStatusWord.TypeSW.ERROR,"Maximum number of unblocks reached"));
        listResponseAPDU.add(new ApduStatusWord("9D","62",ApduStatusWord.TypeSW.ERROR,"Card was not blocked"));
        listResponseAPDU.add(new ApduStatusWord("9D","63",ApduStatusWord.TypeSW.ERROR,"Crypto functions not available"));
        listResponseAPDU.add(new ApduStatusWord("9D","64",ApduStatusWord.TypeSW.ERROR,"No application loaded"));
        listResponseAPDU.add(new ApduStatusWord("9E","00",ApduStatusWord.TypeSW.NOTHING ,"PIN not installed"));
        listResponseAPDU.add(new ApduStatusWord("9E","04",ApduStatusWord.TypeSW.NOTHING ,"PIN not succesfully verified, PIN not installed"));
        listResponseAPDU.add(new ApduStatusWord("9F","00",ApduStatusWord.TypeSW.NOTHING ,"PIN blocked and Unblock Try Counter is 3"));
        listResponseAPDU.add(new ApduStatusWord("9F","04",ApduStatusWord.TypeSW.NOTHING ,"PIN not succesfully verified, PIN blocked and Unblock Try Counter is 3"));
        listResponseAPDU.add(new ApduStatusWord("9F","XX",ApduStatusWord.TypeSW.NOTHING ,"Command successfully executed; 'xx' bytes of data are available and can be requested using GET RESPONSE."));
        listResponseAPDU.add(new ApduStatusWord("9x","XX",ApduStatusWord.TypeSW.NOTHING ,"Application related status, (ISO 7816-3)"));


        
    }
}
