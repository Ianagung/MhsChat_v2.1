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
public class ChatConfig {
    
    public static String udpFromTgAddress = "localhost";
    public static int udpFromTgPort = 9006;
    public static String udpToTgAddress = "localhost";
    public static int udpToTgPort = 9007; 

    public static int ownNpu = 5;
    public static int destinationNpu = 0;
    
    public static String sNpuList = "2,3";
    public static String sGroupList = "1,1";    
    public static String sNameList = "AHP,Makasar";
    
    public static int npuCount = 5;
    public static int[] npuList = new int[256];
    public static int[] groupList = new int[256];
    public static String[] nameList = new String[256];
    
    public static int getGroup(int npu)
    {
        return groupList[npu];
    }
}
