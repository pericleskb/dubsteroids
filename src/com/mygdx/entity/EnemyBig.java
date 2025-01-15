package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.dubsteroids.TextureManager;

public class EnemyBig extends Enemy{

	private Vector2 pointOfFire;
	private Vector2 shootingVector;
	Sound enemyFire;
	
	public EnemyBig(float posX, float posY, float dirX, float dirY,
			Texture texture) {
		super(posX, posY, dirX, dirY, texture);
		this.value = 200;
		enemyFire = Gdx.audio.newSound(Gdx.files.internal("enemy-shoot.mp3"));
	}

	@Override
	public void shoot(EntityManager manager) {
			lastTimeFired = TimeUtils.millis();
			int random = MathUtils.random(0, 7);
			switch(random){
				case 0:
					shootingVector = new Vector2(1,0);
					pointOfFire = new Vector2(pos.x + sprite.getWidth(), 
							pos.y + sprite.getHeight()/2);
					break;
				case 1:
					shootingVector = new Vector2(0,1);
					pointOfFire = new Vector2(pos.x + sprite.getWidth()/2, 
							pos.y + sprite.getHeight());
					break;
				case 2:
					shootingVector = new Vector2(-1,0);
					pointOfFire = new Vector2(pos.x, 
							pos.y + sprite.getHeight()/2);
					break;
				case 3:
					shootingVector = new Vector2(0,-1);
					pointOfFire = new Vector2(pos.x + sprite.getWidth()/2, 
							pos.y);
					break;
				case 4:
					shootingVector = new Vector2((float)0.707,(float)0.707);
					pointOfFire = new Vector2(pos.x + sprite.getWidth(), 
							pos.y + sprite.getHeight());
					break;
				case 5:
					shootingVector = new Vector2((float)-0.707,(float)0.707);
					pointOfFire = new Vector2(pos.x, 
							pos.y + sprite.getHeight());
					break;
				case 6:
					shootingVector = new Vector2((float)0.707,(float)-0.707);
					pointOfFire = new Vector2(pos.x + sprite.getWidth(), 
							pos.y);
					break;
				case 7:
					shootingVector = new Vector2((float)-0.707,(float)-0.707);
					pointOfFire = new Vector2(pos.x, pos.y);
					break;
				default:
					break;
			}
			manager.setEnemyBullet(new BulletEnemy(pointOfFire.x,pointOfFire.y,
					5 * shootingVector.x,5 * shootingVector.y,
					TextureManager.ENEMY_BULLET,sprite.getRotation()));
			long id = enemyFire.play();
			enemyFire.setVolume(id, 6f);
	}
	
	
}
