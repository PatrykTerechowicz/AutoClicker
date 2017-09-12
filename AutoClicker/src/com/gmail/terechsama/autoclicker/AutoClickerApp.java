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
import com.gmail.terechsama.autoclicker.utils.JavaFxDispatchService;
import com.gmail.terechsama.autoclicker.view.RootLayoutController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AutoClickerApp extends Application implements NativeKeyListener, NativeMouseListener{
	private static final String LAYOUT_LOCATION = "view/rootLayout.fxml";
	private Robot robot;
	private RootLayoutController controller;
	private Stage primaryStage;
	private TreeSet<Integer> keysPressed;
	private List<Keybind> keybinds;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			GlobalScreen.registerNativeHook();
		}catch(NativeHookException e) {
			e.printStackTrace();
		}
		keybinds = new ArrayList<Keybind>();
		keybinds.add(new Keybind(44, 29) {
			@Override
			public void doJob() {
				System.out.println("Chuj ci w dupe");
			}
		});
		GlobalScreen.addNativeKeyListener(this);
		GlobalScreen.setEventDispatcher(new JavaFxDispatchService());
		Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.WARNING);;
		keysPressed = new TreeSet<Integer>();
		this.primaryStage = primaryStage;
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
		System.out.println(keysPressed.toString());
		keybinds.forEach((x) -> {
			if(x.isKeybindActive(keysPressed)) {
				x.doJob();
			}
		});
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
		keysPressed.remove(nativeEvent.getKeyCode());
		System.out.println(keysPressed.toString());
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
