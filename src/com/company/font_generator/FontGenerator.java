package com.company.font_generator;

/*
DISCLAIMER: this code is not mine, I've only done some refactoring. All credits go to his creator that, unfortunately,
I do not remember who he/she is - they are. If you know, please advise me, I'll give the credits!
*/

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;

public class FontGenerator extends JFrame {

    private final Font[] fonts;
    private final JComboBox<String> fontComboBox;
    private final JComboBox<Integer> sizeComboBox;

    private final FontGenerator component;

    private final JTextField preview;

    private BufferedImage sprite;

    public FontGenerator() {
        component = this;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 300, 136);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblChooseFont = new JLabel("Choose Font:");
        lblChooseFont.setBounds(10, 11, 120, 14);
        contentPane.add(lblChooseFont);

        fontComboBox = new JComboBox<>();
        fontComboBox.setEditable(true);
        fontComboBox.setBounds(10, 28, 120, 20);
        contentPane.add(fontComboBox);

        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        fonts = e.getAllFonts();

        for (Font font : fonts) {
            fontComboBox.addItem(font.getName());
        }

        fontComboBox.setSelectedItem("Liberation Serif");


        JLabel lblFontSize = new JLabel("Font size:");
        lblFontSize.setBounds(220, 11, 60, 14);
        contentPane.add(lblFontSize);

        sizeComboBox = new JComboBox<>();
        sizeComboBox.setEditable(true);
        sizeComboBox.setBounds(220, 28, 60, 20);
        contentPane.add(sizeComboBox);

        int[] sizes = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72};

        for (int size : sizes) {
            sizeComboBox.addItem(size);
        }

        sizeComboBox.setSelectedItem(11);

        JLabel lblPreview = new JLabel("Preview:");
        lblPreview.setBounds(140, 11, 70, 14);
        contentPane.add(lblPreview);

        preview = new JTextField("AaBb ... 0-9");
        preview.setEditable(false);
        preview.setBounds(140, 28, 70, 20);
        setPreview();
        contentPane.add(preview);

        fontComboBox.addActionListener(e1 -> setPreview());

        sizeComboBox.addActionListener(e1 -> setPreview());

        JButton btnGenerate = new JButton("Create sprite!");
        btnGenerate.addActionListener(arg0 -> {
            generateFontSprite();
            save();
        });
        btnGenerate.setFont(new Font(btnGenerate.getFont().getName(), btnGenerate.getFont().getStyle(), 12));
        btnGenerate.setBounds(10, 59, 270, 35);
        contentPane.add(btnGenerate);

        setVisible(true);
    }

    private void setPreview() {
        String fontName = (String) fontComboBox.getSelectedItem();
        Integer fontSize = (Integer) sizeComboBox.getSelectedItem();

        if (fontName == null || fontSize == null)
            return;

        Font fontPreview = new Font(fontName, Font.PLAIN, fontSize);
        preview.setFont(fontPreview);
    }

    private void generateFontSprite() {
        // Check if font and size are valid
        String fontName = (String) fontComboBox.getSelectedItem();
        if (fontName == null || !existsFont(fontName)) {
            return;
        }

        Integer fontSize = (Integer) sizeComboBox.getSelectedItem();
        if (fontSize == null || fontSize < 8) {
            return;
        }

        // Create Font instance
        Font font = new Font(fontName, Font.PLAIN, fontSize);

        // Create an instance of Graphics2D
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D gr = img.createGraphics();

        // Calculate width of sprite
        int width = 0;

        FontMetrics metrics = new JPanel().getFontMetrics(font);

        for (int c = 0; c < 256; c++) {
            width += metrics.charWidth((char) c) + 2;
        }

        // Calculate height of sprite
        int height;

        FontRenderContext frc = gr.getFontRenderContext();

        StringBuilder buffer = new StringBuilder(256);
        for (int c = 0; c < 256; c++) {
            buffer.append((char) c);
        }

        GlyphVector vec = font.createGlyphVector(frc, buffer.toString());
        Rectangle bounds = vec.getPixelBounds(null, 0, 0);
        height = bounds.height + 1;

        // Calculate offset of font due to wrong height of chars like '(', ')' or 'Q'
        int[] sizes = new int[256];

        for (int c = 0; c < 256; c++) {
            GlyphVector gv = font.createGlyphVector(frc, String.valueOf((char) c));
            sizes[c] = gv.getPixelBounds(null, 0, 0).height;
        }

        int total = 0;
        for (int size : sizes) {
            total += size;
        }

        int ratio = (total / sizes.length);
        fontSize = (fontSize + ratio) / 2;

        // Create sprite image and get the Graphics to draw
        sprite = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = sprite.createGraphics();
        graphics.setColor(new Color(0, 0, 0, 0));
        graphics.fillRect(0, 0, width, height);

        // "Drawer" position
        int x = 0, y = 0;
        graphics.setFont(font);

        for (int c = 0; c < 256; c++) {
            graphics.setColor(Color.BLUE);
            graphics.drawLine(x, y, x, y);
            x++;

            y = 1;
            graphics.setColor(Color.WHITE);
            graphics.drawString(String.valueOf((char) c), x, y + fontSize);
            x += metrics.charWidth((char) c);

            y = 0;
            graphics.setColor(Color.YELLOW);
            graphics.drawLine(x, y, x, y);
            x++;
        }
    }

    private void save() {
        // Save file dialog
        JFileChooser fileChooser = new JFileChooser() {

            @Override
            public File getSelectedFile() {
                File file = super.getSelectedFile();
                if (file == null) {
                    return null;
                }

                String fileName = file.getName();
                if (!fileName.endsWith(".png")) {
                    fileName += ".png";
                }

                return new File(file.getParentFile(), fileName);
            }

            @Override
            public void approveSelection() {
                File file = getSelectedFile();
                if (file == null)
                    return;
                if (file.exists() && getDialogType() == SAVE_DIALOG) {
                    int result = JOptionPane.showConfirmDialog(this, "The file " + file.getName() + " already exists. Do you want to replace the existing file?", "Overwrite file", JOptionPane.YES_NO_CANCEL_OPTION);
                    switch (result) {
                        case JOptionPane.YES_OPTION:
                            super.approveSelection();
                            return;
                        case JOptionPane.NO_OPTION:
                        case JOptionPane.CLOSED_OPTION:
                            return;
                        case JOptionPane.CANCEL_OPTION:
                            cancelSelection();
                            return;
                    }
                }

                super.approveSelection();
            }
        };
        fileChooser.setDialogTitle("Save file");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Portable Network Graphics (.png)", "png"));

        int result = fileChooser.showSaveDialog(component);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                ImageIO.write(sprite, "png", file);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(component, ex.getMessage(), ex.getClass().getCanonicalName(), JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                return;
            }

            JOptionPane.showMessageDialog(component, "Font successfully saved at " + file.getAbsolutePath(), "File saved", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean existsFont(String fontName) {
        for (Font font : fonts) {
            if (font.getName().equals(fontName)) {
                return true;
            }
        }
        return false;
    }
}
