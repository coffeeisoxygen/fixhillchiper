package com.coffeecode.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;

import com.coffeecode.viewmodel.HillCipherViewModel;
import com.coffeecode.viewmodel.ValidationViewModel;

public class InputUI extends JPanel {
    private HillCipherViewModel viewModel;
    private ValidationViewModel validationViewModel;
    private JSpinner blockSizeSpinner;
    private JPanel keyMatrixPanel;
    private JTextField[][] keyMatrixFields;
    private JTextField plainTextField;
    private JComboBox<String> operationComboBox;
    private JButton generateButton;
    JToggleButton themeToggleButton;
    private JCheckBox sizeCheckBox;
    private JCheckBox integerElementsCheckBox;
    private JCheckBox determinantNonZeroCheckBox;
    private JCheckBox determinantRelativelyPrimeCheckBox;
    private JCheckBox invertibleCheckBox;
    private InputUIHandler handler;

    public InputUI(HillCipherViewModel viewModel) {
        this.viewModel = viewModel;
        this.validationViewModel = new ValidationViewModel(2); // Default block size
        this.handler = new InputUIHandler(this, viewModel, validationViewModel);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Theme toggle button
        themeToggleButton = new JToggleButton("Switch to Dark Mode");
        themeToggleButton.addActionListener(handler::handleThemeToggle);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        add(themeToggleButton, gbc);

        // Block size input
        JLabel blockSizeLabel = new JLabel("Block Size:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(blockSizeLabel, gbc);

        blockSizeSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));
        blockSizeSpinner.addChangeListener(handler::handleBlockSizeChange);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(blockSizeSpinner, gbc);

        // Key matrix input panel
        keyMatrixPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(keyMatrixPanel, gbc);
        gbc.gridwidth = 1;

        // Key specification checkboxes
        sizeCheckBox = new JCheckBox("Jumlah kunci = blocksize");
        sizeCheckBox.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(sizeCheckBox, gbc);

        integerElementsCheckBox = new JCheckBox("Elemen-elemen adalah bilangan bulat");
        integerElementsCheckBox.setEnabled(false);
        gbc.gridy = 4;
        add(integerElementsCheckBox, gbc);

        determinantNonZeroCheckBox = new JCheckBox("det(K) tidak 0");
        determinantNonZeroCheckBox.setEnabled(false);
        gbc.gridy = 5;
        add(determinantNonZeroCheckBox, gbc);

        determinantRelativelyPrimeCheckBox = new JCheckBox("det(K) relatif prima dengan Mod 26");
        determinantRelativelyPrimeCheckBox.setEnabled(false);
        gbc.gridy = 6;
        add(determinantRelativelyPrimeCheckBox, gbc);

        invertibleCheckBox = new JCheckBox("K dapat di Inverse");
        invertibleCheckBox.setEnabled(false);
        gbc.gridy = 7;
        add(invertibleCheckBox, gbc);

        // Plain text input
        JLabel plainTextLabel = new JLabel("Plain Text:");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(plainTextLabel, gbc);

        plainTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(plainTextField, gbc);

        // Operation combobox
        JLabel operationLabel = new JLabel("Operation:");
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        add(operationLabel, gbc);

        operationComboBox = new JComboBox<>(new String[]{"Encrypt", "Decrypt"});
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(operationComboBox, gbc);

        // Generate button
        generateButton = new JButton("Generate");
        generateButton.addActionListener(handler::handleGenerateButtonClick);
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.EAST;
        add(generateButton, gbc);

        validationViewModel.addPropertyChangeListener(handler::handlePropertyChange);

        handler.updateKeyMatrixFields(); // Initialize key matrix fields
    }

    public JSpinner getBlockSizeSpinner() {
        return blockSizeSpinner;
    }

    public JPanel getKeyMatrixPanel() {
        return keyMatrixPanel;
    }

    public JTextField[][] getKeyMatrixFields() {
        return keyMatrixFields;
    }

    public void setKeyMatrixFields(JTextField[][] keyMatrixFields) {
        this.keyMatrixFields = keyMatrixFields;
    }

    public JCheckBox getSizeCheckBox() {
        return sizeCheckBox;
    }

    public JCheckBox getIntegerElementsCheckBox() {
        return integerElementsCheckBox;
    }

    public JCheckBox getDeterminantNonZeroCheckBox() {
        return determinantNonZeroCheckBox;
    }

    public JCheckBox getDeterminantRelativelyPrimeCheckBox() {
        return determinantRelativelyPrimeCheckBox;
    }

    public JCheckBox getInvertibleCheckBox() {
        return invertibleCheckBox;
    }

    public JTextField getPlainTextField() {
        return plainTextField;
    }

    public JComboBox<String> getOperationComboBox() {
        return operationComboBox;
    }
}
