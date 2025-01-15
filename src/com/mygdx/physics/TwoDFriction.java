package com.mygdx.physics;

import com.badlogic.gdx.math.Vector2;

public class TwoDFriction {

	Vector2 direction;
	Vector2 speedPerVector;
	float speed;
	
	public TwoDFriction(double cos, double sin, int speed){
		this.direction = new Vector2();
		this.direction.x = (float) cos;
		this.direction.y = (float) sin;
		this.speedPerVector = new Vector2();
		this.speedPerVector.x =(float) ((speed/5) * cos);
		this.speedPerVector.y =(float) ((speed/5) * sin);
		this.speed = speed;
	}
	
	public void update(){
		double angle;
		if(speed > 100)
			speed -= 2;
		else if(speed > 50)
			speed -= 1;
		else if(speed >0){
			speed -=0.5;
		}
		else
			speed =0;
		if(speed != 0){
			angle = Math.atan2(speedPerVector.y, speedPerVector.x);
			direction.x = (float) Math.cos(angle);
			direction.y = (float) Math.sin(angle);
		}
		if(speed > 350){
			speed = 350;
		}
		speedPerVector.x = (float) ((speed) * direction.x);
		speedPerVector.y = (float) ((speed) * direction.y);
	}
	
	public boolean checkEnd(){
		if(this.speed <= 0)
			return true;
		else
			return false;
	}
	
	public void add(TwoDFriction newVector){
		this.speedPerVector.x = (this.speedPerVector.x + ((newVector.speed/5) * newVector.direction.x));
		this.speedPerVector.y = (this.speedPerVector.y + ((newVector.speed/5) * newVector.direction.y));
		this.speed = (float) Math.sqrt(Math.pow(this.speedPerVector.x, 2) + Math.pow(this.speedPerVector.y, 2));
	}
	
	public float getSpeedX(){
		return this.speedPerVector.x;
	}
	
	public float getSpeedY(){
		return this.speedPerVector.y;
	}
	
	public Vector2 getDirection() {
		return direction;
	}

	public void setDirection(Vector2 direction) {
		this.direction = direction;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
