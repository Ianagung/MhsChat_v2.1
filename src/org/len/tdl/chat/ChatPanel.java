/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.len.tdl.chat;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Scrollable;
import org.len.tdl.chat.ChatData.ChatEntry;

/**
 *
 * @author riyanto
 */
public class ChatPanel {

    private JScrollPane jsp;
    private BubblePane bubble;
    private JPanel mainPanel;
    private JPanel chatPanel;
    private JLabel[][] statLabel = new JLabel[30][500];//Label Status outgoing message
    private int currentNpu;

//    public ChatPanel(){
//        jsp = new JScrollPane();
//        initializeScrollPane();
//    }
//    private void initializeScrollPane() {
//        jsp.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
//            public void adjustmentValueChanged(AdjustmentEvent e) {
////                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
////                bubble.select(bubble.getCaretPosition()*100 ,0);
//                bubble.select(bubble.getHeight(),0);
//            }
//        });
//    }
    public void initChatWindow(List<ChatEntry> chatContentList) {
        // Create parent container JPanel for all other JComponents.
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(51, 51, 51));
        mainPanel.setOpaque(true);
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buildChatUI(chatContentList), gbc);

        //init stat Label
        
//        jsp.add(new VerticalScrollPane(mainPanel));
        jsp = new JScrollPane(new VerticalScrollPane(mainPanel));
        jsp.setDoubleBuffered(true);

        bubble.selectAll();

        jsp.validate();

        jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());

    }

    public JScrollPane showChatWindow(List<ChatEntry> chatContentList) {
        // Create parent container JPanel for all other JComponents.
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(51, 51, 51));
        mainPanel.setOpaque(true);
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buildChatUI(chatContentList), gbc);

//        jsp.add(new VerticalScrollPane(mainPanel));
        jsp = new JScrollPane(new VerticalScrollPane(mainPanel));
        jsp.setDoubleBuffered(true);

        bubble.selectAll();

        jsp.validate();

        jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
        return jsp;
    }

    public void addChat(List<ChatEntry> chatContentList) {

        GridBagConstraints gbc = new GridBagConstraints();

        ChatEntry chatEntry = chatContentList.get(chatContentList.size() - 1);

        JLabel nameLabel = new JLabel(chatEntry.name);
        bubble = new BubblePane(chatPanel, chatEntry.content);
//            bubble.select(bubble.getHeight()+1000,0);

        // Arrange each chat entry based on the user.
        if (chatEntry.type == 1) {
            bubble.setBackground(Color.YELLOW);
            gbc.anchor = GridBagConstraints.WEST;
        } else {
            bubble.setBackground(Color.GREEN);
            gbc.anchor = GridBagConstraints.EAST;
        }

        gbc.insets.set(0, 0, 5, 0);
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        chatPanel.add(nameLabel, gbc);

        if (gbc.anchor == GridBagConstraints.WEST) {
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets.set(0, 0, 0, 40);
            chatPanel.add(bubble, gbc);
        } else {
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets.set(0, 40, 0, 0);
            chatPanel.add(bubble, gbc);
        }

        if (chatEntry.type == 0) {
            gbc.insets.set(0, 0, 0, 0);
            gbc.weightx = 1.0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.NONE;
            JLabel dateLabel = new JLabel(chatEntry.date.split(" ")[1]);
            dateLabel.setForeground(Color.GRAY);
            chatPanel.add(dateLabel, gbc);

            gbc.insets.set(0, 0, 40, 0);
            gbc.weightx = 1.0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.NONE;

            String statusString = "";
            switch (chatEntry.status) {
                case ChatStatus.PENDING:
                    statusString = "Pending";
                    break;
                case ChatStatus.ONPROGRES:
                    statusString = "On progress";
                    break;
                case ChatStatus.DELIVERED:
                    statusString = "Delivered";
                    break;
                case ChatStatus.FAIL:
                    statusString = "Fail";
                    break;
            }
            JLabel statusLabel = new JLabel(statusString);
            statusLabel.setForeground(Color.GRAY);
            statLabel[currentNpu][chatEntry.nomor] = new JLabel(statusString);
            System.out.println("[" + currentNpu + "][" + chatEntry.nomor + "]");
            statusLabel.setForeground(Color.GRAY);
            statLabel[currentNpu][chatEntry.nomor].setForeground(Color.GRAY);
//            chatPanel.add(statusLabel, gbc);
            chatPanel.add(statLabel[currentNpu][chatEntry.nomor], gbc);
        } else {
            gbc.insets.set(0, 0, 40, 0);
            gbc.weightx = 1.0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.NONE;
            JLabel dateLabel = new JLabel(chatEntry.date.split(" ")[1]);
            dateLabel.setForeground(Color.GRAY);
            chatPanel.add(dateLabel, gbc);
        }

        bubble.selectAll();

        jsp.validate();

        jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
    }

    public void updateOnlyStatChat(List<ChatEntry> chatContentList, int nomorPesanAck) {

        GridBagConstraints gbc = new GridBagConstraints();

//        ChatEntry chatEntry = new ChatEntry(name, content, date, currentNpu, currentNpu, nomorPesanAck); 
//= chatContentList.get(nomorPesanAck);
        for (ChatEntry chatEntry : chatContentList) {
            if (chatEntry.nomor == nomorPesanAck) {
                String statusString = "";
                switch (chatEntry.status) {
                    case ChatStatus.PENDING:
                        statusString = "Pending";
                        break;
                    case ChatStatus.ONPROGRES:
                        statusString = "On progress";
                        break;
                    case ChatStatus.DELIVERED:
                        statusString = "Delivered";
                        break;
                    case ChatStatus.FAIL:
                        statusString = "Fail";
                        break;
                }

                statLabel[currentNpu][chatEntry.nomor].setText(statusString);

            }

            bubble.selectAll();

            jsp.validate();

            jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
        }
    }

    private JPanel buildChatUI(List<ChatEntry> chatContentList) {
        chatPanel = new JPanel();
        chatPanel.setLayout(new GridBagLayout());
        chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();

        int previousDate = 0;
        for (ChatEntry chatEntry : chatContentList) {

            // DATE
            String sDate = chatEntry.date.split(" ")[0];
            String[] date = sDate.split("/");
            int currentDate = Integer.valueOf(date[1]) * 12 + Integer.valueOf(date[0]);
            if (currentDate != previousDate) {
                JLabel dateSpaceLabel = new JLabel("[ " + sDate + " ]");
                gbc.anchor = GridBagConstraints.CENTER;
                gbc.insets.set(0, 0, 5, 0);
                gbc.weightx = 1.0;
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.NONE;
                dateSpaceLabel.setForeground(Color.GRAY);
                chatPanel.add(dateSpaceLabel, gbc);
            }
            previousDate = currentDate;

            JLabel nameLabel = new JLabel(chatEntry.name);
            bubble = new BubblePane(chatPanel, chatEntry.content);
//            bubble.select(bubble.getHeight()+1000,0);

            // Arrange each chat entry based on the user.
            if (chatEntry.type == 1) {
                bubble.setBackground(Color.YELLOW);
                gbc.anchor = GridBagConstraints.WEST;
            } else {
                bubble.setBackground(Color.GREEN);
                gbc.anchor = GridBagConstraints.EAST;
            }

            gbc.insets.set(0, 0, 5, 0);
            gbc.weightx = 1.0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.NONE;
            chatPanel.add(nameLabel, gbc);

            if (gbc.anchor == GridBagConstraints.WEST) {
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets.set(0, 0, 0, 40);
                chatPanel.add(bubble, gbc);
            } else {
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets.set(0, 40, 0, 0);
                chatPanel.add(bubble, gbc);
            }

            if (chatEntry.type == 0) {
                gbc.insets.set(0, 0, 0, 0);
                gbc.weightx = 1.0;
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.NONE;
                JLabel dateLabel = new JLabel(chatEntry.date.split(" ")[1]);
                dateLabel.setForeground(Color.GRAY);
                chatPanel.add(dateLabel, gbc);

                gbc.insets.set(0, 0, 40, 0);
                gbc.weightx = 1.0;
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.NONE;

                String statusString = "";
                switch (chatEntry.status) {
                    case ChatStatus.PENDING:
                        statusString = "Pending";
                        break;
                    case ChatStatus.ONPROGRES:
                        statusString = "On progress";
                        break;
                    case ChatStatus.DELIVERED:
                        statusString = "Delivered";
                        break;
                    case ChatStatus.FAIL:
                        statusString = "Fail";
                        break;
                }

                JLabel statusLabel = new JLabel(statusString);
                statusLabel.setForeground(Color.GRAY);
                statLabel[currentNpu][chatEntry.nomor] = new JLabel(statusString);
                System.out.println("[" + currentNpu + "][" + chatEntry.nomor + "]");
                statusLabel.setForeground(Color.GRAY);
                statLabel[currentNpu][chatEntry.nomor].setForeground(Color.GRAY);
//            chatPanel.add(statusLabel, gbc);
                chatPanel.add(statLabel[currentNpu][chatEntry.nomor], gbc);
            } else {
                gbc.insets.set(0, 0, 40, 0);
                gbc.weightx = 1.0;
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.NONE;
                JLabel dateLabel = new JLabel(chatEntry.date.split(" ")[1]);
                dateLabel.setForeground(Color.GRAY);
                chatPanel.add(dateLabel, gbc);
            }
        }

        return chatPanel;

    }

    private class BubblePane extends JTextArea {

        private static final long serialVersionUID = -6113801969569504295L;

        private int radius = 10;
        private int strokeThickness = 3;
        private int padding = strokeThickness / 2;
        private JPanel mParent;

        public BubblePane(JPanel parent, String text) {
            mParent = parent;

            setOpaque(false);
            setLineWrap(true);
            setWrapStyleWord(true);
            setEditable(false);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setText(text);

//            append(text);
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(getBackground());
            int x = padding + strokeThickness;
            int width = getWidth() - (strokeThickness * 2);
            int bottomLineY = getHeight() - strokeThickness;
            g2d.fillRect(x, padding, width, bottomLineY);
            g2d.setRenderingHints(new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON));
            g2d.setStroke(new BasicStroke(strokeThickness));
            RoundRectangle2D.Double rect = new RoundRectangle2D.Double(x, padding,
                    width, bottomLineY, radius, radius);
            Area area = new Area(rect);
            g2d.draw(area);

            int lc = countLines(this);
            GridBagLayout gbl = (GridBagLayout) mParent.getLayout();
            GridBagConstraints constraints = gbl.getConstraints(this);
            if (lc == 1) {
                if (constraints.fill == GridBagConstraints.HORIZONTAL) {
                    constraints.fill = GridBagConstraints.NONE;
                    gbl.setConstraints(this, constraints);
                    this.setSize(
                            getFontMetrics(getFont()).stringWidth(getText())
                            + this.getBorder().getBorderInsets(this).left
                            + this.getBorder().getBorderInsets(this).right,
                            getHeight()
                            + this.getBorder().getBorderInsets(this).top
                            + this.getBorder().getBorderInsets(this).bottom);
                }
            } else {
                if (constraints.fill == GridBagConstraints.NONE) {
                    constraints.fill = GridBagConstraints.HORIZONTAL;
                    gbl.setConstraints(this, constraints);

                }
            }

            super.paintComponent(g);

        }

        private int countLines(JTextArea textArea) {
            AttributedString text = new AttributedString(textArea.getText());
            FontRenderContext frc = textArea.getFontMetrics(textArea.getFont())
                    .getFontRenderContext();
            AttributedCharacterIterator charIt = text.getIterator();
            LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(charIt, frc);
            float formatWidth = (float) textArea.getSize().width;
            lineMeasurer.setPosition(charIt.getBeginIndex());

            int noLines = 0;
            while (lineMeasurer.getPosition() < charIt.getEndIndex()) {
                lineMeasurer.nextLayout(formatWidth);
                noLines++;
            }

            return noLines;
        }
    }

    /**
     * This class is used to make the JTextArea lines wrap every time the window
     * is resized. Without this, the JTextArea lines will not shrink back if the
     * parent window shrinks. This is achieved by returning true on
     * getScrollableTracksViewportWidth();
     */
    private class VerticalScrollPane extends JPanel implements Scrollable {

        private static final long serialVersionUID = 7477168367035025136L;

        public VerticalScrollPane() {
            this(new GridLayout(0, 1));
        }

        public VerticalScrollPane(LayoutManager lm) {
            super(lm);
        }

        public VerticalScrollPane(Component comp) {
            this();
            add(comp);
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect,
                int orientation, int direction) {
            return 10;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect,
                int orientation, int direction) {
            return 100;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }

    public void setCurrentNpu(int currentNpu) {
        this.currentNpu = currentNpu;
    }
}
