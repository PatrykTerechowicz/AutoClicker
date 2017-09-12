package com.gmail.terechsama.autoclicker.model;

import java.util.Set;

import com.gmail.terechsama.autoclicker.view.RootLayoutController;

import javafx.application.Platform;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

public class Keybinds {
	private Keybind stopKeybind;
	private Keybind pauseKeybind;
	private Keybind resumeKeybind;
	private Keybind startKeybind;
	private ObservableSet<Integer> keysPressed;
	
	public Keybinds(ObservableSet<Integer> keysPressed, RootLayoutController controller) {
		stopKeybind = new Keybind(42, 44) { 
			@Override
			public void doJob() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						controller.stopClicking();
					}
				});
			}
		};
		startKeybind = new Keybind(29, 45) {
			@Override
			public void doJob() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						controller.startClicking();
					}
				});
			}
		};
		pauseKeybind = new Keybind(29, 48) {
			@Override
			public void doJob() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						controller.pauseClicking();
					}
				});
			}
		};
		resumeKeybind = new Keybind(29, 49) {
			@Override
			public void doJob() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						controller.resumeClicking();
					}
				});
			}
		};
		this.keysPressed = keysPressed;
		keysPressed.addListener(new SetChangeListener<Integer>() {
			@Override
			public void onChanged(Change change) {
				if(startKeybind.isKeybindActive(keysPressed)) {
					startKeybind.doJob();
				}
				if(stopKeybind.isKeybindActive(keysPressed)) {
					stopKeybind.doJob();
				}
				if(resumeKeybind.isKeybindActive(keysPressed)) {
					resumeKeybind.doJob();
				}
				if(pauseKeybind.isKeybindActive(keysPressed)) {
					pauseKeybind.doJob();
				}
			}
		});
	}
	
	public void changeKeybind(String keybindName, int... keys) {
		switch(keybindName) {
			case "stop":
				stopKeybind.setKeys(keys);
				break;
			case "pause":
				pauseKeybind.setKeys(keys);
				break;
			case "start":
				startKeybind.setKeys(keys);
				break;
			case "resume":
				resumeKeybind.setKeys(keys);
				break;
			default:
				break;
		}
	}
}
