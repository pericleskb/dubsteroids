package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.dubsteroids.Asteroids;

public abstract class Entity {
	
	Vector2 pos;
	Vector2 direction;
	Texture texture;
	Sprite sprite;
	
	public Entity(int posX, int posY, float dirX, float dirY,  Texture texture){
		this.pos = new Vector2(posX, posY);
		this.direction = new Vector2(dirX,dirY);
		this.texture = texture;
		this.sprite = new Sprite(texture);
	}
	
	public Entity(float posX, float posY, float dirX, float dirY, Texture texture){
		this.pos = new Vector2(posX, posY);
		this.direction = new Vector2(dirX,dirY);
		this.texture = texture;
		this.sprite = new Sprite(texture);
	}
	
	

	public void update(Asteroids game){
		pos.add(direction);

		/*pos.x/pos.y position must be symmetrical but we must also
		 * take into account that we want the center to be in the
		 * symmetric position. So if the height is 400 and pos.y = 100
		 * pos.y + texture.getHeight(2) could be 116. So we want pos.y+getHeight()/2
		 * to be at 284. So pos.y is 266 instead of 300. 
		 * We also want the spaceship to disappear at 1/2 of its size before transporting
		 * to the other side. So we check for +size/2. So we add another extra half
		 */
		if(pos.y + texture.getHeight()/2 > game.HEIGHT){
			pos.y = - texture.getHeight()/2;
			//pos.x = game.WIDTH - (Math.abs(pos.x) + texture.getWidth());
		}
		else if(pos.y + texture.getHeight()/2 < 0){
			pos.y =game.HEIGHT - texture.getHeight()/2;
			//pos.x = game.WIDTH - (Math.abs(pos.x) + texture.getWidth());
		}
		else if(pos.x + texture.getWidth()/2 > game.WIDTH){
			pos.x = - texture.getWidth()/2;
			//pos.y = game.HEIGHT - (Math.abs(pos.y) + texture.getHeight());
		}
		else if(pos.x + texture.getWidth()/2 < 0){
			pos.x = game.WIDTH - texture.getWidth()/2;
			//pos.y = game.HEIGHT - (Math.abs(pos.y) + texture.getHeight());
		}
	}
	
	public abstract void render(SpriteBatch sb);
	
	public void setDirection(int dirX, int dirY){
		direction.set(dirX,dirY);
		direction.scl(Gdx.graphics.getDeltaTime());
				
	}

	public void setPos(Vector2 pos) {
		this.pos = pos;
	}
	
	public void setDirection(float dirX, float dirY){
		direction.set(dirX,dirY);
		direction.scl(Gdx.graphics.getDeltaTime());
				
	}
	
	public Rectangle getBounds(){
		return new Rectangle(pos.x,pos.y,texture.getWidth(),texture.getHeight());
	}

	public Circle getBoundsCircle(){
		return new Circle(pos.x + sprite.getWidth()/2 ,pos.y + sprite.getHeight()/2,(texture.getWidth()-1)/2);
	}

	
}
