package ru.ivanludvig.planets;

import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class ResManager {

	Gam game;
	Array<Ammo> ress = new Array<Ammo>(20);
	Array<Ammo> delres = new Array<Ammo>(100);
	public float score=0;
	float delta;
	float rand = 1.5f;
	
	public ResManager(Gam gam){
		game = gam;
	}
	
	public void render(float delta, SpriteBatch batch){
		this.delta += delta;
		for(Ammo ammo : ress){
			ammo.render(delta, batch);
		}
		if(!game.defense.world.isLocked()){
			for(Ammo ammo : delres){
				game.defense.world.destroyBody(ammo.body);
				delres.removeValue(ammo, true);
				ammo.dispose();
			}
		}
		if((game.defense.score-score>=1/game.defense.planet.getRes()*30)
				||(this.delta>1/game.defense.planet.getRes()*100*rand)){
			gen();
			score = game.defense.score;
			this.delta = 0;
			rand = (float)Math.random()+1;
		}
	}
	
	public void gen(){
		ress.add(new Ammo(game));
	}
	
	public void delRes(Ammo in){
		game.defense.sputnik.addAmmo(in.amount);
		delres.add(in);
		ress.removeValue(in, true);
	}
	
	public void dispose(){
		for(Ammo ammo : ress){
			ammo.dispose();
		}
		for(Ammo ammo : delres){
			ammo.dispose();
		}
	}
}
