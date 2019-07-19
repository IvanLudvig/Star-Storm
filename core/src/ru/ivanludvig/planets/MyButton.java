package ru.ivanludvig.planets;

import ru.ivanludvig.level.Level;
import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MyButton extends TextButton{
	
	Gam game;
	int num;
	Level level;
	
	public MyButton(Gam gam, int n){
		super("defend", gam.pinkskin);
		game = gam;
		num = n;
		for(Level level:game.solarSystem.levels){
			if(level.planet==num+1){
				this.level = level;
				this.setVisible(true);
				break;
			}else{
				this.setVisible(false);
			}
		}
		this.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
				if(level!=null){
					game.defense.setLevel(level);
					game.setScreen(game.defense);
					System.out.println("Your planet is " + level.planet+" done "+level.don);
				}
	        }
	    });
		game.solarMenu.stage.addActor(this);
	}
	
	public void render(float delta){
		if(this.isVisible()){
			this.setPosition(game.solarMenu.actor[num].localToStageCoordinates(new Vector2(0, 0)).x+
					game.solarSystem.planets.get(num).radius,
					game.solarMenu.actor[num].localToStageCoordinates(new Vector2(0, 0)).y);
		}
	}
	
}
