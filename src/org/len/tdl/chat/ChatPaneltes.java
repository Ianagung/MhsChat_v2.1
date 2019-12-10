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
public class ChatPaneltes {

    private JPanel mainPanel;
    private Color mainColor;
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gbc, gbc2;
    private JScrollPane jsp;
    private VerticalScrollPane verticalScrollPane;
    private JPanel chatPanel;
    private int previousDate;
    private String sDate;
    private String[] date;
    private int currentDate;
    private JLabel dateSpaceLabel = new JLabel(" ");
    private JLabel nameLabel;
    private BubblePane bubble;

    public ChatPaneltes() {
        //initialize
        mainPanel = new JPanel();
        mainColor = new Color(51, 51, 51);
        gridBagLayout = new GridBagLayout();
        gbc = new GridBagConstraints();
        gbc2 = new GridBagConstraints();
//        verticalScrollPane = new VerticalScrollPane();
//        verticalScrollPane = new VerticalScrollPane(mainPanel);
//        jsp = new JScrollPane(verticalScrollPane);
        chatPanel = new JPanel();
        nameLabel = new JLabel(" ");
        bubble = new BubblePane();
        //setup
        mainPanel.setBackground(mainColor);
        mainPanel.setOpaque(true);
        mainPanel.setLayout(gridBagLayout);

        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

//        jsp.setDoubleBuffered(true);

        chatPanel.setLayout(gridBagLayout);
        chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        int previousDate = 0;
        int currentDate = 0;

//        gbc2.anchor = GridBagConstraints.CENTER;
//        gbc2.insets.set(0, 0, 5, 0);
        gbc2.weightx = 1.0;
        gbc2.gridwidth = GridBagConstraints.REMAINDER;
//        gbc2.fill = GridBagConstraints.NONE;

//        dateSpaceLabel.setForeground(Color.GRAY);
//
//        chatPanel.add(dateSpaceLabel, gbc2);
    }

    public JScrollPane showChatWindow(List<ChatEntry> chatContentList) {
        // Create parent container JPanel for all other JComponents.
        mainPanel = null;
        JPanel mainPanel = new JPanel();
        
        mainPanel.setBackground(new Color(51, 51, 51));
        mainPanel.setOpaque(true);
        mainPanel.setLayout(new GridBagLayout());

      gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buildChatUI(chatContentList), gbc);

        jsp = null;
        jsp = new JScrollPane(new VerticalScrollPane(mainPanel));
        jsp.setDoubleBuffered(true);
        return jsp;
    }

    private JPanel buildChatUI(List<ChatEntry> chatContentList) {
//        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new GridBagLayout());
        chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
        gbc2 = new GridBagConstraints();
        previousDate = 0;
        for (ChatEntry chatEntry : chatContentList) {

            // DATE
            sDate = chatEntry.date.split(" ")[0];
            date = sDate.split("/");
            currentDate = Integer.valueOf(date[1]) * 12 + Integer.valueOf(date[0]);
            if (currentDate != previousDate) {
                dateSpaceLabel.setText("[ " + sDate + " ]");
//                JLabel dateSpaceLabel = new JLabel("[ " + sDate + " ]");
                gbc2.anchor = GridBagConstraints.CENTER;
                gbc2.insets.set(0, 0, 5, 0);
                gbc.weightx = 1.0;
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc2.fill = GridBagConstraints.NONE;
                dateSpaceLabel.setForeground(Color.GRAY);
                chatPanel.add(dateSpaceLabel, gbc2);
            }
            previousDate = currentDate;

//            JLabel nameLabel = new JLabel(chatEntry.name);
//            BubblePane bubble = new BubblePane(chatPanel, chatEntry.content);
            nameLabel.setText(chatEntry.name);
            bubble.setupComponents(chatPanel, chatEntry.content);

            // Arrange each chat entry based on the user.
            if (chatEntry.type == 1) {
                bubble.setBackground(Color.YELLOW);
                gbc2.anchor = GridBagConstraints.WEST;
            } else {
                bubble.setBackground(Color.CYAN);
                gbc2.anchor = GridBagConstraints.EAST;
            }

            gbc2.insets.set(0, 0, 5, 0);
//            gbc2.weightx = 1.0;
//            gbc2.gridwidth = GridBagConstraints.REMAINDER;
            gbc2.fill = GridBagConstraints.NONE;
            chatPanel.add(nameLabel, gbc2);

            if (gbc2.anchor == GridBagConstraints.WEST) {
                gbc2.fill = GridBagConstraints.HORIZONTAL;
                gbc2.insets.set(0, 0, 0, 40);
                chatPanel.add(bubble, gbc2);
            } else {
                gbc2.fill = GridBagConstraints.HORIZONTAL;
                gbc2.insets.set(0, 40, 0, 0);
                chatPanel.add(bubble, gbc2);
            }

            if (chatEntry.type == 0) {
                gbc2.insets.set(0, 0, 0, 0);
                gbc2.weightx = 1.0;
                gbc2.gridwidth = GridBagConstraints.REMAINDER;
                gbc2.fill = GridBagConstraints.NONE;
                JLabel dateLabel = new JLabel(chatEntry.date.split(" ")[1]);
                dateLabel.setForeground(Color.GRAY);
                chatPanel.add(dateLabel, gbc2);

                gbc2.insets.set(0, 0, 40, 0);
                gbc2.weightx = 1.0;
                gbc2.gridwidth = GridBagConstraints.REMAINDER;
                gbc2.fill = GridBagConstraints.NONE;

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
                chatPanel.add(statusLabel, gbc2);
            } else {
                gbc2.insets.set(0, 0, 40, 0);
                gbc2.weightx = 1.0;
                gbc2.gridwidth = GridBagConstraints.REMAINDER;
                gbc2.fill = GridBagConstraints.NONE;
                JLabel dateLabel = new JLabel(chatEntry.date.split(" ")[1]);
                dateLabel.setForeground(Color.GRAY);
                chatPanel.add(dateLabel, gbc2);
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
        }

        public BubblePane() {

        }

        public void setupComponents(JPanel parent, String text) {
            mParent = parent;

            setOpaque(false);
            setLineWrap(true);
            setWrapStyleWord(true);
            setEditable(false);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setText(text);
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

        public void addVerticalScrollPane(Component comp) {
            this.add(comp);
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

}
