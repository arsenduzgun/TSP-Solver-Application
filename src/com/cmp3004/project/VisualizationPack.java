package com.cmp3004.project;
public class VisualizationPack{
	protected final int gapBetweenFrameAndPanel = 60;
	protected final int gapBetweenPanelAndVisualization = 20;
	protected final float longAxisLength = 600f;
	protected float xCoordinateMinValue;
	protected float yCoordinateMinValue;
	protected float xCoordMaxDistBetweenLocations;
	protected float yCoordMaxDistBetweenLocations;
	protected float xAxisPanelVisualizationLength;
	protected float yAxisPanelVisualizationLength;
	protected int xAxisPanelLength;
	protected int yAxisPanelLength;
	protected int xAxisFrameLength;
	protected int yAxisFrameLength;
	protected float resizingValue;
	protected String visualizationType;
	protected Location[] locationArray;
	VisualizationPack(float xCoordinateMinValue, float yCoordinateMinValue,
	float xCoordMaxDistBetweenLocations, float yCoordMaxDistBetweenLocations,
	String visualizationType, Location[] locationArray){
		this.xCoordinateMinValue = xCoordinateMinValue;
		this.yCoordinateMinValue = yCoordinateMinValue;
		this.xCoordMaxDistBetweenLocations = xCoordMaxDistBetweenLocations;
		this.yCoordMaxDistBetweenLocations = yCoordMaxDistBetweenLocations;
		this.visualizationType = visualizationType;
		this.locationArray = locationArray;
		visualizationSizeCalculator();
		calculateResizingValue();
	}
	public void visualizationSizeCalculator() {
    	if(xCoordMaxDistBetweenLocations > yCoordMaxDistBetweenLocations) {
    		xAxisPanelVisualizationLength = longAxisLength;
    		yAxisPanelVisualizationLength = (xAxisPanelVisualizationLength * (yCoordMaxDistBetweenLocations / xCoordMaxDistBetweenLocations));
    	}
    	else {
    		yAxisPanelVisualizationLength = longAxisLength;
    		xAxisPanelVisualizationLength = (yAxisPanelVisualizationLength * (xCoordMaxDistBetweenLocations / yCoordMaxDistBetweenLocations));
    	}
    	xAxisPanelLength = Math.round(xAxisPanelVisualizationLength) + (gapBetweenPanelAndVisualization * 2);
		yAxisPanelLength = Math.round(yAxisPanelVisualizationLength) + (gapBetweenPanelAndVisualization * 2);
		xAxisFrameLength = xAxisPanelLength + (gapBetweenFrameAndPanel * 2);
		yAxisFrameLength = yAxisPanelLength + (gapBetweenFrameAndPanel * 2);
    }
	public void calculateResizingValue() {
    	resizingValue = xCoordMaxDistBetweenLocations / xAxisPanelVisualizationLength;
    }
}