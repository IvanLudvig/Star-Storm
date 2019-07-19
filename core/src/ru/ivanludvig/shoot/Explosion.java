package ru.ivanludvig.shoot;

import ru.ivanludvig.ship.Stats;
import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;

public class Explosion {
	
	ParticleEffect effect;
	public Vector2 pos;
	Gam game;
	float scale;
	
	public Explosion(Gam gam, Vector2 pos, float scale, int type){
		this.game = gam;
		if(type==1){
			this.pos = pos;
			this.scale = scale;
			effect = new ParticleEffect((ParticleEffect) 
					game.manager.get("data/explosions/"+Stats.names[game.shipnum].toLowerCase()+"-explosion.p"));
			effect.reset();
			effect.start();
			effect.setPosition(pos.x, pos.y);
			effect.scaleEffect(scale/100f);
			playSound();
		}
	}
	
	public void render(float delta){
		effect.draw(game.defense.batch, delta);
	}
	
	public void playSound(){
		float volume=0.2f+(100/pos.sub(game.defense.sputnik.getPos()).len()*scale);
		int n = (int)(Math.random()*6)+1;
		System.out.println(n+" explosion");
		switch (n){
		case 1:
			((Sound)game.manager.get("audio/sounds/far.ogg")).play(volume);
			break;
		case 2:
			((Sound)game.manager.get("audio/sounds/distant.ogg")).play(volume*2);
			break;
		case 3:
			((Sound)game.manager.get("audio/sounds/far.ogg")).play(volume);
			break;
		case 4:
			((Sound)game.manager.get("audio/sounds/far.ogg")).play(volume);
			((Sound)game.manager.get("audio/sounds/distant.ogg")).play(volume*2);
			break;
		case 5:
			((Sound)game.manager.get("audio/sounds/distant.ogg")).play(volume/2);
			((Sound)game.manager.get("audio/sounds/far.ogg")).play(volume*2);
			break;
		case 6:
			((Sound)game.manager.get("audio/sounds/close.ogg")).play(volume/20);
			((Sound)game.manager.get("audio/sounds/far.ogg")).play(volume);
			break;
		case 7:
			((Sound)game.manager.get("audio/sounds/distant.ogg")).play(volume*2);
			((Sound)game.manager.get("audio/sounds/close.ogg")).play(volume/10);
			((Sound)game.manager.get("audio/sounds/far.ogg")).play(volume);
			break;
		}
	}
	
	public void dispose(){
		effect.dispose();
	}

}
