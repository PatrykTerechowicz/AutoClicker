package com.gmail.terechsama.autoclicker.view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class RootLayoutController {

    @FXML
    private CheckBox atMouseCheckBox;

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
	
	
	public void initialize() {
		atMouseCheckBox.setOnAction(x -> {
			System.out.println(atMouseCheckBox.isSelected());
		});
	}
}
