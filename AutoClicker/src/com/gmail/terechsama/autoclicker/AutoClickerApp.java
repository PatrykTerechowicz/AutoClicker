package com.gmail.terechsama.autoclicker;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AutoClickerApp extends Application {
	private static final String LAYOUT_LOCATION = "com/gmail/terechsama/autoclicker/view/rootLayout.fxml";

	@Override
	public void start(Stage primaryStage) throws Exception {
		initializeLayout(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void initializeLayout(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource(LAYOUT_LOCATION));
		Parent parent = loader.load();
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
