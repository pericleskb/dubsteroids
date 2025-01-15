package com.mygdx.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.dubsteroids.Asteroids;

public abstract class Enemy extends Entity{
	
	private long changeDir;
	protected long lastTimeFired;
	int value;

	public Enemy(float posX, float posY, float dirX, float dirY, Texture texture) {
		super(posX, posY, dirX, dirY, texture);
		changeDir = TimeUtils.millis();
		lastTimeFired = changeDir - 1100;
		// TODO Auto-generated constructor stub
	}
	
	public abstract void shoot(EntityManager manager);

	public void render(SpriteBatch sb,EntityManager manager) {
		sb.draw(sprite,pos.x,pos.y,
				sprite.getWidth()/2,sprite.getHeight()/2,
				sprite.getWidth(),sprite.getHeight(),
				1,1,sprite.getRotation());
		if(manager.getEnemyBullet() != null)
			manager.getEnemyBullet().render(sb);
	}
	
	@Override
	public void render(SpriteBatch sb){
		sb.draw(sprite,pos.x,pos.y,
				sprite.getWidth()/2,sprite.getHeight()/2,
				sprite.getWidth(),sprite.getHeight(),
				1,1,sprite.getRotation());
	}
	
	
	public void update(Asteroids game, EntityManager manager){
		if (TimeUtils.millis() - changeDir > 2000){
			changeDirection();
		}
		
		if(pos.y + texture.getHeight()/2 > game.HEIGHT){
			pos.y = - texture.getHeight()/2;
		}
		else if(pos.y + texture.getHeight()/2 < 0){
			pos.y =game.HEIGHT - texture.getHeight()/2;
		}
		
		if(TimeUtils.millis() - lastTimeFired > 1800)
			shoot(manager);

		pos.add(direction);
		
		if((direction.x < 0 && (pos.x + sprite.getWidth()) < 0)){
			manager.enemyHidTime = TimeUtils.millis();
			manager.respawnTime = MathUtils.random(100, 10000);
		}
		if(direction.x > 0 && (pos.x  >= 800) ){
			manager.enemyHidTime = TimeUtils.millis();
			manager.respawnTime = MathUtils.random(100, 10000);
		}
	}

	private void changeDirection(){
		float rand = MathUtils.random(0, 3);
		if(rand < 1)
			this.direction.y = -1;
		else if(rand < 2)	
			this.direction.y = 0;
		else
			this.direction.y = 1;
			
		changeDir = TimeUtils.millis();
	}

	public int getValue() {
		return value;
	}
}
