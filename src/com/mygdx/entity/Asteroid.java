package com.mygdx.entity;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.dubsteroids.Asteroids;

public class Asteroid extends Entity{
	
	int value;
	Sound crashingSound;
	Vector2 dir1,dir2;
	
	public Asteroid(int posX, int posY, float dirX, float dirY, 
			Texture texture) {
		super(posX, posY, (float)0.5 * dirX,(float) 0.5 * dirY, texture);
		sprite.setRotation(MathUtils.random(0,359));
	}
	
	public Asteroid(float posX, float posY, float dirX, float dirY, 
			Texture texture) {
		super(posX, posY, dirX, dirY, texture);
		sprite.setRotation(MathUtils.random(0,359));
	}
	

	public int crashed(Asteroid a, Array<Asteroid> entities, Entity b){
		//double objectDegrees = Math.toDegrees(MathUtils.atan2(b.direction.y, b.direction.x));
		double asteroidsDegrees = Math.toDegrees( MathUtils.atan2(a.direction.y, a.direction.x));
		dir1 = generateDirections1(asteroidsDegrees);
		dir2 = generateDirections2(asteroidsDegrees);
//TODO
	/*	With this the asteroids are heavily influenced by the direction of the bullet
	 * We could call this from MediumAsteroid alone
	 * if(asteroidsDegrees - 180 + 40 > objectDegrees && asteroidsDegrees - 180 - 40 < objectDegrees){
			dir1 = generateDirections(asteroidsDegrees);
			dir2 = generateDirections(asteroidsDegrees);
		}
		else{
			float newVectorX = (b.direction.x + a.direction.x)/2;
			float newVectorY = (b.direction.y + a.direction.y)/2;
			double newAngle = Math.toDegrees(MathUtils.atan2(newVectorY, newVectorX));
			dir1 = generateDirections(newAngle);
			dir2 = generateDirections(newAngle);
		}*/
		return 0;
	}

	@Override
	public void render(SpriteBatch sb){
		sb.draw(sprite,pos.x,pos.y,texture.getWidth()/2,
				texture.getHeight()/2,
				texture.getWidth(),
				texture.getHeight(),1,1,sprite.getRotation());
	}
	
	@Override
	public void update(Asteroids game) {
		super.update(game);
		//System.out.println(this.direction);
		//sprite.setRotation((sprite.getRotation()+1) % 360);
	}

	public int getValue() {
		return value;
	}

	public Sound getCrashingSound() {
		return crashingSound;
	}
	
	Vector2 generateDirections1(double angleDeggres){
		Vector2 temp = new Vector2();
		angleDeggres += MathUtils.random(-65, 25);
		temp.x = (float) Math.cos(Math.toRadians(angleDeggres));
		temp.y = (float) Math.sin(Math.toRadians(angleDeggres));
		return temp;
	}
	
	Vector2 generateDirections2(double angleDeggres){
		Vector2 temp = new Vector2();
		angleDeggres += MathUtils.random(-25, 65);
		temp.x = (float) Math.cos(Math.toRadians(angleDeggres));
		temp.y = (float) Math.sin(Math.toRadians(angleDeggres));
		return temp;
	}
}
