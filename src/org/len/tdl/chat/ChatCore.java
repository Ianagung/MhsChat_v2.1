/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.len.tdl.chat;

import org.len.tdl.common.DataListener;
import org.len.tdl.common.StructHeader;
import org.len.tdl.common.StructText;
import org.len.tdl.common.DEF;
import org.len.tdl.udp.UdpRx;
import org.len.tdl.udp.UdpTx;

/**
 *
 * @author riyanto
 */
public class ChatCore {
    
    private static final UdpTx udpTransmit = new UdpTx();
    private final UdpRx udpReceive = new UdpRx();
 
    public void setRxSocket(int port, String ipAddress)
    {
        udpReceive.setSocket(port, ipAddress);
    }
    
    public void receiveStart()
    {
        
        udpReceive.receiveStart();
    }
    
    public void addListener(DataListener listener)
    {
        udpReceive.addListener(listener);
    }
    
    public static void setTxSocket(int port, String ipAddress) {
        udpTransmit.setSocket(port, ipAddress);
    }

    public static void sendChat(String sText, int Npu, int NpuDest, int priorityLevel) {
        // INPUT HEADER
        int source = Npu;
        int destination = NpuDest;
        int topic = DEF.TOPIC_CCD2TG;
        int typeMsg = DEF.TYPE_TX_MSG;
        int typeSubMsg = DEF.MSG_TYPE_CHAT;
        int priority = priorityLevel;
        StructHeader structHeader = new StructHeader();
        byte[] header = structHeader.getBytesHeader(source, destination, topic, typeMsg, typeSubMsg, priority);

        // INPUT Text
        String textString = sText;
        ChatState.chatCounter = (ChatState.chatCounter + 1) & 0xFFFF;
        int textNumber = ChatState.chatCounter;        
        StructText structText = new StructText();
        byte[] textBytes = structText.getBytesText(textString, textNumber);

        // COMBINE HEADER AND TEXT
        byte[] packet = new byte[header.length + textBytes.length];
        System.arraycopy(header, 0, packet, 0, header.length);
        System.arraycopy(textBytes, 0, packet, header.length, textBytes.length);

        // SEND VIA UDP
        udpTransmit.sendUdp(packet);
    }
    
    
    
}
