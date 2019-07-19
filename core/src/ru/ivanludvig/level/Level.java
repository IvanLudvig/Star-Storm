package ru.ivanludvig.level;

import ru.ivanludvig.starstorm.Gam;

public class Level {
	
	Gam game;
	public int goal;
	public int planet;
	public int reward;
	public boolean don = false;

	public Level(Gam gam){
		game = gam;
		goal = scoreGen();
		planet = (int) (((Math.random()*game.solarSystem.count()))+1);
		reward = rewardGen();
		System.out.println("goal: "+goal+" reward: "+reward);
		for(int g=0; g<20; g++){
			System.out.println(g+" goal: "+(int)(Math.sqrt(2*g+1)*(20+g))+" reward: "+Math.abs((g+1)*(20+(g/2))));
		}
	}
	
	public void render(){
		if(game.defense.score>=goal){
			done();
		}
	}
	
	
	int y = 0;
	
	public void done(){
		if(y==0){
			reward = rewardGen();
			game.defense.ui.comUI();
			game.solarSystem.done(this);
			don = true;
			y=1;
		}
	}
	
	public int planetNum(){
		return game.solarSystem.planets.get(planet-1).v;
	}
	public int rewardGen(){
		return Math.abs((game.solarSystem.completed+1))*(20+(game.solarSystem.completed/2));
	}
	
	public int fullReward(){
		return reward+(game.defense.sputnik.gameammo/10)+((int)game.defense.planet.gamehp/20);
	}
	
	public int dustGen(){	
		return (game.solarSystem.completed+1)*20;
	}
	
	public int scoreGen(){
		return (int)(Math.sqrt(2*game.solarSystem.completed+1)*(20+game.solarSystem.completed));
	}
}
