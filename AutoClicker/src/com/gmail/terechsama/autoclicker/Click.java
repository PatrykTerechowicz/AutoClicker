package com.gmail.terechsama.autoclicker;

import java.awt.Robot;
import java.util.Random;

import com.gmail.terechsama.autoclicker.model.Point;

public class Click extends Thread {
	private boolean running;
	private Robot robot;
	private long interval;
	private int button;
	private Point point;
	private int randomInterval;
	public Click(Robot robot, long interval, int button, int randomInterval) {
		this.setDaemon(true);
		running = true;
		this.robot = robot;
		this.interval = interval;
		this.button = button;
		point = null;
		this.randomInterval = randomInterval;
	}
	
	@Override
	public void run() {
		Random rand = new Random();
		long currentTime = System.currentTimeMillis();
		long previousTime = System.currentTimeMillis();
		int random = 0;
		while(running) {
			if((currentTime = System.currentTimeMillis())-previousTime >= (interval+random)) { // && keyBind for stopClicking is not active
				if(point != null) { // && keybind for stop pointer move is not active 
					robot.mouseMove(point.getX(), point.getY());
				}
				previousTime=currentTime;
				robot.mousePress(button);
				robot.mouseRelease(button);
				if(randomInterval > 0)
					random = rand.nextInt(randomInterval);
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
