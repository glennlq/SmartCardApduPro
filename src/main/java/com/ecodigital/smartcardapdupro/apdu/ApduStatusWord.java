/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecodigital.smartcardapdupro.apdu;

import com.ecodigital.smartcardapdupro.utils.HexUtils;

/**
 *
 * @author gluque
 */
public class ApduStatusWord {
    public static final String neutre = "X";
    private int isw1;
    private int isw2;
    private String sw1;
    private String sw2;
    private TypeSW type;
    private ModeSW mode;
    private String description;
    private int countX;
    

    public ApduStatusWord(String sw1, String sw2, TypeSW type, String description) {
        this.sw1 = sw1.toUpperCase();
        this.sw2 = sw2.toUpperCase();
        this.mode = ModeSW.GENERIC;
        this.countX = countX();
        if(! sw1.contains(neutre) && ! sw2.contains(neutre)) {
            this.mode = ModeSW.UNIQUE;
            isw1 = HexUtils.HexStringToByte(sw1) & 0xFF; // sin signo
            isw2 = HexUtils.HexStringToByte(sw2) & 0xFF; // sin signo
            
        }
        this.type = type;
        this.description = description;
    }
    
    
    public static enum TypeSW{
        NOTHING,
        INFO,
        WARNING,
        ERROR,
        SECURITY
    }
    
    public static enum ModeSW {
        UNIQUE,
        GENERIC
    }
    
    private int countX(){
        String str = sw1+sw2;
        int lastIndex = 0;
        int count = 0;
        while(lastIndex != -1){
            lastIndex = str.indexOf(neutre,lastIndex);
            if(lastIndex != -1){
                count ++;
                lastIndex += neutre.length();
            }
        }
        return count;
    }
    

    public int getIsw1() {
        return isw1;
    }

    public int getIsw2() {
        return isw2;
    }

    public String getSw1() {
        return sw1;
    }

    public String getSw2() {
        return sw2;
    }

    public TypeSW getType() {
        return type;
    }

    public ModeSW getMode() {
        return mode;
    }

    public String getDescription() {
        return description;
    }

    public int getCountX() {
        return countX;
    }
    
    
    
}
