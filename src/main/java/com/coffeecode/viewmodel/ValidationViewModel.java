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
        // cek ukuran matriks harus sama dengan ordo blok
        return keyMatrix != null && keyMatrix.length == blockSize;
    }

    public boolean areElementsIntegers() {
        // cek apakah ini adalah matriks integer bulat
        return keyMatrix != null && MatrixServicesUtils.areElementsIntegers(keyMatrix);
    }

    public boolean isDeterminantNonZero() {
        // cek apakah determinan matriks tidak nol
        return keyMatrix != null && isSizeValid() && MatrixServicesUtils.calculateDeterminant(keyMatrix) != 0;
    }

    public boolean isDeterminantRelativelyPrime() {
        // cek apakah determinan matriks relatif prima dengan 26 (jumlah karakter alfabet)
        return keyMatrix != null && isSizeValid()
                && MatrixServicesUtils.isRelativelyPrime((int) MatrixServicesUtils.calculateDeterminant(keyMatrix), 26);
    }

    public boolean isInvertible() {
        // cek apakah matriks tersebut dapat diinvert
        return keyMatrix != null && isSizeValid() && MatrixServicesUtils.isInvertible(keyMatrix);
    }

    public boolean isDeterminantValid() {
        // cek apakah determinan matriks tersebut valid
        return keyMatrix != null && isSizeValid() && MatrixServicesUtils.isDeterminantValid(keyMatrix);
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
