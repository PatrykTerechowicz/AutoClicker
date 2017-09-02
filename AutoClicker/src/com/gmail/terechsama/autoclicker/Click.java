package com.gmail.terechsama.autoclicker;

import java.awt.Robot;

import com.gmail.terechsama.autoclicker.model.Point;

public class Click extends Thread {
	private boolean running;
	private Robot robot;
	private long interval;
	private int button;
	private Point point;
	public Click(Robot robot, long interval, int button) {
		this.setDaemon(true);
		running = true;
		this.robot = robot;
		this.interval = interval;
		this.button = button;
		point = null;
	}
	
	@Override
	public void run() {
		long currentTime = System.currentTimeMillis();
		long previousTime = System.currentTimeMillis();
		while(running) {
			if((currentTime = System.currentTimeMillis())-previousTime >= interval) { // && keyBind for stopClicking is not active
				if(point != null) { // && keybind for stop pointer move is not active 
					robot.mouseMove(point.getX(), point.getY());
				}
				previousTime=currentTime;
				robot.mousePress(button);
				robot.mouseRelease(button);
			}
		}
	}
	
	public void setPoint(Point point) {
		this.point = point;
	}
	
	public void freePoint() {
		this.point = null;
	}
	
	public void stopClicking() {
		running = false;
	}
}
