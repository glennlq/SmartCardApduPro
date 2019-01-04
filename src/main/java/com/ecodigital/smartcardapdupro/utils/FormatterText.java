/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecodigital.smartcardapdupro.utils;

/**
 *
 * @author gluque
 */
public class FormatterText {
    
    private char lineTerminatorMacOs    = '\r';
    private char lineTerminatorUnix     = '\n';
    private String lineCorrect          = "\r\n";
    
    public String processData(String data) throws Exception {
        char currentLine = System.lineSeparator().charAt(0);
        StringBuilder stringBuilder = new StringBuilder();
        if(currentLine == lineTerminatorMacOs || currentLine == lineTerminatorUnix) {
            for(int i = 0; i < data.length(); i++) {
                char tmp = data.charAt(i);
                if(tmp == currentLine) {
                    if((i-1) >=0 && data.charAt(i-1) != ' '){
                        stringBuilder.append(' ');
                    }
                    stringBuilder.append(tmp);
                    /*
                    if(tmp == lineTerminatorMacOs){
                        if(i+1 < data.length() && data.charAt(i+1) != lineTerminatorUnix){
                            stringBuilder.append(lineCorrect);
                        } else {
                            stringBuilder.append(tmp);
                        }
                    } else if(tmp == lineTerminatorUnix){
                        if(data.charAt(i-1) != lineTerminatorMacOs){
                            stringBuilder.append(lineCorrect);
                        } else {
                            stringBuilder.append(tmp);
                        }
                    }*/
                } else {
                    stringBuilder.append(tmp);
                }
            }
            return stringBuilder.toString();
        } else {
            return data;
        }
    }
}
