package ru.ivanludvig.ship;

import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Ivan Ludvig
 *
 */
public class Oblivion extends Sputnik {

	public Oblivion(Gam game) {
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
		this.num = 7;
		this.create();
		this.effect.scaleEffect(1.5f);
	}
	
}
