package ru.ivanludvig.planets;

import java.util.Arrays;

import ru.ivanludvig.level.Level;
import ru.ivanludvig.ship.Stats;
import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

public class SolarSystem {
	
	Gam game;
	Star star;
	public Array<Planet> planets = new Array<Planet>(210);
	public Array<Level> levels = new Array<Level>(50);
	public Array<ParticleEffect> effects = new Array<ParticleEffect>(50);
	String ships;
	Label label;
	Stage stage;
	public int completed = 0;
	public int money = 500;
	public int maxplanet = 1;
	
	public SolarSystem(Gam gam){
		this.game = gam;
		star = new Star(game);
		completed = 0;
		stage = new Stage(game.uiviewport);
		label = new Label(money+"", game.skin);
		label.setPosition(750, 450);
		stage.addActor(label);
		get();
		System.out.println("ships: "+ships);
		System.out.println("completed: "+completed);
	}
	
	public void create(){
		if(planets.size>0){
			//while(levels.size<1*Math.random()*planets.size){
				//levels.add(new Level(game));
				//effects.add(new ParticleEffect((ParticleEffect)game.manager.get("asteroids/effect-up.p")));
				//effects.get(effects.size-1).scaleEffect(0.5f);
			//}
			System.out.println("oh wow");
			if(levels.size<2){
				levels.add(new Level(game));
				effects.add(new ParticleEffect((ParticleEffect)game.manager.get("asteroids/effect-up.p")));
				effects.get(effects.size-1).scaleEffect(0.5f);
			}
		}
		//game.defense.setLevel(levels.random());
	}
	
	public void render(){
		stage.act();
		stage.draw();
		if(!label.getText().toString().equals(money+"")){
			label.setText(money+"");
		}
	}
	
	public void renderSun(float delta, SpriteBatch batch, Vector2 pos){
		game.tweenManager.update(delta);
		star.draw(delta, batch, pos);
		for(Level level : levels){
			effects.get(levels.indexOf(level, true))
			.setPosition(planets.get(level.planet-1).stagePos.x,
					(planets.get(level.planet-1).stagePos.y));
			effects.get(levels.indexOf(level, true)).draw(batch, delta);
		}
	}
	
	public void done(Level level){
		//effects.removeIndex(levels.indexOf(level, true));
		levels.removeValue(level, true);
		plus(level.reward+(game.defense.sputnik.gameammo/10)+((int)game.defense.planet.gamehp/20));
		create();
		completed++;
		game.prefs.putInteger("completed", completed);
		game.prefs.flush();
		maxplanet = (int)Math.sqrt(completed);
	}
	
	public int count(){
		return planets.size;
	}
	
	public void get(){
		String string;
		string = game.prefs.getString("solar-system");
		int order = 0;
		if(!string.isEmpty()){
			string = string.replaceAll("[^0-9]+", " ");
			System.out.println(Arrays.asList(string.trim().split(" ")));
			for(String s:Arrays.asList(string.trim().split(" "))){
				int f = Integer.parseInt(s);
				order++;
				planets.add(new Planet(game, f, order));
			}
		}
		completed = game.prefs.getInteger("completed");
		if(game.prefs.getInteger("money")!=0){
			money = game.prefs.getInteger("money");
		}else{
			if(completed!=0){
				money = game.prefs.getInteger("money");
			}else{
				money = 500; //TEMP TEMP TEMP TEMP TEMP
			}
		}
		if(game.prefs.getInteger("shipnum")!=0){
			game.shipnum = game.prefs.getInteger("shipnum");
		}else{
			game.shipnum = 1;
		}
		if(game.prefs.getInteger("my")==0){
			money = 500;
			game.prefs.putInteger("my", 1);
		}
		if(!game.prefs.getString("ships").isEmpty()){
			ships = game.prefs.getString("ships");
		}else{
			ships = "1 ";
		}
		game.prefs.putInteger("money", money);
		maxplanet = (int)Math.sqrt(completed)+1;
	}
	
	public void reset(){
		game.prefs.clear();
		game.prefs.flush();
	}
	String string;
	public void save(){
		string = "";
		for(Planet p : planets){
			string+=p.v+" ";
		}
		game.prefs.putString("solar-system", string);
		game.prefs.putInteger("completed", completed);
		game.prefs.putInteger("money", money);
		game.prefs.putString("ships", ships);
		game.prefs.flush();
	}
	
	public void addPlanet(int i){
		Planet planet = new Planet(game, i, planets.size+1);
		planets.add(planet);
		save();
		create();
	}
	
	public void addShip(int i){
		minus(Stats.price[i-1]);
		ships+=i+" ";
		save();
	}
	
	public boolean hasShip(int i){
		return ships.contains(i+"");
	}
	
	public int upPoints(int n, int what){
		switch(what){
		case 1:
			System.out.println((int)planets.get(n).addHp+1);
			return (int)Math.sqrt(planets.get(n).addHp)+1;
		case 2:
			return (int)Math.sqrt(planets.get(n).addGrv)+1;
		case 3:
			return (int)Math.sqrt(planets.get(n).addRes)+1;
		default:
			return 1;
		}
	}
	public void upgrade(int n, int what){
		switch(what){
		case 1:
			if(money>=planets.get(n).hpCost(game.solarSystem.upPoints(n, 1))){
				minus(planets.get(n).hpCost(game.solarSystem.upPoints(n, 1)));
				planets.get(n).plusHp(upPoints(n, what));
			}
			break;
		case 2:
			if(money>=planets.get(n).grvCost(game.solarSystem.upPoints(n, 2))){
				minus(planets.get(n).grvCost(game.solarSystem.upPoints(n, 2)));
				planets.get(n).plusGrv(upPoints(n, what));
			}
			break;
		case -2:
			if(planets.get(n).getGrv()>upPoints(n, 2)){
				if(money>=planets.get(n).grvCost(game.solarSystem.upPoints(n, 2))){
					minus(planets.get(n).grvCost(game.solarSystem.upPoints(n, 2)));
					planets.get(n).minusGrv(upPoints(n, 2));

				}
			}
			break;
		case 3:
			if(money>=planets.get(n).resCost(game.solarSystem.upPoints(n, 3))){
				minus(planets.get(n).resCost(game.solarSystem.upPoints(n, 3)));
				planets.get(n).plusRes(upPoints(n, what));
			}
			break;
		}
		game.solarMenu.windowui.update();
		//game.solarMenu.newMenu();
	}
	public void plus(int a){
		money+=a;
		game.prefs.putInteger("money", money);
		game.prefs.flush();
	}
	
	public void minus(int a){
		money-=a;
		game.prefs.putInteger("money", money);
		game.prefs.flush();
	}
	public void dispose(){
	}

}
