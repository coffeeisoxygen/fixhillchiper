package com.coffeecode.utils;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import com.coffeecode.view.LatexRender;

public class MatrixServicesUtils {
    private static LatexRender latexRender;

    private MatrixServicesUtils() {
        throw new IllegalStateException("Utility class: Jangan diinstansiasi");
    }

    public static void setLatexRender(LatexRender render) {
        latexRender = render;
    }

    // Fungsi untuk menghitung determinan matriks
    public static double calculateDeterminant(int[][] matrix) {
        RealMatrix realMatrix = convertToRealMatrix(matrix);
        double determinant = new LUDecomposition(realMatrix).getDeterminant();
        determinant = Math.round(determinant * 1e9) / 1e9; // Pembulatan untuk menghindari masalah floating point
        System.out.println("Determinant: " + determinant); // Debug print
        if (latexRender != null) {
            latexRender.renderLatex(MathToString.determinantToLatex(matrix, determinant));
        }
        return determinant;
    }

    // Fungsi untuk memeriksa apakah matriks dapat di-invers
    public static boolean isInvertible(int[][] matrix) {
        RealMatrix realMatrix = convertToRealMatrix(matrix);
        boolean invertible = new LUDecomposition(realMatrix).getDeterminant() != 0;
        System.out.println("Invertible: " + invertible); // Debug print
        return invertible;
    }

    // Fungsi untuk menghitung invers matriks
    public static int[][] inverse(int[][] matrix) {
        RealMatrix realMatrix = convertToRealMatrix(matrix);
        RealMatrix inverseMatrix = new LUDecomposition(realMatrix).getSolver().getInverse();
        int[][] result = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = (int) Math.round(inverseMatrix.getEntry(i, j));
            }
        }
        if (latexRender != null) {
            latexRender.renderLatex(MathToString.inverseMatrixToLatex(matrix, result));
        }
        return result;
    }

    // Fungsi untuk memeriksa apakah dua bilangan relatif prima
    public static boolean isRelativelyPrime(int a, int b) {
        boolean relativelyPrime = gcd(a, b) == 1;
        System.out.println("Relatively Prime: " + relativelyPrime); // Debug print
        return relativelyPrime;
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static RealMatrix convertToRealMatrix(int[][] matrix) {
        double[][] doubleMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                doubleMatrix[i][j] = matrix[i][j];
            }
        }
        return new Array2DRowRealMatrix(doubleMatrix);
    }

    // Fungsi untuk memeriksa apakah determinan matriks adalah 1
    public static boolean isDeterminantValid(int[][] matrix) {
        double determinant = calculateDeterminant(matrix);
        return Math.abs(determinant - 1.0) < 1e-9; // Toleransi untuk floating point
    }

    // Fungsi untuk memeriksa apakah semua elemen dalam matriks adalah bilangan bulat
    public static boolean areElementsIntegers(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                if (value != (int) value) {
                    return false;
                }
            }
        }
        return true;
    }
}