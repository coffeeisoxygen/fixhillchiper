package com.coffeecode.model;

import com.coffeecode.utils.MatrixServicesUtils;

public class HillCipherModel {

    private static AlphabetModel alphabetModel = new AlphabetModel();

    public static String encrypt(String plainText, int[][] keyMatrix) {
        int blockSize = keyMatrix.length;
        int[][] numericMatrix = textToNumericMatrix(plainText, blockSize);
        int[][] encryptedMatrix = multiplyMatrices(numericMatrix, keyMatrix, blockSize);
        return numericMatrixToText(encryptedMatrix);
    }

    public static String decrypt(String cipherText, int[][] keyMatrix) {
        int blockSize = keyMatrix.length;
        int[][] numericMatrix = textToNumericMatrix(cipherText, blockSize);
        int[][] inverseKeyMatrix = MatrixServicesUtils.inverse(keyMatrix);
        int[][] decryptedMatrix = multiplyMatrices(numericMatrix, inverseKeyMatrix, blockSize);
        return numericMatrixToText(decryptedMatrix);
    }

    public static int[][] textToNumericMatrix(String text, int blockSize) {
        int length = text.length();
        int paddedLength = (int) Math.ceil((double) length / blockSize) * blockSize;
        text = String.format("%-" + paddedLength + "s", text).replace(' ', 'X'); // Padding with 'X'

        int[][] numericMatrix = new int[paddedLength / blockSize][blockSize];
        for (int i = 0; i < paddedLength; i++) {
            numericMatrix[i / blockSize][i % blockSize] = alphabetModel.getCharToDecimalMap().get(text.charAt(i));
        }
        return numericMatrix;
    }

    public static String numericMatrixToText(int[][] numericMatrix) {
        StringBuilder text = new StringBuilder();
        for (int[] row : numericMatrix) {
            for (int value : row) {
                text.append(alphabetModel.getAlphabetTable().get(value % 27));
            }
        }
        return text.toString();
    }

    private static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2, int blockSize) {
        int[][] result = new int[matrix1.length][blockSize];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < blockSize; j++) {
                for (int k = 0; k < blockSize; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
                result[i][j] %= 27; // Modulo 27 for Hill Cipher
            }
        }
        return result;
    }
}
