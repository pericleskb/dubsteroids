package com.mygdx.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

public class BulletEnemy extends Bullet{

	public BulletEnemy(Bullet enemyBullet) {
		super(enemyBullet);
		// TODO Auto-generated constructor stub
	}
	
	public BulletEnemy(float x, float y, float f, float g,
			Texture eNEMY_BULLET, float rotation) {
		super(x, y, f, g, eNEMY_BULLET, rotation);
	}

	public boolean checkEnd(){
		if(TimeUtils.timeSinceMillis(timeCreated) > 1000)
			return true;
		else
			return false;
	}

}
