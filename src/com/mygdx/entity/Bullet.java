package com.mygdx.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.dubsteroids.Asteroids;

public class Bullet extends Entity{
	
	long timeCreated;

	public Bullet(float posX, float posY, float dirX, float dirY, 
			Texture texture, float rotation){
		super(posX, posY, dirX, dirY, texture);
		timeCreated = TimeUtils.millis();
		sprite = new Sprite(texture);
		sprite.setRotation(rotation);
	}

	public Bullet(Bullet enemyBullet) {
		super(enemyBullet.pos.x, enemyBullet.pos.y, 
				enemyBullet.direction.x, enemyBullet.direction.y, enemyBullet.texture);
		timeCreated = TimeUtils.millis();
		sprite = new Sprite(enemyBullet.texture);
	}

	@Override
	public void update(Asteroids game){
		super.update(game);
	}
	
	public boolean checkEnd(){
		if(TimeUtils.timeSinceMillis(timeCreated) > 700)
			return true;
		else
			return false;
	}
	
	public void render(SpriteBatch sb){
		sb.draw(sprite,pos.x,pos.y,sprite.getWidth()/2,
				sprite.getHeight()/2,sprite.getWidth(),
				sprite.getHeight(),1,1,sprite.getRotation());
	}
}
