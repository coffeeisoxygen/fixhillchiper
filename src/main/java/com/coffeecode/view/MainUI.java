package com.coffeecode.view;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import com.coffeecode.viewmodel.AlphabetViewModel;
import com.coffeecode.viewmodel.HillCipherViewModel;

public class MainUI extends JFrame {
    public MainUI() {
        setTitle("Hill Cipher Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HillCipherViewModel hillCipherViewModel = new HillCipherViewModel();
        AlphabetViewModel alphabetViewModel = new AlphabetViewModel();

        InputUI inputUI = new InputUI(hillCipherViewModel);
        AlphabetView alphabetView = new AlphabetView(alphabetViewModel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputUI, alphabetView.getMainPanel());
        splitPane.setDividerLocation(400);
        add(splitPane);
    }

    public void showUI() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainUI().showUI());
    }
}
