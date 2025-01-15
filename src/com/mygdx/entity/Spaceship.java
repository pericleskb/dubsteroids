package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.dubsteroids.Asteroids;
import com.mygdx.dubsteroids.TextureManager;
import com.mygdx.physics.TwoDFriction;

public class Spaceship extends Entity{
 
	long lastTimefiredSprite;
	public Array<Bullet> bullets = new Array<Bullet>();
	public Sprite fireSprite;
	double forwardAngle,forwardVectorMag;
	
	public Spaceship(int posX, int posY, int dirX, int dirY, Texture texture){
		super(posX, posY, dirX, dirY, texture);
		this.lastTimefiredSprite = TimeUtils.millis();
		fireSprite = new Sprite(TextureManager.FIRE);
		forwardAngle = MathUtils.atan2(sprite.getHeight()/2 + fireSprite.getHeight()/2, sprite.getWidth()/2);
		forwardVectorMag = Math.sqrt(Math.pow(sprite.getWidth()/2, 2) + Math.pow(sprite.getHeight()/2 + fireSprite.getHeight()/2, 2));
	}

	@Override
	public void update(Asteroids game) {
		if(game.spaceshipNotCrashed){
			super.update(game);

			if(Gdx.input.isKeyPressed(Keys.LEFT) && Gdx.input.isKeyPressed(Keys.RIGHT))
				sprite.setRotation(sprite.getRotation());
			else if(Gdx.input.isKeyPressed(Keys.LEFT))
				sprite.setRotation((sprite.getRotation()+6) % 360);
			else if(Gdx.input.isKeyPressed(Keys.RIGHT))
				sprite.setRotation((sprite.getRotation()-6) % 360);
			
			if(Gdx.input.isKeyPressed(Keys.UP)){
				TwoDFriction newVector = new TwoDFriction(Math.cos(Math.toRadians(sprite.getRotation()+90)),
						Math.sin(Math.toRadians(sprite.getRotation()+90)),30);
				game.movementVector.add(newVector);
				if(game.spaceshipNotCrashed)
					game.soundManager.playMusic(game.soundManager.getEngineSound());
			}
			else{
				game.soundManager.stopMusic(game.soundManager.getEngineSound());
			}
			
			setDirection( game.movementVector.getSpeedX(), game.movementVector.getSpeedY());
			
			if(Gdx.input.isKeyPressed(Keys.SPACE) && (TimeUtils.millis() - lastTimefiredSprite) > 150 && bullets.size < 4){
				float x = (float)((double)pos.x + (Math.cos(Math.toRadians(sprite.getRotation()+90)) * (-8)) +
						Math.abs(Math.sin(Math.toRadians(sprite.getRotation()+90))) * (-1) +
						((0.5*(Math.cos(Math.toRadians(sprite.getRotation()+90))*sprite.getWidth()) + (0.5 * sprite.getWidth()))));
				float y = (float) ((double)pos.y + Math.sin(Math.toRadians(sprite.getRotation()+90)) * (-10) +
						Math.abs(Math.cos(Math.toRadians(sprite.getRotation()+90))) * (-4) + 
						((0.5*(Math.sin(Math.toRadians(sprite.getRotation()+90))*sprite.getHeight()) + (0.5 * sprite.getHeight()))));
				bullets.add(new Bullet(x,y,
						9 * (float)(Math.cos(Math.toRadians(sprite.getRotation()+90))),
						9 * (float)(Math.sin(Math.toRadians(sprite.getRotation()+90))), TextureManager.BULLET, sprite.getRotation()));
				lastTimefiredSprite = TimeUtils.millis();
				game.soundManager.playSound(game.soundManager.getPlayerfireSprite());
			}
		}
		
		for(Bullet b : bullets){
			b.update(game);
			if(b.checkEnd())
				bullets.removeValue(b, false);
		}
	}

	@Override
	public void render(SpriteBatch sb){

		
		
		float angle = (float) (sprite.getRotation() + 139 - Math.toDegrees(forwardAngle));
		float x = (float) ((float) (Math.cos(Math.toRadians(angle)) * forwardVectorMag) - Math.cos(Math.toRadians(sprite.getRotation()+90)) * 10);
		float y = (float) ((float) (Math.sin(Math.toRadians(angle)) * forwardVectorMag) - Math.sin(Math.toRadians(sprite.getRotation()+90)) * 10);
		if(Gdx.input.isKeyPressed(Keys.UP)){
			sb.draw(fireSprite, pos.x + sprite.getWidth()/2 - x, pos.y + sprite.getHeight()/2 - y, 0, 0,
					fireSprite.getWidth(), fireSprite.getHeight(), 1, 1, sprite.getRotation());
		}
		sb.draw(sprite,pos.x,pos.y,
				sprite.getWidth()/2,sprite.getHeight()/2,
				sprite.getWidth(),sprite.getHeight(),
				1,1,sprite.getRotation());
		for(int i=0; i<bullets.size ; i++){
			bullets.get(i).render(sb);
		}
	}
	
	public void setTexture(Texture newTexture){
		float rotation = sprite.getRotation();
		texture = newTexture;
		sprite = new Sprite(texture);
		sprite.setRotation(rotation);
	}
	
}
