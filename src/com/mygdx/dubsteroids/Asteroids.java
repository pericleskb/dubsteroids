package com.mygdx.dubsteroids;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entity.Asteroid;
import com.mygdx.physics.TwoDFriction;
import com.mygdx.screens.MainMenuScreen;

public class Asteroids extends Game {
	
	public SpriteBatch batch;
	public BitmapFont font;
	public int WIDTH = 800 , HEIGHT = 480; 
	
	public Integer score = 0;
	public int lives;
	public TwoDFriction movementVector;
	public boolean spaceshipNotCrashed = true;
	public SoundManager soundManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		soundManager = new SoundManager();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	
	public void dispose(){
        Gdx.app.exit();
	}
	
	public boolean rectangleFree(Array<Asteroid> entities, Rectangle r){
		for(Asteroid a: entities){
			if(a.getBounds().overlaps(r)){
				return false;
			}
		}
		return true;
	}
}
