/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.len.tdl.chat;

import java.util.LinkedList;
import java.util.List;
import org.len.tdl.chat.ChatData.ChatEntry;

/**
 *
 * @author riyanto
 */
public class ChatLogger {
    
    public List<ChatEntry> logChatContentList = new LinkedList<>();
    public void changeStatus(int nomor, int status)
    {
        int n_chat = logChatContentList.size();
        for (int i = 0; i < n_chat; i++)
        {
            ChatEntry chat = logChatContentList.get(i);
            if ( (chat.nomor == nomor) && (chat.type == ChatType.OUTGOING) )
            { 
                chat.status = status;
                logChatContentList.set(i, chat);
                break;
            }
        }
    }
}
