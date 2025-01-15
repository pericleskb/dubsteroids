package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class AsteroidSmall extends Asteroid {

	public AsteroidSmall(float posX, float posY, float dirX, float dirY,
			Texture texture) {
		super(posX, posY, dirX, dirY, texture);
		this.value = 100;
		this.crashingSound = Gdx.audio.newSound(Gdx.files.internal("small-asteroid-bang.wav"));
	}

	@Override
	public int crashed(Asteroid a, Array<Asteroid> entities, Entity b) {
		entities.removeValue(a, false);
		return value;
	}

}
