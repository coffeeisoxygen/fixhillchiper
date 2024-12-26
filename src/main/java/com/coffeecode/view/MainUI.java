package com.coffeecode.view;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.coffeecode.utils.MatrixServicesUtils;
import com.coffeecode.viewmodel.AlphabetViewModel;
import com.coffeecode.viewmodel.HillCipherViewModel;

public class MainUI extends JFrame {
    public MainUI() {
        setTitle("Hill Cipher Visualization");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HillCipherViewModel hillCipherViewModel = new HillCipherViewModel();
        AlphabetViewModel alphabetViewModel = new AlphabetViewModel();

        InputUI inputUI = new InputUI(hillCipherViewModel);
        AlphabetView alphabetView = new AlphabetView(alphabetViewModel);
        MatrixCalcView matrixCalcView = new MatrixCalcView();
        TextConvertView textConvertView = new TextConvertView();
        StepView stepView = new StepView();

        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        centerPanel.add(matrixCalcView);
        centerPanel.add(alphabetView.getMainPanel());
        centerPanel.add(textConvertView);

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputUI, centerPanel);
        mainSplitPane.setDividerLocation(400);

        JSplitPane outerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainSplitPane, stepView);
        outerSplitPane.setDividerLocation(800);

        add(outerSplitPane);

        // Set LatexRender to MatrixServicesUtils
        LatexRender latexRender = new LatexRender(matrixCalcView);
        MatrixServicesUtils.setLatexRender(latexRender);
    }

    public void showUI() {
        setVisible(true);
    }
}
