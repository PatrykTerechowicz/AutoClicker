package com.gmail.terechsama.autoclicker.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class Keybind {
	private List<Integer> keys;
	
	public Keybind(int... keys) {
		this.keys = new ArrayList<Integer>();
		setKeys(keys);
	}
	
	public boolean isKeybindActive(Collection<Integer> keysPressed) {
		boolean result = false;
		for(int key: keys) {
			if(keysPressed.contains(key)) {
				result = true;
			}else {
				result = false;
				break;
			}
		}
		return result;
	}
	
	public void setKeys(int... keys) {
		for(int key: keys) {
			this.keys.add(key);
		}
	}
	
	public abstract void doJob();
	
	
}
