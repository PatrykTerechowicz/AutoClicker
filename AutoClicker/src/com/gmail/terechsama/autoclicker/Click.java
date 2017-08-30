package com.gmail.terechsama.autoclicker;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class Click extends Thread {
	private boolean running;
	private Robot robot;
	private long interval;
	public Click(Robot robot2, long interval) {
		this.setDaemon(true);
		running = true;
		this.robot = robot2;
		this.interval = interval;
	}
	
	@Override
	public void run() {
		long currentTime = System.currentTimeMillis();
		long previousTime = System.currentTimeMillis();
		while(running) {
			if((currentTime = System.currentTimeMillis())-previousTime >= interval) {
				previousTime=currentTime;
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			}
		}
	}
	
	public void stopClicking() {
		running = false;
	}
}
