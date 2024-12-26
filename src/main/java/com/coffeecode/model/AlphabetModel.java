package com.coffeecode.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlphabetModel {
    private List<Character> alphabetTable;
    private Map<Character, Integer> charToDecimalMap;
    private int rows = 6;
    private int columns = 5;

    public AlphabetModel() {
        // Initialize the alphabet table and map
        alphabetTable = new ArrayList<>();
        charToDecimalMap = new HashMap<>();
        initializeAlphabetTable();
        initializeCharToDecimalMap();
    }

    private void initializeAlphabetTable() {
        // Add characters A-Z and space to the alphabet table
        for (char c = 'A'; c <= 'Z'; c++) {
            alphabetTable.add(c);
        }
        alphabetTable.add(' ');
    }

    private void initializeCharToDecimalMap() {
        // Map characters A-Z and space to their decimal values
        for (int i = 0; i < 26; i++) {
            charToDecimalMap.put((char) ('A' + i), i);
        }
        charToDecimalMap.put(' ', 26);
    }

    public List<Character> getAlphabetTable() {
        return alphabetTable;
    }

    public Map<Character, Integer> getCharToDecimalMap() {
        return charToDecimalMap;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void resetToDefault() {
        // memgatur ulang baris dan kolom ke nilai default
        rows = 6;
        columns = 5;

    }
}
