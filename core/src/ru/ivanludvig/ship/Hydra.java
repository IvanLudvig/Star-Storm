package ru.ivanludvig.ship;

import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Hydra extends Sputnik{


	public Hydra(Gam game) {
		super(game);
		/*this.sprite = new Sprite((Texture) game.manager.get(("ships/2.png")));
		this.ammo = 100;
		this.hp = 70;
		this.speed = 8f;
		this.maxspeed = 12f;
		this.power = 8f;
		this.agility = 6f;
		this.weaponry = 0.8f;
		this.sprite.setOrigin(32, 32);*/
		size = new Vector2(14, 14);
		this.num = 2;
		this.create();
	}

}
