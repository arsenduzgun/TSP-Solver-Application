package com.cmp3004.project;

import java.awt.Graphics;
import javax.swing.JPanel;

public class VisualizationPanel extends JPanel {
	protected Location[] locationArray;
	protected float xCoordinateMinValue;
	protected float yCoordinateMinValue;
	protected int gapBetweenPanelAndVisualization;
	protected float resizingValue;
	protected String visualizationType;
	VisualizationPanel(VisualizationPack visualizationPack){
		this.locationArray = visualizationPack.locationArray;
		this.xCoordinateMinValue = visualizationPack.xCoordinateMinValue;
		this.yCoordinateMinValue = visualizationPack.yCoordinateMinValue;
		this.gapBetweenPanelAndVisualization = visualizationPack.gapBetweenPanelAndVisualization;
		this.visualizationType = visualizationPack.visualizationType;
		this.resizingValue = visualizationPack.resizingValue;
	}
    
    @Override
    public void paint(Graphics g) {
    	if(visualizationType.equals("Locations(" + AppInterface.fileName + ")")) {
    		int gapForOval = gapBetweenPanelAndVisualization - 1;
    		int xCoordinate;
    		int yCoordinate;
    		for(Location location: locationArray) {
        		xCoordinate = Math.round((location.xCoordinate - xCoordinateMinValue) / resizingValue);
        		yCoordinate = Math.round((location.yCoordinate - yCoordinateMinValue) / resizingValue);
        		g.fillOval(xCoordinate + gapForOval, yCoordinate + gapForOval, 2, 2);
        	}
    	}
    	else if(visualizationType.equals("Path(" + AppInterface.fileName + ")(" + AppInterface.selectedMethod + ")")) {
    		int xCoordOfFirstLocation;
    		int yCoordOfFirstLocation;
    		int xCoordOfSecondLocation;
    		int yCoordOfSecondLocation;
    		for(int i = 0; i < locationArray.length - 1; i++) {
    			xCoordOfFirstLocation = Math.round((locationArray[i].xCoordinate - xCoordinateMinValue) / resizingValue);
    			yCoordOfFirstLocation = Math.round((locationArray[i].yCoordinate - yCoordinateMinValue) / resizingValue);
    			xCoordOfSecondLocation = Math.round((locationArray[i + 1].xCoordinate - xCoordinateMinValue) / resizingValue);
    			yCoordOfSecondLocation = Math.round((locationArray[i + 1].yCoordinate - yCoordinateMinValue) / resizingValue);
        		g.drawLine(
        		xCoordOfFirstLocation + gapBetweenPanelAndVisualization,
        		yCoordOfFirstLocation + gapBetweenPanelAndVisualization,
        		xCoordOfSecondLocation + gapBetweenPanelAndVisualization,
        		yCoordOfSecondLocation + gapBetweenPanelAndVisualization
        		);
        	}
    		xCoordOfFirstLocation = Math.round((locationArray[locationArray.length - 1].xCoordinate - xCoordinateMinValue) / resizingValue);
			yCoordOfFirstLocation = Math.round((locationArray[locationArray.length - 1].yCoordinate - yCoordinateMinValue) / resizingValue);
			xCoordOfSecondLocation = Math.round((locationArray[0].xCoordinate - xCoordinateMinValue) / resizingValue);
			yCoordOfSecondLocation = Math.round((locationArray[0].yCoordinate - yCoordinateMinValue) / resizingValue);
			g.drawLine(
	        xCoordOfFirstLocation + gapBetweenPanelAndVisualization,
	        yCoordOfFirstLocation + gapBetweenPanelAndVisualization,
	        xCoordOfSecondLocation + gapBetweenPanelAndVisualization,
	        yCoordOfSecondLocation + gapBetweenPanelAndVisualization
	        );
    	}
    }
}
