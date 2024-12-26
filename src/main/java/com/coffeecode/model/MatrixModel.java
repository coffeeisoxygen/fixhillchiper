package com.coffeecode.model;

import com.coffeecode.utils.MatrixServicesUtils;

public class MatrixModel {
    private int blockSize;
    private int[][] keyMatrix;
    private boolean isValidKey;

    public MatrixModel(int blockSize) {
        this.blockSize = blockSize;
        this.keyMatrix = null;
        this.isValidKey = false;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int[][] getKeyMatrix() {
        return keyMatrix;
    }

    public void setKeyMatrix(int[][] keyMatrix) {
        this.keyMatrix = keyMatrix;
        this.isValidKey = isValidMatrix();
    }

    public boolean isValidKey() {
        return isValidKey;
    }

    public boolean isSizeValid() {
        return keyMatrix != null && keyMatrix.length == blockSize;
    }

    public boolean areElementsIntegers() {
        return keyMatrix != null && MatrixServicesUtils.areElementsIntegers(keyMatrix);
    }

    public boolean isDeterminantNonZero() {
        return keyMatrix != null && isSizeValid() && MatrixServicesUtils.calculateDeterminant(keyMatrix) != 0;
    }

    public boolean isDeterminantRelativelyPrime() {
        return keyMatrix != null && isSizeValid()
                && MatrixServicesUtils.isRelativelyPrime((int) MatrixServicesUtils.calculateDeterminant(keyMatrix), 26);
    }

    public boolean isInvertible() {
        return keyMatrix != null && isSizeValid() && MatrixServicesUtils.isInvertible(keyMatrix);
    }

    public boolean isDeterminantValid() {
        return keyMatrix != null && isSizeValid() && MatrixServicesUtils.isDeterminantValid(keyMatrix);
    }

    private boolean isValidMatrix() {
        return isSizeValid() && areElementsIntegers() && isDeterminantNonZero() && isDeterminantRelativelyPrime() && isInvertible();
    }
}
