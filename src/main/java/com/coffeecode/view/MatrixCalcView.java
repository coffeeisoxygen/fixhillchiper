package com.coffeecode.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MatrixCalcView extends JPanel {
    private JTextArea calculationArea;

    public MatrixCalcView() {
        setLayout(new BorderLayout());
        calculationArea = new JTextArea();
        calculationArea.setEditable(false);
        calculationArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(calculationArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addCalculation(String calculation) {
        calculationArea.append(calculation + "\n");
    }

    public void clearCalculations() {
        calculationArea.setText("");
    }
}