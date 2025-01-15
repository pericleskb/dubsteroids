package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.dubsteroids.TextureManager;

public class AsteroidMedium extends Asteroid {

	public AsteroidMedium(float posX, float posY, float dirX, float dirY,
			Texture texture) {
		super(posX, posY, dirX, dirY, texture);
		this.value = 50;
		this.crashingSound = Gdx.audio.newSound(Gdx.files.internal("medium-asteroid-bang.wav"));
	}

	@Override
	public int crashed(Asteroid a, Array<Asteroid> entities, Entity b) {
		super.crashed(a, entities, b);
		entities.add(new AsteroidSmall(a.pos.x,a.pos.y,
				 dir1.x*(float)1.5,dir1.y*(float)1.5,
					TextureManager.ASTEROID_SMALL));
		entities.add(new AsteroidSmall(a.pos.x,a.pos.y,
				 dir2.x*(float)1.5,dir2.y*(float)1.5,
					TextureManager.ASTEROID_SMALL));
		entities.removeValue(a, false);
		return value;
	}

}
