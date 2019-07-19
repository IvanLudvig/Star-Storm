package ru.ivanludvig.shoot;

import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Shooter {
	
	Gam game;
	public Array<Shot> shots = new Array<Shot>(200);
	Array<Shot> delshots = new Array<Shot>(200);
	Array<Explosion> explosions = new Array<Explosion>(10);
	
	public Shooter(Gam game){
		this.game = game;
	}
	
	public void render(float delta){
		if(!game.defense.world.isLocked()){
			for(Shot sh : delshots){
				game.defense.world.destroyBody(sh.body);
				delshots.removeValue(sh, true);
				sh.dispose();
			}
		}
		for(Shot sh : shots){
			sh.render(delta);
		}
		for(Explosion p : explosions){
			p.render(delta);
			if(p.effect.isComplete()){
				explosions.removeValue(p, true);
				p.dispose();
			}
		}
	}
	
	public void shoot(Vector2 vec, Vector2 off){
		shots.add(new Shot(game, game.defense.sputnik.getPos().add(off), vec, off));
		//game.defense.sputnik.addAngle((float) (((new Vector2(vec)).sub(game.defense.sputnik.getPos())).angle()));
	}
	
	public void destroy(int i, float radius){
		explosions.add(new Explosion(game, shots.get(i).getPos(), radius, 1));
		delshots.add(shots.get(i));
		shots.removeIndex(i);
	}
	
	public void dustPlanet(int i, float radius){
		System.out.println("shooter "+game.defense.dustmanager.dust.get(i).getPos());
		explosions.add(new DustExplosion(game, i, game.defense.dustmanager.dust.get(i).getPos(), radius));
	}
	
	public void dispose(){
		for(Shot sh : shots){
			sh.dispose();
		}
		for(Shot sh : delshots){
			sh.dispose();
		}
	}
}
