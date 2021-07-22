package com.cmp3004.project;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VisualizationFrame extends JFrame {
	protected int gapBetweenFrameAndPanel;
	protected int gapBetweenPanelAndVisualization;
	protected int xAxisPanelLength;
	protected int yAxisPanelLength;
	protected int xAxisFrameLength;
	protected int yAxisFrameLength;
	VisualizationFrame(VisualizationPack visualizationPack){
        super(visualizationPack.visualizationType);
        this.gapBetweenFrameAndPanel = visualizationPack.gapBetweenFrameAndPanel;
        this.gapBetweenPanelAndVisualization = visualizationPack.gapBetweenPanelAndVisualization;
        this.xAxisPanelLength = visualizationPack.xAxisPanelLength;
        this.yAxisPanelLength = visualizationPack.yAxisPanelLength;
        this.xAxisFrameLength = visualizationPack.xAxisFrameLength;
        this.yAxisFrameLength = visualizationPack.yAxisFrameLength;
        setFrame();
        setPanel(visualizationPack);
        setVisible(true);
    }
    public void setFrame() {
    	setSize(xAxisFrameLength, yAxisFrameLength);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.white);
    }
    public void setPanel(VisualizationPack visualizationPack) {
    	JPanel locationsVisualizationPanel = new VisualizationPanel(visualizationPack);
        locationsVisualizationPanel.setBounds(gapBetweenFrameAndPanel, gapBetweenFrameAndPanel, xAxisPanelLength, yAxisPanelLength);
        add(locationsVisualizationPanel);
        repaint();
    }
}
