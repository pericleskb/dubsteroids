package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.dubsteroids.Asteroids;
import com.mygdx.dubsteroids.TextureManager;
import com.mygdx.entity.EntityManager;

public class GameScreen implements Screen {

	private int  numberOfAsteroids;
	final Asteroids game;
	OrthographicCamera camera;
	EntityManager entityManager;
	long silenceDuration,lastBeep ;
	long levelTimeOut,levelFinished;
	float r,g,b;
	Stage stage;
	Skin skin,noSoundSkin;
	Viewport viewport;
	String scoreString; 
	TextButton score;
	
	public GameScreen(Asteroids gam){
		this.game = gam;
		numberOfAsteroids = 4;
		camera = new OrthographicCamera();
		camera.setToOrtho(false,game.WIDTH,game.HEIGHT);
		entityManager = new EntityManager(game,numberOfAsteroids);
		levelFinished =0;
		silenceDuration = 2000;
		lastBeep = 0;
		levelTimeOut = 700;
		game.score = 0;;
		viewport = new FitViewport(game.WIDTH, game.HEIGHT, camera);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin();
		noSoundSkin = new Skin();
		BitmapFont bfont=new BitmapFont();
		bfont.scale(1);
		
		skin.add("default",bfont);
		skin.add("sound", TextureManager.SOUND);
		noSoundSkin.add("noSound", TextureManager.NO_SOUND);
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("sound", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("sound", Color.DARK_GRAY);
		textButtonStyle.checked = noSoundSkin.newDrawable("noSound", Color.DARK_GRAY);
		textButtonStyle.over = skin.newDrawable("sound", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		

		final TextButton textButton=new TextButton("",textButtonStyle);
		textButton.setPosition(game.WIDTH - textButton.getWidth() -15,
			game.HEIGHT - textButton.getHeight());
		stage.addActor(textButton);
		
		textButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if(game.soundManager.isMute()){
					textButton.setSkin(skin);
					game.soundManager.setMute(false);
					stage.addActor(textButton);
				}
				else{
					textButton.setSkin(noSoundSkin);
					game.soundManager.setMute(true);
					stage.addActor(textButton);
				}
			}
		});
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		String scoreToScreen;
		if(game.lives == 0){
			game.setScreen(new MainMenuScreen(game));
        	dispose();
		}
		
		
		if(entityManager.getEnemy() != null && !game.soundManager.getEnemyOnScreen().isPlaying()){
			game.soundManager.getEnemyOnScreen().setLooping(true);
			game.soundManager.playMusic(game.soundManager.getEnemyOnScreen());
		}
		
		
		if(entityManager.getEntities().size == 0 && entityManager.getEnemy()==null){
			if(levelFinished == 0){
				levelFinished = TimeUtils.millis();
			}
			if(TimeUtils.millis() - levelFinished > levelTimeOut){
				entityManager.generateAsteroids(++numberOfAsteroids);
				entityManager.setTimeInLevel(TimeUtils.millis());
				silenceDuration = 2000;
				levelFinished = 0;
			}
		}
        entityManager.update(game,numberOfAsteroids);
        
        if(game.soundManager.getEnemyOnScreen().isPlaying()){
			if(TimeUtils.millis() - lastBeep > silenceDuration){
				r = MathUtils.random();
				g = MathUtils.random();
				b = MathUtils.random();
			}
		}
		else{
			r = 0;
			g = 0;
			b = 0;
		}

		Gdx.gl.glClearColor(r, g, b, 0.7f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
		if(TimeUtils.millis() - lastBeep > silenceDuration){
			if(silenceDuration >300)
				silenceDuration -= 65;
			game.soundManager.playMusic(game.soundManager.getBeep());
			lastBeep = TimeUtils.millis();
		}
		
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        if(entityManager.getTempScore()>10000){
        	game.lives++;
        	entityManager.setTempScore(entityManager.getTempScore()%10000);
        }
        
        game.batch.begin();
        entityManager.render(game.batch,numberOfAsteroids,game);
        scoreToScreen = "SCORE: " + game.score ;
        game.font.draw(game.batch,scoreToScreen,50,480);
        for(int i=0; i<game.lives; i++)
        	game.batch.draw(TextureManager.SPACESHIP, 50 + i*30, 430, 20, 20);
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		game.soundManager.getBeep().dispose();
		game.soundManager.getEnemyOnScreen().dispose();
		game.soundManager.getEngineSound().dispose();
	}

}
