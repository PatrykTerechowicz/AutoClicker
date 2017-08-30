package com.gmail.terechsama.autoclicker;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;

import com.gmail.terechsama.autoclicker.view.RootLayoutController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AutoClickerApp extends Application {
	private static final String LAYOUT_LOCATION = "view/rootLayout.fxml";
	private Robot robot;
	private RootLayoutController controller;

	@Override
	public void start(Stage primaryStage) throws Exception {
		initializeLayout(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void initializeLayout(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(LAYOUT_LOCATION));
		Parent parent = loader.load();
		controller = loader.getController();
		controller.setApplication(this);
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public Robot getRobot() {
		if(robot == null) {
			try {
				robot = new Robot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return robot;
	}
	
	@Override
	public void stop() throws Exception {
		controller.stopClickThread();
	}
}
