package com.coffeecode.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class StepView extends JPanel {
    private JTextArea stepArea;
    private JTextArea resultArea;

    public StepView() {
        setLayout(new BorderLayout());

        stepArea = new JTextArea();
        stepArea.setEditable(false);
        stepArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane stepScrollPane = new JScrollPane(stepArea);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, resultScrollPane, stepScrollPane);
        splitPane.setDividerLocation(100);

        add(splitPane, BorderLayout.CENTER);
    }

    public void addStep(String step) {
        stepArea.append(step + "\n");
    }

    public void setResult(String result) {
        resultArea.setText(result);
    }

    public void clearSteps() {
        stepArea.setText("");
    }

    public void clearResult() {
        resultArea.setText("");
    }
}