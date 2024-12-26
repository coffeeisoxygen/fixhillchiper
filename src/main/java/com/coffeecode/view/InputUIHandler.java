package com.coffeecode.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.coffeecode.viewmodel.HillCipherViewModel;
import com.coffeecode.viewmodel.ValidationViewModel;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class InputUIHandler {
    private InputUI inputUI;
    private HillCipherViewModel viewModel;
    private ValidationViewModel validationViewModel;

    public InputUIHandler(InputUI inputUI, HillCipherViewModel viewModel, ValidationViewModel validationViewModel) {
        this.inputUI = inputUI;
        this.viewModel = viewModel;
        this.validationViewModel = validationViewModel;
    }

    public void handleThemeToggle(ActionEvent e) {
        if (inputUI.themeToggleButton.isSelected()) {
            FlatDarkLaf.setup();
            inputUI.themeToggleButton.setText("Switch to Light Mode");
        } else {
            FlatLightLaf.setup();
            inputUI.themeToggleButton.setText("Switch to Dark Mode");
        }
        SwingUtilities.updateComponentTreeUI(SwingUtilities.getWindowAncestor(inputUI));
    }

    public void handleBlockSizeChange(ChangeEvent e) {
        int blockSize = (int) inputUI.getBlockSizeSpinner().getValue();
        validationViewModel.setBlockSize(blockSize);
        updateKeyMatrixFields();
        resetValidation();
    }

    public void handleGenerateButtonClick(ActionEvent e) {
        try {
            int blockSize = (int) inputUI.getBlockSizeSpinner().getValue();
            viewModel.setBlockSize(blockSize);

            int[][] keyMatrix = new int[blockSize][blockSize];
            for (int i = 0; i < blockSize; i++) {
                for (int j = 0; j < blockSize; j++) {
                    keyMatrix[i][j] = Integer.parseInt(inputUI.getKeyMatrixFields()[i][j].getText());
                }
            }
            validationViewModel.setKeyMatrix(keyMatrix);

            if (validationViewModel.isSizeValid() && validationViewModel.areElementsIntegers() &&
                validationViewModel.isDeterminantNonZero() && validationViewModel.isDeterminantRelativelyPrime() &&
                validationViewModel.isInvertible()) {
                viewModel.setKeyMatrix(keyMatrix);
            } else {
                throw new IllegalArgumentException("Key matrix tidak valid");
            }

            String plainText = inputUI.getPlainTextField().getText();
            viewModel.setPlainText(plainText);

            String operation = (String) inputUI.getOperationComboBox().getSelectedItem();
            String result;
            if ("Encrypt".equals(operation)) {
                result = viewModel.encryptText();
            } else {
                result = viewModel.decryptText();
            }

            JOptionPane.showMessageDialog(inputUI, "Result: " + result);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(inputUI, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handlePropertyChange(PropertyChangeEvent evt) {
        if ("keyMatrix".equals(evt.getPropertyName())) {
            updateValidationCheckboxes();
        }
    }

    public void updateKeyMatrixFields() {
        inputUI.getKeyMatrixPanel().removeAll();
        int blockSize = (int) inputUI.getBlockSizeSpinner().getValue();
        JTextField[][] keyMatrixFields = new JTextField[blockSize][blockSize];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add left curly brace
        JLabel leftBrace = new JLabel("{");
        leftBrace.setFont(new Font("Serif", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = blockSize;
        gbc.anchor = GridBagConstraints.CENTER;
        inputUI.getKeyMatrixPanel().add(leftBrace, gbc);

        // Add key matrix fields
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                keyMatrixFields[i][j] = new JTextField(5);
                keyMatrixFields[i][j].getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        validateKeyMatrix();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        validateKeyMatrix();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        validateKeyMatrix();
                    }
                });
                gbc.gridx = j + 1;
                gbc.gridy = i;
                gbc.gridheight = 1;
                inputUI.getKeyMatrixPanel().add(keyMatrixFields[i][j], gbc);
            }
        }

        // Add right curly brace
        JLabel rightBrace = new JLabel("}");
        rightBrace.setFont(new Font("Serif", Font.PLAIN, 24));
        gbc.gridx = blockSize + 1;
        gbc.gridy = 0;
        gbc.gridheight = blockSize;
        gbc.anchor = GridBagConstraints.CENTER;
        inputUI.getKeyMatrixPanel().add(rightBrace, gbc);

        inputUI.setKeyMatrixFields(keyMatrixFields);
        inputUI.getKeyMatrixPanel().revalidate();
        inputUI.getKeyMatrixPanel().repaint();
    }

    private void validateKeyMatrix() {
        try {
            int blockSize = (int) inputUI.getBlockSizeSpinner().getValue();
            int[][] keyMatrix = new int[blockSize][blockSize];
            for (int i = 0; i < blockSize; i++) {
                for (int j = 0; j < blockSize; j++) {
                    keyMatrix[i][j] = Integer.parseInt(inputUI.getKeyMatrixFields()[i][j].getText());
                }
            }
            if (isMatrixFullyFilled(keyMatrix)) {
                validationViewModel.setKeyMatrix(keyMatrix);
            } else {
                resetValidation();
            }
        } catch (NumberFormatException e) {
            // Handle invalid input
            resetValidation();
        }
    }

    private boolean isMatrixFullyFilled(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                if (value == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void updateValidationCheckboxes() {
        inputUI.getSizeCheckBox().setSelected(validationViewModel.isSizeValid());
        inputUI.getIntegerElementsCheckBox().setSelected(validationViewModel.areElementsIntegers());
        inputUI.getDeterminantNonZeroCheckBox().setSelected(validationViewModel.isDeterminantNonZero());
        inputUI.getDeterminantRelativelyPrimeCheckBox().setSelected(validationViewModel.isDeterminantRelativelyPrime());
        inputUI.getInvertibleCheckBox().setSelected(validationViewModel.isInvertible());
    }

    private void resetValidation() {
        validationViewModel.resetValidation();
        inputUI.getSizeCheckBox().setSelected(false);
        inputUI.getIntegerElementsCheckBox().setSelected(false);
        inputUI.getDeterminantNonZeroCheckBox().setSelected(false);
        inputUI.getDeterminantRelativelyPrimeCheckBox().setSelected(false);
        inputUI.getInvertibleCheckBox().setSelected(false);
    }
}
