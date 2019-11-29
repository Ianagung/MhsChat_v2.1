/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.len.tdl.chat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.len.tdl.chat.ChatData.ChatEntry;
import org.len.tdl.common.DataListener;
import org.len.tdl.common.StructAck;
import org.len.tdl.common.StructHeader;
import org.len.tdl.common.StructText;
import org.len.tdl.common.DEF;

/**
 *
 * @author riyanto
 */
public class ChatMain {
    
    
    private final ChatCore coreChat = new ChatCore();
    private final UdpDataReceive udpDataReceive = new UdpDataReceive();
    private final File configFile = new File("chat_config.properties"); 
    private final File stateFile = new File("db/chat_state.properties");
    
    private ChatLogger[] logsChat;
    private ChatLogger logChat;
    private boolean receiveChatFlag = false;
    private final int[] indexToDestination = new int[256];
    private final int[] destinationToIndex = new int[256];
    private String selfName;
    private List<String> destNames = new ArrayList<>();
    
    public void initChat()
    {
        loadProperties();
        loadStateVariables();
        loadLoggerChat();
        
        coreChat.addListener(udpDataReceive);
        coreChat.setRxSocket(ChatConfig.udpFromTgPort, ChatConfig.udpFromTgAddress);
        coreChat.receiveStart();
    }
    
    public String getSelfName()
    {
        return selfName;
    }
    
    public List<String> getDestinationNames()
    {
        return destNames;
    }
    
    public void setDestination(int idx)
    {
        ChatConfig.destinationNpu = indexToDestination[idx];
        logChat = logsChat[idx];
    }
    
    public List<ChatEntry> getChatContentList()
    {
        return logChat.logChatContentList;
    }
    
    public void sendChat(String sChat)
    {
        ChatCore.setTxSocket(ChatConfig.udpToTgPort, ChatConfig.udpToTgAddress);
        ChatCore.sendChat(sChat, ChatConfig.ownNpu, ChatConfig.destinationNpu, 1);        
        ChatEntry chatEntry = new ChatEntry(selfName, sChat, getDateTimeNow(), ChatType.OUTGOING,  ChatStatus.ONPROGRES, ChatState.chatCounter);
        logChat.logChatContentList.add(chatEntry); 
    }
    
    public boolean isReceiveChat()
    {
        boolean flag = receiveChatFlag;
        if (receiveChatFlag)
            receiveChatFlag = false;        
        return flag;
    }
    
    public void closeChat()
    {
        saveStateVariables();
        saveHistoryChat();
    }
    
    private void loadLoggerChat()
    {
        int n_dest = ChatConfig.npuCount - 1;
        logsChat = new ChatLogger[n_dest];
        for (int i = 0; i < n_dest; i++)
        {
            logsChat[i] = new ChatLogger();
        }
        logChat = logsChat[0]; 
        
        loadHistoryChat();
    }
    
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); 
    private String getDateTimeNow()
    {
        LocalDateTime now = LocalDateTime.now();  
        return DTF.format(now);  
    }   
    
    private void loadStateVariables() {
        try (
            InputStream inputStream = new FileInputStream(stateFile)) {
            Properties configProps = new Properties();
            configProps.load(inputStream);
            inputStream.close();
            ChatState.chatCounter = Integer.parseInt(configProps.getProperty("CHAT_COUNTER")); 
        }catch (IOException ex) {
            System.out.println("The chat_state.properties file does not exist");
        }
    }
    
    private void saveStateVariables()
    {
        Properties configProps = new Properties();
        configProps.setProperty("CHAT_COUNTER", Integer.toString(ChatState.chatCounter));

        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(stateFile);
            configProps.store(outputStream, "chat state");
            outputStream.close();
        } catch (IOException ex) {
            System.out.println("Save file chat_state.properties fail");
        }
 
    }
    
    private void loadProperties() {
        Properties configProps = new Properties();
        // sets default properties        
        configProps.setProperty("UDP_FROM_TG_ADDR", ChatConfig.udpFromTgAddress);
        configProps.setProperty("UDP_FROM_TG_PORT", Integer.toString(ChatConfig.udpFromTgPort)); 
        configProps.setProperty("UDP_TO_TG_ADDR", ChatConfig.udpToTgAddress);
        configProps.setProperty("UDP_TO_TG_PORT", Integer.toString(ChatConfig.udpToTgPort));     
        configProps.setProperty("OWN_NPU", Integer.toString(ChatConfig.ownNpu));
        configProps.setProperty("NPU_LIST", ChatConfig.sNpuList);
        configProps.setProperty("GROUP_LIST", ChatConfig.sGroupList);
        configProps.setProperty("NAME_LIST", ChatConfig.sNameList);    
        
        try ( // loads properties from file
            InputStream inputStream = new FileInputStream(configFile)) {
            configProps.load(inputStream);
            inputStream.close();
        }catch (IOException ex) {
            System.out.println("The chat_config.properties file does not exist");
        }
        
        // set to configuration variable        
        ChatConfig.udpFromTgAddress = configProps.getProperty("UDP_FROM_TG_ADDR"); 
        ChatConfig.udpFromTgPort = Integer.parseInt(configProps.getProperty("UDP_FROM_TG_PORT"));
        ChatConfig.udpToTgAddress = configProps.getProperty("UDP_TO_TG_ADDR"); 
        ChatConfig.udpToTgPort = Integer.parseInt(configProps.getProperty("UDP_TO_TG_PORT"));
        ChatConfig.ownNpu = Integer.parseInt(configProps.getProperty("OWN_NPU"));   
        ChatConfig.sNpuList = configProps.getProperty("NPU_LIST");
        ChatConfig.sGroupList = configProps.getProperty("GROUP_LIST");
        ChatConfig.sNameList = configProps.getProperty("NAME_LIST");      
        
        // SET ARRAY
        String[] npu_list = ChatConfig.sNpuList.split(",");
        String[] group_list = ChatConfig.sGroupList.split(",");    
        String[] name_list = ChatConfig.sNameList.split(",");
        for (int i = 0; i < npu_list.length; i++) {
            int idx = Integer.parseInt(npu_list[i]);
            ChatConfig.npuList[i] = Integer.parseInt(npu_list[i]);
            ChatConfig.groupList[idx] = Integer.parseInt(group_list[i]);
            ChatConfig.nameList[idx] = name_list[i];
        }      
        ChatConfig.npuCount =  npu_list.length;  
        
        // UPDATE
        int own_id = ChatConfig.ownNpu;
        selfName = "ID-" + own_id + " (" + ChatConfig.nameList[own_id]  + ")";

        int n_npu = ChatConfig.npuCount;
        int npu_dest = 0;
        int j = 0;
        for (int i = 0; i < n_npu; i++)
        {
            int npu_id = ChatConfig.npuList[i];
            if ( npu_id != own_id)
            {
                npu_dest = npu_id;
                destNames.add("ID-" + npu_id + " (" + ChatConfig.nameList[npu_id]  + ")");
                indexToDestination[j] = npu_dest;
                destinationToIndex[npu_dest] = j;
                j++;
            }
        }           
        ChatConfig.destinationNpu = npu_dest;
    }
    
    //=========================== UDP RECEIVE FROM TG ==========================
    
    private class UdpDataReceive implements DataListener 
    {
        @Override
        public void dataReceive(byte[] aData) 
        {
            byte[] header = new byte[DEF.API_HEADER_LENGTH];
            byte[] packet = new byte[aData.length - DEF.API_HEADER_LENGTH];
            System.arraycopy(aData, 0, header, 0, DEF.API_HEADER_LENGTH);
            System.arraycopy(aData, DEF.API_HEADER_LENGTH, packet, 0, packet.length);
            StructHeader sHeader = new StructHeader(header);

            if ((sHeader.isValid() == true) && (sHeader.getTopic() == DEF.TOPIC_TG2CCD)) 
            {
                int packetType = sHeader.getType_msg();
                int subType = sHeader.getType_sub_msg();
                int source = sHeader.getSource();   
                
                if (packetType == DEF.TYPE_RX_MSG)
                {
                    switch (subType)
                    {
                        case DEF.MSG_TYPE_CHAT:
                            StructText sText = new StructText(packet); 
                            
                            String sSource = "ID-" + source + " (" + ChatConfig.nameList[source]  + ")";                                     
                            ChatEntry chatEntry = new ChatEntry(sSource, sText.getStext(), getDateTimeNow(), ChatType.INCOMING,  ChatStatus.RECEIVE, sText.getTextNumber());
                            int idx = destinationToIndex[source];
                            logsChat[idx].logChatContentList.add(chatEntry);  
                            receiveChatFlag = true;
                            break;
                                        
                        case DEF.MSG_TYPE_ACK:
                            StructAck sAck = new StructAck(packet);
                            int nomor = sAck.getObjNumber();
                            int valid = sAck.getAck();
                            int chatStatusRx = valid == 1 ? ChatStatus.DELIVERED : ChatStatus.FAIL;
                            
                            idx = destinationToIndex[source];                            
                            logsChat[idx].changeStatus(nomor, chatStatusRx);
                            receiveChatFlag = true;                   
                            break;
                    }     
                } 
            }
        }
    }    
    
    private void loadHistoryChat()
    {
        int n_user = ChatConfig.npuCount - 1;
        for (int i = 0; i < n_user; i++)
        {        
            try 
            {
                String filename = "db/history/ID" + indexToDestination[i] + "_CHAT_HISTORY.txt";
                String[] ss = new String(Files.readAllBytes(Paths.get(filename))).split(System.lineSeparator());   
                int n_chat = ss.length / 6;
                int k = 0;
                for (int j = 0; j < n_chat; j++) 
                {                
                    String name = ss[k++];
                    String content = ss[k++];
                    String date = ss[k++];
                    int type = Integer.valueOf(ss[k++]);
                    int status = Integer.valueOf(ss[k++]);
                    int nomor = Integer.valueOf(ss[k++]);
                    logsChat[i].logChatContentList.add(new ChatEntry(name, content, date, type,  status, nomor));  
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }    
    
    private void saveHistoryChat()
    {
        int n_user = ChatConfig.npuCount - 1;     
        for (int i = 0; i < n_user; i++)
        {        
            StringBuilder str = new StringBuilder();
            int n_chat = logsChat[i].logChatContentList.size();
            for (int j = 0; j < n_chat; j++)
            {
                ChatEntry chat = logsChat[i].logChatContentList.get(j);
                str.append(chat.name);
                str.append(System.lineSeparator());
                str.append(chat.content);
                str.append(System.lineSeparator());
                str.append(chat.date);
                str.append(System.lineSeparator());
                str.append(chat.type);
                str.append(System.lineSeparator());
                str.append(chat.status);
                str.append(System.lineSeparator());
                str.append(chat.nomor);
                str.append(System.lineSeparator());
            }

            try {
                String path = "db/history/ID" + indexToDestination[i] + "_CHAT_HISTORY.txt";
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                writer.write(str.toString());
                writer.close();
            } catch (IOException ex) {
                System.out.println("SAVE HISTORY FAIL");
            }
        }
        
    }  
    
}
