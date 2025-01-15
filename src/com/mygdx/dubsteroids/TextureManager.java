package com.mygdx.dubsteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TextureManager {
	
	public static Texture PLAYER = new Texture(Gdx.files.internal("aaa.png"));
	public static Sprite SPACESHIP = new Sprite(PLAYER);
	public static Texture FIRE = new Texture(Gdx.files.internal("fire.png"));
	public static Texture BULLET = new Texture(Gdx.files.internal("bullet.png"));
	public static Texture EMPTY = new Texture(Gdx.files.internal("trans.png"));
	public static Sprite transparent = new Sprite(EMPTY);
	
	
	public static Texture ENEMY_BIG = new Texture(Gdx.files.internal("alienCircleMedium.png"));
	public static Texture ENEMY_SMALL = new Texture(Gdx.files.internal("alienCircleSmall.png"));
	public static Texture ENEMY_BULLET = new Texture(Gdx.files.internal("EnemyBullet.png"));

	public static Texture ASTEROID_LARGE = new Texture(Gdx.files.internal("MegaloKotroni.png"));
	public static Texture ASTEROID_MEDIUM = new Texture(Gdx.files.internal("AsteroidBigTest.png"));
	public static Texture ASTEROID_SMALL = new Texture(Gdx.files.internal("AsteroidSmallTest.png"));
	
	public static Texture MENU_BACK = new Texture(Gdx.files.internal("Menu_Background.png"));
	
	public static Texture PLAY = new Texture(Gdx.files.internal("Play.png"));
	public static Texture KEYS = new Texture(Gdx.files.internal("Keys.png"));
	public static Texture BACK = new Texture(Gdx.files.internal("Back.png"));
	public static Texture CREDITS = new Texture(Gdx.files.internal("Credits.png"));
	public static Texture QUIT = new Texture(Gdx.files.internal("Quit.png"));
	public static Texture ARROWS = new Texture(Gdx.files.internal("arrows.png"));
	public static Texture UPARROW = new Texture(Gdx.files.internal("UpArrow.png"));
	public static Texture SOUND = new Texture(Gdx.files.internal("sound.png"));
	public static Texture NO_SOUND = new Texture(Gdx.files.internal("noSound.png"));
}
