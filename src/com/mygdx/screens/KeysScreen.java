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

public class KeysScreen implements Screen {

	final Asteroids game;
	OrthographicCamera camera;
	Stage stage;
	Skin skin;
	Viewport viewport;
	
	public KeysScreen(final Asteroids gam) {
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
		skin.add("back", TextureManager.BACK);
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("back", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("back", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("back", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("back", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		
		final TextButton BackButton=new TextButton("",textButtonStyle);
		BackButton.setPosition(game.WIDTH/2 - BackButton.getWidth()/2, 50);
		stage.addActor(BackButton);
		
		BackButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				BackButton.setText("Going Back");
				game.setScreen( new MainMenuScreen(game));
				dispose();
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
        
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        game.batch.begin();
        game.batch.draw(TextureManager.MENU_BACK,0,0);
        game.batch.draw(TextureManager.ARROWS,game.WIDTH/2- 110 - TextureManager.ARROWS.getWidth(),260);
        game.font.draw(game.batch, "Use Left and Right keys to turn the spaceship.", game.WIDTH/2 - 105, 278);
        game.batch.draw(TextureManager.UPARROW,game.WIDTH/2- 127 - TextureManager.UPARROW.getWidth(),300);
        game.font.draw(game.batch, "Use UP key to accelarate the spaceship.", game.WIDTH/2 - 105, 318);
        game.font.draw(game.batch, "SPACE", game.WIDTH/2 - 165, 220);
        game.font.draw(game.batch, "Use SPACE to shoot.", game.WIDTH/2 - 105, 220);
        game.font.draw(game.batch, "SHIFT", game.WIDTH/2 - 165, 180);
        game.font.draw(game.batch, "Use SHIFT to use hyperspace", game.WIDTH/2 - 105, 180);
        game.font.draw(game.batch, "and teleport to a random position.", game.WIDTH/2 - 105, 164);
        game.batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();

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

	@Override
	public void dispose() {
	}

}
