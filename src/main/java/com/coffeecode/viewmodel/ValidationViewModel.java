package com.coffeecode.viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.coffeecode.model.MatrixModel;

public class ValidationViewModel {
    private MatrixModel matrixModel;
    private PropertyChangeSupport support;

    public ValidationViewModel(int blockSize) {
        this.matrixModel = new MatrixModel(blockSize);
        this.support = new PropertyChangeSupport(this);
    }

    public void setBlockSize(int blockSize) {
        matrixModel.setBlockSize(blockSize);
        support.firePropertyChange("blockSize", null, blockSize);
    }

    public void setKeyMatrix(int[][] keyMatrix) {
        matrixModel.setKeyMatrix(keyMatrix);
        support.firePropertyChange("keyMatrix", null, keyMatrix);
    }

    public boolean isSizeValid() {
        return matrixModel.isSizeValid();
    }

    public boolean areElementsIntegers() {
        return matrixModel.areElementsIntegers();
    }

    public boolean isDeterminantNonZero() {
        return matrixModel.isDeterminantNonZero();
    }

    public boolean isDeterminantRelativelyPrime() {
        return matrixModel.isDeterminantRelativelyPrime();
    }

    public boolean isInvertible() {
        return matrixModel.isInvertible();
    }

    public boolean isDeterminantValid() {
        return matrixModel.isDeterminantValid();
    }

    public void resetValidation() {
        matrixModel.setKeyMatrix(null);
        support.firePropertyChange("keyMatrix", null, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
