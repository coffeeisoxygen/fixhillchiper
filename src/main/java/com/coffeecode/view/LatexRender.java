package com.coffeecode.view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class LatexRender {
    private JPanel latexPanel;

    public LatexRender(JPanel latexPanel) {
        this.latexPanel = latexPanel;
        this.latexPanel.setLayout(new BoxLayout(latexPanel, BoxLayout.Y_AXIS));
    }

    public void renderLatex(String latex) {
        TeXFormula formula = new TeXFormula(latex);
        TeXIcon icon = formula.createTeXIcon(TeXFormula.SERIF, 20);
        JLabel label = new JLabel();
        label.setIcon(icon);
        latexPanel.add(label);
        latexPanel.revalidate();
        latexPanel.repaint();
    }

    public void clearLatex() {
        latexPanel.removeAll();
        latexPanel.revalidate();
        latexPanel.repaint();
    }
}