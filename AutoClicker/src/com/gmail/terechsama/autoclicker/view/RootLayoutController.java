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
    private TextField xCoordinateField;

    @FXML
    private TextField yCoordinateField;

    @FXML
    private TextField intervalField;

    @FXML
    private RadioButton leftRadioButton;

    @FXML
    private ToggleGroup mouseButton;

    @FXML
    private RadioButton middleRadioButton;

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
			try {
				clickThread = new Click(application.getRobot(), this.getInterval(), getButtonMask());
			}catch(NumberFormatException e) {
				x.consume();
				return;
			}
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
	
	private long getInterval() throws NumberFormatException{
		long interval = 200;
		String readIntervalField = intervalField.getText();
		interval = Long.parseLong(readIntervalField.trim());
		return interval;
	}
	
	private int getButtonMask() {
		if(leftRadioButton.isSelected())
			return InputEvent.BUTTON1_DOWN_MASK;
		if(rightRadioButton.isSelected())
			return InputEvent.BUTTON3_DOWN_MASK;
		return InputEvent.BUTTON2_DOWN_MASK;
	}
}
