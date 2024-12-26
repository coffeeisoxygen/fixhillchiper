package com.coffeecode.utils;

import org.ejml.simple.SimpleMatrix;

public class MatrixUtils {

    private MatrixUtils() {
        throw new IllegalStateException("Utility class: Jangan diinstansiasi");
    }

    // Fungsi untuk menghitung determinan matriks
    public static double calculateDeterminant(int[][] matrix) {
        double[][] doubleMatrix = convertToDoubleMatrix(matrix);
        SimpleMatrix simpleMatrix = new SimpleMatrix(doubleMatrix);
        return simpleMatrix.determinant();
    }

    // Fungsi untuk memeriksa apakah matriks dapat di-invers
    public static boolean isInvertible(int[][] matrix) {
        double[][] doubleMatrix = convertToDoubleMatrix(matrix);
        SimpleMatrix simpleMatrix = new SimpleMatrix(doubleMatrix);
        return simpleMatrix.determinant() != 0;
    }

    // Fungsi untuk menghitung invers matriks
    public static int[][] inverse(int[][] matrix) {
        double[][] doubleMatrix = convertToDoubleMatrix(matrix);
        SimpleMatrix simpleMatrix = new SimpleMatrix(doubleMatrix);
        SimpleMatrix inverseMatrix = simpleMatrix.invert();
        int[][] result = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = (int) Math.round(inverseMatrix.get(i, j));
            }
        }
        return result;
    }

    // Fungsi untuk memeriksa apakah dua bilangan relatif prima
    public static boolean isRelativelyPrime(int a, int b) {
        return gcd(a, b) == 1;
    }

    // Fungsi untuk menghitung GCD (Greatest Common Divisor)
    private static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    // Fungsi untuk mengkonversi matriks int ke matriks double
    private static double[][] convertToDoubleMatrix(int[][] matrix) {
        double[][] doubleMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                doubleMatrix[i][j] = matrix[i][j];
            }
        }
        return doubleMatrix;
    }
}