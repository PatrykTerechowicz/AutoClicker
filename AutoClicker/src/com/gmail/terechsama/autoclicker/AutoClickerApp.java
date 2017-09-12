package com.gmail.terechsama.autoclicker;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import com.gmail.terechsama.autoclicker.model.Keybind;
import com.gmail.terechsama.autoclicker.model.Keybinds;
import com.gmail.terechsama.autoclicker.utils.JavaFxDispatchService;
import com.gmail.terechsama.autoclicker.view.RootLayoutController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AutoClickerApp extends Application implements NativeKeyListener, NativeMouseListener{
	private static final String LAYOUT_LOCATION = "view/rootLayout.fxml";
	private Robot robot;
	private RootLayoutController controller;
	private Stage primaryStage;
	private ObservableSet<Integer> keysPressed;

	@Override
	public void start(Stage primaryStage) throws Exception {
		initializeNativeHook();
		this.primaryStage = primaryStage;
		initializeLayout(primaryStage);
		initializeKeybinds();
		System.out.println("Lol");
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void initializeNativeHook() {
		try {
			GlobalScreen.registerNativeHook();
		}catch(NativeHookException e) {
			e.printStackTrace();
		}
		GlobalScreen.addNativeKeyListener(this);
		GlobalScreen.setEventDispatcher(new JavaFxDispatchService());
		Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.WARNING);;
	}
	
	private void initializeKeybinds() {
		TreeSet<Integer> keysPressedSet = new TreeSet<>();
		keysPressed = FXCollections.observableSet(keysPressedSet);
		new Keybinds(keysPressed, controller);
	}
	
	private void initializeLayout(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(LAYOUT_LOCATION));
		Parent parent = loader.load();
		controller = loader.getController();
		controller.setApplication(this);
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.show(); 
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
		keysPressed.add(nativeEvent.getKeyCode());
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
		keysPressed.remove(nativeEvent.getKeyCode());
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
