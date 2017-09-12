package com.gmail.terechsama.autoclicker;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import com.gmail.terechsama.autoclicker.utils.JavaFxDispatchService;
import com.gmail.terechsama.autoclicker.view.RootLayoutController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AutoClickerApp extends Application implements NativeKeyListener, NativeMouseListener{
	private static final String LAYOUT_LOCATION = "view/rootLayout.fxml";
	private Robot robot;
	private RootLayoutController controller;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		initializeLayout(primaryStage);
	}

	public static void main(String[] args) {
		try {
			GlobalScreen.registerNativeHook();
		}catch(NativeHookException e) {
			e.printStackTrace();
		}
		GlobalScreen.setEventDispatcher(new JavaFxDispatchService());
		Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.WARNING);;
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
		GlobalScreen.addNativeKeyListener(this);
		primaryStage.setOnCloseRequest((x) -> {
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}); 
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

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
		if(nativeEvent.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					controller.pauseClicking();
				}
			});
		}else if(nativeEvent.getKeyCode() == NativeKeyEvent.VC_SHIFT) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					controller.stopClicking();
				}
			});
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
		if(nativeEvent.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					controller.resumeClicking();
				}
			});
		}
	}
	
	@Override
	public void nativeMouseClicked(NativeMouseEvent nativeEvent) {

	}

	@Override
	public void nativeMousePressed(NativeMouseEvent nativeEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
		// TODO Auto-generated method stub
		
	}
}
