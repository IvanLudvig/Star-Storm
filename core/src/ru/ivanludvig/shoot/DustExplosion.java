package ru.ivanludvig.shoot;

import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;

public class DustExplosion extends Explosion{
	
	Gam game;
	
	public DustExplosion(Gam gam, int i, Vector2 pos, float scale){
		super(gam, pos, scale, 2);
		game = gam;
		effect = new ParticleEffect((ParticleEffect)game.manager.get("data/explosions/dust.p"));
		effect.reset();
		effect.start();
		effect.setPosition(pos.x, pos.y);
		effect.scaleEffect(1f);
		game.defense.dustmanager.fullRemove(i);
		((Sound)game.manager.get("audio/sounds/close.ogg")).play();
	}

	@Override
	public void render(float delta){
		//effect.setPosition(pos.x,  pos.y);
		effect.draw(game.defense.batch, delta);
	}
	
}
