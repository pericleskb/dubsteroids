package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.dubsteroids.TextureManager;

public class EnemySmall extends Enemy {

	private Vector2 pointOfFire;
	private Vector2 shootingVector;
	Sound  enemyFire;
	
	public EnemySmall(float posX, float posY, float dirX, float dirY,
			Texture texture) {
		super(posX, posY, dirX, dirY, texture);
		this.value = 1000;
		enemyFire = Gdx.audio.newSound(Gdx.files.internal("enemy-shoot.mp3"));
	}

	@Override
	public void shoot(EntityManager manager) {
		lastTimeFired = TimeUtils.millis();
		shootingVector = new Vector2(manager.getSpaceship().pos);
		shootingVector.x = shootingVector.x - pos.x + manager.getSpaceship().sprite.getWidth()/2;
		shootingVector.y = shootingVector.y - pos.y + manager.getSpaceship().sprite.getHeight()/2;
		float theta = MathUtils.atan2(shootingVector.y, shootingVector.x);
		shootingVector.x = (float) (Math.cos(theta) * 5);
		shootingVector.y = (float) (Math.sin(theta) * 5);
		pointOfFire = new Vector2();
		pointOfFire.x = (float) (pos.x + (Math.cos(theta) *(sprite.getWidth()/2)));
		pointOfFire.y = (float) (pos.y + (Math.sin(theta) *(sprite.getHeight()/2)));

		manager.setEnemyBullet(new BulletEnemy(pointOfFire.x,pointOfFire.y,
				(float)0.9*shootingVector.x, (float)0.9*shootingVector.y,
				TextureManager.ENEMY_BULLET,sprite.getRotation()));
		long id = enemyFire.play();
		enemyFire.setVolume(id,(float) 0.6);
	}

}
