/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.len.tdl.common;

import org.len.tdl.tools.crc16_ccitt;

/**
 *
 * @author datalink1
 */
public class StructAck {

    private boolean valid;
    private int msgType = 0;
    private int objNumber = 0;
    private int ack;
    private final crc16_ccitt crc16 = new crc16_ccitt();

    public StructAck() {
    }

    public StructAck(byte[] data) {

        int data_length = data.length;
        byte[] crc = crc16.compute(data, 0, data_length - 2);

        if ((crc[0] == data[data_length - 2]) && (crc[1] == data[data_length - 1])) {

            msgType = (data[0] & 0xFF) * 256 + (data[1] & 0xFF);
            objNumber = (data[2] & 0xFF) * 256 + (data[3] & 0xFF);
            ack = (data[4] & 0xFF);

            valid = true;
            System.out.println("CRC ACK OKE");
        } else {
            valid = false;
            System.out.println("CRC ACK NOT OKE");
        }
    }

    public byte[] getBytesAck(int ack, int message_type, int object_number) {

        int pack_length = DEF.PACK_LENGTH;
        byte[] dataAck = new byte[pack_length];

        dataAck[0] = (byte) ((message_type >> 8) & 0xFF);
        dataAck[1] = (byte) (message_type & 0xFF);
        dataAck[2] = (byte) ((object_number >> 8) & 0xFF);
        dataAck[3] = (byte) (object_number & 0xFF);
        dataAck[4] = (byte) ack;
        byte[] crc = crc16.compute(dataAck, 0, pack_length - 2);
        dataAck[pack_length - 2] = crc[0];
        dataAck[pack_length - 1] = crc[1];

        return dataAck;
    }

    /**
     * @return the valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @return the msgType
     */
    public int getMsgType() {
        return msgType;
    }

    /**
     * @return the objNumber
     */
    public int getObjNumber() {
        return objNumber;
    }

    /**
     * @return the ack
     */
    public int getAck() {
        return ack;
    }

}
