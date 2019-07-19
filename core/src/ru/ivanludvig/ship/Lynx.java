package ru.ivanludvig.ship;

import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Lynx  extends Sputnik{


	public Lynx(Gam game) {
		super(game);
		/*this.sprite = new Sprite((Texture) game.manager.get(("ships/5.png")));
		this.ammo = 100;
		this.hp = 180;
		this.speed = 3f;
		this.maxspeed = 8f;
		this.power = 13f/1.1f;
		this.agility = 10f;
		this.weaponry = 1.1f;
		this.sprite.setOrigin(32, 32);*/
		this.num = 5;
		this.create();
	}
	
}
