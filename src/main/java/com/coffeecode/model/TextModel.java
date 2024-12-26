package com.coffeecode.model;

public class TextModel {
    private String plainText;
    private String encryptedText;
    private String decryptedText;

    public TextModel(String plainText) {
        this.plainText = plainText;
        this.encryptedText = "";
        this.decryptedText = "";
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getEncryptedText() {
        return encryptedText;
    }

    public void setEncryptedText(String encryptedText) {
        this.encryptedText = encryptedText;
    }

    public String getDecryptedText() {
        return decryptedText;
    }

    public void setDecryptedText(String decryptedText) {
        this.decryptedText = decryptedText;
    }
}
