package com.coffeecode.view;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextConvertView extends JPanel {
    private JTextArea textToDecimalArea;
    private JTextArea blockConversionArea;

    public TextConvertView() {
        setLayout(new GridLayout(2, 1));

        textToDecimalArea = new JTextArea();
        textToDecimalArea.setEditable(false);
        textToDecimalArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        blockConversionArea = new JTextArea();
        blockConversionArea.setEditable(false);
        blockConversionArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane textScrollPane = new JScrollPane(textToDecimalArea);
        JScrollPane blockScrollPane = new JScrollPane(blockConversionArea);

        add(textScrollPane);
        add(blockScrollPane);
    }

    public void addTextToDecimal(String text) {
        textToDecimalArea.append(text + "\n");
    }

    public void addBlockConversion(String block) {
        blockConversionArea.append(block + "\n");
    }

    public void clearTextToDecimal() {
        textToDecimalArea.setText("");
    }

    public void clearBlockConversion() {
        blockConversionArea.setText("");
    }
}