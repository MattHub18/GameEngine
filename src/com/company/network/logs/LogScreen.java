package com.company.network.logs;

import com.company.graphic.primitives.ColorPalette;
import com.company.network.Server;
import com.company.network.packets.Packet;
import com.company.network.packets.PacketType;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.WindowAdapter;

public class LogScreen extends JFrame {
    private static JTextPane tPane;
    private static LogScreen instance = null;

    private LogScreen(Server server) {
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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(LogScreen.this, "Are you sure you want to close the server?", "Close Window?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    appendToPane("Server has closed", LogType.SERVER);
                    Packet packet = new Packet(PacketType.SHUTDOWN, null);
                    packet.writeData(server);
                    server.interrupt();
                    close();
                }
            }
        });
    }

    private static int getColor(LogType type) {
        switch (type) {
            case PLAIN:
                return ColorPalette.BLACK;
            case UNKNOWN:
                return ColorPalette.RED;
            case SERVER:
                return ColorPalette.GREEN;
            case PLAYER:
                return ColorPalette.BLUE;
            default:
                return -1;
        }
    }

    public static LogScreen getInstance(Server server) {
        if (instance == null)
            instance = new LogScreen(server);
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

    private void close() {
        if (instance != null)
            instance = null;
        setVisible(false);
        dispose();
    }
}
