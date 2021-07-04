package com.company.network.logs;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

public class LogScreen extends JFrame {
    private static JTextPane tPane;
    private static LogScreen instance = null;

    private LogScreen() {
        setMinimumSize(new Dimension(500, 500));
        setMaximumSize(new Dimension(500, 500));
        setResizable(false);
        setTitle("SERVER LOG");

        tPane = new JTextPane();
        tPane.setSize(500, 500);
        tPane.setEditable(false);
        tPane.setVisible(true);

        JScrollPane topPanel = new JScrollPane();
        topPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        topPanel.add(tPane);


        getContentPane().add(topPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private static int getColor(LogType type) {
        switch (type) {
            case PLAIN:
                return 0xff000000;
            case UNKNOWN:
                return 0xffff0000;
            case SERVER:
                return 0xff00ff00;
            case PLAYER:
                return 0xff0000ff;
        }
        return -1;
    }

    public static LogScreen getInstance() {
        if (instance == null)
            instance = new LogScreen();
        return instance;
    }

    public void appendToPane(String msg, LogType type) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(getColor(type)));
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        int len = tPane.getDocument().getLength();
        tPane.setCaretPosition(len);
        tPane.setCharacterAttributes(aset, false);
        tPane.setEditable(true);
        tPane.replaceSelection(msg + '\n');
        tPane.setEditable(false);
    }

    public void close() {
        if (instance != null)
            instance = null;
        setVisible(false);
        dispose();
    }
}
