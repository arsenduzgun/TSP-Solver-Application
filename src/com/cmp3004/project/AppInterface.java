package com.cmp3004.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.Timer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AppInterface extends JFrame implements Runnable, ActionListener {
    protected JButton searchFileButton;
    protected JTextArea selectedFileArea;
    protected JButton findPathButton;
    protected JTextArea output;
    protected String filePath;
    protected static String fileName;
    protected static String selectedMethod;
    protected Thread thread;
    protected Timer timer;
    protected float totalDistance;
    protected long start;
    protected long elapsedTime;
    protected String processing;
    protected Location[] locationArray;
    protected MyFileReader myFileReader;
    AppInterface(){
        super("TSP Solver");
        setSize(550, 500);
        setDefaultCloseOperation(AppInterface.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setup();
        setTimer();
        setVisible(true);
    }
    
    public void setup(){
        searchFileButton = new JButton("Choose File");
        searchFileButton.setBounds(50, 50, 110, 24);
        searchFileButton.addActionListener(this);
        add(searchFileButton);
        JLabel selectedFileLabel = new JLabel("Selected File: ");
        selectedFileLabel.setBounds(270, 50, 130, 24);
        add(selectedFileLabel);
        selectedFileArea = new JTextArea();
        selectedFileArea.setEditable(false);
        selectedFileArea.setBounds(370, 53, 120, 19);
        add(selectedFileArea);
        String NOT_SELECTABLE_OPTION = " - Select a method - ";
        String[] NORMAL_OPTION = {"Nearest Neighbor Algorithm", "Greedy Algorithm", "Divide and Conquer Algorithm"};
        JComboBox<String> methodList = new JComboBox<String>();
        
        methodList.setModel(new DefaultComboBoxModel<String>() {
            boolean selectionAllowed = true;
            @Override
            public void setSelectedItem(Object object) {
                if (!NOT_SELECTABLE_OPTION.equals(object)) {
                    super.setSelectedItem(object);
                }
                else if (selectionAllowed) {
                    selectionAllowed = false;
                    super.setSelectedItem(object);
                }
            }
        });
        
        methodList.addItem(NOT_SELECTABLE_OPTION);
        for(int i = 0; i < NORMAL_OPTION.length; i++){
            methodList.addItem(NORMAL_OPTION[i]);
        }
        
        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectedMethod = methodList.getSelectedItem().toString();
            }
        };
        
        methodList.addItemListener(itemListener);
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setBounds(165, 140, 200, 30);
        comboBoxPanel.add(methodList);
        add(comboBoxPanel);
        findPathButton = new JButton("Find Path");
        findPathButton.setBounds(200, 240, 128, 24);
        findPathButton.addActionListener(this);
        add(findPathButton);
        output = new JTextArea();
        output.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(output);
        outputScrollPane.setBounds(0, 300, 537, 164);
        add(outputScrollPane);
    }
    
    public boolean isFilePathExist(){
        return (filePath != null);
    }
    
    public boolean isExtensionAcceptable(String filePath){
        String extension = filePath.substring(filePath.lastIndexOf("."));
        if(extension.equals(".txt") || extension.equals(".tsp")){
            return true;
        }
        return false;
    }
    
    public boolean isMethodSelected(){
        return (selectedMethod != null);
    }
    
    public void analysisStarted() {
    	searchFileButton.setEnabled(false);
    	findPathButton.setText("Stop Processing");
    	processing = "Processing";
        timer.start();
    }
    
    public void analysisFinished() {
    	timer.stop();
    	output.setText("");
    	printOutput();
    	displayVisualization();
    	searchFileButton.setEnabled(true);
    	findPathButton.setText("Find Path");
    }
    
    public void printOutput() {
    	output.append("Name of the file: " + fileName +
 		       		  "\n\n" + "Number of locations: " + locationArray.length +
 		       		  "\n\n" + "Applied algorithm: " + selectedMethod +
 		       		  "\n\n" + "Length of the path: " + totalDistance +
 		       		  "\n\n" + "Elapsed time: " + elapsedTime + " milliseconds" +
 		       		  "\n\n" + "Location order of the minimum length of path found: ");
    	for(int i = 0; i < locationArray.length; i++) {
        	output.append("\n\n" + (i + 1) + "-) Location name: " + locationArray[i].locationName +
        			      " - " + "x Coordinate: " + locationArray[i].xCoordinate +
        			      " - " + "y Coordinate: " + locationArray[i].yCoordinate);
        }
    	output.append("\n\n" + (locationArray.length + 1) + "-) Location name: " + locationArray[0].locationName +
  		      		  " - " + "x Coordinate: " + locationArray[0].xCoordinate +
  		      		  " - " + "y Coordinate: " + locationArray[0].yCoordinate);
    }
    
    public void displayVisualization() {
    	VisualizationPack visualizationPack = new VisualizationPack(myFileReader.xCoordinateMinValue,
			    							  myFileReader.yCoordinateMinValue,
			    							  myFileReader.xCoordMaxDistBetweenLocations,
			    							  myFileReader.yCoordMaxDistBetweenLocations,
			    							  "Locations(" + fileName + ")",
			    							  locationArray);
    	new VisualizationFrame(visualizationPack);
    	visualizationPack.visualizationType = "Path(" + fileName + ")(" + selectedMethod + ")";
    	new VisualizationFrame(visualizationPack);
    }
    
    public void setTimer(){
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(processing.length() == 14){
                    processing = processing.substring(0, 10);
                    output.setText(processing);
                }
                else{
                	output.setText(processing);
                    processing += ".";
                }
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == searchFileButton){
            JFileChooser fileChooser = new JFileChooser();
            if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)){
                String filePathHolder = fileChooser.getSelectedFile().getPath();
                if(isExtensionAcceptable(filePathHolder)){
                    filePath = fileChooser.getSelectedFile().getPath();
                    fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
                    selectedFileArea.setText(fileName);
                    
                }
                else{
                    JOptionPane.showMessageDialog(this, "Selected file must be in .txt or .tsp format.");
                }
            }
        }
        else if(e.getSource() == findPathButton){
            if(isFilePathExist()){
                if(isMethodSelected()){
                	if(!(thread == null) && thread.isAlive()){
                        timer.stop();
                        thread.stop();
                        findPathButton.setText("Find Path");
                        searchFileButton.setEnabled(true);
                        output.setText("");
                    }
                    else{
                        thread = new Thread(this);
                        thread.start();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Please select a method.");
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Please select a file.");
            }
        }
        
    }
    
    @Override
    public void run() {
    	analysisStarted();
        myFileReader = new MyFileReader(filePath);
        locationArray = myFileReader.locationArray;
        start = System.currentTimeMillis();
        if(selectedMethod.equals("Nearest Neighbor Algorithm")) {
        	NearestNeighbor nearestNeighbor = new NearestNeighbor(locationArray);
        	locationArray = nearestNeighbor.locationArray;
        	totalDistance = nearestNeighbor.totalDistance;
        }
        else if(selectedMethod.equals("Greedy Algorithm")) {
        	Greedy greedy = new Greedy(locationArray);
        	locationArray = greedy.locationArray;
        	totalDistance = greedy.totalDistance;
        }
        else {
        	DivideAndConquer divideAndConquer = new DivideAndConquer(locationArray);
        	locationArray = divideAndConquer.locationArray;
        	totalDistance = divideAndConquer.totalDistance;
        }
        elapsedTime = System.currentTimeMillis() - start;
        analysisFinished();
    }
}
