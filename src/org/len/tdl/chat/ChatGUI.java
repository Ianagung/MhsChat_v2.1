/* 
 * Copyright PT Len Industri (Persero) 

 *  
 * TO PT LEN INDUSTRI (PERSERO), AS APPLICABLE, AND SHALL NOT BE USED IN ANY WAY
 * OTHER THAN BEFOREHAND AGREED ON BY PT LEN INDUSTRI (PERSERO), NOR BE REPRODUCED
 * OR DISCLOSED TO THIRD PARTIES WITHOUT PRIOR WRITTEN AUTHORIZATION BY
 * PT LEN INDUSTRI (PERSERO), AS APPLICABLE
 */
package org.len.tdl.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 *
 * @author riyanto
 */
public class ChatGUI extends javax.swing.JFrame {

    private final ChatPanel chatPanel = new ChatPanel();
    private final ChatMain chatMain = new ChatMain();
    private boolean initDone = false;
    private boolean needToRefreshChat = false;
        
    /**
     * Creates new form MainChat
     */
    public ChatGUI() {
        initComponents();
        jPanelMessager.setLayout(new java.awt.BorderLayout());
        chatMain.initChat();  
        jLabelMyKapal.setText("My ID: " + chatMain.getSelfName());
        showDestinationList();
        timerRefreshChat.start();
        initDone = true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jComboBoxChatDestination = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabelInfo = new javax.swing.JLabel();
        jTextFieldMessage = new javax.swing.JTextField();
        jLabelMyKapal = new javax.swing.JLabel();
        jPanelMessager = new javax.swing.JPanel();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MHS Chat");
        setBackground(new java.awt.Color(102, 102, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jComboBoxChatDestination.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jComboBoxChatDestination.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kapal 5 (Makasar)", "Kapal 7 (AHP)" }));
        jComboBoxChatDestination.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxChatDestinationActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Chat with");

        jLabelInfo.setBackground(new java.awt.Color(51, 51, 51));
        jLabelInfo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelInfo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelInfo.setText(" ");
        jLabelInfo.setOpaque(true);

        jTextFieldMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMessageActionPerformed(evt);
            }
        });
        jTextFieldMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldMessageKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldMessageKeyReleased(evt);
            }
        });

        jLabelMyKapal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelMyKapal.setText("My ID: Kapal 5 (AHP)");

        jPanelMessager.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanelMessagerLayout = new javax.swing.GroupLayout(jPanelMessager);
        jPanelMessager.setLayout(jPanelMessagerLayout);
        jPanelMessagerLayout.setHorizontalGroup(
            jPanelMessagerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 495, Short.MAX_VALUE)
        );
        jPanelMessagerLayout.setVerticalGroup(
            jPanelMessagerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelMyKapal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxChatDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanelMessager, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTextFieldMessage, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jLabelInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxChatDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelMyKapal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelMessager, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void showDestinationList()
    {
        jComboBoxChatDestination.removeAllItems();
        List<String> destNames = chatMain.getDestinationNames();
        
        if (destNames.size() > 0)
        {
            for (String destName : destNames) {
                jComboBoxChatDestination.addItem(destName);
            }
            jComboBoxChatDestination.setSelectedIndex(0);
            chatMain.setDestination(jComboBoxChatDestination.getSelectedIndex());
            jTextFieldMessage.requestFocusInWindow();
            needToRefreshChat = true;
        }
    }
    
     /**
     * Method to show update chat
     */
    javax.swing.Timer timerRefreshChat = new javax.swing.Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if ( chatMain.isReceiveChat() || needToRefreshChat )
            {                
                showChat();           
                needToRefreshChat = false;                
            }
        }
    });

    private void showChat()
    {
        jPanelMessager.removeAll();
        JScrollPane jspane = chatPanel.showChatWindow(chatMain.getChatContentList());
        jPanelMessager.add(jspane);
        jPanelMessager.revalidate();     
        scrollDown(jspane);     
    }
    
    private void scrollDown(JScrollPane scrollPane) {
      
        scrollPane.validate();
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        int maxScrollValue = verticalBar.getMaximum();       
        verticalBar.setValue(maxScrollValue);
    } 

    private void jTextFieldMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMessageActionPerformed

        if (jTextFieldMessage.getText().length() > 0)
        {
            String sChat = jTextFieldMessage.getText();           
            chatMain.sendChat(sChat);                       
            needToRefreshChat = true;
            jTextFieldMessage.setText("");
            jLabelInfo.setText("");   
        }
    }//GEN-LAST:event_jTextFieldMessageActionPerformed

    private void jComboBoxChatDestinationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxChatDestinationActionPerformed
        if (initDone)
        {
            chatMain.setDestination(jComboBoxChatDestination.getSelectedIndex());
            jLabelInfo.setText(" ");
            jTextFieldMessage.requestFocusInWindow();
            needToRefreshChat = true;
        }
    }//GEN-LAST:event_jComboBoxChatDestinationActionPerformed

    private void showCounterChar()
    {
        int n_char = jTextFieldMessage.getText().length();
        if (n_char > 0)
            jLabelInfo.setText("Typing " + n_char + " chars, [Press ENTER to Send]");
        else
            jLabelInfo.setText("");
    }
    
    private void jTextFieldMessageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMessageKeyPressed
        showCounterChar();
    }//GEN-LAST:event_jTextFieldMessageKeyPressed
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        chatMain.closeChat();
    }//GEN-LAST:event_formWindowClosing

    private void jTextFieldMessageKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMessageKeyReleased
        showCounterChar();
    }//GEN-LAST:event_jTextFieldMessageKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBoxChatDestination;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelInfo;
    private javax.swing.JLabel jLabelMyKapal;
    private javax.swing.JPanel jPanelMessager;
    private javax.swing.JTextField jTextFieldMessage;
    // End of variables declaration//GEN-END:variables
}
