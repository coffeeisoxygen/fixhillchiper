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

    private boolean isValidMatrix() {
        // Cek determinan dan ukuran matrix valid
        if (keyMatrix == null || keyMatrix.length != blockSize) {
            return false;
        }
        // Validasi tambahan
        if (!MatrixServicesUtils.areElementsIntegers(keyMatrix)) {
            return false;
        }
        if (!MatrixServicesUtils.isDeterminantValid(keyMatrix)) {
            return false;
        }
        return MatrixServicesUtils.isInvertible(keyMatrix);
    }
}
