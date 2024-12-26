package com.coffeecode.viewmodel;

import com.coffeecode.model.AlphabetModel;

public class AlphabetViewModel {
    private AlphabetModel charDecimalMapper;

    public AlphabetViewModel() {
        this.charDecimalMapper = new AlphabetModel();
    }

    public AlphabetModel getCharDecimalMapper() {
        return charDecimalMapper;
    }

    public void increaseRows() {
        if (charDecimalMapper.getRows() < 4) {
            charDecimalMapper.setRows(charDecimalMapper.getRows() + 1);
        }
    }

    public void decreaseRows() {
        if (charDecimalMapper.getRows() > 2) {
            charDecimalMapper.setRows(charDecimalMapper.getRows() - 1);
        }
    }

    public void resetToDefault() {
        charDecimalMapper.resetToDefault();
    }
}
