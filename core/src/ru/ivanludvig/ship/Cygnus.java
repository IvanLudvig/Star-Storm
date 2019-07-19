package ru.ivanludvig.ship;

import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Cygnus extends Sputnik{

	public Cygnus(Gam game) {
		super(game);
		/*this.sprite = new Sprite((Texture) game.manager.get(("ships/4.png")));
		this.ammo = 100;
		this.hp = 130;
		this.speed = 5f;
		this.maxspeed = 7f;
		this.power = 11.5f/1.25f;
		this.agility = 4f;
		this.weaponry = 1.25f;
		this.size = new Vector2(20, 14);
		this.sprite.setOrigin(32, 32);*/
		this.num = 4;
		this.create();
	}

}
