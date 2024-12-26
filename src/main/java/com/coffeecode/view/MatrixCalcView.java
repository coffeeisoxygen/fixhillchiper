package com.coffeecode.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class MatrixCalcView extends JPanel {
    private JTextArea calculationArea;
    private JPanel latexPanel;

    public MatrixCalcView() {
        setLayout(new BorderLayout());

        calculationArea = new JTextArea();
        calculationArea.setEditable(false);
        calculationArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(calculationArea);
        add(scrollPane, BorderLayout.CENTER);

        latexPanel = new JPanel();
        latexPanel.setLayout(new BoxLayout(latexPanel, BoxLayout.Y_AXIS));
        JScrollPane latexScrollPane = new JScrollPane(latexPanel);
        add(latexScrollPane, BorderLayout.EAST);
    }

    public void addCalculation(String calculation) {
        calculationArea.append(calculation + "\n");
    }

    public void addLatexCalculation(String latex) {
        TeXFormula formula = new TeXFormula(latex);
        TeXIcon icon = formula.createTeXIcon(TeXFormula.SERIF, 20);
        JLabel label = new JLabel();
        label.setIcon(icon);
        latexPanel.add(label);
        latexPanel.revalidate();
        latexPanel.repaint();
    }

    public void clearCalculations() {
        calculationArea.setText("");
        latexPanel.removeAll();
        latexPanel.revalidate();
        latexPanel.repaint();
    }
}