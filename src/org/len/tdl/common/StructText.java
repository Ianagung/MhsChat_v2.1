/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.len.tdl.common;

import org.len.tdl.tools.crc16_ccitt;

/**
 *
 * @author datalink
 */
public class StructText {
    
    private boolean valid;
    private String stext;
    private int lengthText = 0;
    private int textNumber = 0;
    
    private final DEF DEF = new DEF();
    private final crc16_ccitt crc16 = new crc16_ccitt();
    
    
    public StructText() {}
    
    public StructText(byte[] data) {  
        
        int data_length = data.length;
        byte[] crc = crc16.compute(data, 0, data_length - 2); 
        
        if ( (crc[0] == data[data_length-2]) && (crc[1] == data[data_length-1])) {
            valid = true;
            textNumber = (data[0] & 0xFF) * 256 + (data[1] & 0xFF);
            lengthText = (data[2] & 0xFF) * 256 + (data[3] & 0xFF);      
            stext = new String(data,4, getLengthText());            
            System.out.println("CRC MSG_TEXT OKE");
        }
        else 
        {
            valid = false;
            System.out.println("CRC MSG_TEXT NOT OKE");
        }
    }
        
    public byte[] getBytesText(String s_text, int text_number) {        
        
        byte[] byte_text = s_text.getBytes();
        int text_length = byte_text.length;
        
        int dt_length = 6 + text_length;
        int pack_length = DEF.PACK_LENGTH * (dt_length / DEF.PACK_LENGTH);
        if ((dt_length % DEF.PACK_LENGTH) != 0) {
             pack_length = pack_length + DEF.PACK_LENGTH;
        }
        
        byte[] dataText = new byte[pack_length];
        
        dataText[0] = (byte) ( (text_number >> 8) & 0xFF);
        dataText[1] = (byte) ( text_number & 0xFF);
        dataText[2] = (byte) ( (text_length >> 8) & 0xFF);
        dataText[3] = (byte) ( text_length & 0xFF);
        
        System.arraycopy(byte_text, 0, dataText, 4, byte_text.length);
        byte[] crc = crc16.compute(dataText, 0, pack_length - 2);
        dataText[pack_length - 2] = crc[0];
        dataText[pack_length - 1] = crc[1];
        
        return dataText;
    }
    
    public boolean isValid() {
        return valid;
    }

    /**
     * @return the textNumber
     */
    public int getTextNumber() {
        return textNumber;
    }

    /**
     * @return the lengthText
     */
    public int getLengthText() {
        return lengthText;
    }

    /**
     * @return the stext
     */
    public String getStext() {
        return stext;
    }
    
    
    
}
