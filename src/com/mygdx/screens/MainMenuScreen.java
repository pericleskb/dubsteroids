package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.dubsteroids.Asteroids;
import com.mygdx.dubsteroids.TextureManager;

public class MainMenuScreen implements Screen {
	
	Asteroids game;
	OrthographicCamera camera;
	Stage stage;
	Skin skin;
	Viewport viewport;
	String scoreString; 
	TextButton score;
	
	public MainMenuScreen(Asteroids gam){
		this.game = gam;
		camera = new OrthographicCamera();
		camera.setToOrtho(false,game.WIDTH,game.HEIGHT);
		viewport = new FitViewport(game.WIDTH, game.HEIGHT, camera);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin();
		BitmapFont bfont=new BitmapFont();
		bfont.scale(1);
		
		skin.add("default",bfont);
		skin.add("play", TextureManager.PLAY);
		skin.add("keys", TextureManager.KEYS);
		skin.add("credits", TextureManager.CREDITS);
		skin.add("quit", TextureManager.QUIT);
		skin.add("score", TextureManager.transparent);
		
		
		TextButtonStyle playButtonStyle = generateStyle("play");
		TextButtonStyle keysButtonStyle = generateStyle("keys");
		TextButtonStyle creditsButtonStyle = generateStyle("credits");
		TextButtonStyle quitButtonStyle = generateStyle("quit");
		TextButtonStyle scoreButtonStyle = generateStyle("score");
		

		final TextButton textButton=new TextButton("",playButtonStyle);
		textButton.setPosition(game.WIDTH/2 - textButton.getWidth()/2, game.HEIGHT/3);

		final TextButton keysButton=new TextButton("",keysButtonStyle);
		keysButton.setPosition(game.WIDTH/2 - keysButton.getWidth()/2, game.HEIGHT/3 - 40 );

		final TextButton creditsButton=new TextButton("",creditsButtonStyle);
		creditsButton.setPosition(game.WIDTH/2 - creditsButton.getWidth()/2, game.HEIGHT/3 - 80);

		final TextButton quitButton=new TextButton("",quitButtonStyle);
		quitButton.setPosition(game.WIDTH/2 - quitButton.getWidth()/2 , game.HEIGHT/3- 120);
		
		scoreString = new String(game.score.toString());
		score = new TextButton(scoreString,scoreButtonStyle);
		//score.setPosition(game.WIDTH/2 - score.getWidth()/2, game.HEIGHT/3 + 100);
		//stage.addActor(score);
		
		stage.addActor(textButton);
		stage.addActor(keysButton);
		stage.addActor(creditsButton);
		stage.addActor(quitButton);
		
		
		textButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				textButton.setText("Starting new game");
				game.setScreen( new GameScreen(game));
			}
		});
		
		keysButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen( new KeysScreen(game));
			}
		});
		
		creditsButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen( new CreditsScreen(game));
			}
		});
		
		quitButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				dispose();
				game.dispose();
				
			}
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //the update to the orthographic camera should be done
        //when a property of the camera changes. However, I live this here
        //just to be sure. I will experiment later
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        game.batch.begin();
        game.batch.draw(TextureManager.MENU_BACK,0,0);
        if(game.score!=0){
        	game.font.draw(game.batch, "YOUR SCORE IS", game.WIDTH/2 - 65, game.HEIGHT/2 + 100);
        	game.font.draw(game.batch, scoreString, game.WIDTH/2 - score.getWidth()/2 , game.HEIGHT/2 + 75);
        }
       // game.font.draw(game.batch, "*** ASTEROIDS ***", 320, 300);
        //game.font.draw(game.batch, "Tap Anywhere to Begin", 320, 100);
        game.batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		//Table t;
        
       /* if(Gdx.input.isTouched()){
        	game.setScreen(new GameScreen(game));
        	dispose();
        }*/
		

	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);

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
	
	TextButtonStyle generateStyle(String nameOfDrawable){
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable(nameOfDrawable, Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable(nameOfDrawable, Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable(nameOfDrawable, Color.BLUE);
		textButtonStyle.over = skin.newDrawable(nameOfDrawable, Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		return textButtonStyle;
	}
}
