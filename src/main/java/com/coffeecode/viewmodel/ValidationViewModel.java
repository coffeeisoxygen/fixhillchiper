package com.coffeecode.viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.coffeecode.utils.MatrixServicesUtils;

public class ValidationViewModel {
    private int blockSize;
    private int[][] keyMatrix;
    private PropertyChangeSupport support;

    public ValidationViewModel(int blockSize) {
        this.blockSize = blockSize;
        this.keyMatrix = null;
        this.support = new PropertyChangeSupport(this);
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
        support.firePropertyChange("blockSize", null, blockSize);
    }

    public void setKeyMatrix(int[][] keyMatrix) {
        this.keyMatrix = keyMatrix;
        support.firePropertyChange("keyMatrix", null, keyMatrix);
    }

    public boolean isSizeValid() {
        return keyMatrix != null && keyMatrix.length == blockSize;
    }

    public boolean isDeterminantNonZero() {
        if (keyMatrix == null || !isSizeValid()) return false;
        double determinant = MatrixServicesUtils.calculateDeterminant(keyMatrix);
        return determinant != 0;
    }

    public boolean isDeterminantRelativelyPrime() {
        if (keyMatrix == null || !isSizeValid()) return false;
        double determinant = MatrixServicesUtils.calculateDeterminant(keyMatrix);
        return MatrixServicesUtils.isRelativelyPrime((int) determinant, 26);
    }

    public boolean isInvertible() {
        if (keyMatrix == null || !isSizeValid()) return false;
        return MatrixServicesUtils.isInvertible(keyMatrix);
    }

    public boolean isDeterminantValid() {
        if (keyMatrix == null || !isSizeValid()) return false;
        return MatrixServicesUtils.isDeterminantValid(keyMatrix);
    }

    public void resetValidation() {
        this.keyMatrix = null;
        support.firePropertyChange("keyMatrix", null, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
