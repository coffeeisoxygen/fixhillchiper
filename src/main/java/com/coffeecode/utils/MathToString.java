package com.coffeecode.utils;

public class MathToString {

    public static String matrixToLatex(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        sb.append("\\begin{pmatrix}");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j]);
                if (j < matrix[i].length - 1) {
                    sb.append(" & ");
                }
            }
            if (i < matrix.length - 1) {
                sb.append(" \\\\ ");
            }
        }
        sb.append("\\end{pmatrix}");
        return sb.toString();
    }

    public static String determinantToLatex(int[][] matrix, double determinant) {
        return matrixToLatex(matrix) + " = " + determinant;
    }

    public static String inverseMatrixToLatex(int[][] matrix, int[][] inverseMatrix) {
        return matrixToLatex(matrix) + "^{-1} = " + matrixToLatex(inverseMatrix);
    }
}