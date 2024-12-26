package com.coffeecode.viewmodel;

import com.coffeecode.utils.MatrixUtils;

public class ValidationViewModel {
    private int blockSize;
    private int[][] keyMatrix;

    public ValidationViewModel(int blockSize) {
        this.blockSize = blockSize;
        this.keyMatrix = null;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public void setKeyMatrix(int[][] keyMatrix) {
        this.keyMatrix = keyMatrix;
    }

    public boolean isSizeValid() {
        return keyMatrix != null && keyMatrix.length == blockSize;
    }

    public boolean isDeterminantNonZero() {
        if (keyMatrix == null) return false;
        double determinant = MatrixUtils.calculateDeterminant(keyMatrix);
        return determinant != 0;
    }

    public boolean isDeterminantRelativelyPrime() {
        if (keyMatrix == null) return false;
        double determinant = MatrixUtils.calculateDeterminant(keyMatrix);
        return MatrixUtils.isRelativelyPrime((int) determinant, 26);
    }

    public boolean isInvertible() {
        if (keyMatrix == null) return false;
        return MatrixUtils.isInvertible(keyMatrix);
    }
}
