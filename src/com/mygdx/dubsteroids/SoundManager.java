package com.mygdx.dubsteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

	Sound playerfireSprite,playerExplosion,enemyExplosion;
	Sound mediumAsteroidBang,bigAsteroidBang,smallAsteroidBang;
	Music enemyOnScreen, beep, engineSound;
	boolean mute;
	
	public SoundManager(){
		
		this.bigAsteroidBang = Gdx.audio.newSound(Gdx.files.internal("big-asteroid-bang.wav"));
		this.mediumAsteroidBang = Gdx.audio.newSound(Gdx.files.internal("medium-asteroid-bang.wav"));
		this.smallAsteroidBang = Gdx.audio.newSound(Gdx.files.internal("small-asteroid-bang.wav"));
		this.playerExplosion = Gdx.audio.newSound(Gdx.files.internal("player-explosion.mp3"));
		this.enemyExplosion = Gdx.audio.newSound(Gdx.files.internal("enemy-explosion.wav"));
		this.enemyOnScreen = Gdx.audio.newMusic(Gdx.files.internal("danger.wav"));
		this.beep = Gdx.audio.newMusic(Gdx.files.internal("beep.wav"));
		this.beep.setVolume((float) 0.6);
		this.playerfireSprite = Gdx.audio.newSound(Gdx.files.internal("spaceship-shoot.wav"));
		this.engineSound = Gdx.audio.newMusic(Gdx.files.internal("spaceship-ambience.wav"));
		this.mute = false;
	}
	
	public void playSound(Sound s){
		if(!mute){
			s.play();
		}
	}
	
	public void playMusic(Music m){
		if(!mute){
			m.play();
		}
	}
	
	public void stopMusic(Music m){
		if(m.isPlaying()){
			m.stop();
		}
	}

	public Sound getBigAsteroidBang() {
		return bigAsteroidBang;
	}

	public Sound getPlayerExplosion() {
		return playerExplosion;
	}

	public Sound getEnemyExplosion() {
		return enemyExplosion;
	}

	public Sound getMediumAsteroidBang() {
		return mediumAsteroidBang;
	}

	public Sound getPlayerfireSprite() {
		return playerfireSprite;
	}

	public Music getEnemyOnScreen() {
		return enemyOnScreen;
	}

	public Music getBeep() {
		return beep;
	}

	public Music getEngineSound() {
		return engineSound;
	}

	public boolean isMute() {
		return mute;
	}

	public void setMute(boolean mute) {
		this.mute = mute;
	}
}
