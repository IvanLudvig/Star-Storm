package ru.ivanludvig.starstorm;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class DustManager {
	
	
	Gam game;
	public Array<Dust> dust = new Array<Dust>(30);
	Array<Dust> del = new Array<Dust>(30);
	Array<Dust> in = new Array<Dust>(30);
	

	public DustManager(Gam game) {
		this.game = game;
	}
	
	public void render(){
		add();
		speedControl();
		if(!game.defense.world.isLocked()){
			for(Dust dus : del){
				game.defense.world.destroyBody(dus.body);
				del.removeIndex(del.indexOf(dus, true));
			}
			
			for(Dust du : in){
				split(du);
				in.removeIndex(in.indexOf(du, true));
			}
			for(Dust d : dust){
				d.render();
			}
		}

	}
	
	public void add(){
		if(dust.size < (20 + (game.defense.score/10))){
			//Vector2 pos = new Vector2((float)(Math.random()*1000-100), (float)(Math.random()*680-100));
			Vector2 pos = new Vector2();
		//	System.out.println((int)(Math.random()*4)+1);
			switch((int)(Math.random()*8)+1){
			case 1: 			
			    pos = new Vector2((float)((Math.random()*50)+1000), (float)((Math.random()*50)+680));
				break;
			case 2:
			    pos = new Vector2((float)((Math.random()*50)-400), (float)((Math.random()*50)+680));
				break;
			case 3:
			    pos = new Vector2((float)((Math.random()*50)-400), (float)((Math.random()*50)-400));
				break;
			case 4:	
			    pos = new Vector2((float)((Math.random()*50)+1000), (float)((Math.random()*50)-400));
				break;
			case 5: 			
			    pos = new Vector2((float)((Math.random()*50)+1000), (float)(Math.random()*480));
				break;
			case 6:
			    pos = new Vector2((float)((Math.random()*50)-400), (float)(Math.random()*480));
				break;
			case 7:
			    pos = new Vector2((float)(Math.random()*800), (float)((Math.random()*50)+680));
				break;
			case 8:	
			    pos = new Vector2((float)(Math.random()*800), (float)((Math.random()*50)-400));
				break;
			}
			newDust(pos, (float)(Math.random()*(30+game.defense.score/10)+5));
		}
	}
	
	public void split(Dust du){
		if(du.radius>=16f){
			int f = (int)(Math.random()*10);
			if(f%2==0){
				newDust(du.getPos().add((int)(Math.random()*10), (int)(Math.random()*10)), du.radius/2);
				newDust(du.getPos().add((int)(Math.random()*10), (int)(Math.random()*10)), du.radius/2);
			}
			else if(f%3==0 && du.radius>=20f){
				newDust(du.getPos().add((int)(Math.random()*10), (int)(Math.random()*10)), du.radius/3);
				newDust(du.getPos().add((int)(Math.random()*10), (int)(Math.random()*10)), du.radius/3);
				newDust(du.getPos().add((int)(Math.random()*10), (int)(Math.random()*10)), du.radius/3);
			}
			else if((f==7||f==5) && du.radius>=30f){
				newDust(du.getPos().add((int)(Math.random()*10), (int)(Math.random()*10)), du.radius/4);
				newDust(du.getPos().add((int)(Math.random()*10), (int)(Math.random()*10)), du.radius/4);
				newDust(du.getPos().add((int)(Math.random()*10), (int)(Math.random()*10)), du.radius/4);
				newDust(du.getPos().add((int)(Math.random()*10), (int)(Math.random()*10)), du.radius/4);
			}else{
				newDust(du.getPos().add((int)(Math.random()*10), (int)(Math.random()*10)), du.radius/2);
				newDust(du.getPos().add((int)(Math.random()*10), (int)(Math.random()*10)), du.radius/2);
			}
		}
	}
	
	public void speedControl(){
		for(Dust d : dust){
			if(d.body.getLinearVelocity().len()>1f){
				d.body.setLinearVelocity(d.body.getLinearVelocity().scl(0.9f));
			}
		}
	}
	
	public void newDust(Vector2 pos, float radius){
		dust.add(new Dust(game, pos, radius));
	}
	
	public void remove(int i){
		in.add(dust.get(i));
		del.add(dust.get(i));
		dust.removeIndex(i);
	}
	public void fullRemove(int i){
		del.add(dust.get(i));
		dust.removeIndex(i);
	}

}
