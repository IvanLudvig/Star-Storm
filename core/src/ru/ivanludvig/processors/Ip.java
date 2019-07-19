package ru.ivanludvig.processors;

import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Ip extends InputAdapter{

	
	Gam game;
	int j=0;
	
	public Ip(Gam game){
		this.game = game;
	}
	

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(game.defense.lost()==0){
			if(((!game.defense.ui.tp.isTouched()))||((pointer==1)&&(game.defense.ui.tp.isTouched()))){
				game.defense.sputnik.shoot(new Vector2(game.defense.camera.unproject(new Vector3(screenX, screenY,0)).x,
						game.defense.camera.unproject(new Vector3(screenX, screenY,0)).y));
			}
		}

		/*if((!game.defense.ui.tp.isTouched())||((j==0)&&(pointer==1))){
			game.defense.sputnik.shoot(new Vector2(game.defense.camera.unproject(new Vector3(screenX, screenY,0)).x,
					game.defense.camera.unproject(new Vector3(screenX, screenY,0)).y));
			System.out.println(game.defense.ui.tp.isTouched()+" "+pointer);
		}
		if((game.defense.ui.tp.isTouched()) && (pointer == 1)){
			j=1;
		}}*/
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		/*if((!game.defense.ui.tp.isTouched())){
			j=0;
		}*/
		return false;
	}

}
