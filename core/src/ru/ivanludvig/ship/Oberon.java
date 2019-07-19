package ru.ivanludvig.ship;

import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Oberon extends Sputnik{


	public Oberon(Gam game) {
		super(game);
		this.num = 1;
		this.create();
	}

}
