package com.gmail.terechsama.autoclicker.view;

import java.awt.event.InputEvent;

import com.gmail.terechsama.autoclicker.AutoClickerApp;
import com.gmail.terechsama.autoclicker.Click;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class RootLayoutController {

    @FXML
    private CheckBox atMouseCheckBox;
    
    @FXML
    private Label xLabel;

    @FXML
    private Label yLabel;

    @FXML
    private TextField yCoordinateField;

    @FXML
    private TextField xCoordinateField;

    @FXML
    private TextField intervalField;

    @FXML
    private RadioButton leftRadioButton;

    @FXML
    private ToggleGroup mouseButton;

    @FXML
    private RadioButton rightRadioButton;
    
    @FXML
    private Button startButton;
    
    @FXML
    private Button stopButton;
    
    private AutoClickerApp application;
    
    private Click clickThread;
    
    public void setApplication(AutoClickerApp app) {
    	application = app;
    }
	
	
	public void initialize() {
		configureStartButton();
		configureStopButton();
	}
	
	private void configureStartButton() {
		startButton.setOnAction(x ->{
			if(clickThread != null) {
				clickThread.stopClicking();
			}
			clickThread = new Click(application.getRobot(), this.getInterval());
			clickThread.start();
		});
	}
	
	private void configureStopButton() {
		stopButton.setOnAction(x -> {
			if(clickThread != null) {
				clickThread.stopClicking();
			}
		});
	}
	
	private long getInterval() {
		long interval = 200;
		String readIntervalField = intervalField.getText();
		try{
			interval = Long.parseLong(readIntervalField.trim());
		}catch(NumberFormatException e) {
			interval = 200;
			intervalField.setText("HAS BE NUMBER");
		}
		return interval;
	}
}
