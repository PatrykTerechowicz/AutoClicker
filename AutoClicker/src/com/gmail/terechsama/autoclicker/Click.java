package com.gmail.terechsama.autoclicker;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class Click extends Thread {
	private boolean running;
	private Robot robot;
	private long interval;
	private int button;
	public Click(Robot robot2, long interval, int button) {
		this.setDaemon(true);
		running = true;
		this.robot = robot2;
		this.interval = interval;
		this.button = button;
	}
	
	@Override
	public void run() {
		long currentTime = System.currentTimeMillis();
		long previousTime = System.currentTimeMillis();
		while(running) {
			if((currentTime = System.currentTimeMillis())-previousTime >= interval) {
				previousTime=currentTime;
				robot.mousePress(button);
				robot.mouseRelease(button);
			}
		}
	}
	
	public void stopClicking() {
		running = false;
	}
}
