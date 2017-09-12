package com.gmail.terechsama.autoclicker.view;

import java.awt.event.InputEvent;

import com.gmail.terechsama.autoclicker.AutoClickerApp;
import com.gmail.terechsama.autoclicker.Click;
import com.gmail.terechsama.autoclicker.MouseWatcher;
import com.gmail.terechsama.autoclicker.model.Point;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class RootLayoutController {


    @FXML
    private RadioButton atCoordinatesRadioButton;

    @FXML
    private RadioButton atMouseRadioButton;

    @FXML
    private TextField xCoordinateField;

    @FXML
    private TextField yCoordinateField;

    @FXML
    private TextField intervalField;
    
    @FXML
    private TextField randomIntervalField;

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
    
    @FXML
    private Label xCoordinateLabel;
    
    @FXML
    private Label yCoordinateLabel;
    
    private AutoClickerApp application;
    
    private Click clickThread;
    
    private MouseWatcher mouseWatcherThread;
    
    public void setApplication(AutoClickerApp app) {
    	application = app;
    }
	
    public void setCoordinateLabel(int x, int y) {
    	xCoordinateLabel.setText(Integer.toString(x));
    	yCoordinateLabel.setText(Integer.toString(y));
    }
    
    public boolean isFocused() {
    	return application.getPrimaryStage().focusedProperty().get();
    }
	
	public void initialize() {
		initializeAtCoordinatesRadioButtons();
		configureStartButton();
		configureStopButton();
		initializeMouseWatcher();
	}
	
	public void initializeMouseWatcher() {
		mouseWatcherThread = new MouseWatcher(this);
		mouseWatcherThread.start();
	}
	
	public void pauseClicking() {
		if(clickThread != null)
			clickThread.pauseClicking();
	}
	
	public void resumeClicking() {
		if(clickThread != null)
			clickThread.resumeClicking();
	}
	
	private void initializeAtCoordinatesRadioButtons() {
		disableCoordinates();
		atMouseRadioButton.selectedProperty().addListener((obsrvable, oldValue, newValue) -> {
			if(newValue) {
				disableCoordinates();
			}else {
				enableCoordinates();
			}
		});
	}
	
	private void disableCoordinates() {
		xCoordinateField.setDisable(true);
		yCoordinateField.setDisable(true);
	}
	
	private void enableCoordinates() {
		xCoordinateField.setDisable(false);
		yCoordinateField.setDisable(false);
	}
	
	private void configureStartButton() {
		startButton.setOnAction(x ->{
			startClicking();
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
		long interval;
		String readIntervalField = intervalField.getText();
		interval = Long.parseLong(readIntervalField.trim());
		if(interval <= 0) {
			throw new NumberFormatException();
		}
		return interval;
	}
	
	private Click createClickThread() {
		long interval = 0;
		int randomInterval = 0;
		try {
			interval = getInterval();
		}catch(NumberFormatException e) {
			intervalField.setText("Type positive number");
			return null;
		}
		try {
			randomInterval = getRandomInterval();
		}catch(NumberFormatException e) {
			randomIntervalField.setText("Type number");
			return null;
		}
		Click createdClickThread = new Click(application.getRobot(), interval, getButtonMask(), randomInterval);
		if(atCoordinatesRadioButton.isSelected()) {
			try {
				createdClickThread.setPoint(getCoordinates());
			}catch(NumberFormatException e) {
				xCoordinateField.setText("INTEGER");
				yCoordinateField.setText("INTEGER");
				return null;
			}
		}
		return createdClickThread;
	}
	
	private Point getCoordinates() throws NumberFormatException{
		int x, y;
		x = Integer.parseInt(xCoordinateField.getText().trim());
		y = Integer.parseInt(yCoordinateField.getText().trim());
		return new Point(x, y);
	}
	
	private int getRandomInterval() {
		int randInterval;
		String randIntervalString = randomIntervalField.getText();
		randInterval = Integer.parseInt(randIntervalString);
		if(randInterval < 0) {
			throw new NumberFormatException();
		}
		return randInterval;
	}
	
	private int getButtonMask() {
		if(leftRadioButton.isSelected())
			return InputEvent.BUTTON1_DOWN_MASK;
		if(rightRadioButton.isSelected())
			return InputEvent.BUTTON3_DOWN_MASK;
		return InputEvent.BUTTON2_DOWN_MASK;
	}

	public void stopClicking() {
		if(clickThread != null)
			clickThread.stopClicking();
	}

	public void startClicking() {
		if(clickThread != null) {
			clickThread.stopClicking();
		}
		clickThread = createClickThread();
		if(clickThread != null) {
			clickThread.start();
		}
	}

}
