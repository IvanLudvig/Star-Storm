package ru.ivanludvig.ship;

import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class NightFall extends Sputnik{


	public NightFall(Gam game) {
		super(game);
		/*this.sprite = new Sprite((Texture) game.manager.get(("ships/3.png")));
		this.ammo = 100;
		this.hp = 100;
		this.speed = 5f;
		this.maxspeed = 8f;
		this.power = 10.5f/1.1f;
		this.agility = 6f;
		this.weaponry = 1.1f;
		this.sprite.setOrigin(32, 32);*/
		this.num = 3;
		this.create();
		this.effect.scaleEffect(0.5f);
	}

}
