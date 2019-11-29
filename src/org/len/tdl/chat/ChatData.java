/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.len.tdl.chat;

/**
 *
 * @author riyanto
 */
public class ChatData {
    
    /**
     * Class structure for storing a single chat entry in a full conversation.
     */
  public static class ChatEntry {
        public String name;
        public String content;
        public String date;
        public int type; // For type 0=sent, 1=received.
        public int status;
        public int nomor;

        public ChatEntry(String name, String content, String date, int type,  int status, int nomor) {
            this.name = name;
            this.content = content;
            this.type = type;
            this.date = date;
            this.status = status;
            this.nomor = nomor;
        }
    }
    
}
