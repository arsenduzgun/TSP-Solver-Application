package com.cmp3004.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyFileReader {
    protected Location[] locationArray;
    protected float xCoordinateMinValue;
    protected float yCoordinateMinValue;
    protected float xCoordMaxDistBetweenLocations;
    protected float yCoordMaxDistBetweenLocations;
    MyFileReader(String filePath){
        readFile(filePath);
    }
    public void readFile(String filePath){
        Scanner scanner;
        try{
            scanner = new Scanner(new File(filePath));
            xCoordinateMinValue = Float.MAX_VALUE;
            yCoordinateMinValue = Float.MAX_VALUE;
            float xCoordinateMaxValue = Float.MIN_VALUE;
            float yCoordinateMaxValue = Float.MIN_VALUE;
            String line;
            String lineParsed[];
            int index = 0;
            while(scanner.hasNextLine()){
            	line = scanner.nextLine();
            	if(line.contains("DIMENSION")) {
            		lineParsed = line.split(" ");
            		int size = Integer.parseInt(lineParsed[lineParsed.length - 1]);
            		locationArray = new Location[size];
            		break;
            	}
            }
            while(scanner.hasNextLine()) {
            	if(scanner.nextLine().equals("NODE_COORD_SECTION")){
            		break;
                }
            }
            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                if(!line.equals("EOF")){
                    lineParsed = line.split(" ");
                    String locationName = lineParsed[0];
                    float xCoordinate = Float.parseFloat(lineParsed[1]);
                    float yCoordinate = Float.parseFloat(lineParsed[2]);
                    if(xCoordinate < xCoordinateMinValue) {
                    	xCoordinateMinValue = xCoordinate;
                    }
                    if(xCoordinate > xCoordinateMaxValue) {
                    	xCoordinateMaxValue = xCoordinate;
                    }
                    if(yCoordinate < yCoordinateMinValue) {
                    	yCoordinateMinValue = yCoordinate;
                    }
                    if(yCoordinate > yCoordinateMaxValue) {
                    	yCoordinateMaxValue = yCoordinate;
                    }
                    locationArray[index] = new Location(locationName, xCoordinate, yCoordinate);
                    index++;
                }
                else {
                	break;
                }
            }
            xCoordMaxDistBetweenLocations = xCoordinateMaxValue - xCoordinateMinValue;
            yCoordMaxDistBetweenLocations = yCoordinateMaxValue - yCoordinateMinValue;
        } catch (FileNotFoundException ex) {}
    }
}
