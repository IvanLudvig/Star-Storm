package ru.ivanludvig.planets;

import ru.ivanludvig.starstorm.Gam;
import ru.ivanludvig.tween.PlanetTween;
import ru.ivanludvig.tween.SpriteTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Star {
	
	Gam game;
	Sprite sprite;
	public float radius = 240;
	ParticleEffect effect;
	ParticleEffect stars;
	
	public Star(Gam gam){
		game = gam;
		sprite = new Sprite((Texture)game.manager.get("planets/stars/1.png"));
		effect = new ParticleEffect((ParticleEffect) game.manager.get("data/star2.p"));
		effect.reset();
		//effect.scaleEffect(0.7f);
		stars = new ParticleEffect((ParticleEffect) game.manager.get("data/stars.p"));
		stars.reset();
		sprite.setRotation(0);
		sprite.setOrigin(radius, radius);
		tween();
	}
	
	public void draw(float delta, SpriteBatch batch,  Vector2 pos){
		sprite.setBounds((float) (pos.x-(radius*1.5f)), 0, radius*2, radius*2);
		sprite.draw(batch);
		effect.setPosition(pos.x-100, 220);
		effect.draw(batch, delta/2);
		stars.setPosition(300, 0);
		stars.draw(batch, delta);
	}
	
	public void tween(){
		Tween.to(sprite, SpriteTween.ROTATION, 240f)
		.target(360)
		.ease(Linear.INOUT)
		.repeat(Tween.INFINITY, 0)
		.start(game.tweenManager);
	}

}
