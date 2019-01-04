/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecodigital.smartcardapdupro.apdu;

/**
 *
 * @author gluque
 */
public class InstructionsApdu {
    private TokenApdu tokenApdu;
    private String instructions;

    public InstructionsApdu(TokenApdu tokenApdu, String instructions) {
        this.tokenApdu = tokenApdu;
        this.instructions = instructions;
    }

    public TokenApdu getTokenApdu() {
        return tokenApdu;
    }

    public void setTokenApdu(TokenApdu tokenApdu) {
        this.tokenApdu = tokenApdu;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
    
}
