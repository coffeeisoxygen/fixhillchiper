package com.coffeecode.viewmodel;

import com.coffeecode.model.HillCipherModel;
import com.coffeecode.model.MatrixModel;
import com.coffeecode.model.TextModel;

public class HillCipherViewModel {
    private MatrixModel matrixModel;
    private TextModel textModel;

    public HillCipherViewModel() {
        this.matrixModel = new MatrixModel(2); // Default block size
        this.textModel = new TextModel("");
    }

    public void setBlockSize(int blockSize) {
        matrixModel.setBlockSize(blockSize);
        // Validasi block size (misal: 2-4)
        if (blockSize < 2 || blockSize > 4) {
            throw new IllegalArgumentException("Block size harus antara 2-4");
        }
    }

    public void setKeyMatrix(int[][] keyMatrix) {
        matrixModel.setKeyMatrix(keyMatrix);
        if (!matrixModel.isValidKey()) {
            throw new IllegalArgumentException("Key matrix tidak valid");
        }
    }

    public boolean isKeyValid() {
        return matrixModel.isValidKey();
    }

    public void setPlainText(String plainText) {
        textModel.setPlainText(plainText);
    }

    public String encryptText() {
        if (matrixModel.getKeyMatrix() == null) {
            throw new IllegalStateException("Key matrix belum di-set");
        }
        String encrypted = HillCipherModel.encrypt(
                textModel.getPlainText(), matrixModel.getKeyMatrix());
        textModel.setEncryptedText(encrypted);
        return encrypted;
    }

    public String decryptText() {
        if (matrixModel.getKeyMatrix() == null) {
            throw new IllegalStateException("Key matrix belum di-set");
        }
        String decrypted = HillCipherModel.decrypt(
                textModel.getEncryptedText(), matrixModel.getKeyMatrix());
        textModel.setDecryptedText(decrypted);
        return decrypted;
    }
}
