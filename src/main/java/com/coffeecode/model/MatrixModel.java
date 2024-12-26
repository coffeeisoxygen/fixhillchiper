package com.coffeecode.model;

import com.coffeecode.utils.MatrixServicesUtils;

public class MatrixModel {
    private int blockSize;
    private int[][] keyMatrix;
    private boolean isValidKey;
    private boolean sizeValid;
    private boolean elementsIntegers;
    private boolean determinantNonZero;
    private boolean determinantRelativelyPrime;
    private boolean invertible;
    private boolean determinantValid;

    public MatrixModel(int blockSize) {
        this.blockSize = blockSize;
        this.keyMatrix = null;
        this.isValidKey = false;
        resetValidation();
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
        resetValidation();
    }

    public int[][] getKeyMatrix() {
        return keyMatrix;
    }

    public void setKeyMatrix(int[][] keyMatrix) {
        this.keyMatrix = keyMatrix;
        validateMatrix();
    }

    public boolean isValidKey() {
        return isValidKey;
    }

    public boolean isSizeValid() {
        return sizeValid;
    }

    public boolean areElementsIntegers() {
        return elementsIntegers;
    }

    public boolean isDeterminantNonZero() {
        return determinantNonZero;
    }

    public boolean isDeterminantRelativelyPrime() {
        return determinantRelativelyPrime;
    }

    public boolean isInvertible() {
        return invertible;
    }

    public boolean isDeterminantValid() {
        return determinantValid;
    }

    private void validateMatrix() {
        if (keyMatrix == null) {
            resetValidation();
            return;
        }

        sizeValid = keyMatrix.length == blockSize;
        elementsIntegers = MatrixServicesUtils.areElementsIntegers(keyMatrix);
        double determinant = MatrixServicesUtils.calculateDeterminant(keyMatrix);
        determinantNonZero = determinant != 0;
        determinantRelativelyPrime = MatrixServicesUtils.isRelativelyPrime((int) determinant, 26);
        invertible = MatrixServicesUtils.isInvertible(keyMatrix);
        determinantValid = Math.abs(determinant - 1.0) < 1e-9; // Toleransi untuk floating point

        isValidKey = sizeValid && elementsIntegers && determinantNonZero && determinantRelativelyPrime && invertible;
    }

    private void resetValidation() {
        sizeValid = false;
        elementsIntegers = false;
        determinantNonZero = false;
        determinantRelativelyPrime = false;
        invertible = false;
        determinantValid = false;
        isValidKey = false;
    }
}
