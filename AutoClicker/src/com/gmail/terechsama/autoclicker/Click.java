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
	private boolean pause;
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
		int random = 0;
		while(running) {
				if(point != null) {
					robot.mouseMove(point.getX(), point.getY());
				}
				if(!pause) {
					robot.mousePress(button);
					robot.mouseRelease(button);
				}
				if(randomInterval > 0)
					random = rand.nextInt(randomInterval);
				try {
					Thread.sleep(interval+random);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	
	public void pauseClicking() {
		pause = true;
	}
	
	public void resumeClicking() {
		pause = false;
	}
}
