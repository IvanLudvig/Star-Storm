package ru.ivanludvig.ship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import ru.ivanludvig.starstorm.Gam;


public class Proximo extends Sputnik {

	
	public Proximo(Gam game) {
		super(game);
		/*this.sprite = new Sprite((Texture) game.manager.get(("ships/1.png")));
		this.ammo = 100;
		this.hp = 100;
		this.speed = 3f;
		this.maxspeed = 8f;
		this.power = 11f;
		this.agility = 3f;
		this.weaponry = 1f;
		this.sprite.setOrigin(32, 32);*/
		this.num = 6;
		this.create();
	}

}
