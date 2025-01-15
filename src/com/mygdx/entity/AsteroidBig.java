package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.dubsteroids.TextureManager;

public class AsteroidBig extends Asteroid{

	public AsteroidBig(float posX, float posY, float dirX, float dirY,
			Texture texture) {
		super(posX, posY, dirX, dirY, texture);
		this.value = 20;
		this.crashingSound = Gdx.audio.newSound(Gdx.files.internal("big-asteroid-bang.wav"));
	}

	public int crashed(Asteroid a, Array<Asteroid> entities, Entity b){
		super.crashed(a, entities, b);
		entities.add(new AsteroidMedium(a.pos.x,a.pos.y,
				(float) (dir1.x*(float)1.5), (float) (dir1.y*(float)1.5),
					TextureManager.ASTEROID_MEDIUM));
		entities.add(new AsteroidMedium(a.pos.x,a.pos.y,
				(float)(dir2.x*(float)1.5), (float)(dir2.y*(float)1.5),
					TextureManager.ASTEROID_MEDIUM));
		entities.removeValue(a, false);
		return value;
	}
}
