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
public enum TokenApdu {
    HEX, 
    APDU, 
    /*LC apdu unknow */
    APDU_LC,
    ERROR,
    RESET, 
    LENGTH_UNKNOW,
    TraditionalComment, 
    EndOfLineComment,  
    DocumentationComment
}
