package com.coffeecode;

import javax.swing.SwingUtilities;

import com.coffeecode.view.MainUI;
import com.formdev.flatlaf.FlatLightLaf;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        // Setup FlatLaf look and feel
        FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> {
            MainUI mainUI = new MainUI();
            mainUI.showUI();
        });
    }
}
