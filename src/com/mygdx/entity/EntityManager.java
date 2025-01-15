package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.dubsteroids.Asteroids;
import com.mygdx.dubsteroids.TextureManager;
import com.mygdx.physics.TwoDFriction;

public class EntityManager {
	
	private final Array<Asteroid> entities;
	private final Spaceship spaceship;
	private Enemy enemy;
	private BulletEnemy enemyBullet = null;
	private Rectangle respawnRectangle;
	private long timeCrashed,timeInLevel,timeRespawned;
	private long transShip;
	private boolean firstEnemy = true;
	public long enemyHidTime,respawnTime;
	private int tempScore;
	private boolean invincible;
	
	public EntityManager(Asteroids game, int amount){
		timeInLevel = TimeUtils.millis();
		timeCrashed = timeInLevel;
		game.lives = 3;
		entities = new Array<Asteroid>();
		this.spaceship = new Spaceship(getCentered(game.WIDTH,TextureManager.PLAYER.getWidth()), 
				getCentered(game.HEIGHT,TextureManager.PLAYER.getHeight()),
				0, 0, TextureManager.PLAYER);
		enemy = null;
		enemyHidTime =0;
		respawnTime = 0;
		tempScore =0;
		game.spaceshipNotCrashed = true;
		generateAsteroids(amount);
		
		game.movementVector = new TwoDFriction(Math.cos(Math.toRadians(spaceship.sprite.getRotation()))
				, Math.sin(Math.toRadians(spaceship.sprite.getRotation())), 0);

		respawnRectangle = new Rectangle(game.WIDTH/2 - spaceship.sprite.getWidth()/2,
				game.HEIGHT/2 - spaceship.sprite.getHeight()/2,
				spaceship.sprite.getWidth()/2 + 40, spaceship.sprite.getHeight()/2+40);
	}
	
	public void render(SpriteBatch sb, int amount, Asteroids game){
		spaceship.render(sb);
		for(int i=0; i<entities.size ; i++){
			entities.get(i).render(sb);
		}
		if(enemy != null)
			enemy.render(sb,this);
		if(enemyBullet != null)
			enemyBullet.render(sb);
		
	}
	
	public void update(Asteroids game, int amount){
		
		if(!game.spaceshipNotCrashed && game.rectangleFree(entities, respawnRectangle) && TimeUtils.millis() - timeCrashed > 1000){
			spaceship.setPos(new Vector2(
					getCentered(game.WIDTH,TextureManager.PLAYER.getWidth()), 
					getCentered(game.HEIGHT,TextureManager.PLAYER.getHeight())));
			game.spaceshipNotCrashed = true;
			timeRespawned = TimeUtils.millis();
			transShip = timeRespawned;
			invincible = true;
			//TODO also make unhitable
		}
		if(invincible){
			if(TimeUtils.millis() - transShip > 200){
				if(spaceship.sprite.equals(TextureManager.transparent)){
					float tempRotation = spaceship.sprite.getRotation();
					spaceship.sprite = TextureManager.SPACESHIP;
					spaceship.sprite.setRotation(tempRotation);
					spaceship.fireSprite = new Sprite(TextureManager.FIRE);
				}
				else{
					float tempRotation = spaceship.sprite.getRotation();
					spaceship.sprite = TextureManager.transparent;
					spaceship.sprite.setRotation(tempRotation);
					spaceship.fireSprite = TextureManager.transparent;
				}
				transShip = TimeUtils.millis();
			}
			
			if(TimeUtils.millis() - timeRespawned > 2000){
				invincible = false;
				spaceship.sprite = TextureManager.SPACESHIP;
				spaceship.fireSprite = new Sprite(TextureManager.FIRE);
			}
		}
		
		//this shouldn't be here :(

		if(Gdx.input.isKeyJustPressed(Keys.SHIFT_RIGHT) ||
				Gdx.input.isKeyJustPressed(Keys.SHIFT_LEFT)){
			hyperspace(game);
		}
		
		if(TimeUtils.millis() - timeInLevel > 30000){
			int randomSide,dir;	

			if(MathUtils.randomBoolean()){
				randomSide = 0 - TextureManager.ENEMY_SMALL.getWidth();
				dir = 2;
			}
			else{
				randomSide = 800 + TextureManager.ENEMY_SMALL.getWidth();
				dir = -2;
			}
			
			if(firstEnemy){//firsts enemy should be big
				enemy = new EnemyBig(randomSide ,MathUtils.random(0, 480),
						 dir, 0,TextureManager.ENEMY_BIG);
				firstEnemy = false;
			}
			else{
				//if the enemy gets out of the screen, the enemy class changes enemyHidTime
				//to simulate a hit,so the re-spawn algorithm will commence
				if(enemyHidTime != 0){
					enemy = null;
					game.soundManager.stopMusic(game.soundManager.getEnemyOnScreen());
					respawnEnemy(randomSide,dir);
				}
			}
		}
		
		spaceship.update(game);
		game.movementVector.update();
		
		if(enemy != null){
			enemy.update(game,this);
		}
		
		if(enemyBullet != null){
			enemyBullet.update(game);
			if(enemyBullet.checkEnd())
				enemyBullet = null;
		}
		
		if(game.movementVector.checkEnd()){
			game.movementVector.setDirection(new Vector2(0,0));
		}
		for(Asteroid a : entities){
			a.update(game);
		}
		
		//for asteroids including bullets and spaceships
		checkCollisions(game);
		
		
		//enemy bullet
		
	}
	
	
	public int getCentered(int frameSize, int textureSize){
		return frameSize/2 - textureSize/2;
	}
	
	public void checkCollisions(Asteroids game){
		if(!invincible){
			if(enemyBullet != null){
				if(enemyBullet.getBounds().overlaps(spaceship.getBounds())){
					spaceshipCrashed(game);
					enemyBullet = null;
				}
			}
			
			//for ships
			 if(enemy!= null && Intersector.overlaps(enemy.getBoundsCircle(), spaceship.getBounds())){
				 playerHitEnemy(game);
				 spaceshipCrashed(game);
			 }
		}
		 
		 //player bullets and enemy
		 if(enemy!= null){
			 for(Bullet b : spaceship.bullets){
				 if(Intersector.overlaps(enemy.getBoundsCircle(),b.getBounds())    ){
					 playerHitEnemy(game);
					 spaceship.bullets.removeValue(b, false);
					 break;
				 }
			 }
		}
		 
		checkAsteroidsCollisions(game); 
	}
	
	
	void checkAsteroidsCollisions(Asteroids game){
		for(Asteroid a : entities){
			if(!invincible){
				if (Intersector.overlaps(a.getBoundsCircle(), spaceship.getBounds())){
					spaceshipCrashed(game);
					playerHitAsteroid(a,game,spaceship);
					break;
				}
			}
			
			if(enemy != null && enemy.getBoundsCircle().overlaps(a.getBoundsCircle())){
				game.soundManager.stopMusic(game.soundManager.getEnemyOnScreen());
				game.soundManager.playSound(a.getCrashingSound());
				a.crashed(a, entities,enemy);
				enemy = null;
				enemyHidTime = TimeUtils.millis();
			 	respawnTime = MathUtils.random(100, 10000);
				game.soundManager.playSound(game.soundManager.getEnemyExplosion());
			}
			for(Bullet b : spaceship.bullets){
				if(Intersector.overlaps(a.getBoundsCircle(),b.getBounds())){
					playerHitAsteroid(a,game,b);
					spaceship.bullets.removeValue(b, false);
					break;
				}
			}
			if(enemyBullet != null){
				if(Intersector.overlaps(a.getBoundsCircle(),enemyBullet.getBounds())){
					game.soundManager.playSound(a.getCrashingSound());
					a.crashed(a, entities, enemyBullet);
					enemyBullet = null;
				}
			}
		}
	}
	
	
	void spaceshipCrashed(Asteroids game){
		game.spaceshipNotCrashed = false;
		spaceship.setDirection(0, 0);
		game.movementVector.setSpeed(0);
		spaceship.setPos(new Vector2(-100,-100));
		timeCrashed = TimeUtils.millis();
		spaceship.sprite.setRotation(0);
		game.lives--;
		game.soundManager.playSound(game.soundManager.getPlayerExplosion());
	}
	
	void playerHitAsteroid(Asteroid a,Asteroids game,Entity o){
		game.soundManager.playSound(a.getCrashingSound());
		int temp =a.crashed(a, entities,o);
		game.score += temp;
		tempScore += temp;
	}
	
	void respawnEnemy(int randomSide, int dir){
			if(TimeUtils.millis() - enemyHidTime > respawnTime){
				if(MathUtils.randomBoolean())
					enemy = new EnemyBig(randomSide ,MathUtils.random(0, 480),
							 dir, 0,TextureManager.ENEMY_BIG);
				else
					enemy = new EnemySmall(randomSide ,MathUtils.random(0, 480),
							 dir, 0,TextureManager.ENEMY_SMALL);
				enemyHidTime = 0;
				respawnTime = 0;
			}
	}
	
	public void generateAsteroids(int amountOfAsteroids){
		int asteroidX,asteroidY;
		for(int i=0; i < amountOfAsteroids; i++){
			asteroidX = MathUtils.random(800);
			if(asteroidX < 170 || asteroidX > 630){
				asteroidY = MathUtils.random(480);
			}
			else{
				if(MathUtils.randomBoolean())
					asteroidY = MathUtils.random(20);
				else
					asteroidY = MathUtils.random(400,460);
			}
			Circle temp = new Circle(asteroidX + TextureManager.ASTEROID_LARGE.getWidth()/2
					,asteroidY + TextureManager.ASTEROID_LARGE.getHeight()/2,
					 TextureManager.ASTEROID_LARGE.getWidth()/2 + 20);
			
			if(Intersector.overlaps(temp,spaceship.getBounds())){
				i--;
				continue;
			}
			entities.add(new AsteroidBig(asteroidX,asteroidY,
					 MathUtils.random((float)-1.0,(float)1.0), MathUtils.random((float)-1.0,(float)1.0),
					TextureManager.ASTEROID_LARGE));
		}
	}

	public void playerHitEnemy(Asteroids gam){
		 gam.score += enemy.getValue();
		 tempScore += enemy.getValue();
		 enemy = null;
		 gam.soundManager.stopMusic(gam.soundManager.getEnemyOnScreen());
		 enemyHidTime = TimeUtils.millis();
		 respawnTime = MathUtils.random(100, 10000);
		 gam.soundManager.playSound(gam.soundManager.getEnemyExplosion());
	}
	
	private void hyperspace(Asteroids game) {
		Rectangle temp = new Rectangle(MathUtils.random(0, 800),
				MathUtils.random(0,480),
				spaceship.sprite.getWidth()/2 + 40, spaceship.sprite.getHeight()/2+40);
		while(!game.rectangleFree(entities,temp)){
			temp = new Rectangle(MathUtils.random(0, 800),MathUtils.random(0,480),
					spaceship.sprite.getWidth()/2 + 40, spaceship.sprite.getHeight()/2+40);
		}
		spaceship.pos.x = temp.x;
		spaceship.pos.y = temp.y;
		spaceship.direction= new Vector2(0,0);
	}
	
	public Spaceship getSpaceship() {
		return spaceship;
	}

	public int getTempScore() {
		return tempScore;
	}
	
	public void setTempScore(int remainingScore) {
		tempScore = remainingScore;
	}

	public Array<Asteroid> getEntities() {
		return entities;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public void setTimeInLevel(long timeInLevel) {
		this.timeInLevel = timeInLevel;
	}

	public void setFirstEnemy(boolean firstEnemy) {
		this.firstEnemy = firstEnemy;
	}

	public Bullet getEnemyBullet() {
		return enemyBullet;
	}

	public void setEnemyBullet(BulletEnemy enemyBullet) {
		this.enemyBullet = new BulletEnemy(enemyBullet);
	}
}

